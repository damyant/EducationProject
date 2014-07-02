var checkCourseCodeFlag=false
var groupCount =0;
var k=0;
var l=0;
var groups= ["A", "B", "C","D","E","F","G","H","I"];



function semesterList() {
    var courseType= $('#programType').val()
    if(courseType){
    }

    $('#multiSelectTab tbody tr').remove()
    for (var j = 1; j <= $('#noOfTerms').val(); j++) {
        $('#multiSelectTab tbody').append('<tr id="tr' + j + '"><td style="width:14% "></div> <label>All Course <span class="university-obligatory">*</span></label><select style="width: 80%" name="allsubjectList' + j + '" id="allsubjectList' + j + '"  multiple="true"  /></td>' +
        ' <td style="width:7% "> <button type="button" class="multiSelect-buttons-button" onclick="addToList(' + j + ')" name="add' + j + '"  id="add' + j + '">Add</button>' +
        '  <button type="button" class="multiSelect-buttons-button" onclick="removeFromList(' + j + ')" name="remove' + j + '"  id="remove' + j + '">Remove</button> </td>' +
        '<td style="width:17%;"><select class="select-to" style="width: 50%"  name="semester' + j + '" id="semester' + j + '"  multiple="true"  />' +
        '<input type="button" value="Add Groups" onclick="showGroup(' + j + ')"/></td>' +


    //'<td style="width:5%;"><input type="button" value="Remove Groups" onclick="removeGroup(' + (j+8) + ')"/>' +
//            '<div id="upload-syllabus" style="width: 30%;float:right;">' +
//            '<input type="button" style="float: right; margin-top:20%" id="Syllabus_link" value="Syllabus" onclick="syllabusUpload(' + j + ')" /></div>' +

'<div id="error-select-' + j + '"></div></div></td></tr>')

if ($('#modeName option:selected').text().toLowerCase() == "annual") {
    $("<div>Term-" + j + "</div>").insertBefore($('#semester' + j))
}
else if (($('#modeName option:selected').text().toLowerCase() == "semester")) {
    $("<div>Semester-" + j + "</div>").insertBefore($('#semester' + j))
}


for (var i = 0; i < subjectList.length; i++) {

    $("#allsubjectList" + j).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
}
$("#createCourse").append('<div id="groupDialog'  + j +'" class="dialog" hidden="hidden">'+
    '<g:form method="post" name="groupsOfSubject" id="groupsOfSubject" enctype="multipart/form-data">'+
    '<input id="addGroup" onclick="addGroups(' + j +')" type="reset" value="Add Subject Groups"  class="university-button">'+
    '<input id="remove" onclick="removeSubjectGroup(' + j +')" type="reset" value="Remove Groups"  class="university-button">'+
    '<table  name="subjectGroup" id="subjectGroup'  + j +'"><tr></tr><input type="button" value="Save Groups" onclick="saveSubjectGroup(' + j +')" ></table></g:form></div>')
}

}


function viewSemesterList() {
    $('#multiSelectTab tr').remove()
    for (var j = 1; j <= $('#noOfTerms').html(); j++) {
        $('#multiSelectTab').append('<tr><td style="width: 40%"><label>Term - ' + j + ' Course</label></td>' +
            '<td style="width: 60%"><select class="select-to university-size-2-3" readonly="readonly" style="width: 75%;border: 0px;margin: 0px" name="semester' + j + '" id="semester' + j + '"  multiple="true" /></td></tr>')

    }

}


