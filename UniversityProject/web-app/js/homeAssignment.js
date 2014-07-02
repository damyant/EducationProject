function searchHomeAssignmentByRollNumber(){
    if($('#rollNumberInput').val().length!=0)
    {
        $.ajax({
            type: "post",
            url: url('homeAssignment', 'searchDataByRollNumber', ''),
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
    if(data['programType']=='Traditional'){
        for(var i=1;i<=data['totalYears'];i++){
            $("#checkTerms").append('<label>Term'+i+'</label><input type="checkbox" name="terms"/>')
        }
    }
    else{
        for(var i=1;i<=data['totalYears']*2;i++){
            $("#checkTerms").append('<label>Semester'+i+'</label><input type="checkbox" name="terms"/>')
        }

    }
}