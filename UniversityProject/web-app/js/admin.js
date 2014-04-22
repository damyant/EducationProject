
var studentIdList = [];
var subjectIdList=[];
$(document).ready(function () {
        $("#submit").click(function(){
            var rollNo = $("#rollNo").val()
            var feeType = $("#type").val()
            if(rollNo.length==0){
                $("#rollNo").after('<label class="error">Please Enter Roll Number</label>')
                return false
            }
            if(feeType.length==0){
                $("#type").after('<label class="error">Please Select Fee Type</label>')
                return false
            }


            window.open('/UniversityProject/admin/generateFeeVoucher/?rollNo='+rollNo+'&feeType='+feeType);
        });



    $(document).on('click', '#assignRollNo', function () {

        if ($("input[name=rollno_checkbox]:checked").length != 0) {
            $("input[name=rollno_checkbox]:checked").each(function (i) {

                if ($(this).attr("checked", true)) {
                    studentIdList[i] = $(this).attr("id");
                    $("#studentId").val(studentIdList);
                }

            })
            generateRollNo(this.value)
//            document.forms["generateRollNo"].submit();
        }
        else {
            alert("Select the student first.");
            return false;
        }
    });






});


function submitExamDate(){
    alert("submit")
    var course=$('#programList').val();
    $.ajax({
        type: "post",
        url: url('admin', 'saveExamDate', ''),
        data: $('#assignDate').serialize()+'&subjectIdList=' + subjectIdList,
        success: function (data) {
            if(data.saveFlag==true){

//                    $("#subjectList tr").remove()
                $("#successMessage").html("Examination Date is saved")
                setTimeout(function(){  $('#successMessage').hide(); }, 8000);
                $("html, body").animate({ scrollTop: 0 }, "slow");


            }
            else{
                $("#successMessage").html("")
            }


        }
    });

}
function getStudents() {

    $.ajax({
        type: "post",
        url: url('admin', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), pageType: $('#pageType').val()},
        success: function (data) {
            //document.location.reload();
//           getSemesterAndSubjectListStudyCenterList()
            appendTable(data)


        }
    });

}

function enableProgram(t) {
    var op = $(t).val();
    if (op != 'null') {
        $('#programId').prop('disabled', false);
    } else {
        $('#programId').prop('disabled', true);
    }
}
function toggleChecked(status) {
    $(".checkbox").each(function () {
        $('input:checkbox:not(:disabled)').attr("checked", status)
    })
}

function generateRollNo(value) {

    $.ajax({
        type: "post",
        url: url('admin', 'generateRollNo', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), studentList: $("#studentId").val(), pageType: value},
        success: function (data) {
            appendTable(data)

        }
    });

}


function appendTable(data) {


    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    if (data.stuList.length > 0) {
        $('#msg').html("")
        document.getElementById("studentList").style.visibility = "visible";
        $('#studentList thead').append('<tr><th><input type="checkbox" name="chkbox" onchange="toggleChecked(this.checked)"/> <label for="chkbox">Select All</label> </th><th>' + "Student Name" + '</th><th>' + "Reference Number" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data.stuList[i].id + '"/></td><td>' + data.stuList[i].name + '</td><td>' + data.stuList[i].referenceNumber + '</td></tr>')
        }
        $('#studentList tbody').append('<tr><td colspan="3"><input type="button" value="' + data.label + '" id="assignRollNo"></td></tr>')

    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }
}

function getSemesterAndSubjectList(){

    $.ajax({
        type: "post",
        url: url('admin', 'getSubjectList', ''),
        data: {programId: $('#programList').val()},
        success: function (data) {

            if(data.noSubjects==true){

                $("#subjectList tr").remove()
                $("#msgDiv").html("The is no subjects associated with the program")
            }
            else{

            appendSubjects(data)
                $("#msgDiv").html("")
            }


        }
    });

}

