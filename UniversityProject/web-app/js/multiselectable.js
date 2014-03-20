/**
 * Created by sonali on 3/13/14.
 */
var subjectList

function  semesterList(){

 $('#multiSelectTab tbody tr').remove()
    for(var j=1;j<=$('#terms').val();j++){

        $('#multiSelectTab tbody').append('<tr><td><select name="allsubjectList'+j+'" id="allsubjectList'+j+'"  multiple="true"/></td>'+
            ' <td> <button type="button" onclick="addToList('+j+')" name="add'+j+'"  id="add'+j+'">Add</button></td>'+
            ' <td> <button type="button" onclick="removeFromList('+j+')" name="remove'+j+'"  id="remove'+j+'">Remove</button> </td>'+
            '<td><select name="semester'+j+'" id="semester'+j+'"  multiple="true"  /></td></tr>' )


    for(var i=0;i<subjectList.length;i++){

        $("#allsubjectList"+j).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
    }

    }
}

function makeJson(list){
    subjectList=jQuery.parseJSON(list.replace(/&quot;/g,'"'))

}
function addToList(j){
    var selectedValues=[];
    var nonSelected=[];
    var inList2;
    $('#allsubjectList'+j+' :selected').each(function(l,list1Selected
        ){
        selectedValues[l]=$(list1Selected).val();
        inList2 = false;
        $('#semester'+j+' option').each(function(m,list2Selected){
            nonSelected[m]=$(list2Selected).val();
            if(selectedValues[l]==nonSelected[m]){
                inList2 = true;
            }
        });

        if(inList2!=true){
            $('#semester'+j).append("<option value='"+selectedValues[l]+"'>"+$(list1Selected).text()+"</option>");

        }
    });
}

function removeFromList(j){
    $('#semester'+j+' option:selected').each( function() {
        $(this).remove();
    });
}


function test(){

    var semesterList=[]
    var formObj = $("#createCourse");
    for(var j=1;j<=$('#terms').val();j++){
        var a="semester1"
        var b="option"
        var subList = [];
        $('#semester'+j+' option').each(function()
        {
            subList.push($(this).val());
        })
        semesterList[j]=subList

    }

    var data = ConvertFormToJSON(formObj);
//    data : $('#form').serialize() + "&par1=1&par2=2&par3=232"
//    var data = studyCenterId;
    $.ajax({
        type: "post",
        url: url('course', 'saveCourse', ''),
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            //document.location.reload();

        }
    });

}

function ConvertFormToJSON(form){
    var array = jQuery(form).serializeArray();
    var json = {};
    var finalList=new Array();

    var i = 0;


    jQuery.each(array, function() {


            json[this.name] = this.value || '';


        i++;
    });
    var semesterList ={};
    for(var j=1;j<=$('#terms').val();j++){

        var subList = []
    $('#semester'+j+' option').each(function(){


         subList.push($(this).val() || '');
        semesterList["semester"+j]=subList;

    })
        console.log(semesterList)

    }
    finalList.push(semesterList);
    console.log(finalList)
    json["semesterList"] = finalList;
//    console.log(json)
    return json
}