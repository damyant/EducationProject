
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
            $.ajax({
                type: "post",
                url: url('admin', 'generateRollIsAllow', ''),
                success: function (data) {
                    if (data.status) {
                        generateRollNo(this.value)
                    }
                    else {
                            alert("Roll No Generation Date has Expired.")
                            $('#generateRollNo').reset();
                    }
                }
            })
//            document.forms["generateRollNo"].submit();
        }
        else {
            alert("Select the student first.");
            return false;
        }
    });




$("#feeType").append('<option value="0">' + "Admission Fee" + '</option>')


});


function a(id) {
    window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' +id);
}

function saveExamDate(){
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

function viewStudentByRollNo(){
    var rollNo= $("#StudentRollNo").val()
   if(rollNo.length==8){
    window.location.href = '/UniversityProject/student/updateStudent?rollNo=' + rollNo;
    }
    else{
    alert("Please enter 8 digit roll number!")
    }

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
        data: {sessionId: $('#SessionList').val(),sessionTypeId:$("#sessionType").val()},
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

        $("#subjectList").append('<tr><th>'+"Term"+ obj.semesterNoList[i][0].semesterNo+" Courses" +'</th><th>Examination Date</th><th>Examination Time</th></tr>' )
        for(var j=0;j<obj.allSubjects[i].length;j++){
            subjectIdList[counter]=obj.allSubjects[i][j].id
            var datesInNewFormat="",examTime=""

            if(obj.dateList[counter]!=undefined && obj.dateList[counter].toString()!='noo' ){
            var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString());
            datesInNewFormat = $.datepicker.formatDate( "dd/mm/yy", d);
            }

            if(obj.allSubjects[i][j].toString()!=null){
               examTime=obj.examTimeList[i][j]

            }


            $("#subjectList").append('<tr id="subjectRows'+counter+'"><td class="university-size-1-3">'+obj.allSubjects[i][j].subjectName+'</td><td class="university-size-1-3">'+
                '<input type="text"  name="examinationDate" id="examDate'+counter+'"  onchange="clearError(this)" class="datePickers university-size-1-2 "  value='+datesInNewFormat+'></input><label id="dateError'+counter+'" class="error3">&nbsp;</label></td>'+
                '<td class="university-size-1-3"><input type="text" id="examTime'+counter+'"  onchange="clearError(this)"  name="examinationTime" style="width: 70px;" class="timePicker_6" value="'+examTime+'" /><label id="timeError'+counter+'" class="error4">&nbsp;</label></td>'+
                '</tr>')
            ++counter;
        }
        count++;
    }
    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" class="university-button" value="Submit" onclick="saveExamDate()"/></td></tr>' )
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
//function validateFields(counter){
//    var date=null;
//    var time = null;
//    var bool = true;
//    for(var i=0;i<counter;i++){
//            date = $('#examDate'+i).val();
//            time = $('#examTime'+i).val()
//            if ((date == "null" || date.length == 0)) {
//                $('#dateError'+i).text("Please Select Examination Date")
//                bool=false
//            }
//            if ((time == "null" || time == "")) {
//                $('#timeError'+i).text("Please Select Examination Time")
//                bool=false
//            }
//    }
//    if(bool){
//        submitExamDate();
//    }
//    return bool;
//}

