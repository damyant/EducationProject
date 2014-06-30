



var maxCapacity=0;
var availableCapacity=0;
var i ;
var selected = new Array();
var updatedAvailable=0;
var totalRows= 0;


$(document).ready(function () {


    var count=0;
   // $("input[name='studentCheckbox']").change(function () {
    $(document).on('change', "input[name='studentCheckbox']", function () {
        $("#from").val("");
        $("#to").val("");
        $('#remainingCapacityBox').prop('hidden', false);
        var maxAllowed = availableCapacity;
        count = $("input[name='studentCheckbox']:checked").length;
        if(availableCapacity>0)

            $("#remainingCapacity").val(availableCapacity-count);

        if (count>availableCapacity) {
            $("#remainingCapacity").val(count-1);
            $(this).prop("checked", "");
            $("#remainingCapacity").val(0);
            alert('You can select maximum ' + maxAllowed + ' Students only!!');
        }
    });


    $(document).on('change', "input[name='selectAll']", function () {
        $("#from").val("")
        $("#to").val("")
        var updatedAvailable=availableCapacity;
        for(i=0;i<updatedAvailable;i++)
            $('#admitCardTab').find('#rowID'+i).find('input[type="checkbox"]').prop('checked',$("#selectAll").prop("checked"))

        $('input:checked').each(function() {
            selected.push($(this).attr('id'));
        });
        var selectedRows=selected.length-1

        if($("#selectAll").prop('checked')==false){
          $("#remainingCapacity").val(updatedAvailable);
        }else{

            $("#remainingCapacity").val(updatedAvailable-selectedRows);
            selectedRows=0;
            selected.length=0;
        }

    })

    $(document).on('change', "input[name='to']", function () {
        if($("#from").val().length==0){
            alert("Please enter range correctly")
            return false
        }else{
           selectRows();
        }

    });
    $(document).on('change', "input[name='from']", function () {

        if($("#to").val().length==0 && $("#to").val()==""){
            return false
        }else{
            selectRows();
        }

    });
});



function selectRows(){
    var from=$("#from").val()
    var to = $("#to").val()
    $("#selectAll").prop('checked',false)
    updatedAvailable=availableCapacity

    if(from.length==0){
        alert("Please Enter from Sr No.")
    }
    var selectedRange=0;
    if(to>=from){
        selectedRange = (to-from)
    }else{
        alert("Please enter range correctly")
        return false
    }

    var rangeCount = parseInt(from)+selectedRange;
    for(i=from-1;i<rangeCount;i++)
        $('#admitCardTab').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', true)

    for(i=to;i<totalRows;i++)
        $('#admitCardTab').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', false)
    for(i=from-2;i>=0;i--)
        $('#admitCardTab').find('#rowID'+i).find('input[type="checkbox"]').prop('checked', false)
    $('input:checked').each(function() {
        selected.push($(this).attr('id'));
    });

    var selectedRows=selected.length;
    $("#remainingCapacity").val(updatedAvailable-selectedRows)
    selected.length=0;
}

function hideAll(){
    $('#studyCenterFeeEntryTable').prop('hidden', true)
    $('#allProgram').prop('checked', false)
    $('#rangeRadioButtons').prop('hidden', true)
//    $('#paymentDetails').prop('hidden', true)
    document.getElementById("paginationDiv").style.visibility = "hidden";
    document.getElementById("paymentDetails").style.visibility="hidden"
    document.getElementById("generateFeeChallan").style.visibility="hidden"
//    $('#generateFeeChallan').prop('hidden', true)
}
function getSemester(t){
    if($("#paginationDiv").length>0) {
        document.getElementById("paginationDiv").style.visibility = "hidden";
    }
    $('#studyCenterFeeEntryTable').prop('hidden', true)
    $('#allProgram').prop('checked', false)
    $('#rangeRadioButtons').prop('hidden', true)
    var test= $("#paymentDetails").length
    if(test>0) {
        document.getElementById("paymentDetails").style.visibility = "hidden"
    }
    if($("#generateFeeChallan").length>0) {
        document.getElementById("generateFeeChallan").style.visibility = "hidden"
    }
//    $('#generateFeeChallan').prop('hidden', true)
    var data = $(t).val();
    $('#semesterList').prop('disabled',false)
    $('#SessionList').prop('disabled',false)
    if(data){
        $.ajax({
            type: "post",
            url: url('admitCard', 'getSemesterList', ''),
            data: {data: data},
            success: function (data) {
                $("#semesterList").empty().append('data <option value="">Select Semester</option>')

                $("#SessionList").empty().append('data <option value="">Select Session</option>')

                for (var i = 1; i <= data.totalSem; i++) {
                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
                }
                for (var i = 0; i < data.session.length; i++) {
                    $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
                }
            }
        })
    }
    else{
        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
        $("#SessionList").empty().append('data <option value="">Select Session</option>')
        $("#sessionType").val(0)
        $("#subjectList").empty();
    }
}
function getTermByCatagory(t){
    var data = $(t).val();
    var catagory = $('#programCategory').val();
    $('#semesterList').prop('disabled',false)
    $('#SessionList').prop('disabled',false)
    if(data){
        $.ajax({
            type: "post",
            url: url('admitCard', 'getTermListByCatagory', ''),
            data: {data: data,catagory:catagory},
            success: function (data) {
                $("#semesterList").empty().append('data <option value="">Select Term</option>')

                for (var i = 1; i <= data.programlist; i++) {
                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
                }
            }
        })
    }
    else{
        $("#semesterList").empty().append('data <option value="">Select Term</option>')
    }

}
function setType(){
    $("#sessionType").val(0)
    $("#subjectList").empty();
}



