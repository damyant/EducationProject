
var studentIdList = [];
var subjectIdList=[],selectedStudentId=[];
var feeTypeList=[];
$(document).ready(function () {

        $("#submit").click(function(){
            var rollNo = $("#rollNo").val()
            var feeType = $("#feeType").val()
//            alert(feeType)
            if(rollNo.length==""){
                $("#rollNo").after('<label class="error">Please Enter Roll Number</label>')
                return false
            }
            if(feeType==""){
                $("#type").after('<label class="error">Please Select Fee Type</label>')
                return false
            }


            window.open('/UniversityProject/admin/generateFeeVoucher/?rollNo='+rollNo+'&feeType='+feeType);
        });



    $(document).on('click', '#assignRollNo', function () {
//        alert("hi")
        if ($("input[name=rollno_checkbox]:checked").length != 0) {
            $("input[name=rollno_checkbox]:checked").each(function (i) {

                if ($(this).attr("checked", true)) {
                    studentIdList[i] = $(this).attr("id");
                    $("#studentId").val(studentIdList);
                }

            })
//            alert(this.value)
            generateRollNo(this.value)
//            document.forms["generateRollNo"].submit();
        }
        else {
            alert("Select the student first.");
            return false;
        }
    });






});


function a(id) {
    window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' +id);
}

function submitExamDate(){
//    alert("submit")
    var course=$('#programList').val();
    $.ajax({
        type: "post",
        url: url('admin', 'saveExamDate', ''),
        data: $('#assignDate').serialize()+'&subjectIdList=' + subjectIdList,
        success: function (data) {
            if(data.saveFlag==true){
               $('#assignDate')[0].reset();
                $('#assignDate').find(':input').each(function() {
                    switch(this.type) {
                        case 'text':
                            $(this).val('');
                            break;
                    }
                });
                $("#successMessage").html("Examination Date is saved")
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
//alert("hi")
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
            $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data.stuList[i].id + '"/></td><td>' + data.stuList[i].firstName+' '+data.stuList[i].lastName + '</td><td>' + data.stuList[i].referenceNumber + '</td></tr>')
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
                $("#msgDiv").html("There is no Course associated with the program")
            }
            else{
                $("#msgDiv").html("")
                appendSubjects(data)
            }
        }
    });
}

