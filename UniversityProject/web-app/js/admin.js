/**
 * Created by shweta on 3/26/14.
 */

function getStudents(){


    $.ajax({
        type: "post",
        url: url('admin', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCenter').val(),programId:$('#programs').val()},
        success: function (data) {
            //document.location.reload();
//            showStudyCenterList()


            $('#studentList tbody tr').remove()
            if(data.length>0){
            $('#studentList thead').append('<th><td><input type="checkbox"/></td><td>'+"Student Name"+'</td><td>'+"Reference Number"+'</td></th>')
            for( var i=0;i<data.length;i++){
            $('#studentList tbody').append('<tr><td><input type="checkbox" id="'+data[i].id+'"/></td><td>'+data[i].name+'</td><td>'+"Reference Number"+'</td></tr>')
            }
            $('#studentList tbody').append('<tr><td colspan="3"><input type="button" value="Assign RollNo"></td></tr>')
            }
            else{
                $('#msg').html("No Students Found")
            }
        }
    });

}
