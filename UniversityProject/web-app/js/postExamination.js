/**
 * Created by Digvijay on 3/6/14.
 */
var marksTypeId =0;
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
                $("#session").attr('disabled',false)
            }
        });

    }
    else{
        $("#session").empty().append('');
        $("#session").append('<option value="">Select Session</option>');
        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
        $('#courseCode').empty().append('<option value="">Select Course</option>')
    }
}

//function loadSemester(){
//    var data = $('#programId').val();
//    if(data){
//        $.ajax({
//            type: "post",
//            url: url('admitCard', 'getSemesterList', ''),
//            data: {data: data},
//            success: function (data) {
//                $("#semesterList").empty().append('data <option value="">Select Semester</option>')
//                $("#SessionList").empty().append('data <option value="">Select Session</option>')
//                $('#courseCode').empty().append('<option value="">Select Course</option>')
//                for (var i = 1; i <= data.totalSem; i++) {
//                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
//                }
//                for (var i = 0; i < data.session.length; i++) {
//                    $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
//                }
//            }
//        })
//    }
//    else{
//        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
//        $('#courseCode').empty().append('<option value="">Select Course</option>')
//    }
//}

function loadGroup(){
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getGroup', ''),
        data: {program: program,session: session,semester: semester},

        success: function (data) {

            $("#groupList").empty().append('<option value="">Select Group</option>')

               if(data.length>0){
                   $("#groupList").append('<option value="0">Mandatory Subjects</option>')
                   for(var i=0;i<data.length;i++){
                       $("#groupList").append('<option value="'+data[i].id+'">'+data[i].groupName+'</option>')
                   }


               }
                else{
                   $("#groupList").append('<option value="0">Mandatory Subjects</option>')
                 }

        }
    });

    $("#groupList").attr('disabled',false)
}


function enableMarksType(){
    $("#marksType").attr('disabled',false)
    $("#pdfButton").attr('disabled',false)
    $("#excelButton").attr('disabled',false)
    $("#cancelButton").attr('disabled',false)

}

function enableButton(){
     if($("#marksType").val()==0)
     {
     $("#setButton").attr('disabled',true)
  }else{
      $("#setButton").attr('disabled',false)
  }

}

function loadCourse() {
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    var groupType= $("#groupList").val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getCourseData', ''),
        data: {program: program,session: session,semester: semester,groupType:groupType},

        success: function (data) {

               $('#courseCode').empty().append('<option value="">Select Course</option>')
               for(i=0; i<data.length;i++){
                   $('#courseCode').append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')

               }
        }
    });
    $("#courseCode").attr('disabled',false)
}


function loadSemester(){

    var program = $('#programId').val();
    var session = $('#SessionList').val();
//    var semester = $('#semesterList').val();
//    var groupType= $("#groupList").val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getSemesterOfProgram', ''),
        data: {program: program,session: session},

        success: function (data) {

            $('#semesterList').empty().append('<option value="">Select Semester</option>')
            for(i=0; i<data.length;i++){
                $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')

            }
            $("#semesterList").attr('disabled',false)
        }
    });

    $("#semesterList").attr('disabled',false)

}


function loadMismatchStudents(){
//    alert("hello kuldeep")

}

