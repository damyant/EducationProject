/**
 * Created by Digvijay on 3/6/14.
 */

function loadSession(t){
    var program=$(t).val();
    if(program){
        $.ajax({
            type: "post",
            url: url('programFee', 'getProgramSession', ''),
            data: {program: program},
            success: function (data) {
                $("#session").empty().append('');
                $("#session").append('<option value="">Select Session</option>');
                for (var i = 0; i < data.length-1; i++) {
                    $("#session").append('<option value="' + data[i].sessionOfProgram + '">' + data[i].sessionOfProgram + '</option>')
                }
            }
        });
    }
}

function loadSemester(){
    //alert("Inside Load Semester")
    var data = $('#programId').val();
//    $('#semesterList').prop('disabled',false)
//    $('#SessionList').prop('disabled',false)
    if(data){
        $.ajax({
            type: "post",
            url: url('admitCard', 'getSemesterList', ''),
            data: {data: data},
            success: function (data) {
                $("#semesterList").empty().append('data <option value="">Select Semester</option>')

                $("#SessionList").empty().append('data <option value="">Select Session</option>')

                for (var i = 1; i <= data.totalSem; i++) {
                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
                }
                for (var i = 0; i < data.session.length; i++) {
                    $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
                }
            }
        })
    }
}

function getSemesterAndSubjectList(){
    var session= $('#SessionList').val()
    var sessionType= $("#sessionType").val()
    if(session && sessionType!='0'){
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
    else{
        $("#subjectList").empty();
    }
}

function loadCourse() {
    alert("Inside Load Course..")
    var program = $('#programId').val();
    var session = $('#session').val();
    var semester = $('#semesterList').val();
    alert("-------"+semester)
    $.ajax({
        type: "post",
        url: url('postExamination', 'getCourseData', ''),
        data: {program: program,session: session,semester: semester},
        success: function (data) {
               for(i=0; i<data.length;i++){
                   $('#courseCode').empty().append('<option value="' + data[i].subject.id + '">' + data[i].subject.subjectName + '</option>')
               }

//            if(data.courseCode==true){
//                $("#semesterList tr").remove()
//                $("#msgDiv").html("There is no Course associated with the program")
//            }
//            else{
//                $("#msgDiv").html("")
//                appendSubjects(data)
//            }
        }
    });
}