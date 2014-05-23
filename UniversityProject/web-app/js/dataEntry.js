/**
 * Created by shweta on 3/26/14.
 */


var studentList = [];
// document ready function............................................

$(document).ready(function () {


})


$(function () {
    $("#dialog").dialog({
        autoOpen: false,
//        maxWidth:600,
//        maxHeight: 500,
        width: 1000,
        height: 650,
        modal: true,
        title: 'Enter Fee Details',
        close: function (ev, ui) {
            getStudentsList()
        }

    });
});

function nextStudent() {
    var nextValue = $('#nextStudentId').val()
//    alert("Next button clicked-----------"+nextValue)
    openPopUp(nextValue)
}
function previousStudent() {
    var previousValue = $('#previousStudentId').val()
//    alert("hello------------------------------"+previousValue)
    openPopUp(previousValue)
}
function getStudentsList() {

    var date = $('#admissionDate').val()
//    alert(date)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getStudentList', ''),
        data: {studyCenterId: $('#studyCentre').val(), programId: $('#programId').val(), admissionDate: $('#admissionDate').val(), pageType: $('#pageType').val()},

        success: function (data) {

            appendDataTable(data)


        }
    });

}

function appendDataTable(data) {
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    if (data.stuList.length > 0) {
        $('#msg').html("")
        $('#responseMessage').html("")

        document.getElementById("studentList").style.visibility = "visible";
        $('#studentList thead').append('<tr><th>' + "Roll Number" + '</th><th>' + "Student Name" + '</th><th>' + "Fee Entry" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            studentList[i] = data.stuList[i]
            var ide = data.stuList[i].id

            $('#studentList tbody').append('<tr></td><td>' + data.stuList[i].rollNo + '</td><td>' + data.stuList[i].firstName + ' ' + data.stuList[i].lastName
                + '</td><td><button id="fee" onclick="openPopUp(' + data.stuList[i].id + ')">Fee Entry</button></td></tr>')

        }
    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }

}


function enablecriteria(t) {
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove();
    var op = $(t).val();


    if (op == 'By Program') {
        //  $('#programId').prop('disabled', false);
        $('#programl').show();
        $('#programv').show();
//        $('#studyCentre').val('')
    } else {
        $('#programl').hide();
        $('#programv').hide();
    }

    if (op == "By Study Centre") {
        $('#studyCentrel').show();
        $('#studyCentrev').show();
//        $('#programId').val('')
    } else {
        $('#studyCentrel').hide();
        $('#studyCentrev').hide();
    }

    if (op == "By Admission Date") {
        $('#admissionDatel').show();
        $('#admissionDatev').show();
    } else {
        $('#admissionDatel').hide();
        $('#admissionDatev').hide();
    }


}


function toggleChecked(status) {
    $(".checkbox").each(function () {
        $('input:checkbox:not(:disabled)').attr("checked", status)
    })
}

function openPopUp(studentId) {
    $("#dialog").dialog('open');
    for (var i = 0; i < studentList.length; i++) {
        if (studentList[i]) {
            if (studentList[i].id == studentId) {
                $('#rollNo').val(studentList[i].rollNo)
                $('#currentStudentId').val(studentList[i].id)
                if (i + 1 < studentList.length) {

                    $('#nextStudentId').val(studentList[i + 1].id)
                }
                else {
//              alert('in first else'+i)
                    $('#nextStudentId').val(studentList[0].id)
                }
                if (i - 1 >= 0) {
//              alert('in second if'+i)
                    $('#previousStudentId').val(studentList[i - 1].id)
                }
                else {
//              alert('in second else'+i)
                    $('#previousStudentId').val(studentList[studentList.length - 1].id)
                }
                break;
            }
        }
    }
}


