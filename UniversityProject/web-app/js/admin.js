/**
 * Created by shweta on 3/26/14.
 */

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
                $('#studentList thead').append('<tr><th><input type="checkbox"/></th><th>' + "Student Name" + '</th><th>' + "Reference Number" + '</th></tr>')
                for (var i = 0; i < data.length; i++) {
                    $('#studentList tbody').append('<tr><td><input type="checkbox" id="' + data[i].id + '"/></td><td>' + data[i].name + '</td><td>' + "Reference Number" + '</td></tr>')
                }
                $('#studentList tbody').append('<tr><td colspan="3"><input type="button" value="Assign RollNo"></td></tr>')
            }
            else {
                $('#msg').html("No Students Found")
            }
        }
    });

}
