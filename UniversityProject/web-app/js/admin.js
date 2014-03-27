/**
 * Created by shweta on 3/26/14.
 */


var studentIdList = [] ;
$(document).ready(function() {

    $(document).on('click', '#assignRollNo', function() {

        if ($("input[name=rollno_checkbox]:checked").length != 0) {
            $("input[name=rollno_checkbox]:checked").each(function(i) {

                if ($(this).attr("checked", true)) {
                    studentIdList[i] = $(this).attr("id");
                    $("#studentId").val(studentIdList);
                }

            })
            document.forms["generateRollNo"].submit();
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
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programs').val()},
        success: function (data) {
            //document.location.reload();
//            showStudyCenterList()


            $('#studentList tbody tr').remove()

            if (data.length > 0) {
                document.getElementById("studentList").style.visibility = "visible";
                $('#studentList thead').append('<tr><th><input type="checkbox" onchange="toggleChecked(this.checked)"/></th><th>' + "Student Name" + '</th><th>' + "Reference Number" + '</th></tr>')
                for (var i = 0; i < data.length; i++) {
                    $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data[i].id + '"/></td><td>' + data[i].name + '</td><td>' + data[i].referenceNumber + '</td></tr>')
                }
                $('#studentList tbody').append('<tr><td colspan="3"><input type="button" value="Assign RollNo" id="assignRollNo"></td></tr>')

            }
            else {
                $('#msg').html("No Students Found")
            }
        }
    });

}


function toggleChecked(status) {
    $(".checkbox").each( function() {
        $('input:checkbox:not(:disabled)').attr("checked",status)
    })
}