function submitFeeDetail() {
    validate();
    var result = $('#createFeeDetail').valid()
    if (result) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'saveBulkFeeDetails', ''),
            async: false,
            data: $('#createFeeDetail').serialize(),
            success: function (response) {
                $('div#responseMessage').html(response);
                var current = $('#currentStudentId').val()
//            alert("value of current is "+current)
                for (var i = 0; i < studentList.length; i++) {
                    if (studentList[i]) {
                        if (studentList[i].id == current) {
                            delete studentList[i]
                            break;
                        }
                    }
                }
                console.log("---------------------------------------" + studentList)
                for (var i = 0; i < studentList.length; i++) {
                    if (studentList[i]) {
                        $('#createFeeDetail').trigger("reset");
//                    alert("Fill Fee Details For Next Student")
                        $("#next").click();
                        break;
                    }

                }
                $("#dialog").dialog('close');


            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                $('div#responseMessage').html("Fee details cannot be saved ");
                console.log("response in error")
            }

        });
    }
    else {
        return false;
    }

}

function populateStudentsForAllProgram(object) {
    $.ajax({
        type: "post",
        url: url('feeDetails', 'populateStudentsForAllProgram', ''),
        success: function (data) {


            appendStudentList(data)

        }

    });
}

function populateStudents(object) {
    var programId = $(object).val();
    $('#allProgram').prop('checked', false);
    if (programId) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'populateStudents', ''),
            data: {programId: programId},
            success: function (data) {


                appendStudentList(data)

            }
//

        });
    }
}


function getBankBranch(index) {
//alert("jdfjdjf"+index)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getBankBranch', ''),
        data: {bankId: $('#bankName' + index).val()},
        success: function (data) {
            $("#branch" + index).empty().append('<option value="">Select Branch</option>')
            for (var i = 0; i < data.length; i++) {
                $("#branch" + index).append('<option value="' + data[i].id + '">' + data[i].branchLocation + '</option>')
            }
        }

    })

}


function saveFeeData(index) {
    var type = $('#paramType').val()
    var bool = admissionFeeValidation(index)
    if (bool) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'saveFeeData', ''),
            data: {programId: $('#programId').val(), bankId: $('#bankName' + index).val(), paymentModeId: $('#paymentMode' + index).val(), branchId: $('#branch' + index).val(),
                paymentDate: $('#datePick' + index).val(), paymentReferenceNumber: $('#referenceNumber' + index).val(), studentId: $('#studentId' + index).val(), feeTypeId: $('#feeType' + index).val()},
            success: function (data) {
                appendStudentList(data)
            }

        })
    } else {
        return bool
    }

}


function appendStudentList(data) {
    var type = $('#paramType').val()
    $('#studyCenterFeeEntryTable').attr('hidden', false);
    $('#rangeRadioButtons').attr('hidden', false);
    $("#studyCenterFeeEntryTable tbody tr").remove()
    var count = 1
    for (var i = 0; i < data.studentList.length; i++) {

        $("#studyCenterFeeEntryTable tbody").append('<tr id="rowID' + i + '"><td><input type="hidden" value="' + count + '" id="' + data.studentList[i].rollNo + '"/><input name="studentCheckbox" hidden="hidden" class="studentCheckbox" type="checkbox" id=' + data.studentList[i].id + '>' + count + '</td>' +
            '<td><input type="text" hidden="hidden" id="studentId' + i + '" value="' + data.studentList[i].id + '" >' +
            '<input type="text" class="university-size-1-1" name="rollNo" id="rollNo' + i + '" value="' + data.studentList[i].rollNo + '" readonly></td>' +
            '<td>' + data.studentList[i].firstName + ' ' + data.studentList[i].lastName + '</td>' +
            '<td><input type="text" id="feeAmount' + i + '" name="feeAmount" readonly/></td>' +
            '<td><input type="text" id="semester' + i + '" name="semester" value="' + data.studentList[i].semester + '" readonly/></td></tr>');
        if (type == '') {
            $("#feeType" + i).empty().append('<option value="1">Education Fee</option>')
            $("#feeAmount" + i).val(data.feeAmount[i])
        }
        else {

            $("#feeType" + i).empty().append('<option value="">Select Fee Type</option>')
            for (var l = 0; l < data.feeList.length; l++) {
                $("#feeType" + i).append('<option value="' + data.feeList[l].id + '">' + data.feeList[l].type + '</option>')
            }
        }
        count++;
    }
    $(".datePickers").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });

}
function putAmount(studentId, index) {
//    alert("shdshdsds "+studentId+" ########### "+index+" $$$$$$$$ "+$("#feeType" + index).val());
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getFeeAmount', ''),
        data: {studentId: studentId, index: index, feeType: $("#feeType" + index).val()},
        success: function (data) {
            $("#feeAmount" + index).val('')
            $("#feeAmount" + index).val(data.programFeeAmount)
        }
    })
}

