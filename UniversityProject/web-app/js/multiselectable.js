var checkCourseCodeFlag=false,updateModeFlag=false
var groupCount = 0,k= 0,l=0;
var groups= ["A", "B", "C","D","E","F","G","H","I"];
var selectedSubjectValues = [];
var numberOfGroups = [];



function semesterList(jsonObject) {

    var courseType= $('#programType').val()

    $.ajax({
        type: "post",
        url: url('course', 'getCourseOnProgramCode', ''),
        data: $("#createCourse").serialize(),
        success: function (data) {
            subjectList=data

           // showSemesterAndSubjects()
            if(subjectList.length>0) {
                showSemesterAndSubjects(jsonObject)
                $("#worningMsg").hide();
            }else{
                $('#multiSelectTab tbody tr').remove()
                $("#worningMsg").html("Sorry We Are Unable To Find Courses Associated The Current Program And Session");
                $("#worningMsg").show();
            }
        }
    })

}


function viewSemesterList() {
    $('#multiSelectTab tr').remove()
    for (var j = 1; j <= $('#noOfTerms').html(); j++) {
        $('#multiSelectTab').append('<tr><td style="width: 40%"><label>Term - ' + j + ' Course</label></td>' +
            '<td style="width: 60%"><select class="select-to university-size-2-3" readonly="readonly" style="width: 75%;border: 0px;margin: 0px" name="semester' + j + '" id="semester' + j + '"  multiple="true" /></td></tr>')

    }

}


function makeJson(list) {

//    subjectList = jQuery.parseJSON(list.replace(/&quot;/g, '"'))


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
    updateModeFlag=true
    var courseDetailJson = jQuery.parseJSON(obj.replace(/&quot;/g, '"'))
    $('#courseName').val(courseDetailJson['course'].courseName)
    $('#modeName option[value=' + courseDetailJson['course'].courseMode.id + ']').attr("selected", "selected");
    $('#modename').prop("disabled",false)
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

    semesterList(courseDetailJson)
//    for (var i = 1; i <= $('#noOfTerms').val(); i++) {
//
//        for (var j = 0; j < courseDetailJson['semesterList'][i].length; j++) {
//            alert(courseDetailJson['semesterList'][i][j].subjectName)
//            alert(i)
//            $('#semester' + i).append('<option value="' + courseDetailJson['semesterList'][i][j].id + '">' + courseDetailJson['semesterList'][i][j].subjectName + '</option> ')
//        }
//
//    }
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
        i++;
    });
    json['uploadSyllabus'] = $('#uploadSyllabus').val() || '';
    var semesterList = {};

    var counter=0;

    for (var j = 1; j <= $('#noOfTerms').val(); j++) {
        var totalList=[];
        var subList = [], subGroupList=[]

        $('#semester' + j + ' option').each(function () {

            subList.push($(this).val() || '');

//            semesterList["semester" + j] = subList;

        })
        totalList.push(subList);


        if($("#groupListBox"+j).length>0){
//            alert("hi")

            $('#groupListBox' + j + ' option').each(function () {

                subGroupList.push($(this).val() || '');

//                semesterList["semester" + j] = subGroupList;

            })
            totalList.push(subGroupList);


        }
        semesterList["semester" + j] = totalList;

    }
    finalList.push(semesterList);
    console.log("hello java"+semesterList)
    json["semesterList"] = finalList;
//       console.log("hello java"+json);

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
            url: url('course', 'saveProgram', ''),
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