function checkTimeFormat(count){

    re = /^\d{1,2}:\d{2}([ap]m)?$/;

    var val =$('#examTime'+count).val() ;

    if(val != '' && !val.match(re)) {
//        alert("Invalid time format: " + val);
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
                $('#studentList thead').append('<tr><th>' + "Student Name" + '</th><th>' + "Date of Birth" + '</th><th>' + "Gender" + '</th><th>' + "Roll Number" + '</th><th>' + "Mobile No" + '</th><th>&nbsp;</th><th>&nbsp;</th></tr>')
                for (var i = 0; i < data.length; i++) {
                    $('#studentList tbody').append('<tr><td>' + data[i].firstName+' '+data[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data[i].dob)) + '</td><td>' + data[i].gender + '</td><td>' + data[i].rollNo + '</td><td>' + data[i].mobileNo + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data[i].id + ')"/></td><td style="text-align: center;"><input type="button" class="university-button"  value="Update" onclick="updateStudent(' + data[i].id + ')"/></td></tr>')
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
    window.location.href = '/UniversityProject/student/viewStudent?studentId=' + data;
}
function updateStudent(studentId){
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
                for (var i = 0; i < data.length-1; i++) {
                    $("#session").append('<option value="' + data[i].sessionOfProgram + '">' + data[i].sessionOfProgram + '</option>')
                }
            }
        });
    }
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

   var programId = $("#programDetail").val()
    alert(programId)

    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#createNewFee").serialize()+"&feeTypeList="+feeTypeList+"&programDetail="+programId,

        success: function (data) {
            if(data.status){
                $('#createNewFee')[0].reset();
                document.getElementById("statusMessage").style.visibility = "visible";
                $('#statusMessage').html("Saved Successfully")
            }
        }

    })

}

function updateProgramFee(){

   var programId = $("#programId").val()
    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#updateFee").serialize()+"&feeTypeList="+feeTypeList+"&programDetail="+programId,

        success: function (data) {
            if(data.status){
                $('#updateFee')[0].reset();
                document.getElementById("statusMessage").style.visibility = "visible";
                $('#statusMessage').html("Updated Successfully")
            }
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
    $('input[name="studentCheckbox"]:checked').each(function() {
        selectedStudentId.push($(this).attr('id'));
    });
    $("#studentListId").val(selectedStudentId)

    if(selectedStudentId!=null){
        $("#challanForStudyCenter").submit()
      }
    }
    else{

    }
}





function showStudents(){


    $.ajax({
        type: "post",
        url: url('admin', 'searchByChallanNo', ''),
        data: 'challanNo='+$('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for(var i=0;i<data.stuList.length;i++){
            $("#scStudnetList tbody").append('<tr><td>'+data.stuList[i].firstName+' &nbsp;' +data.stuList[i].lastName+'</td><td><input type="text" name="rollNo'+i+'" value="'+data.stuList[i].rollNo+'"</td><td>'+data.courseNameList[i]+'</td><td>'+data.courseFee[i]+'</td></tr>')
            }
        }

    })

}
function showListOfStudents(){
    document.getElementById("studentPayList").style.visibility = "visible";
    document.getElementById("paySubmit").style.visibility = "visible";
    $.ajax({
        type: "post",
        url: url('admin', 'searchListStudentByChallanNo', ''),
        data: 'challanNo='+$('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for(var i=0;i<data.stuList.length;i++){
                $("#scStudnetList tbody").append('<tr><td>'+data.stuList[i].firstName+' &nbsp;' +data.stuList[i].lastName+'</td><td><input type="text" readonly name="rollNo'+i+'" value="'+data.stuList[i].rollNo+'"/></td><td>'+data.courseNameList[i]+'</td><td>'+data.courseFee[i]+'</td></tr>')
            }
        }
    })
}

function showMiscFeeListOfStudents(){
    document.getElementById("studentPayList").style.visibility = "visible";
    document.getElementById("paySubmit").style.visibility = "visible";
    $.ajax({
        type: "post",
        url: url('admin', 'searchMiscFeeListByChallanNo', ''),
        data: 'challanNo='+$('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for(var i=0;i<data.stuList.length;i++){
                $("#scStudnetList tbody").append('<tr><td>'+data.stuList[i].firstName+' &nbsp;' +data.stuList[i].lastName+'</td><td><input type="text" name="rollNo'+i+'" value="'+data.stuList[i].rollNo+'"</td><td>'+data.courseNameList[i]+'</td><td>'+data.courseFee[i]+'</td></tr>')
            }
        }
    })
}