$(document).ready(function () {
    $("input[name='entry']").change(function () {
        $("#paymentDetails tr").remove();
        document.getElementById("generateFeeChallan").style.visibility = "visible";
        document.getElementById("paymentDetails").style.visibility = "visible";
        if ($('#rangeEntry').is(':checked')) {
            $("#paymentDetails").append('<tr><th class="university-size-full-1-1" style="text-align: center;">Serial No.</th></tr>');
            $("#paymentDetails").append('<tr><td  style="text-align: center;"><input type="text" class="university-size-1-7"  id="serialNoFrom" name="serialNoFrom"> - <input type="text" class="university-size-1-7" id="serialNoTo" name="serialNoTo"></td>' +
                '</tr>');
        }
        if ($('#individualEntry').is(':checked')) {
            $("#paymentDetails").append('<tr><th class="university-size-1-1" style="text-align: center;">Roll No</th>' +
                '</tr>');
            $("#paymentDetails").append('<tr><td style="text-align: center;"><input type="text" class="university-size-1-3" name="rollNoSearch" id="rollNo" onchange="enterSrNo(this)">' +
                '<input type="hidden" class="university-size-1-7"  id="serialNoFrom" name="serialNoFrom"><input type="hidden" class="university-size-1-7" id="serialNoTo" name="serialNoTo">' +
                '</td>' +
                '</tr>')
        }

    });
});

function enterSrNo(t) {
    var rollNo = $(t).val();
    var value = $('#' + rollNo).val();
//    alert(value)
    $('#serialNoFrom').val(value);
    $('#serialNoTo').val(value);
}

function populateStudentList() {

    var program = $('#programList').val();
    var semester = $('#semesterList').val();
    var chkBox1 = document.getElementById('allProgram');
//    alert(chkBox1.checked)
    if (program != '' && semester != '' && chkBox1.checked == false) {
        program = $('#programList').val();
        semester = $('#semesterList').val();
    }
    else if (program == '' && semester == '' && chkBox1.checked == true) {
        program = 'All';
        semester = 'All';
    }
    else if (program == '' && semester != '' && chkBox1.checked == true) {
        program = 'All';
        semester = $('#semesterList').val();
    }
    else {
        alert("Please Select Program and Semester.")
    }
    if (program) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'populateStudents', ''),
            data: {program: program, semester: semester},
            success: function (data) {
                appendStudentList(data)
            }
        });
    }
}

