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
        document.getElementById("studentList").style.visibility = "visible";
        $('#studentList thead').append('<tr><th>' + "Roll Number" + '</th><th>' + "Student Name" + '</th><th>' + "Fee Entry" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            studentList[i]= data.stuList[i]
            var ide= data.stuList[i].id
            $('#studentList tbody').append('<tr></td><td>' + data.stuList[i].rollNo + '</td><td>' + data.stuList[i].studentName
                + '</td><td><button id="fee" onclick="openPopUp('+ data.stuList[i].id +')">Fee Entry</button></td></tr>')
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
    }else{
        $('#programl').hide();
        $('#programv').hide();
    }

    if(op=="By Study Centre"){
        $('#studyCentrel').show();
        $('#studyCentrev').show();
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
          $('#currentStudentId').attr('value', studentList[i].id)
          if(i+1<studentList.length){

          $('#nextStudentId').attr('value', studentList[i+1].id)
          }
          else{
//              alert('in first else'+i)
              $('#nextStudentId').attr('value', studentList[0].id)
          }
          if(i-1>=0){
//              alert('in second if'+i)
          $('#previousStudentId').attr('value', studentList[i-1].id)
          }
          else{
//              alert('in second else'+i)
              $('#previousStudentId').attr('value', studentList[studentList.length-1].id)
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

             delete studentList[current-1]

            console.log("---------------------------------------"+studentList)
            for(var i=0; i< studentList.length; i++){
            if(studentList[i]){
            $('#createFeeDetail').trigger("reset");
                $( "#next" ).click();
                break;
            }

            }
            $( "#dialog" ).dialog('close');


        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('div#responseMessage').html(textStatus);
            console.log("response in error")
        }

    });
    }
    else{
        return false;
    }


}