function getSemesterForAttendance(t){
    var data = $(t).val();
    if(data){
    $('#semesterList').prop('disabled',false)
    $('#SessionList').prop('disabled',false)
    if(data=='allProgram'){
    $.ajax({
        type: "post",
        url: url('admitCard', 'getSemesterList', ''),
        data: {data: data},
        success: function (data) {
            $("#semesterList").empty().append('data <option value="allSemester">All Semester</option>')
            $("#SessionList").empty().append('data <option value="allSession">All Session</option>')
            for (var i = 1; i <= data.totalSem; i++) {
                $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
            }
            for (var i = 0; i < data.session.length; i++) {
                $("#SessionList").append('<option value="' + data.session[i] + '">' + data.session[i] + '</option>')
            }
        }
    })
  }
    else{

        $.ajax({
            type: "post",
            url: url('admitCard', 'getSemesterList', ''),
            data: {data: data},
            success: function (data) {
                $("#semesterList").empty().append('data <option value="">Select Semester</option>')
                $("#SessionList").empty().append('data <option value="">Select Session</option>')
                for (var i = 1; i <= data.totalSem; i++) {
                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
                }
                for (var i = 0; i < data.session.length; i++) {
                    $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
                }
            }
        })
    }
    }
    else{
        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
        $("#SessionList").empty().append('data <option value="">Select Session</option>')
        $('#semesterList').prop('disabled',true)
        $('#SessionList').prop('disabled', true)
    }

    $("#semesterList").attr('enabled',true)

}


function showStudentInfo(){
    document.getElementById("admitCardTab").style.display = "block";
    document.getElementById("subjectTab").style.display = "block";
$("#admitCardTab ").append('<tr><th>Roll Number</th><th>Name of the Student</th><th>Select Student</th><th></th></tr>')
 $('#subjectTab').append('<tr><th>Subject</th><th>Subject-Code</th><th>Time Of Exam</th><th>Date Of Exam</th></tr>')
}

