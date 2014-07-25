/**
 * Created by chandan on 7/18/2014.
 */

var programIdList=[]
var programList = []
function openTabulator() {

    $('#tab1').click(function () {
        initializeDailog1();


        if ($('#tab1').is(':checked')) {
            $.ajax({
                type: "post",
                url: url('user', 'getProgramList', ''),
                data: {},
                success: function (data) {
                    if (data) {
                        programList = data
                        appendTab(programList)
                    }
                    else {
                        $('#errorMsg').text("")
                        return true
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
            $('.dialogTab1').dialog('open');
            console.log("in js"+programList.length)
//            for(){
//
//            }
           // appendTab($(programList))
        }
    });
    $('#tab2').click(function () {
        if ($('#tab2').is(':checked')) {
            $('.dialogTab2').dialog('open');
        }
    });
}

function saveSelectedSemesters() {

    var programMap={};

    var noOfPrograms = programIdList.length
    alert(noOfPrograms)

    for(var i=0;i<noOfPrograms;i++){
        var listOfSem =[];
        if($('#programCheck'+programIdList[i]).is(':checked')){
            var noOfSemester = $('input[name=programSemster'+programIdList[i]+']').length
            alert(programIdList[i])
            for(var j=1;j<=noOfPrograms;j++){
                if($('#programSemster' + programIdList[i]+j).is(":checked")){
//                 alert("Checked"+j)
                    listOfSem.push(j)
                }

            }
            programMap[programIdList[i]]=listOfSem
            console.log("list of sem"+listOfSem)
        }else{
            //  alert("hi")
        }
        console.log("final list "+ programMap)

    }
}

function togleProgram(id){

    $("#"+id).toggle()

}
function initializeDailog1(){
    alert("hi")
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

function initializeDailog2(){
    $(".dialogTab2").dialog({
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

function ConvertGroupFormToJSON(id) {
    var json = {};
    var finalList = new Array();
    var i = 0;
    var groupMap = {};
    var rowCount = $('#subjectGroup'+id+' tr').length;

    for (var j = 0; j < rowCount; j++) {
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


function appendTab(programList){
    alert("programlist size"+programList.length)
    for(var i=0;i<programList.length;i++){
        $('#tabulator1').append('<div><h5> <input type="checkbox"  name= "programCheck'+programList[i].id+'" id="programCheck'+programList[i].id+'"  value="" ' +
            'onclick="togleProgram('+programList[i].id+')"/>'+programList[i].programName+'</h5></div>' +
            '<div id="'+programList[i].id+'" hidden="hidden"/></div>');
         for(var j=0;j<programList[i].noOfSemester;j++){
              $('#'+programList[i].id).append('<div id="checkboxes'+programList[i].id+'" name="checkboxes'+programList[i].id+'"' +
                  '<input type="checkbox" name="checkbox"/>' +
                  '<label>'+(j+1)+' Semester</label>' +
                  '</div>')
         }

    }

}