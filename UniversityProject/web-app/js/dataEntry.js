/**
 * Created by shweta on 3/26/14.
 */


var studentIdList = [];
$(document).ready(function () {

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

function getStudentsList() {

    var date = $('#admissionDate').val()
    alert(date)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCentre').val(), programId: $('#programId').val(), admissionDate: $('#admissionDate').val(),pageType: $('#pageType').val()},

        success: function (data) {
            //document.location.reload();
//           showStudyCenterList()
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
        $('#studentList thead').append('<tr><th><input type="checkbox" name="chkbox" onchange="toggleChecked(this.checked)"/> <label for="chkbox">Select All</label> </th><th>' + "Roll Number" + '</th><th>' + "Student Name" + '</th><th>' + "Fee Entry" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data.stuList[i].id + '"/></td><td>' + data.stuList[i].rollNo + '</td><td>' + data.stuList[i].name + '</td><td><input type="submit" value="Update" class="university-button" onclick="enterFeeDetails(' + data.stuList+ ')"/></td></tr>')
        }
//        $('#studentList tbody').append('<tr><td colspan="3"><input type="button" value="' + data.label + '" id="assignRollNo"></td></tr>')

    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }
}


function enterFeeDetails(value) {

    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter';
}

function enablecriteria(t) {
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove();
    var op = $(t).val();
    alert(">>>>>>>>option"+op)

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




//    if (op != 'null') {
//        $('#centreId').prop('disabled', false);
//    } else {
//        $('#centreId').prop('disabled', true);
//    }
//
//    if (op != 'null') {
//        $('#programId').prop('disabled', false);
//    } else {
//        $('#programId').prop('disabled', true);
//    }
}
function toggleChecked(status) {
    $(".checkbox").each(function () {
        $('input:checkbox:not(:disabled)').attr("checked", status)
    })
}