function appendSubjects(obj){
    var count=1;
    var counter=0;
    $("#subjectList").empty();
    for(var i=0;i<obj.allSubjects.length;i++){
        $("#subjectList").append('<tr><th>'+"Term"+ count+" Courses" +'</th><th>Examination Date</th><th>Examination Time</th></tr>' )
        for(var j=0;j<obj.allSubjects[i].length;j++){
            subjectIdList[counter]=obj.allSubjects[i][j].id
            var datesInNewFormat=""
//            alert(obj.dateList[counter])
            if(obj.dateList[counter]!=undefined && obj.dateList[counter].toString()!='noo' ){
            var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString());
            datesInNewFormat = $.datepicker.formatDate( "dd/mm/yy", d);
            }
            $("#subjectList").append('<tr id="subjectRows'+counter+'"><td class="university-size-1-3">'+obj.allSubjects[i][j].subjectName+'</td><td class="university-size-1-3">'+
                '<input type="text"  name="examinationDate" id="examDate'+counter+'"  onchange="clearError(this)" class="datePickers university-size-1-2 "  value='+datesInNewFormat+'></input><label id="dateError'+counter+'" class="error3">&nbsp;</label></td>'+
                '<td class="university-size-1-3"><input type="text" id="examTime'+counter+'"  onchange="clearError(this)"  name="examinationTime" style="width: 70px;" class="timePicker_6" value="'+obj.allSubjects[i][j].examTime+'" /><label id="timeError'+counter+'" class="error4">&nbsp;</label></td>'+
                '</tr>')
            ++counter;
        }
        count++;
    }
    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" class="university-button" value="Submit" onclick="validateFields('+counter+')"/></td></tr>' )
    $(".datePickers").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        minDate: 0
    });
    $('.timePicker_6').timepicker({
        showPeriod: true,
        showLeadingZero: true
    });
}
function validateFields(counter){
    var date=null;
    var time = null;
    var bool = true;
    for(var i=0;i<counter;i++){
            date = $('#examDate'+i).val();
            time = $('#examTime'+i).val()
            if ((date == "null" || date.length == 0)) {
                $('#dateError'+i).text("Please Select Examination Date")
                bool=false
            }
            if ((time == "null" || time == "")) {
                $('#timeError'+i).text("Please Select Examination Time")
                bool=false
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
//            setTimeout(function(){  $('#successMessage').hide(); }, 8000);
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
//                alert(data[0].firstName);
                $('#msg').html("");
                document.getElementById("studentList").style.visibility = "visible";
                $('#studentList thead').append('<tr><th>' + "Student Name" + '</th><th>' + "Date of Birth" + '</th><th>' + "Gender" + '</th><th>' + "Roll Number" + '</th><th>' + "Mobile No" + '</th><th>&nbsp;</th></tr>')
                for (var i = 0; i < data.length; i++) {
                    $('#studentList tbody').append('<tr><td>' + data[i].firstName+' '+data[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data[i].dob)) + '</td><td>' + data[i].gender + '</td><td>' + data[i].rollNo + '</td><td>' + data[i].mobileNo + '</td><td style="text-align: center;"><input type="button" class="university-button" value="View" onclick="viewStudent(' + data[i].id + ')"/></td></tr>')
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
function clearError(t) {
    $(t).next("label").text("");

}
function updateFeeType(feeTypeId) {
//    alert(feeTypeId)
    var data = feeTypeId
    window.location.href = '/UniversityProject/programFee/addFeeType?feeTypeId=' + data;

}
function deleteFeeType(feeTypeId) {
    var result = confirm("Are you sure you want to delete this item?", "Confirm Delete");

    if (result == true) {
        var data = feeTypeId;
//        alert(result)
        $.ajax({
            type: "post",
            url: url('programFee', 'deleteFeesType', ''),
            data: {data: data},
            success: function (data) {
                //document.location.reload();
                window.location.href = '/UniversityProject/programFee/viewExistingFeeType';
            }
        });
    }
}
function loadBranch(t){
    var bank=$(t).val();
//    alert(bank)
    if(bank){
        $.ajax({
            type: "post",
            url: url('admin', 'getBranchList', ''),
            data: {bank: bank},
            success: function (data) {
                //document.location.reload();
                $("#branchLocation").empty().append('');
                $("#branchLocation").append('Select Branch');
                for (var i = 0; i < data.length; i++) {
                    $("#branchLocation").append('<option value="' + data[i].id + '">' + data[i].branchLocation + '</option>')
                }
            }
        });
    }
}



function loadSession(t){
    var program=$(t).val();
//    alert(bank)
    if(program){
        $.ajax({
            type: "post",
            url: url('programFee', 'getProgramSession', ''),
            data: {program: program},
            success: function (data) {
                //document.location.reload();
                $("#session").empty().append('');
                $("#session").append('Select Session');
                for (var i = 0; i < data.length; i++) {
                    $("#session").append('<option value="' + data[i].sessionOfProgram + '">' + data[i].sessionOfProgram + '</option>')
                }
            }
        });
    }
}
function populateChallanDetail(t){
    var challanNo=$(t).val();
    $.ajax({
        type: "post",
        url: url('admin', 'getChallanDetails', ''),
        data: {challanNo: challanNo},

        success: function (data) {
            console.log("dsfddsdds"+data.studentInst.rollNo);
            $('#rollNo').val(data.studentInst[0].rollNo);
            $('#feeAmount').val(data.feeAmount[0]);

        }
    });
}

function approvePayInSlip(){
    $.ajax({
        type: "post",
        url: url('admin', 'saveApprovePayInSlip', ''),
        data: {rollNo:$('#rollNo').val(),bankId: 10,paymentModeId: 5, branchId: 21,
            paymentDate:$('#datePick').val(),paymentReferenceNumber:$('#payInSlipNo').val(),feeTypeId:1},
        success: function (data) {
                if(data.flag){
                    $('#rollNo').val('');
                    $('#payInSlipNo').val('');
                    $('#datePick').val('');
                    $('#approvePayInSlip')[0].reset();
                    $('#statusMessage').html("Approved Succesfully")
                }
        }

    })
}

function submitProgramFee(){

alert("hi")
   var programId = $("#programDetail").val()
    alert(programId)

    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#createNewFee").serialize()+"&feeTypeList="+feeTypeList+"&programDetail="+programId,

        success: function (data) {
//            if(data.flag){
//                $('#rollNo').val('');
//                $('#payInSlipNo').val('');
//                $('#datePick').val('');
//                $('#approvePayInSlip')[0].reset();
//                $('#statusMessage').html("Approved Succesfully")
//            }
        }

    })

}

function updateProgramFee(){
    alert("hi")
   var programId = $("#programId").val()
    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#updateFee").serialize()+"&feeTypeList="+feeTypeList+"&programDetail="+programId,

        success: function (data) {
//            if(data.flag){
//                $('#rollNo').val('');
//                $('#payInSlipNo').val('');
//                $('#datePick').val('');
//                $('#approvePayInSlip')[0].reset();
//                $('#statusMessage').html("Approved Succesfully")
//            }
        }

    })

}


function generateChallanForRange(){

    var from=$("#serialNoFrom").val()
    var to = $("#serialNoTo").val()

    if(from!=undefined){
    if(from.length==0){
        alert("Please Enter from Sr No.")
    }
    var selectedRange=0;
    if(to>=from){
        selectedRange = (to-from)
    }else{
        alert("Please enter range correctly")
        return false
    }

    var rangeCount = parseInt(from)+selectedRange;
    for(i=from-1;i<rangeCount;i++)
        $('#studyCenterFeeEntryTable').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', true)

    for(i=to;i<totalRows;i++)
        $('#studyCenterFeeEntryTable').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', false)
    for(i=from-2;i>=0;i--)
        $('#studyCenterFeeEntryTable').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', false)
    $('input:checked').each(function() {
        selectedStudentId.push($(this).attr('id'));
    });
    $("#studentListId").val(selectedStudentId)

    if(selectedStudentId!=null){

      }
    }
    else{
        $("#challanForStudyCenter").submit()
    }
}



function showStudents(){


    $.ajax({
        type: "post",
        url: url('admin', 'searchByChallanNo', ''),
        data: 'challanNo='+$('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for(var i=0;i<data.stuList.length;i++){
            $("#scStudnetList tbody").append('<tr><td>'+data.stuList[i].firstName+' &nbsp;' +data.stuList[i].lastName+'</td><td><input type="text" name="rollNo'+i+'" value="'+data.stuList[i].rollNo+'"</td><td>'+data.courseNameList[i]+'</td><td>'+data.courseFee[i]+'</td></tr>')
            }
        }

    })

}