$(document).ready(function(){
    $('#pdfButton').on('click', function(){
         $("#btn").val("pdf")
        $("#marksFoilId").submit()
    })

    $('#excelButton').on('click', function(){
        $("#btn").val("excel")
        $("#marksFoilId").submit()
    })

    $("#SessionList").on('change',function(){
        $("#semesterList").removeAttr('disabled')
    })

    $("#missMatchedButton").on('click',function(){

        $("#marksMissMatchForm").submit()
    })

})
function populateStudentListForMarksUpdate(){
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    var groupType= $("#groupList").val();
    var subjectId=$("#courseCode").val()
    var marksType=$("#marksType").val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'marksMissMatchUpdate', ''),
        data: {programId: program, session: session, semester: semester,groupType:groupType,subjectId:subjectId,marksType:marksType},
        success: function (data) {
            $('#rollNoList').empty()
            if(data.length>0){
                $('#rollNoList').append('<option value="">Select Roll Number</option>')
                for (var i=0;i<data.length;i++){
                    $('#rollNoList').append('<option value="'+data[i].id+'">'+data[i].rollNo+'</option>')
                }
//                $('#rollNoList').prop('disabled', false)
            }
            else{
                $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>No Roll numbers found.</p></div>").dialog({
                    title: "Sorry",
                    resizable: false,
                    modal: true,
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }

        }
    });

}
function loadTabulatorMarks(){
    var studentId=$('#rollNoList').val()
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    var groupType= $("#groupList").val();
    var subjectId=$("#courseCode").val()
    var marksType=$("#marksType").val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadTabulatorMarks', ''),
        data: {studentId:studentId,programId: program, session: session, semester: semester, groupType: groupType, subjectId: subjectId, marksType: marksType},
        success: function (data) {
//            alertPopup(data[0].tab1Marks)
            if(data.tab1Marks) {
                $('#tab1Marks').val(data.tab1Marks)
                $('#tab2Marks').val(data.tab2Marks)
            }
        }
    })
}
function populateStudentListForMarks() {
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
//    var stuSession = $('#sessionVal').val();
    var groupType= $("#groupList").val();
    var subjectId=$("#courseCode").val()
    var marksType=$("#marksType").val()


    var course = $('#courseCode').val();
        $.ajax({
            type: "post",
            url: url('postExamination', 'getRollNoList', ''),
            data: {program: program, session: session, semester: semester,groupType:groupType,subjectId:subjectId,marksType:marksType},
            success: function (data) {
                $('#rollNoList').empty()
                if(data.length>0){

                    for (var i=0;i<data.length;i++){
                        $('#rollNoList').append('<option value="'+data[i].id+'">'+data[i].rollNo+'</option>')
                    }
                    document.getElementById("dataTable").style.visibility = "visible";
                    document.getElementById("buttonDiv").style.visibility = "visible";
                    $("#rollNoList option:first").attr('selected','selected');
                }
                else{
                    sorryPopup("No Roll numbers found.")
                }

            }
        });
}

function updateMisMatchMarks(){
    validationPostExam()
    var result = $('#marksUpdate').valid()
    if(result) {
        var studentId = $('#rollNoList').val()
        var program = $('#programId').val();
        var session = $('#SessionList').val();
        var semester = $('#semesterList').val();
        var groupType = $("#groupList").val();
        var subjectId = $("#courseCode").val()
        var marksType = $("#marksType").val()
        var updatedMarks = $("#updatedMarks").val()
        $.ajax({
            type: "post",
            url: url('postExamination', 'updateMisMatchMarks', ''),
            data: {updatedMarks: updatedMarks, studentId: studentId, programId: program, session: session, semester: semester, groupType: groupType, subjectId: subjectId, marksType: marksType},
            success: function (data) {
                if (data.status) {
                    successPopup("Marks Updated Succesfully")
                    populateStudentListForMarksUpdate()
                    $('#tab1Marks').val('')
                    $('#tab2Marks').val('')
                    $('#updatedMarks').val('')
                }
            }
        })
    }
}

