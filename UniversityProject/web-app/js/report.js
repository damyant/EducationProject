
$(function() {
    $(".dialog").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center',150],
//        maxWidth:600,
//        maxHeight: 500,
        width: 600,
        resizable: false,
        height: 210,
        modal: true,
        title:'Enter Details',
        close: function(ev, ui) { getStudentsList()}

    });
    $("#feeToDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });
    $("#feeFromDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });

  setSessions()


    $('#session').on('click', function(){
//        alert("clicked")
        openPopUp(1)
    })
    $('#sessions').on('click', function(){
//        alert("clicked")
        openPopUp(2)
    })
    $('#course').on('click', function(){
//        alert("clicked")
        openPopUp(3)
    })
    $('#studyCentreNoOfStudents').on('click', function(){
//        alert("clicked")
        openPopUp(4)
    })
    $('#examinationCentreNoOfStudents').on('click', function(){
//        alert("clicked")
        openPopUp(5)
    })
    $('#categoryDiv').on('click', function(){
//        alert("clicked")
        openPopUp(6)
    })
    $('#categoryGenderDiv').on('click', function(){
//        alert("clicked")
        openPopUp(7)
    })
    $('#admissionReportUnapproved').on('click', function(){
//        alert("clicked")
        openPopUp(8)
    })
    $('#admissionReportSelfRegister').on('click', function(){
//        alert("clicked")
        openPopUp(9)
    })
    $('#feePaidStudyCentre').on('click', function(){
//        alert("clicked")
        openPopUp(10)
    })
    $('#dailyFeeReport').on('click', function(){
//        alert("clicked")
        openPopUp(11)
    })

    $('#comparativeReport').on('click', function(){
//        alert("clicked")
        openPopUp(12)
    })

    $('#studyCentreStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(13)
    })

    $('#examinationCentreStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(14)
    })

    $('#categoryGenderStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(15)
    })
    $('#sessionStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(16)
    })

    $('#byCourseUnapproved').on('click', function(){
//        alert("clicked")
        openPopUp(17)
    })
    $('#byCourseApproved').on('click', function(){
//        alert("clicked")
        openPopUp(18)
    })
    $('#admissionApproved').on('click', function(){
//        alert("clicked")
        openPopUp(19)
    })
    $('#byCumulativeCandidateNo').on('click', function(){
//        alert("clicked")
        openPopUp(20)
    })
});



function openPopUp(value){
//    alert(value)
    if(value==1){
//        alert("condition is true")
        $('#flagValue').val('session')
        $('#inExcel').val('')
        $('tr').hide()
        $("#bySession").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
   else if(value==2){
//        alert("condition is true")
        $('#flagValue').val('sessions')
        $('tr').hide()
        $("#bySessions").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==3){
        $('#flagValue').val('course')
//        alert("condition is true")
        $('tr').hide()
        $("#byCourse").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==4){
//        alert("condition is true")
        $('#flagValue').val('studyCentre')
        $('#inExcel').val('')
        $('tr').hide()
        $("#byStudyCentre").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==5){
        $('tr').hide()
        $('#flagValue').val('examinationCentre')
        $('#inExcel').val('')
        $("#byExaminationCentre").show()
        $("#examCenterSelect tr").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==6){
        $('tr').hide()
        $('#flagValue').val('category')
        $("#byCategory").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==7){
        $('tr').hide()
        $('#flagValue').val('categoryGender')
        $("#byCategoryGender").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==8){
        $('tr').hide()
        $('#flagValue').val('admissionUnapproved')
        $("#byAdmissionUnapprovedReport").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==9){
        $('tr').hide()
        $('#flagValue').val('admissionSelfRegistration')
        $("#byAdmissionSelfRegistration").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==10){
        $('tr').hide()
        $('#flagValue').val('studyCentreFeePaid')
        $("#byStudyCentreFeePaid").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==11){
        $('tr').hide()
        $('#flagValue').val('dailyFeePaid')
        $("#byDailyFeeReport").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')

    }

    else if(value==12){
        $('tr').hide()
        $('#flagValue').val('sessionsComparative')
        $("#bySessionsComparative").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')

    }
    else if(value==13){
        $('#flagValue').val('studyCentre')
        $('#inExcel').val('true')
        $('tr').hide()
        $("#byStudyCentre").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')

    }

    else if(value==14){
        $('tr').hide()
        $('#flagValue').val('examinationCentre')
        $('#inExcel').val('true')
        $("#byExaminationCentre").show()
        $("#examCenterSelect tr").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==15){
//                alert("condition is true" + value)
        $('tr').hide()
        $('#flagValue').val('categoryStudentList')
        $("#categoryStudentList").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==16){
//        alert("condition is true")
        $('#inExcel').val('true')
        $('tr').hide()
        $('#flagValue').val('session')
        $("#bySessionStudentList").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }

    else if(value==17){
        $('tr').hide()
        $('#flagValue').val('courseUnapproved')
        $("#courseUnapprovedStudyCentre").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==18){
        $('tr').hide()
        $('#flagValue').val('courseApproved')
        $("#courseApprovedStudyCentre").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==19){
        $('tr').hide()
        $('#flagValue').val('admissionApproved')
        $("#byAdmissionApprovedReport").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }

    else if(value==20){
        $('tr').hide()
        $('#flagValue').val('examinationCentreCumulative')
        $('#district').val('')
        $('#inExcel').val('')
        $("#byExaminationCentre").show()
        $("#examCenterSelectCumulative tr").show()
        $("#submitButton").show()
//        alert("condition is true")
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
                $(".allSession").append('<option value="' + data[i] + '">' + data[i]+'-'+ (data[i]+1) + '</option>')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }

    });
}