function checkChallan(challan){
    if(challan.length!=10){
        return false
    }
}
function populateChallanDetail(){
    var challanNo=$("#payInSlipNo").val();
    if(challanNo.length==10){
//    alert("?????????????")
    $.ajax({
        type: "post",
        url: url('admin', 'getChallanDetailsforStudent', ''),
        data: {challanNo: challanNo},

        success: function (data) {
            if(data.stuList) {
                console.log("error")
                $("#allStudentList tbody").empty().append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Bank</th><th>Branch</th><th>Amount</th></tr>')
                for (var i = 0; i < data.stuList.length; i++) {
                    $("#allStudentList tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.stuList[i].id + '"/> ' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td>' + data.stuList[i].rollNo + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.bank + '</td><td>' + data.branch + '</td><td>' + data.courseFee[i] + '</td></tr>')
                }
                $("#allStudentList tbody").append('<tr><td><input type="button" value="Approve" onclick="submitStudents()"/> </td></tr>')
                $("#error").hide()
            }else{
                console.log("error")
                $("#error").show()
            }
        }
    });
    }else{
        alert("please enter 10 digit valid challan number")
        return false
    }
}

function submitStudents(){
    $("#approvePayInSlip").submit()
}

$( document ).ready(function() {
   $("#rollNoGenerationDate").ready(function(){
       $("#startD").datepicker({
           changeMonth: true,
           changeYear: true,
           dateFormat: "dd/mm/yy",
           minDate: 0
       });
       $("#endD").datepicker({
           changeMonth: true,
           changeYear: true,
           dateFormat: "dd/mm/yy",
           minDate: 0
       });
   })
});



function studentForStudyMaterial(){
    var result = $('#studyMaterialPage').valid()

    $.ajax({
        type: "post",
        url: url('admin', 'getStudentForStudyMaterial', ''),
        data: $("#studyMaterialPage").serialize(),

        success: function (data) {

            if(data.studentList) {
                $("#msgDiv").html(" ")
                $("#studentRecord tbody").empty().append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th></tr>')
                for (var i = 0; i < data.studentList.length; i++) {
                    $("#studentRecord tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.studentList[i].id + '"/> ' + data.studentList[i].firstName + ' &nbsp;' + data.studentList[i].lastName + '</td><td>' + data.studentList[i].rollNo + '</td><td>' + data.courseDetail[0].courseName + '</td></tr>')
                    $("#studentRecord tbody").append('<tr><th colspan="3">Current Semester Courses</th></tr>')

                    for(var j=0;j<data.subjectsList[i].length;j++){

                    $("#studentRecord tbody").append('<tr><td><input type="checkbox" name="subjectCheckBox" id="'+data.subjectsList[i][j].id+'" value="'+data.subjectsList[i][j].id+'" /></td><td>'+data.subjectsList[i][j].subjectName+'</td></tr>')
                    }
                }

                $("#studentRecord tbody").append('<tr><td><input type="button" value="Assign Study Material" onclick="assignStudyMaterial()"/> </td></tr>')

                for(var k=0;k<data.assignedStudyMaterail.length;k++){
                  $("#"+data.assignedStudyMaterail[k].id).attr('checked',true)
                }
                $("#error").hide()
            }else{
                $("#error").show()
            }
        }
    });


}


function assignStudyMaterial(){
        var subjectList=[]
    if ($("input[name=subjectCheckBox]:checked").length != 0) {

        $.ajax({
            type: "post",
            url: url('admin', 'saveStudyMaterial', ''),
            data: $("#studyMaterialPage").serialize(),
            success: function (data) {
                if (data.status=='true') {
                    $("#studentRecord tbody tr").remove()
                    $("#studyMaterialText").val('')
                        $("#msgDiv").html("Study Material has been assigned to student")
                }
                else {
                    alert("There is some problem in assigning Study Material")

                }

            }
        })

    }
    else {
        alert("Select the Subject first.");
        return false;
    }

}
