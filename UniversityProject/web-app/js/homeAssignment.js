function searchHomeAssignmentByRollNumber(){
    if($('#rollNumberInput').val().length!=0)
    {
        $.ajax({
            type: "post",
            url: url('homeAssignment', 'searchHomeAssignmentByRollNumber', ''),
            data:'rollNumber='+$('#rollNumberInput').val(),
            success: function (data) {
                if(data.status==true){
                    appendTerms(data)
                    $("#errorMsgForRollNo").html("")
                }
                else{
                    //                alert("false")
                    $("#errorMsgForRollNo").html("No record Found")
                }
            }
        })
    }
}


function appendTerms(data){
    $("#semester").attr('disabled',false)
    $("#course").val(data['courseName'])
    $("#semester option").remove();
    $("#postFeeType option").remove();
    $("#checkTerms").empty()
    if(data['programType']=='Traditional'){
        for(var i=1;i<=data['totalYears'];i++){
            $("#checkTerms").append('<label id="'+i+'">Term'+i+'<input type="checkbox" value="'+i+'" name="terms" id="'+i+'"/></label>')

        }
    }
    else{
        for(var i=1;i<=data['totalYears']*2;i++){
            $("#checkTerms").append('<label id="semLabel'+i+'">Semester'+i+'<input type="checkbox" value="'+i+'" name="terms" id="'+i+'"/></label>')
        }
    }
    $('input[type=checkbox]').each(function (){
        var term = $(this).attr('id')
        for (var i=0; i<data.submitList.length;i++){
             if(term==data.submitList[i]){
                 $(this).attr('checked', 'checked')
                 if(data.user=='Admin'){
                 }
                 else{
                     $(this).attr('disabled', 'disabled')
                     $('#semLabel'+term).hide();
                 }
                  break;
             }
        }
    });
    var checkedBoxes = $('input[name=terms]:not(:checked)').length;
    if(checkedBoxes==0){
        if(data.user=='Admin'){

            $('#saveHomeAssignment').show()
        }
//        $("#statusMsgForRollNo").html("Assignment Is Already Submitted");
        else{
            $("#checkTerms").append('<label >Assignment Is Already Submitted</label>')
            $('#saveHomeAssignment').hide()
        }

    }
    else{
        $('#saveHomeAssignment').show()
    }
}