function populateStudentListForMiscFee() {
//    alert("dffffffffff")
    var program = $('#programList').val();
    var semester = $('#semesterList').val();
    var feeType = $('#programCategory').val();
    var chkBox1 = document.getElementById('allProgram');
//    alert(chkBox1.checked)
    if (program != '' && semester != '' && chkBox1.checked == false) {
        program = $('#programList').val();
        semester = $('#semesterList').val();
    }
    else if (program == '' && semester == '' && chkBox1.checked == true) {
        program = 'All';
        semester = 'All';
    }
    else if (program == '' && semester != '' && chkBox1.checked == true) {
        program = 'All';
        semester = $('#semesterList').val();
    }
    else {
        alert("Please Fill the Filters.")
    }
//    alert(" @@@@@@@@@@@@@@@@@@@ "+program+" ########## "+semester)
    if (program) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'populateStudentsForMFee', ''),
            data: {program: program, semester: semester, feeType: feeType},
            success: function (data) {
                appendStudentList(data)
            }
        });
    }
}
function filterProgram(t) {
    var type = $(t).val();

//   alert(type)
    $.ajax({
        type: "post",
        url: url('admin', 'loadProgram', ''),
        data: {type: type},
        success: function (data) {
            $('#datepicker').prop("disabled", false)
            $("#courseList thead").empty().append('<tr><th class="university-size-1-3">' + '<input type="checkbox" id="chkAll" name="all" onclick="selectAllCheck(this)">Select All</input>' + '</th><th class="university-size-1-3">' + "Course Name" + '</th><th class="university-size-1-3"></th></tr>')
            $("#courseList tbody").empty()

            for (var i = 0; i < data.programList.length; i++) {

//                var date=$.datepicker.formatDate('dd/MM/yy', data.programList[i].lateFeeDate)
                $("#courseList tbody").append('<tr><td><input type="checkbox" name="programs" class="course" onchange="enableTextField(this)" value="' + data.programList[i].id + '" id="course' + data.programList[i].id + '"/></td>' + '<td>' + data.programList[i].courseName + '</td><td><input type="text" readonly value="' + data.dateList[i] + '" name="assignDate' + data.programList[i].id + '" class="assignDate" id="' + data.programList[i].id + '"><input type="hidden" id="hidden' + data.programList[i].id + '" value="' + data.dateList[i] + '" name="hidden' + i + '"/></td></tr>')
                $("#"+ data.programList[i].id).datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "mm/dd/yy",
                    minDate: 0
                });
            }
        }

    });

}
function loadAssignDate(t) {
    var valueDate = $(t).val()
    $('input[name="programs"]:checked').each(function () {
//        selectedStudentId.push($(this).attr('id'));
        var checkboxValue = $(this).val()
        $("#" + checkboxValue).val(valueDate)
    });
}


function selectAllCheck(t) {
    $(".course").prop("checked", $("#chkAll").prop("checked"))
    if ($(t).is(':checked')) {
        $(".assignDate").prop("readonly", false)
        if ($('#datepicker').val() != "") {
            $(".assignDate").val($('#datepicker').val())
        }
        $('input[name="programs"]:checked').each(function () {
            var checkboxValue = $(this).val()
        })
    }
    else {
        $(".assignDate").prop("readonly", true)
        $('input[name="programs"]').each(function () {
            var checkboxValue = $(this).val()
            $("#" + checkboxValue).val($("#hidden" + checkboxValue).val())
        })
    }

}

function enableTextField(t) {
    var txtId = $(t).val()
//    alert(txtId)
    if ($(t).is(':checked')) {
        $('#' + txtId).prop('readonly', false);
        if ($('#datepicker').val() != "") {
            $(t).val($('#datepicker').val())
        }
    }
    else {
        $('#' + txtId).prop('readonly', true);
        $("#" + txtId).val($("#hidden" + txtId).val())
    }

}


function loadProgram(t) {
    var type = $(t).val();
//    alert(type)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'loadProgram', ''),
        data: {type: type},
        success: function (data) {
            $("#programList").empty().append('data <option value="">Select Program</option>')
            for (var i = 0; i < data.programList.length; i++) {
                $("#programList").append('<option value="' + data.programList[i].id + '">' + data.programList[i].courseName + '</option>')
            }

        }
    });
}

function loadStudents(t) {
    var challanNo = $(t).value();
//    alert(challanNo)
    $.ajax({
        type: "post",
        url: url('feeDetails', 'populateStudentsByChallan', ''),
        data: {challanNo: challanNo},
        success: function (data) {
            $("#studyCenterFeeEntryTable tbody tr").remove()
            for (var i = 0; i < data.studList.length; i++) {
                $("#studyCenterFeeEntryTable tbody").append('<tr><td>' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td>' +
                    '<td>' + data.studList[i].rollNo + '</td>' +
                    '<td>' + data.studList[i].rollNo + '</td>' +
                    '</tr>')
            }

        }
    });
}
function enableAll() {
    $('#programCategory').attr('disabled', false);
    $('#programList').attr('disabled', false);
    $('#allProgram').attr('disabled', false);
    $('#semesterList').attr('disabled', false);

}
