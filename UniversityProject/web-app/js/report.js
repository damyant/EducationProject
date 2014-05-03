
$(function() {
    $(".dialog").dialog({
        autoOpen: false,
//        maxWidth:600,
//        maxHeight: 500,
        width: 500,
        height: 200,
        modal: true,
        title:'Enter Details',
        close: function(ev, ui) { getStudentsList()}

    });

  setSessions()


    $('#session').on('click', function(){
        alert("clicked")
        openPopUp(1)
    })
    $('#sessions').on('click', function(){
        alert("clicked")
        openPopUp(2)
    })
    $('#course').on('click', function(){
        alert("clicked")
        openPopUp(3)
    })
    $('#studyCentre').on('click', function(){
        alert("clicked")
        openPopUp(4)
    })
    $('#examinationCentreDiv').on('click', function(){
        alert("clicked")
        openPopUp(5)
    })
});



function openPopUp(value){
    alert(value)
    if(value==1){
        alert("condition is true")
        $('#flagValue').val('session')
        $('tr').hide()
        $("#bySession").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
   else if(value==2){
        alert("condition is true")
        $('#flagValue').val('sessions')
        $('tr').hide()
        $("#bySessions").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==3){
        $('#flagValue').val('course')
        alert("condition is true")
        $('tr').hide()
        $("#byCourse").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==4){
        alert("condition is true")
        $('#flagValue').val('studyCentre')
        $('tr').hide()
        $("#byStudyCentre").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==5){
        $('tr').hide()
        $('#flagValue').val('examinationCentre')
        $("#byExaminationCentre").show()
        $("#examCenterSelect tr").show()
        $("#submitButton").show()
        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
}



function setSessions(){
    $.ajax({
        type: "post",
        url: url('report', 'getSessionList', ''),
        async: false,
        data: '',
        success: function (data) {
            $(".allSession").empty().append('')
            for (var i = 0; i < data.length; i++) {
                $(".allSession").append('<option value="' + data[i] + '">' + data[i] + '</option>')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }

    });
}