function setSessions(){
    $.ajax({
        type: "post",
        url: url('report', 'getSessionList', ''),
        async: false,
        data: '',
        success: function (data) {
            $(".allSession").empty().append('')
            for (var i = 0; i < data.length; i++) {
                $(".allSession").append('<option value="' + data[i] + '">' + data[i]+'-'+ (data[i]+1) + '</option>')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }

    });
}


function updateMarksType(id){
    marksTypeId = id
    alert(marksTypeId)
    window.location.href = '/UniversityProject/marksType/createMarksType?marksTypeId=' + marksTypeId;
}
function deleteMarksType(id){
    marksTypeId = id
//    alert(bankId)
    window.location.href = '/UniversityProject/marksType/deleteMarksType?marksTypeId=' + marksTypeId;
}


function saveMarks(){
    if($("#rollNoList").val()){
       $.ajax({
        type: "post",
        url: url('postExamination', 'saveMarks', ''),
       data: {program: $('#programId').val(), session: $('#SessionList').val(), semester:  $('#semesterList').val(),groupType:$("#groupList").val(),
               subjectId:$("#courseCode").val(),marksType:$("#marksType").val(),rollNoId:$("#rollNoList").val(),marksValue:$("#marksValue").val()},
        success: function (data) {

            $("#marksValue").val('')
            populateStudentListForMarks()

        }
         });
    }
    else{
            alert(" There is no roll numbers to enter marks")
        }



}

function enableSession(){

        $.ajax({
            type: "post",
            url: url('postExamination', 'getStudentSession', ''),
           success: function (data) {
               $('#studentSession').empty().append('<option value="">Select Student Session</option>')
               if(data.length>0){
                   for (var i=0;i<data.length;i++){
                       $('#studentSession').append('<option value="'+data[i]+'">'+data[i]+'</option>')
                   }
                   $("#studentSession").attr('disabled',false)

               }

            }
        });


}

function disableAllSelectBox(){

    $("select").attr("disabled", true);
    $("#showButton").attr("disabled",false)
    $("#resetButton").attr("disabled",false)
    $("#setButton").attr("disabled",true)
    $("#rollNoList").attr("disabled", false);

}

function enableAllSelectBox(){
    $("select").attr("disabled", false);
    $("#showButton").attr("disabled",true)
    $("#resetButton").attr("disabled",true)
    $("#setButton").attr("disabled",false)

//    $("#rollNoList").attr("disabled",true)

    document.getElementById("dataTable").style.visibility = "hidden";
    document.getElementById("buttonDiv").style.visibility = "hidden";
}

function enableButtonOfMissMatch(){
    $("input[type=button]").removeAttr("disabled");

}


function matchMarks(){
    if($("#marksValue").length!=0) {
        if ($("#marksValue").val().length > 1) {
            $.ajax({
                type: "post",
                url: url('postExamination', 'checkMarks', ''),
                data: {program: $('#programId').val(), session: $('#SessionList').val(), semester: $('#semesterList').val(), groupType: $("#groupList").val(),
                    subjectId: $("#courseCode").val(), marksType: $("#marksType").val(), rollNoId: $("#rollNoList").val(), marksValue: $("#marksValue").val()},
                success: function (data) {

                    if (data.status == false) {
                        alertPopup("Current entered marks did not match,the marks entered by "+data.tabulator)

                    }
                }
            });
        }
    }
    else{
        alert(" There is no roll numbers to enter marks")
    }

}
function getTabulatorSession(t){
    var program=$(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getTabulatorSession', ''),
        data: {program: program},
        success: function (data) {
            if(data.session){
                $('#SessionList').prop('disabled',false)
                $("#SessionList").empty().append('data <option value="">Select Session</option>')
                for (var j = 0; j < data.session.length; j++) {
                    $("#SessionList").append('<option value="' + data.session[j].id + '">' + data.session[j].sessionOfProgram + '</option>')
                }
            }

        }
    })
}
function getSemesterForMarksUpdate(t){
    var program=$('#programId').val()
    var session=$(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getSemesterForMarksUpdate', ''),
        data: {program: program,session:session},
        success: function (data) {
            if(data.semesterList){
                $('#semesterList').prop('disabled',false)
                $('#semesterList').empty().append("<option value=''>Select Semester</option>")

                for(var i=0;i<data.semesterList.length;i++){
                    $('#semesterList').append("<option value='"+data.semesterList[i].id+"'>"+data.semesterList[i].semesterNo+"</option>")
                }
            }
        }
    });
}
function getTabulatorSemester(t){
    var program=$('#programId').val()
    var session=$(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getTabulatorSemester', ''),
        data: {program: program,session:session},
        success: function (data) {
            if(data.tabSemesterList){
                $('#semesterList').prop('disabled',false)
                $('#semesterList').empty().append("<option value=''>Select Semester</option>")

                for(var i=0;i<data.tabSemesterList.length;i++)
                {
                    for(var j=0;j<data.tabSemesterList[i].length;j++){
                         $('#semesterList').append("<option value='"+data.tabSemesterList[i][j].id+"'>"+data.tabSemesterList[i][j].semesterNo+"</option>")
                        }
                }
//
            }

        }
    })
}
function alertPopup(data){
    var dialog=$("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>"+data+".</p></div>").dialog({
        title: "Alert!",
        resizable: false,
        modal: true,
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });
    setTimeout(function(){
        dialog.dialog('destroy');
    },3000);
}

function sorryPopup(data){
    var dialog=$("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>"+data+".</p></div>").dialog({
        title: "Sorry",
        resizable: false,
        modal: true,
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });
    setTimeout(function(){
        dialog.dialog('destroy');
    },3000);
}

function successPopup(data){
    var dialog=$("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>"+data+".</p></div>").dialog({
        title: "Success",
        resizable: false,
        modal: true,
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });
    setTimeout(function(){
        dialog.dialog('destroy');
    },3000);
}