function showSemesterAndSubjects(courseDetailJson){

    var clVal=0
    $('#multiSelectTab tbody tr').remove()
    for (var j = 1; j <= $('#noOfTerms').val(); j++) {
        $('#multiSelectTab tbody').append('<tr id="tr' + j + '"><td style="width:14% "> <label>All Course <span class="university-obligatory">*</span></label><select style="width: 80%" name="allsubjectList' + j + '" id="allsubjectList' + j + '"  multiple="true"  /></td>' +
            ' <td style="width:7% "> <button type="button" class="multiSelect-buttons-button" onclick="addToList(' + j + ')" name="add' + j + '"  id="add' + j + '">Add</button>' +
            '  <button type="button" class="multiSelect-buttons-button" onclick="removeFromList(' + j + ')" name="remove' + j + '"  id="remove' + j + '">Remove</button> </td>' +
            '<td style="width:17%;"><select class="select-to" style="width: 50%"  name="semester' + j + '" id="semester' + j + '"  multiple="true"  />' +
            '<input type="button" value="Add Groups" onclick="showGroup(' + j + ')"/></td>' +
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
            '<input id="addGroup'  + j +'" onclick="addGroups(' + j +')" type="reset" value="Add Groups"  class="university-button">'+
            '<input id="removeGroupOnPopUp' + j +'" onclick="removeSubjectGroup(' + j +')" type="reset" value="Remove Groups" disabled class="university-button">'+
            '<table  name="subjectGroup" id="subjectGroup'  + j +'"><tr><input type="button" id="saveGroups'  + j +'" value="Save Groups"  disabled class="university-button" onclick="saveSubjectGroup('+ j+','+clVal+')" /></tr>' +
            '</table></g:form></div>')
    }


    if(updateModeFlag){

        appendSubjectsInUpdateMode(courseDetailJson)


    }

}

function showGroup(j) {
    initializeDialog(j)
    if(numberOfGroups[j]){
        k=numberOfGroups[j];
        l=numberOfGroups[j];
    }else{
        k=0;
        l=0;
    }

    $('#groupDialog' + j).dialog('open');
}

function addGroups(id){
    var rowCount = $('#subjectGroup'+id+' tr').length;
    $("#removeGroupOnPopUp"+id).prop('disabled',false)
    $("#saveGroups"+id).prop('disabled',false)

    k=rowCount-1

    $('#subjectGroup'+id+' tbody').append('<tr id="groupTr' + id +l+ '"><td style="width:30% "></div> <label>All Course <span class="university-obligatory">*</span></label>' +
        '<select style="width: 60%" name="listOfAllsubject' + id +l+ '" id="listOfAllsubject' + id +l+ '"  multiple="true"  /></td>' +
        ' <td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="addToGroupList(' + id +l+ ')" name="add' + id +l+ '"  id="add' + id +l+ '">Add</button>' +
        '  <button type="button" class="multiSelect-buttons-button" onclick="removeGroupFromList('+ id +l+ ')" name="remove' + id +l+ '"  id="remove' + id +l+ '">Remove</button> </td>' +
        '<td style="width:30%;"><select class="select-to" style="width: 50%"  name="group' + id +l+ '" id="group' + id +l+ '"  multiple="true"  />' +
        //'<td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="removeSubjectGroup(' +id+',' + id +l+ ')" name="removeGroup' + id +l+ '"  id="removeGroup' + id +l+ '">Remove Group</button>' +
        '<div id="error-select"></div></div></td></tr>')



    $("<div>Group <label id='groupName'>"+groups[k]+"</label></div>").insertBefore($('#group' + id +''+l))
    k++;
    if(k==9){
        $('#addGroup'+id).prop('disabled',true)
    }

    for (var i = 0; i < subjectList.length; i++) {

        $("#listOfAllsubject" + id +''+l).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
    }

    l++;

}

function removeSubjectGroup(t){
    var closeVal = "notClose";
    $('#subjectGroup'+t+' tbody tr:last').remove()
    k--;
    l--;
    if(k==0){
        $("#removeGroupOnPopUp"+t).prop('disabled',true)
    }
    saveSubjectGroup(t,closeVal)
}

