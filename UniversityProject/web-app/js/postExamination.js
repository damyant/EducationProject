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
    var data = $('#programId').val();
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

function loadCourse() {
    var program = $('#programId').val();
    var session = $('#session').val();
    var semester = $('#semesterList').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getCourseData', ''),
        data: {program: program,session: session,semester: semester},
        success: function (data) {
//               console.log(data)
               $("#courseCode").empty().append('data <option value="">Select Course</option>')
//               $('#courseCode').empty().append('<option value="">Select Course</option>')
               for(i=0; i<data.length;i++){
                   $('#courseCode').empty().append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')
               }
        }
    });
}

$(document).ready(function(){
    $('#pdfid').on('click', function(){
        alert("clicked pdf")
        $("#btn").val("pdf")
        $("#marksFoilId").submit()
    })

    $('#excelid').on('click', function(){
        alert("clicked excel")
        $("#btn").val("excel")
        $("#marksFoilId").submit()
    })

})

function populateStudentList() {
    alert("Inside populateStudentList Method.. ")
    var program = $('#programId').val();
    var session = $('#session').val();
    var semester = $('#semesterList').val();
    var course = $('#courseCode').val();
    alert("course--"+course)
//    if (course) {
        alert("Inside ajax call....")
        $.ajax({
            type: "post",
            url: url('postExamination', 'getRollNoList', ''),
            data: {program: program, session: session, semester: semester},
            success: function (data) {
                if(data.length>0){
                    $('#selectBox').empty()
                    for (var i=0;i<data.length;i++){
                        $('#selectBox').append('<option value="'+data[i].id+'">'+data[i].rollNo+'</option>')
//                        $('#courseCode').empty().append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')


                    }
                }
            }
        });
//    }
}
