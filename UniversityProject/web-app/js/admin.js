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


function getStudents() {

    $.ajax({
        type: "post",
        url: url('admin', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), pageType: $('#pageType').val()},
        success: function (data) {
            //document.location.reload();
//           showStudyCenterList()
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
