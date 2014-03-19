/**
 * Created by sonali on 3/13/14.
 */
var subjectList

function  semesterList(){


    for(var j=1;j<=$('#terms').val();j++){

        $('#subjectDiv').append('<select name="semester'+j+'" id="semester'+j+'"  multiple="true"  />')
//        $('#subjectDiv').append('<select name="semester'+j+'" id="semester'+j+'"  multiple="true"  />')

    for(var i=0;i<subjectList.length;i++){

        $("#semester"+j).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
    }

    }
}

function makeJson(list){
    subjectList=jQuery.parseJSON(list.replace(/&quot;/g,'"'))

}