function showExamVenueList() {


    var data = $('#examinationCentre').val();

    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: "examinationCentre="+$('#examinationCentre').val()+"&programList="+$('#programList').val(),
        success: function (data) {
            $("#examCenterList").empty().append('data <option value="">Select Examination Venue</option>')

            for(var i=0;i<data.assocaitedExamVenue.length;i++){

                $("#examCenterList").append('<option value="' + data.assocaitedExamVenue[i].id + '">' + data.assocaitedExamVenue[i].name + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function showExamVenueCapacity(){

    $.ajax({
        type: "post",
        url: url('admitCard', 'examVenueCapacity', ''),
        data: {examVenueId: $("#examCenterList").val()},
        success: function (data) {
        if(data.capacity){

            $('#totalCapacity').val(data.capacity)
            $('#remainingCapacity').val(data.availabelCapacity)

            $('#maxCapacityBox').prop('hidden', false)
            $('#remainingCapacityBox').prop('hidden', false)
            maxCapacity=data.capacity
            availableCapacity=data.availabelCapacity
        }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}


function getStudentsForAdmitCard(){

    $.ajax({
        type: "post",
        url: url('admitCard', 'getStudentsForAdmitCard', ''),
        data: $("#admitCardForm").serialize(),
        success: function (data) {

            $('#admitCardTab').find("tr:gt(0)").remove();
              if(data.length!=undefined){
                  $('#studentListTable').prop('hidden', false)
                  $('#studentListPrint').prop('hidden', false)
                  $('#studentListPrintButton').prop('hidden', false)
                  var count=1;
                 for(var i=0;i<data.length;i++){
                       $('#admitCardTab tbody').append('<tr id="rowID'+i+'"><td><input name="studentCheckbox" class="studentCheckbox" type="checkbox" id='+data[i].id+'></td><td>'+count+'</td><td>'+data[i].rollNo+'</td><td>'+data[i].firstName+' '+data[i].lastName+'</td></tr>')
                    ++count;
                }
                  totalRows=count;
                  document.getElementById("paginationDiv").style.visibility = "visible";
                  $table_rows = $('#admitCardTab tbody tr');

                  var table_row_limit = 10;

                  var page_table = function(page) {

                      // calculate the offset and limit values
                      var offset = (page - 1) * table_row_limit,
                          limit = page * table_row_limit;

                      // hide all table rows
                      $table_rows.hide();

                      // show only the n rows
                      $table_rows.slice(offset, limit).show();

                  }
                  var pageNo=0
                  if($table_rows.length % table_row_limit){
                      pageNo=parseInt(parseInt($table_rows.length) / table_row_limit)+1
                  }
                  else{
                      pageNo=parseInt($table_rows.length / table_row_limit)
                  }
//                alert(5%5)
                  $('.pagination').jqPagination({
                      max_page: pageNo,
                      paged: page_table
                  });
                  page_table(1);
            }
            else{
                  document.getElementById("paginationDiv").style.visibility = "hidden";
                  $('#showErrorMessage').prop('hidden', false)
                  $('#showErrorMessage').text('No Students Found');
                  $('#studentListTable').prop('hidden', true)
                  $('#studentListPrint').prop('hidden', true)
                  $('#studentListPrintButton').prop('hidden', true)
//                  setTimeout(function(){  $('#showErrorMessage').hide(); }, 8000);
              }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}



function showExamVenueList1(){
    $('#addExamCentre').empty();
    var data = $('#examinationCentre').val();
    $('#CentreForExamVenue').html($('#examinationCentre option:selected').text());
    if(data){
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: $('#assignExamVenue').serialize(),
        success: function (data) {
            $('#submitButton').show()
            $("#examCenterList").empty().append('')
            for (var i = 0; i < data.name.length; i++) {
                $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
                $("#moveButton").css("visibility", 'visible');
                $("#movetoSelect").css("visibility", 'visible');
            }
//                alert(data.assocaitedExamVenue)
            for(var j=0;j<data.assocaitedExamVenue.length;j++){

                $("#addExamCentre").append('<option value="' + data.assocaitedExamVenue[j].id + '">' + data.assocaitedExamVenue[j].name + '</option>')
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    }
    else{
        $('#submitButton').hide();
        $("#examCenterList").empty();
        $("#moveButton").css("visibility", 'hidden');
        $("#movetoSelect").css("visibility", 'hidden');
    }
}

function addVenue(param){
//    alert('kiii')
    var venue=$(param).find(":selected").text()
    var centre=$('#city option:selected').text()
    var course=$('#programList option:selected').text()
    $("#examVenueList").append('<tr><td>'+course+'</td><td>'+centre+'</td><td>'+venue+'</td></tr>');
}

function addExamCenterToList() {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#examCenterList :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;
        $('#addExamCentre option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#addExamCentre').append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
            $('#examCenterList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#examCenterList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
}

function removeExamCenterFromList() {
    $('#addExamCentre option:selected').each(function () {
        $(this).remove();
        $('#addExamCentre option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()
            $('#examCenterList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#examCenterList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
}

function setCourseLabel(t){
//    $('#assignExamVenue').reset();
    $('#courseForExamVenue').html($(t).find(":selected").text());
    $('#CentreForExamVenue').html('');
    $('#city').val('');
    $('#examCenterList').empty();
    $('#addExamCentre').empty();
    $("#examinationCentre").val(0)
    $("#examinationCentre").prop("disabled", false)

}

function generateAdmitCard(){

    var selectedStudentList=[]
    if ($("input[name=studentCheckbox]:checked").length != 0) {
        $("input[name=studentCheckbox]:checked").each(function (i) {

            if ($(this).attr("checked", true)) {
                selectedStudentList[i] = $(this).attr("id");

            }

        })
        $("#studentList").val(selectedStudentList)
        var studentList =$("#studentList").val()
        var venue =$("#examCenterList").val()
        var programSessionId=$("#SessionList").val()
        window.open('/UniversityProject/admitCard/printAdmitCard/?studentList='+studentList+'&examinationVenue='+venue+'&programSessionId='+programSessionId);
        //$("#admitCardForm").submit();
//        studentsSelected(selectedStudentList)

        setTimeout(function(){ getStudentsForAdmitCard()},300);

        return true;

    }
    else {
        alert("Select the student first.");
        return false;
    }




}

function studentsSelected(selectedStudentList){
    $.ajax({
        type: "post",
        url: url('admitCard', 'printAdmitCard', ''),
        data: "studentList="+selectedStudentList,
        success: function (data) {
            $('#admitCardTab').find("tr:gt(0)").remove();
            if(data.length!=undefined){
                var count=1;
                for(var i=0;i<data.length;i++){
                    $('#admitCardTab').append('<tr><td><input type="checkbox" id='+data[i].id+'></td><td>'+count+'</td><td>'+data[i].rollNo+'</td><td>'+data[i].studentName+'</td></tr>')
                    ++count;
                }
            }
            else{
                $('#admitCardTab').append('<tr><td colspan="4">No Students Found</td></tr>');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
function enableShowCandidate(){

    $('#studentListTable').prop('hidden', true)
    $('#studentListPrint').prop('hidden', true)
    $('#studentListPrintButton').prop('hidden', true)
    if($('#examinationCentre').val()==''||$('#programList').val()==''||$('#semesterList').val()==''||$('#SessionList').val()==''||$('#examCenterList').val()==''){
        $('#showCandidates').prop('disabled', true)    }
    else{
        $('#showCandidates').prop('disabled', false)
    }
}


function downloadAdmitCard(){
    validate();
    var result = $('#individualDownloadAdmitCard').valid()
    if(result) {
        $("#individualDownloadAdmitCard").submit();
    }
}








