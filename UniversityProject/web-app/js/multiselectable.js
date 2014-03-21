/**
 * Created by sonali on 3/13/14.
 */
var subjectList

function  semesterList(){

 $('#multiSelectTab tbody tr').remove()
    for(var j=1;j<=$('#noOfTerms').val();j++){

        $('#multiSelectTab tbody').append('<tr><td style="width:40% "></div> <select  style="width: 90%" name="allsubjectList'+j+'" id="allsubjectList'+j+'"  multiple="true"  /></td>'+
            ' <td <td style="width:20% "> <button type="button" class="multiSelect-buttons-button" onclick="addToList('+j+')" name="add'+j+'"  id="add'+j+'">Add</button>'+
            '  <button type="button" class="multiSelect-buttons-button" onclick="removeFromList('+j+')" name="remove'+j+'"  id="remove'+j+'">Remove</button> </td>'+
            '<td <td style="width:40% "><select style="width: 90%"  name="semester'+j+'" id="semester'+j+'"  multiple="true"  /></td></tr>' )



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

            var text1 = $(list1Selected).val()
//            alert(text1);
//            $('#semester'+j+' option').filter(function() {
//                //may want to use $.trim in here
//                return $(this).val() == text1;
//            }).attr('selected', true);
            $('#allsubjectList'+j+' option:selected').each( function(n,allsubSelected) {
                var text3=$(allsubSelected).val()
//                alert("textam"+text3);
                $('#allsubjectList'+j+' option').filter(function(){
                    return $(this).val() == text3;
                }).attr('selected',false);
            });
        }

    });
}

function removeFromList(j){
  $('#semester'+j+' option:selected').each( function() {
        $(this).remove();
        $('#semester'+j+' option:not(selected)').each( function(k,semSelected) {
        var text2=$(semSelected).val()
//        $('#semester'+j+' option').filter(function() {
//            return $(this).val() == text2;
//        }).attr('selected', true);
            $('#allsubjectList'+j+' option:selected').each( function(n,allsubSelected) {
                var text3=$(allsubSelected).val()
                alert("textam"+text3);
                $('#allsubjectList'+j+' option').filter(function(){
                    return $(this).val() == text3;
                }).attr('selected',false);
            });
});
    });
}


function submitForm(){


    var formObj = $("#createCourse");
    var data = ConvertFormToJSON(formObj);

    $.ajax({
        type: "post",
        url: url('course', 'saveCourse', ''),
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            //document.location.reload();
            clearFields()
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

    for(var j=1;j<=$('#noOfTerms').val();j++){

        var subList = []
    $('#semester'+j+' option').each(function(){
        subList.push($(this).val() || '');
        semesterList["semester"+j]=subList;
        console.log(semesterList)

    })

    }
    finalList.push(semesterList);
 console.log(finalList)

    json["semesterList"] = finalList;

    return json
}

function test(obj){

    var courseDetailJson=jQuery.parseJSON(obj.replace(/&quot;/g,'"'))

    $('#courseName').val(courseDetailJson['course'].courseName)
    $('#modeName option[value='+courseDetailJson['course'].courseMode.id+']').attr("selected", "selected");
    $('#courseTypeName option[value='+courseDetailJson['course'].courseType.id+']').attr("selected", "selected");
    $('#noOfTerms').val(courseDetailJson['course'].noOfTerms)
    $('#courseCode').val(courseDetailJson['course'].courseCode)
    $('#noOfAcademicYears').val(courseDetailJson['course'].noOfAcademicYears)
    $('#totalMarks').val(courseDetailJson['course'].totalMarks)
    $('#marksPerPaper').val(courseDetailJson['course'].marksPerPaper)
    $('#totalCreditPoints').val(courseDetailJson['course'].totalCreditPoints)
    $('#noOfPapers').val(courseDetailJson['course'].noOfPapers)
    semesterList()
    for(var i=1;i<= $('#noOfTerms').val();i++){
        for(var j=0;j<courseDetailJson['semesterList'][i].length;j++){
            $('#semester'+i).append('<option value="'+courseDetailJson['semesterList'][i][j].id+'">'+courseDetailJson['semesterList'][i][j].subjectName +'</option> ')
        }

    }



}

function clearFields(){

}