function makeJson(list) {
    subjectList = jQuery.parseJSON(list.replace(/&quot;/g, '"'))

}
function addToList(j) {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#allsubjectList' + j + ' :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;
        $('#semester' + j + ' option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#semester' + j).append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
//            alert(text1);
//            $('#semester'+j+' option').filter(function() {
//                //may want to use $.trim in here
//                return $(this).val() == text1;
//            }).attr('selected', true);
            $('#allsubjectList' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allsubjectList' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateLength(j);
}

function removeFromList(j) {
    $('#semester' + j + ' option:selected').each(function () {
        $(this).remove();
        $('#semester' + j + ' option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()
//        $('#semester'+j+' option').filter(function() {
//            return $(this).val() == text2;
//        }).attr('selected', true);
            $('#allsubjectList' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allsubjectList' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateLength(j);
}
function validateLength(j) {
    var validate;
    var length = document.getElementById('semester' + j).options.length;
    if (length == 0) {
        $('#error-select-' + j).html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-' + j).html("");
        validate = true;
    }

    return validate;
}

function updateInfo(obj) {

    var courseDetailJson = jQuery.parseJSON(obj.replace(/&quot;/g, '"'))
    $('#courseName').val(courseDetailJson['course'].courseName)
    $('#modeName option[value=' + courseDetailJson['course'].courseMode.id + ']').attr("selected", "selected");
    $('#courseTypeName option[value=' + courseDetailJson['course'].courseType.id + ']').attr("selected", "selected");
    $('#programType option[value=' + courseDetailJson['course'].programType.id+ ']').attr("selected", "selected");
    $('#noOfTerms').val(courseDetailJson['course'].noOfTerms)
    $('#courseCode').val(courseDetailJson['course'].courseCode)
    $('#noOfAcademicYears').val(courseDetailJson['course'].noOfAcademicYears)
    $('#totalMarks').val(courseDetailJson['course'].totalMarks)
    $('#marksPerPaper').val(courseDetailJson['course'].marksPerPaper)
    $('#totalCreditPoints').val(courseDetailJson['course'].totalCreditPoints)
    $('#noOfPapers').val(courseDetailJson['course'].noOfPapers)
    $('#courseId').val(courseDetailJson['course'].id)
    $('#session option[value=' + courseDetailJson['sessionOfCourse']+']').attr("selected", "selected");

    if($("#session option:selected").text()==courseDetailJson['sessionOfCourse'].toString()){
        $('#session option[value=' + courseDetailJson['sessionOfCourse']+']').attr("selected", "selected");
    }
    else{
        $('#session').prepend('<option value="'+courseDetailJson['sessionOfCourse']+'">'+ courseDetailJson['sessionOfCourse']+'</option>');
        $('#session option[value=' + courseDetailJson['sessionOfCourse']+']').attr("selected", "selected");
    }
    semesterList()
    for (var i = 1; i <= $('#noOfTerms').val(); i++) {

        for (var j = 0; j < courseDetailJson['semesterList'][i].length; j++) {

            $('#semester' + i).append('<option value="' + courseDetailJson['semesterList'][i][j].id + '">' + courseDetailJson['semesterList'][i][j].subjectName + '</option> ')
        }

    }
}
function enableNoOfSem(t) {
//    alert($(t).val())
    if ($('#modeName').val() == 1 || $('#modeName').val() == 2) {
        $('#noOfTerms').prop('readonly', false);
    }
    else {
        $('#noOfTerms').val('');
        $('#noOfTerms').prop('readonly', true);
    }
}

function viewCourseInfo(obj) {

    var courseDetailJson = jQuery.parseJSON(obj.replace(/&quot;/g, '"'))
    $('#courseName').html(courseDetailJson['course'].courseName)
    //$('#modeName option[value='+courseDetailJson['course'].courseMode.id+']').attr("selected", "selected");
    $('#modeName').html(courseDetailJson['courseMode'])
    $('#courseTypeName').html(courseDetailJson['courseType'])
    $('#courseCategory').html(courseDetailJson['ProgramType'])
    $('#noOfTerms').html(courseDetailJson['course'].noOfTerms)
    $('#courseCode').html(courseDetailJson['course'].courseCode)
    $('#noOfAcademicYears').html(courseDetailJson['course'].noOfAcademicYears)
    $('#totalMarks').html(courseDetailJson['course'].totalMarks)
    $('#marksPerPaper').html(courseDetailJson['course'].marksPerPaper)
    $('#totalCreditPoints').html(courseDetailJson['course'].totalCreditPoints)
    $('#noOfPapers').html(courseDetailJson['course'].noOfPapers)
    $('#courseId').html(courseDetailJson['course'].id)
    viewSemesterList()
    for (var i = 1; i <= courseDetailJson['course'].noOfTerms; i++) {

        for (var j = 0; j < courseDetailJson['semesterList'][i].length; j++) {

            $('#semester' + i).append('<option value="' + courseDetailJson['semesterList'][i][j].id + '">' + courseDetailJson['semesterList'][i][j].subjectName + '</option> ')
        }

    }

}

function fireMultiValidate() {
    var validate = true;
    for (var i = 1; i <= $("#noOfTerms").val(); i++) {
        if (!validateLength(i)) {
            validate = false;
//            return false;
        }

    }

    return validate;
}


function ConvertFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var finalList = new Array();
    var i = 0;

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
//        alert(this.name)
        i++;
    });
    json['uploadSyllabus'] = $('#uploadSyllabus').val() || '';
    var semesterList = {};

    for (var j = 1; j <= $('#noOfTerms').val(); j++) {

        var subList = []
        $('#semester' + j + ' option').each(function () {


            subList.push($(this).val() || '');
            //console.log(subList)
            semesterList["semester" + j] = subList;
            // console.log(semesterList)
        })

    }
    finalList.push(semesterList);

    json["semesterList"] = finalList;
    //   console.log("hello java"+json);

    return json
}

function clearField() {

    for (var i = 1; i <= $('#noOfTerms').val(); i++) {
        $('#semester' + i).empty();
    }
    $('#createCourse').each(function () {
        this.reset();
    });
//    $("html, body").animate({ scrollTop: 0 }, "slow");
}
function save() {

    validate();
    var status = $("#createCourse").valid();
    if (!fireMultiValidate()) {
        return;
    }
    if (status && $("#errorMsg").text().length==0) {
        var formObj = $("#createCourse");
        var data = ConvertFormToJSON(formObj);

        $.ajax({
            type: "post",
            url: url('course', 'saveCourse', ''),
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                if (data.response1=='updated') {
                    document.getElementById("statusMessage").style.display = "block";
                }
                else if(data.response1=='Created'){
                    document.getElementById("statusMessage").style.display = "block";
                    clearField()
                }
                $("html, body").animate({ scrollTop: 0 }, "slow");
            }
        });
    }
}
function syllabusUpload() {
    if ($('#courseName').val()!= "") {

    }
    else {
        alert("Enter The Programme Name First.");
    }
}


function checkCourseCode() {

    var data = $('#courseCode').val();
    $.ajax({
        type: "post",
        url: url('course', 'checkCourseCode', ''),
        data: {courseCode: data},
        success: function (data) {
            if (data.courseCode == "true") {
                $('#errorMsg').text("Programme Code is already registered")
                $('#errorMsg').attr('display', true)
                return false
            }
            else {
                $('#errorMsg').text("")
                return true
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
function checkFileType(e){

    var file_list = e.target.files;

    for (var i = 0, file; file = file_list[i]; i++) {
        var sFileName = file.name;
        var sFileExtension = sFileName.split('.')[sFileName.split('.').length - 1].toLowerCase();
        var iFileSize = file.size;
        var iConvert = (file.size / 10485760).toFixed(2);

        if (!(sFileExtension === "pdf" || sFileExtension === "doc" || sFileExtension === "docx") || iFileSize > 10485760) {
            txt = "File type : " + sFileExtension + "\n\n";
            txt += "Size: " + iConvert + " MB \n\n";
            txt += "Please make sure your file is in pdf or doc format and less than 10 MB.\n\n";
        }
        else{
            return false;
        }
    }
}


function showGroup(id){
      openGroupPopUp(id);

}

function removeGroup(id){
    alert("Remove the current group"+id)
    $('#semester'+id).hide()
}

function openGroupPopUp(j) {
    // alert("ssffsff")
    initializeDialog()
    k=0;
    $('#groupDialog' + j).dialog('open');
}

function addGroups(id){
//    alert("Hello"+id)
    var rowCount = $('#subjectGroup'+id+' tr').length;
    k=rowCount-1
    //$('#subjectGroup tbody tr').remove()

       $('#subjectGroup'+id+' tbody').append('<tr id="groupTr' + id +l+ '"><td style="width:30% "></div> <label>All Course <span class="university-obligatory">*</span></label>' +
        '<select style="width: 60%" name="listOfAllsubject' + id +l+ '" id="listOfAllsubject' + id +l+ '"  multiple="true"  /></td>' +
        ' <td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="addToGroupList(' + id +l+ ')" name="add' + id +l+ '"  id="add' + id +l+ '">Add</button>' +
        '  <button type="button" class="multiSelect-buttons-button" onclick="removeGroupFromList('+ id +l+ ')" name="remove' + id +l+ '"  id="remove' + id +l+ '">Remove</button> </td>' +
        '<td style="width:30%;"><select class="select-to" style="width: 50%"  name="group' + id +l+ '" id="group' + id +l+ '"  multiple="true"  />' +
//        '<td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="removeSubjectGroup(' +id+',' + id +l+ ')" name="removeGroup' + id +l+ '"  id="removeGroup' + id +l+ '">Remove Group</button>' +
        '<div id="error-select"></div></div></td></tr>')



    $("<div>Group <label id='groupName'>"+groups[k]+"</label></div>").insertBefore($('#group' + id +''+l))
    k++;
    if(k==9){
        $('#addGroup').prop('disabled',true)
    }

    for (var i = 0; i < subjectList.length; i++) {

        $("#listOfAllsubject" + id +''+l).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
    }

    l++;

}

function removeSubjectGroup(t){

    $('#subjectGroup'+t+' tbody tr:last').remove()
   // $('#multiSelectTab tbody #tr'+j).find("td:last").remove()
    k--;

}

function addToGroupList(j) {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#listOfAllsubject' + j + ' :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;
        $('#group' + j + ' option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#group' + j).append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
//            alert(text1);
//            $('#semester'+j+' option').filter(function() {
//                //may want to use $.trim in here
//                return $(this).val() == text1;
//            }).attr('selected', true);
            $('#listOfAllsubject' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#listOfAllsubject' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateGroupLength(j);
}

function saveSubjectGroup(j){

    if($("#groupListBox"+j+" option").length>0){

        $('#multiSelectTab tbody #tr'+j).find("td:last").remove()
    }
//

    $('#multiSelectTab tbody #tr'+j+':last').append('<td style="width:30%;"><select multiple="multiple" id="groupListBox'+j+'" style="height: 100px; width: 150px;">' +
       '</select> </td>')
    var rowCount = $('#subjectGroup'+j+' tr').length;
    for(var i=0;i<rowCount-1;i++){
        $("#groupListBox"+j).append('<option value=1>Group <label >'+groups[i]+'</option>');
    }
    k=0;

    $('#groupDialog' + j).dialog('close');

}

function validateGroupLength(j) {
    var validate;
    var length = document.getElementById('group' + j).options.length;
    if (length == 0) {
        $('#error-select-' + j).html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-' + j).html("");
        validate = true;
    }

    return validate;
}


function removeGroupFromList(j) {
    $('#group' + j + ' option:selected').each(function () {
        $(this).remove();
        $('#group' + j + ' option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()

            $('#listOfAllsubject' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#listOfAllsubject' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateGroupLength(j);
}

function ConvertGroupFormToJSON(id) {
    // alert(form)
    // var array = jQuery(form).serializeArray();
    var json = {};
    var finalList = new Array();
    var i = 0;
    var groupList = {};

    for (var j = 0; j <= k; j++) {

        var subList = []
        $('#group' +id+''+ j + ' option').each(function () {
            subList.push($(this).val() || '');
            console.log(subList)
            groupList["group" +id+''+ j] = subList;
            console.log(groupList)
        })

    }
    finalList.push(groupList);

    json["groupList"] = finalList;
    console.log("====="+json);

    return json
}

function initializeDialog() {
    $(".dialog").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center', 0],
        width: 750,
        resizable: false,
        height: 550,
        modal: true,
        title: 'Subject Groups',
        close: function (ev, ui) {
            $.unblockUI();
        }
    });
}


function addToTestList(id){
    ConvertGroupFormToJSON(id)
}