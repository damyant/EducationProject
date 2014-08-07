/**
 * Created by chandan on 7/18/2014.
 */

var programIdList=[]
var programList = []
var programSemesterListTab1= []
var programSemesterListTab2= []
function openTabulator() {

    $('#tab1').click(function () {
        initializeDailog();
        if ($('#tab1').is(':checked')) {
            getProgramList(1);
            $('.dialogTab1').dialog('open');
            console.log("in js"+programList.length)
        }
    });
    $('#tab2').click(function () {
        initializeDailog();
        if ($('#tab2').is(':checked')) {
            getProgramList(2);

            $('.dialogTab1').dialog('open');
        }
    });
}


function getProgramList(tab){
    if(programList.length==0) {
        $.ajax({
            type: "post",
            url: url('user', 'getProgramList', ''),
            data: {},
            success: function (data) {
                if (data) {
                    programList = data
                    appendTab(programList,tab)
                    console.log("in success"+programList.length)
                }
                else {
                    $('#errorMsg').text("")
                    return true
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }else{
        appendTab(programList,tab)
    }


}
function saveSelectedSemesters(tabId) {
    $('#tab' + tabId +'Semester').val('')
    $('#tab' + tabId +'Program').val('')
    $('#dialogTab').dialog('close');
    var programMap={};
    var programSemester={};
    var noOfPrograms = programList.length

    $('#viewSelected' + tabId +' select').empty().append('')
    for(var i=0;i<programList.length;i++){
        var listOfSem =[];
        var programIndex =[];
        if($('#programCheck'+programList[i].id).is(':checked')){
            var noOfSemester = programList[i].semesterList.length
            alert(programList[i].semesterList.length)
            for(var j=0;j<programList[i].semesterList.length;j++){
                if($('#semCheckBox' + programList[i].id+programList[i].semesterList[j].id).is(":checked")){
                    listOfSem.push(programList[i].semesterList[j].id)
                }
            }
            programIndex.push(programList[i].id)
            programMap[programList[i].id]=listOfSem
            programSemester["id"]=programList[i].id
            programSemester["semesterList"] =listOfSem
            console.log("selected Sem"+programSemester)
        }else{
        }


        for(var k=0;k<programIndex.length;k++) {
            if($('#tab' + tabId +'Semester').val()!=''){
                $('#tab' + tabId +'Semester').val($('#tab' + tabId +'Semester').val()+"/"+programMap[programIndex[k]])
            }
            else{
                $('#tab' + tabId +'Semester').val(programMap[programIndex[k]])
            }
            if($('#tab' + tabId +'Program').val()!=''){
                $('#tab' + tabId +'Program').val($('#tab' + tabId +'Program').val()+" "+programList[i].id)
            }
            else{
                $('#tab' + tabId +'Program').val(programList[i].id)
            }
            $('#viewSelected' + tabId +' select').append('<option value="'+ programList[i].id+'/'+programMap[programIndex[k]]+'">'+ programList[i].programName+'( Semesters '+ programMap[programIndex[k]]+')</option>')
        }
    }
}

function togleProgram(id){
    $("#"+id).toggle()
}
function initializeDailog(){
    $(".dialogTab1").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center', 0],
        width: 850,
        resizable: true,
        height: 750,
        modal: true,
        title:'Assign Semesters',
        close: function(ev, ui) {
            $.unblockUI();
        }
    });
}

function appendTab(programList,tabId){
    $('#dialogTab').empty().append(' <div> <button name="submit" id="saveSelectedSemesters" onclick="return saveSelectedSemesters('+tabId+')">Save</button></div>')
      $('#dialogTab').append('<div style="height: 550px; width:750px;  border: 1px" id="tabulator'+tabId+'"> </div>')
//    alert(programList.length)
    $('#tabulator'+tabId).empty().append('')
    for(var i=0;i<programList.length;i++){
           $('#tabulator'+tabId).append('<div><h5> <input type="checkbox"  name= "programCheck'+programList[i].id+'" id="programCheck'+programList[i].id+'"  value="" ' +
            'onclick="togleProgram('+programList[i].id+')"/>'+programList[i].programName+'</h5></div>' +
            '<div id="'+programList[i].id+'" hidden="hidden"/></div>');
        $('#'+programList[i].id).empty().append('')
         for(var j=0;j<programList[i].semesterList.length;j++){
              $('#'+programList[i].id).append('<div id="checkboxes'+programList[i].id+'" name="checkboxes'+programList[i].id+'">' +
                  '<input type="checkbox" id="semCheckBox'+programList[i].id+programList[i].semesterList[j].id+'" name="semCheckBox'+programList[i].id+'" />' +
                  '<label>'+programList[i].semesterList[j].semesterNo+' Semester</label>' +
                  '</div>')
         }
    }
    for (var l=1; l<=$('#viewSelected'+tabId+' select option').length;l++){
        var arrProgram=$('#viewSelected'+tabId+' select option:nth-child('+l+')').val().split('/')
        $('#programCheck'+arrProgram[0]).click()
        var arrSem=arrProgram[1].split(',')
        for (var m=0; m<arrSem.length;m++){
            $('#semCheckBox'+arrProgram[0]+''+arrSem[m]).prop('checked', true);
        }
    }
    var otherTab
    if(tabId==1){
        otherTab=2
    }
    else{
        otherTab=1
    }
    if($('#viewSelected'+otherTab + ' select option').length>0) {
        for (var l = 1; l <= $('#viewSelected' + otherTab + ' select option').length; l++) {
            var arrProgram = $('#viewSelected' + otherTab + ' select option:nth-child(' + l + ')').val().split('/')
            var arrSem = arrProgram[1].split(',')
            var totalCheckedSem=arrSem.length
            var totalSem=$("input[name='semCheckBox"+arrProgram[0]+"']").length
            if(totalSem==totalCheckedSem){
                $('#programCheck' + arrProgram[0]).prop('disabled', true);
            }
            else{
                for (var m = 0; m < arrSem.length; m++) {
                    $('#semCheckBox' + arrProgram[0] + '' + arrSem[m]).prop('disabled', true);
                }
            }
        }
    }

}