function addToGroupList(j) {

    var nonSelected = [];
    var inList2;
    $('#listOfAllsubject' + j + ' :selected').each(function (l, list1Selected) {
        selectedSubjectValues[l] = $(list1Selected).val();
        //numberOfGroups[l] = selectedSubjectValues
        inList2 = false;

        $('#group' + j + ' option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedSubjectValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#group' + j).append("<option value='" + selectedSubjectValues[l] + "'>" + $(list1Selected).text() + "</option>");

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

function saveSubjectGroup(j,closeVal){
    var subList =[];
    numberOfGroups[j]=k;
    if($("#groupListBox"+j+" option").length>0){
        $('#multiSelectTab tbody #tr'+j).find("td:last").remove()
    }
    var groupSubjectJson = ConvertGroupFormToJSON(j);
    subList= groupSubjectJson["groupList"];
    var subMap = subList[0];
    var rowCount = $('#subjectGroup'+j+' tr').length;
    if(rowCount-1!=0) {
        if(!document.getElementById("groupListBox" + j)) {
            $('#multiSelectTab tbody #tr' + j + ':last').append('<td style="width:30%;"><select multiple="multiple" id="groupListBox' + j + '" style="height: 100px; width: 150px;">' +
                '</select> </td>+' +
                '<td><div id="groupOption"><label><span>Single Subject</span><input type="radio" name="groupSelection' + j + '" value="singleSubject" onclick="hideTextBox(' + j + ')" class="radioInput"/></label></div><br/>' +
                '<label><span>Multiple Subject</span><input type="radio" name="groupSelection' + j + '" value="multipleSubject" class="radioInput" onclick="openTextBox(' + j + ')" id="multiSubjects' + j + '" /></label><input type="text" hidden="hidden" id="noOfSubjects' + j + '" ><br/>' +
                '<label><span>Test Subject</span><input type="radio" name="groupSelection' + j + '" value="Test" class="radioInput" onclick="hideTextBox(' + j + ')"/></label></div></td>')
        }
        $("#groupListBox" + j).empty()
        for (var i = 0; i < rowCount - 1; i++) {
            var subGroupMap = subMap["group" +j+''+ i]

            $("#groupListBox" + j).append('<option value="Group'+groups[i]+'" style="font-family: bold;">Group <label >' + groups[i]+'</option>');
            for (var key in subGroupMap) {
                $("#groupListBox" + j).append('<option value="'+key+'"><label>' + subGroupMap[key]+'</label></option>');
            }
        }
    }else{
       // $('#multiSelectTab tbody #tr'+j+':last').remove()
    }
//    k=0;
    if(closeVal=="") {
        $('#groupDialog' + j).dialog('close');
    }
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
    // var array = jQuery(form).serializeArray();
    var json = {};
    var finalList = new Array();
    var i = 0;
    var groupMap = {};


    var rowCount = $('#subjectGroup'+id+' tr').length;
    for (var j = 0; j < k; j++) {

        var subMap = {};
        $('#group' +id+''+ j + ' option').each(function () {
                var key=$(this).val()
                subMap[key]=$('#group' +id+''+ j + ' option[value='+$(this).val()+']').text()
                 groupMap["group" +id+''+ j] = subMap;
                 }
        )

    }
    finalList.push(groupMap);

    json["groupList"] = finalList;


    return json
}

function initializeDialog(j) {
    $(".dialog").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center', 0],
        width: 850,
        resizable: false,
        height: 650,
        modal: true,
        title: 'Subject Groups',
        close: function (ev, ui) {
            $.unblockUI();
        },
        open: function() {
            //do something
            if(updateModeFlag){

                showSubjectGroupInDialog(j)

            }
        }
    });



}


function addToTestList(id){
    ConvertGroupFormToJSON(id)
}

function openTextBox(data){
  // alert("open a text box");
    $("#noOfSubjects"+data).show()

}

function hideTextBox(data){
    //alert("open a text box");
    $("#noOfSubjects"+data).hide()

}

function appendSubjectsInUpdateMode(courseDetailJson){
    alert("group i update mode")

    for (var i = 1; i <= $('#noOfTerms').val(); i++)
    {
        for (var j = 0; j < courseDetailJson['semesterList'][i].length; j++)
        {
            var groupFalg=false
            for( var k=0;k<courseDetailJson['semesterList'][i][j].length;k++){

                if(courseDetailJson['semesterList'][i][j][k].toString().indexOf("Group")>-1){
                    appendSubjectGroupInUpdate(i)

                    $('#groupListBox' + i).append('<option value="' + courseDetailJson['semesterList'][i][j][k] + '">' + courseDetailJson['semesterList'][i][j][k] + '</option> ')

                    groupFalg=true

                }
                             else if(groupFalg){
                        $('#groupListBox' + i).append('<option value="' + courseDetailJson['semesterList'][i][j][k].id + '">' + courseDetailJson['semesterList'][i][j][k].subjectName + '</option> ')

                    }
                    else{
                        $('#semester' + i).append('<option value="' + courseDetailJson['semesterList'][i][j][k].id + '">' + courseDetailJson['semesterList'][i][j][k].subjectName + '</option> ')

                }
            }

        }
    }

}

function appendSubjectGroupInUpdate(i) {
    alert("appending group on update")

    if (document.getElementById("groupListBox" + i)) {

    }
    else {
        $('#multiSelectTab tbody #tr' + i + ':last').append('<td style="width:30%;"><select multiple="multiple" id="groupListBox' + i + '" style="height: 100px; width: 150px;">' +
            '</select> </td><td><div id="groupOption"><label><span>Single Subject</span>' +
            '<input type="radio" name="groupSelection' + i + '" value="singleSubject" onclick="hideTextBox(' + i + ')" class="radioInput"/></label></div><br/>' +
            '<label><span>Multiple Subject</span><input type="radio" name="groupSelection' + i + '" value="multipleSubject" class="radioInput" onclick="openTextBox(' + i + ')" id="multiSubjects' + i + '" /></label>' +
            '<input type="text" hidden="hidden" id="noOfSubjects' + i + '" ><br/>' +
            '<label><span>Test Subject</span><input type="radio" name="groupSelection' + i + '" value="Test" class="radioInput" onclick="hideTextBox(' + i + ')"/></label></div></td>')

    }
}


function showSubjectGroupInDialog(i){
    alert("showSubjectGroupInDialog")


    var groupRowCounter=0;

    if (document.getElementById("groupListBox" + i)) {

        $('#groupListBox'+ i+' option').each(function () {

            if($(this).val().indexOf("Group")>-1){
                ++groupRowCounter
            }

        })

    }
    $('#subjectGroup'+i+' tbody tr:not(first)').remove();
    var  groupLabel
    for( var j=0;j<groupRowCounter;j++){
//        groupFlag=false
        if((document.getElementById("subjectGroup" + i))){


         $("#subjectGroup"+i+' tbody').append('<tr id="groupTr' + i +j+ '"><td style="width:30% "></div> <label>All Course <span class="university-obligatory">*</span></label>' +
        '<select style="width: 60%" name="listOfAllsubject' + i +j+ '" id="listOfAllsubject' + i +j+ '"  multiple="true"  /></td>' +
        ' <td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="addToGroupList(' + i +j+ ')" name="add' + i +j+ '"  id="add' + i +j+ '">Add</button>' +
        '  <button type="button" class="multiSelect-buttons-button" onclick="removeGroupFromList('+ i +j+ ')" name="remove' + i +j+ '"  id="remove' + i +j+ '">Remove</button> </td>' +
        '<td style="width:30%;"><div id="groupName'+i+j+'"></div><select class="select-to" style="width: 50%"  name="group' + i +j+ '" id="group' + i +j+ '"  multiple="true"  />' +
        //'<td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="removeSubjectGroup(' +id+',' + id +l+ ')" name="removeGroup' + id +l+ '"  id="removeGroup' + id +l+ '">Remove Group</button>' +
        '<div id="error-select"></div></div></td></tr>')

            $('#groupListBox'+ i+' option').each(function () {

                if($(this).val().indexOf("Group")>-1){
                    console.log("**"+$("#groupName"+i+j).text().length)
                    console.log("<<<"+groupLabel)

                  if($("#groupName"+i+j).text().length==0 && groupLabel!=$(this).val() ){
                     console.log("innnnn")
                    $("#groupName"+i+j).html($(this).val())
                      groupLabel=$(this).val()
                      groupFlag=true
                    }
                    else{
                      groupFlag=false
                  }
                }
                else if(groupFlag){
                    $("#group"+i+j).append('<option value="'+$(this).val()+'">'+$('#groupListBox' +i+ ' option[value='+$(this).val()+']').text()+'</option>')
                }



            })

            for (var k = 0; k < subjectList.length; k++) {

                $("#listOfAllsubject" + i +''+j).append('<option value="' + subjectList[k].id + '">' + subjectList[k].subjectName + '</option>')
            }

        }



    }

}