function appendSubjects(obj){

    var count=1;
    var counter=0;


    for(var i=0;i<obj.allSubjects.length;i++){

        $("#subjectList").append('<tr><th>'+"Term"+ count+" Subjects" +'</th><th>Examination Date</th><th>Examination Time</th></tr>' )
        for(var j=0;j<obj.allSubjects[i].length;j++){

            subjectIdList[counter]=obj.allSubjects[i][j].id

            var datesInNewFormat=""
            if(obj.dateList[counter]!=undefined && obj.dateList[counter].toString()!='noo' ){

            var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString())

            datesInNewFormat = $.datepicker.formatDate( "dd/mm/yy", d);
            }

            $("#subjectList").append('<tr id="subjectRows'+j+'"><td class="university-size-1-3">'+obj.allSubjects[i][j].subjectName+'</td><td class="university-size-1-3">'+
                '<input type="text" id="examDate'+j+'" name="examinationDate"  class="datepicker university-size-1-2 "  value='+datesInNewFormat+'></input><label id="dateError" class="error3">&nbsp;</label></td>'+
                '<td class="university-size-1-3"> <input type="text" id="examTime'+j+'" name="examinationTime" style="width: 70px;" class="timepicker_6" value="'+obj.allSubjects[i][j].examTime+'" /><label id="timeError" class="error4">&nbsp;</label></td>'+
                '</tr>')
           ++counter
        }
        count++;

    }


    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" value="Submit" onclick="validateFields('+counter+')"></td></tr>' )

    $(".datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy"
    });
    $('.timepicker_6').timepicker({
        showPeriod: true,
        showLeadingZero: true
    });
}

function validateFields(counter){
    var date=null;
    var time = null;
    var bool = false;
    for(i=0;i<counter;i++){

        date = $('#subjectList').find('#subjectRows'+i).find('#examDate'+i).val()
        time = $('#subjectList').find('#subjectRows'+i).find('#examTime'+i).val()

       if((date=="null" || date.length==0)) {
            $('#subjectList').find('#subjectRows'+i).find('.error3').text("Please Select Examination Date")
            bool= false;
        }else{
           bool=true
       }
       if((time=="null" || time=="")){
          $('#subjectList').find('#subjectRows'+i).find('.error4').text("Please Select Examination Time")
       }else{
           bool=true
       }

    }
    if(bool){
        submitExamDate();

    }
        return bool;
   }

function checkTimeFormat(count){

    re = /^\d{1,2}:\d{2}([ap]m)?$/;

    var val =$('#examTime'+count).val() ;

    if(val != '' && !val.match(re)) {
        alert("Invalid time format: " + val);
        form.timeVal.focus();
        return false;
    }

//            alert("All input fields have been validated!");
            return true;

}

function saveExamVenue(){
    var venueList=[]
    if($("#addExamCentre").html()==""){
        alert("Please assign at least one venue");
        return false
    }


    $('#addExamCentre option').each(function () {
        venueList.push($(this).val() || '');

    });

    $.ajax({
        type: "post",
        url: url('admin', 'saveExamVenue', ''),
        data: $("#assignExamVenue").serialize()+"&venueList="+venueList,
        success: function (data) {
            $('#assignExamVenue')[0].reset();
            $('#courseForExamVenue').html('');
            $('#CentreForExamVenue').html('');
            $('#examCenterList').empty();
            $('#addExamCentre').empty();
            $('#successMessage').html('Successfully Assigned Examination Venue');
            setTimeout(function(){  $('#successMessage').hide(); }, 8000);
//            if(data.noSubjects==true){
//                $("#subjectList tr").remove();
//                $("#msgDiv").html("The is no subjects associated with the program")
//
//            }
//            else{
//                appendSubjects(data);
//                $("#msgDiv").html("")
//                alert(data)
//            }
        }
    });
}

function generateStudentsList() {

    $.ajax({
        type: "post",
        url: url('admin', 'generateStudentList', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val()},
        success: function (data) {
            $('#studentList thead tr').remove()
            $('#studentList tbody tr').remove()
            if (data.length>0) {
//                alert(data.length);
                $('#msg').html("");
                document.getElementById("studentList").style.visibility = "visible";
                $('#studentList thead').append('<tr><th>' + "Student Name" + '</th><th>' + "Date of Birth" + '</th><th>' + "Gender" + '</th><th>' + "Roll Number" + '</th><th>' + "Mobile No" + '</th><th>&nbsp;</th></tr>')
                for (var i = 0; i < data.length; i++) {
                    $('#studentList tbody').append('<tr><td>' + data[i].name + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data[i].dob)) + '</td><td>' + data[i].gender + '</td><td>' + data[i].rollNo + '</td><td>' + data[i].mobileNo + '</td><td style="text-align: center;"><input type="button" class="university-button" value="View" onclick="viewStudent(' + data[i].id + ')"/></td></tr>')
                }

            }
            else {
                document.getElementById("studentList").style.visibility = "hidden";
                $('#msg').html("<div class='university-status-message'>No Students Found</div>")
            }

        }
    });

}
function viewStudent(studentId){
    var data = studentId
    window.location.href = '/UniversityProject/student/registration?studentId=' + data;
}
