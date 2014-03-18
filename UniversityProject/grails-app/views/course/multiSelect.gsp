<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 3/14/14
  Time: 11:04 AM
--%>

<div class="multiSelect" id="multiSelect-${index}">
    <div>
        <label>Semester-${index+1}</label>
    </div>
    %{--<g:select name="selectfrom" id="select-from" optionKey="id" optionValue="subjectName" from="${Subject.findAll()}" multiple="5"  />--}%
    <g:select id="list-1-${index}" name="list-1-${index}" from="${list1?.subjectName}" multiple="true"/>
    <span class="multiSelect-buttons">
        <button type="button" name="add-${index}" onclick="addToList('${index}')">Add</button>
        <button type="button"  name="remove-${index}" onclick="removeFromList('${index}')">Remove</button>
    </span>
    <g:select id="list-2-${index}" name="list-2-${index}" from="${list2}" multiple="true"/>
</div>

<script type="text/javascript">
    function addToList(index){
        var selectedValues=[];
        var nonSelected=[];
        var inList2;
        $('#list-1-'+index+' :selected').each(function(i,list1Selected
                ){
            selectedValues[i]=$(list1Selected).val();
            inList2 = false;
            $('#list-2-'+index+' option').each(function(j,list2Selected){
                nonSelected[j]=$(list2Selected).val();
                if(selectedValues[i]==nonSelected[j]){
                    inList2 = true;
                }
            });

            if(inList2!=true){
                $('#list-2-'+index).append("<option value='"+selectedValues[i]+"'>"+$(list1Selected).text()+"</option>");
            }
        });
    }

    function removeFromList(index){
        $('#list-2-'+index+' option:selected').each( function() {
            $(this).remove();
        });
    }
</script>