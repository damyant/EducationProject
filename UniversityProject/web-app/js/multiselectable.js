/**
 * Created by sonali on 3/13/14.
 */
var subjectList

function  semesterList(){

 $('#multiSelectTab tbody tr').remove()
    for(var j=1;j<=$('#terms').val();j++){

        $('#multiSelectTab tbody').append('<tr><td><select name="allsubjectList'+j+'" id="allsubjectList'+j+'"  multiple="true"  /></td>'+
            ' <td> <button type="button" class="multiSelect-buttons" onclick="addToList('+j+')" name="add'+j+'"  id="add'+j+'">Add</button></td>'+
            ' <td> <button type="button" class="multiSelect-buttons" onclick="removeFromList('+j+')" name="remove'+j+'"  id="remove'+j+'">Remove</button> </td>'+
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

            var text1 = $(list1Selected).val()
//            alert(text1);
            $('#semester'+j+' option').filter(function() {
                //may want to use $.trim in here
                return $(this).val() == text1;
            }).attr('selected', true);
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
        $('#semester'+j+' option').filter(function() {
            return $(this).val() == text2;
        }).attr('selected', true);
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
