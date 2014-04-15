

var studentIdList = [];
var subjectIdList=[];
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


    $(document).on('click', '#submitExamDate', function () {

        $.ajax({
            type: "post",
            url: url('admin', 'saveExamDate', ''),
            data: $('#assignDate').serialize()+'&subjectIdList=' + subjectIdList,
            success: function (data) {
                if(data.saveFlag==true){

                    $("#subjectList tr").remove()
                    $("#msgDiv").html("Examination Date is saved")

                }
                else{
                    $("#msgDiv").html("")
                }


            }
        });

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

            var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString())
            var datesInNewFormat = $.datepicker.formatDate( "dd/mm/yy", d);

            $("#subjectList").append('<tr><td>'+obj.allSubjects[i][j].subjectName+'</td><td><input type="text" name="examinationDate" class="datepicker university-size-1-2"  value='+datesInNewFormat+'></input></td><td><input type="text"  onchange="checkTimeFormat('+counter+')" name="examTime" id="examTime'+counter+'" ></td></tr>')
           ++counter
        }
        count++;

    }
    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" value="Submit"></td></tr>' )

    $(".datepicker").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy"
    });


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
