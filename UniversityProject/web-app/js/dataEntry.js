/**
 * Created by shweta on 3/26/14.
 */


var studentList = [];
// document ready function............................................
$(function() {
    $("#dialog").dialog({
        autoOpen: false,
//        maxWidth:600,
//        maxHeight: 500,
        width: 1000,
        height: 650,
        modal: true,
        title:'Enter Fee Details',
        close: function(ev, ui) { getStudentsList()}

    });
});

function nextStudent(){

    var nextValue = $('#nextStudentId').val()
//    alert("Next button clicked-----------"+nextValue)
    openPopUp(nextValue)
}
function previousStudent(){
    var previousValue = $('#previousStudentId').val()
//    alert("hello------------------------------"+previousValue)
    openPopUp(previousValue)
}



function getStudentsList() {

    var date = $('#admissionDate').val()
//    alert(date)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCentre').val(), programId: $('#programId').val(), admissionDate: $('#admissionDate').val(),pageType: $('#pageType').val()},

        success: function (data) {

            appendDataTable(data)


        }
    });

}

function appendDataTable(data) {


    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    if (data.stuList.length > 0) {
        $('#msg').html("")
        $('#responseMessage').html("")

        document.getElementById("studentList").style.visibility = "visible";
        $('#studentList thead').append('<tr><th>' + "Roll Number" + '</th><th>' + "Student Name" + '</th><th>' + "Fee Entry" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            studentList[i]= data.stuList[i]
            var ide= data.stuList[i].id

            $('#studentList tbody').append('<tr></td><td>' + data.stuList[i].rollNo + '</td><td>' + data.stuList[i].studentName
                + '</td><td><button id="fee" onclick="openPopUp('+ data.stuList[i].id+')">Fee Entry</button></td></tr>')

    }
    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }

}


function enablecriteria(t) {
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove();
    var op = $(t).val();


    if (op == 'By Program') {
        //  $('#programId').prop('disabled', false);
        $('#programl').show();
        $('#programv').show();
//        $('#studyCentre').val('')
    }else{
        $('#programl').hide();
        $('#programv').hide();
    }

    if(op=="By Study Centre"){
        $('#studyCentrel').show();
        $('#studyCentrev').show();
//        $('#programId').val('')
    }else{
        $('#studyCentrel').hide();
        $('#studyCentrev').hide();
    }

    if(op=="By Admission Date"){
        $('#admissionDatel').show();
        $('#admissionDatev').show();
    }else{
        $('#admissionDatel').hide();
        $('#admissionDatev').hide();
    }


}



function toggleChecked(status) {
    $(".checkbox").each(function () {
        $('input:checkbox:not(:disabled)').attr("checked", status)
    })
}

function openPopUp(studentId){


    $( "#dialog" ).dialog('open');
    for(var i=0 ; i<studentList.length;i++){
        if(studentList[i]){
            if(studentList[i].id==studentId){
                $('#rollNo').val(studentList[i].rollNo)
                $('#currentStudentId').val( studentList[i].id)
                if(i+1<studentList.length){

                    $('#nextStudentId').val(studentList[i+1].id)
                }
                else{
//              alert('in first else'+i)
                    $('#nextStudentId').val(studentList[0].id)
                }
                if(i-1>=0){
//              alert('in second if'+i)
                    $('#previousStudentId').val( studentList[i-1].id)
                }
                else{
//              alert('in second else'+i)
                    $('#previousStudentId').val( studentList[studentList.length-1].id)
                }
                break;
            }
        }
    }
}


function submitFeeDetail(){
   validate();
    var result= $('#createFeeDetail').valid()
    if(result){
    $.ajax({
        type: "post",
        url: url('feeDetails', 'saveBulkFeeDetails', ''),
        async: false,
        data: $('#createFeeDetail').serialize(),
        success: function (response) {
            $('div#responseMessage').html(response);
            var current = $('#currentStudentId').val()
//            alert("value of current is "+current)
            for(var i=0; i< studentList.length; i++){
                if(studentList[i]){
                if(studentList[i].id==current){
                    delete studentList[i]
                    break;
                }
              }
            }



            console.log("---------------------------------------"+studentList)
            for(var i=0; i< studentList.length; i++){
                if(studentList[i]){
                    $('#createFeeDetail').trigger("reset");
//                    alert("Fill Fee Details For Next Student")
                    $( "#next" ).click();
                    break;
                }

            }
            $( "#dialog" ).dialog('close');


        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('div#responseMessage').html("Fee details cannot be saved ");
            console.log("response in error")
        }

    });
    }
    else{
        return false;
    }

}
function populateStudents(object){
    var programId=$(object).val();
    if(programId) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'populateStudents', ''),
            data: {programId:programId},
            success: function (data) {
                 for( var i=0;i<data.studentList.length;i++){

                $("#studyCenterFeeEntryTable tbody").append('<tr><td><input type="text" class="university-size-1-1" name="rollNo" id="rollNo'+i+'" value="'+data.studentList[i].rollNo+'" readonly></td><td>Education fee</td><td></td>' +
                    '<td><select id="paymentMode'+i+'" name="paymentMode"  class="many-to-one university-size-1-2" /></td><td></td><td><select onchange="getBankBranch('+i+')" id="bankName'+i+'" name="bankName" o class="many-to-one university-size-1-2" /></td>' +
                    '<td><select id="branch'+i+'" name="branch" class="many-to-one university-size-1-2" /></td><td><input type="button" value="save" onclick="saveFeeData('+i+')"></td></tr>');

                $("#bankName"+i).empty().append('<option value="">Select Bank Name</option>')
                $("#paymentMode"+i).empty().append('<option value="">Select Payment Mode</option>')
                for(var j=0;j<data.bankName.length;j++){
                    $("#bankName"+i).append('<option value="' + data.bankName[j].id + '">' +data.bankName[j].bankName + '</option>')
                }
                for(var k=0;k<data.paymentMode.length;k++){
                    $("#paymentMode"+i).append('<option value="' + data.paymentMode[k].id + '">' +data.paymentMode[k].paymentModeName + '</option>')
                }
                }

            }
//

        });
    }
}


function getBankBranch(index){

         $.ajax({
            type: "post",
            url: url('feeDetails', 'getBankBranch', ''),
            data: {bankId:$('#bankName'+index).val(),bankId:$('#bankName'+index).val(),bankId:$('#bankName'+index).val()},
            success: function (data) {
                $("#branch"+index).empty().append('<option value="">Select Payment Mode</option>')
                for(var i=0;i<data.length;i++){
                    $("#branch"+index).append('<option value="' + data[i].id + '">' +data[i].branchLocation + '</option>')
                }
            }

        })

}


function saveFeeData(index){
alert("hu")
    $.ajax({
        type: "post",
        url: url('feeDetails', 'saveFeeData', ''),
        data: {rollNo:$('#rollNo'+index).val(),bankId:$('#bankName'+index).val(),paymentModeId:$('#paymentMode'+index).val(),branchId:$('#branch'+index).val()},
        success: function (data) {
            $("#branch"+index).empty().append('<option value="">Select Payment Mode</option>')
            for(var i=0;i<data.length;i++){
                $("#branch"+index).append('<option value="' + data[i].id + '">' +data[i].branchLocation + '</option>')
            }
        }

    })

}





