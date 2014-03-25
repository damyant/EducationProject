/**
 * Created by sonali on 3/13/14.
 */

var subjectList

function  semesterList(){

 $('#multiSelectTab tbody tr').remove()
    for(var j=1;j<=$('#terms').val();j++){
        $('#multiSelectTab tbody').append('<tr><td style="width:40% "></div> <label>All Subjects</label><select style="width: 90%" name="allsubjectList'+j+'" id="allsubjectList'+j+'"  multiple="true"  /></td>'+
            ' <td <td style="width:20% "> <button type="button" class="multiSelect-buttons-button" onclick="addToList('+j+')" name="add'+j+'"  id="add'+j+'">Add</button>'+
            '  <button type="button" class="multiSelect-buttons-button" onclick="removeFromList('+j+')" name="remove'+j+'"  id="remove'+j+'">Remove</button> </td>'+
            '<td <td style="width:40% "><select class="select-to" style="width: 90%"  name="semester'+j+'" id="semester'+j+'"  multiple="true"  /></td></tr>' )

        if($('#mode option:selected').text()=="annual"){
            $("<div>Term"+j+"</div>").insertBefore($('#semester'+j))
        }
        else if(($('#mode option:selected').text()=="semester")){
            $("<div>semester"+j+"</div>").insertBefore($('#semester'+j))
        }



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
                $('#allsubjectList'+j+' option').filter(function(){
                    return $(this).val() == text3;
                }).attr('selected',false);
            });
});
    });
}


//$(document).ready(function(){
//$("#createCourse").validate({
//    rules:{
//
//        courseName :{
//            required:true
//        },
//        courseMode:{
//            required:true
//        },
//        courseType :{
//            required:true
//        },
//        noOfTerms:{
//            required:true
//        },
//        courseCode:{
//            required:true
//        },
//        noOfAcademicYears:{
//            required:true
//        },
//        totalMarks:{
//            required:true
//        },
//        noOfPapers:{
//            required:true
//        },
//        passMarks:{
//            required:true
//        },
//        totalCreditPoints:{
//            required:true
//        }
//    },
//
//    messages:{
//
//        courseName:"please Enter your Name",
//        courseMode:"please Enter course mode",
//        courseType:"please Enter your Course Type",
//        noOfTerms:"please Enter Number of terms",
//        courseCode:"please Enter your Course Code ",
//        noOfAcademicYears:"please enter your Academic years",
//        totalMarks:"please Enter Total Marks",
//        noOfPapers:"please Enter Number of papers",
//        passMarks:"please Enter Passing Marks",
//        totalCreditPoints:"Please Enter total Credit Points"
//  },
//
//    submitHandler: function(form) {
//        var formObj = $("#createCourse");
//        var data = ConvertFormToJSON(formObj);
//        $.ajax({
//           type: "post",
//           url: url('course', 'saveCourse', ''),
//           data: JSON.stringify(data),
//           contentType: 'application/json; charset=utf-8',
//           dataType: 'json',
//           success: function (data) {
//               if(data.response1){
//                   document.getElementById("statusMessage").style.display = "block";
//               }
//               clearField();
//
//           }
//        });
//
//    }
//
//})
//    fireMultiValidate();
//
//});

function fireMultiValidate(){
            for(var i=1;i<=$("#terms").val();i++){
                if(document.getElementById('semester'+i).options.length==0){
                    $('#semester'+i).after("<label style='margin-left: 4px; color: #cd0a0a; '>please choose subjects for semesters</label>")
                }

}
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

    }
    finalList.push(semesterList);

    json["semesterList"] = finalList;

    return json
}

function clearField(){

    for(var i=1;i<=$('#terms').val();i++){
        $('#semester'+i).empty();
    }
    $( '#createCourse' ).each(function(){
        this.reset();
    });


}
function save() {
    fireMultiValidate();
    validate();
    var status = $("#createCourse").valid();


    if (status ) {
        var formObj = $("#createCourse");
        var data = ConvertFormToJSON(formObj);

        $.ajax({
            type: "post",
            url: url('course', 'saveCourse', ''),
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                if(data.response1){
                    document.getElementById("statusMessage").style.display = "block";
                }
                clearField();

            }
        });
    }
}