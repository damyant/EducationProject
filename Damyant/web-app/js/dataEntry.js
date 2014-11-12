/**
 * Created by shweta on 3/26/14.
 */


var studentList = [];
// document ready function............................................


//$(function () {
//    if($("#dialog").length>0) {
//        $("#dialog").dialog({
//            autoOpen: false,
////        maxWidth:600,
////        maxHeight: 500,
//            width: 1000,
//            height: 650,
//            modal: true,
//            title: 'Enter Fee Details',
//            close: function (ev, ui) {
//                getStudentsList()
//            }
//
//        });
//    }
//});


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
    if (data.studentList.length > 0) {
        $('#noStudentMsg').html("")
        var type = $('#paramType').val()
        document.getElementById("paginationDiv").style.visibility = "hidden";
//        document.getElementById("studyCenterFeeEntryTable").style.visibility = "visible";
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
                '<td><input type="text" id="semester' + i + '" name="semester" value="' + data.term + '" readonly/></td></tr>');
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
        document.getElementById("paginationDiv").style.visibility = "visible";
        $table_rows = $('#studyCenterFeeEntryTable tbody tr');

        var table_row_limit = 10;

        var page_table = function (page) {
            var offset = (page - 1) * table_row_limit,
                limit = page * table_row_limit;

            // hide all table rows
            $table_rows.hide();

            // show only the n rows
            $table_rows.slice(offset, limit).show();

        }
        var pageNo = 0
        if ($table_rows.length % table_row_limit) {
            pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
        }
        else {
            pageNo = parseInt($table_rows.length / table_row_limit)
        }
        $('.pagination').jqPagination({
            max_page: pageNo,
            current_page: 1,
            paged: page_table,
            trigger: true
        });
        page_table(1);
        $(".datePickers").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            maxDate: 0
        });

    }
    else {
        document.getElementById("paginationDiv").style.visibility = "hidden";
        $('#noStudentMsg').html("No Student Available")
    }

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
            $("#paymentDetails").append('<tr><td  style="text-align: center;"><input type="text" class="university-size-1-7"  onchange="checkRange()"  id="serialNoFrom" name="serialNoFrom"> - <input type="text" class="university-size-1-7"  onchange="checkRange()"  id="serialNoTo" name="serialNoTo"><label id="rollNoError" class="error4"></label></td>' +
                '</tr>');
        }
        if ($('#individualEntry').is(':checked')) {
            $("#paymentDetails").append('<tr><th class="university-size-1-1" style="text-align: center;">Roll No</th>' +
                '</tr>');
            $("#paymentDetails").append('<tr><td style="text-align: center;"><input type="text" class="university-size-1-3" style="text-align: center;" name="rollNoSearch" id="rollNo" onchange="enterSrNo(this)"><label id="rollNoError" class="error4"></label>' +
                '<input type="hidden" class="university-size-1-7"  id="serialNoFrom" name="serialNoFrom"><input type="hidden" class="university-size-1-7" id="serialNoTo" name="serialNoTo">' +
                '</td>' +
                '</tr>')
        }

    });
});
function checkRange() {
    $('#rollNoError').html("")
}
function enterSrNo(t) {
    $('#rollNoError').html("")
    var rollNo = $(t).val();
    if ($('#' + rollNo).length > 0) {
        var value = $('#' + rollNo).val();
//    alert(value)
        $('#serialNoFrom').val(value);
        $('#serialNoTo').val(value);
    }
    else {
        $('#rollNoError').html("Roll Number is Unavailable For Challan Generation. Please Select one From the above List.")
        $('#challanForStudyCenter').valid(false)
        return false;
    }
}

function populateStudentList() {
    var feeType = $('#feeCategory').val();
    var catagory = $('#programCategory').val();
    var program = $('#programList').val();
    if (feeType != '2') {
        var semester = $('#semesterList').val();
        var chkBox1 = document.getElementById('allProgram');
        if (program != '' && semester != '' && chkBox1.checked == false) {
            program = $('#programList').val();
            semester = $('#semesterList').val();
        }
        else if (program == '' && semester == '' && chkBox1.checked == true) {
            program = 'All';
            semester = '1';
        }
        else {
            alert("Please Fill the Filters.")
        }
    }
    else {
        if (program == '' && chkBox1.checked == true) {
            program = 'All';
        }
        else {
            program = $('#programList').val();
        }
    }
    if (feeType) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'populateStudentsForStudyCenter', ''),
            data: {program: program, semester: semester, catagory: catagory, feeType: feeType},
            success: function (data) {
                if (data.studentError) {
                    $('#noStudentMsg').html(data.studentError)
                } else {
                    appendStudentList(data)
                }
            }
        });
    }
}

function populateStudentListForMiscFee() {
//    alert("dffffffffff")
    var program = $('#programList').val();
    var semester = $('#semesterList').val();
    var feeType = $('#feeCategory').val();
//    alert(feeType)
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

//    alert(type)
    if (type) {
        $.ajax({
            type: "post",
            url: url('admin', 'loadProgram', ''),
            data: {type: type},
            success: function (data) {
                $('#datepicker').prop("disabled", false)
                $("#courseList thead").empty().append('<tr><th class="university-size-1-3">' + '<input type="checkbox" id="chkAll" name="all" onclick="selectAllCheck(this)">Select All</input>' + '</th><th class="university-size-1-3">' + "Course Name" + '</th><th class="university-size-1-3"></th></tr>')
                $("#courseList tbody").empty()
                for (var i = 0; i < data.programList.length; i++) {
                    $("#courseList tbody").append('<tr><td><input type="checkbox" name="programs" class="course" onchange="enableTextField(this)" value="' + data.programList[i].id + '" id="course' + data.programList[i].id + '"/></td>' + '<td>' + data.programList[i].courseName + '</td><td><input type="text" readonly value="' + data.dateList[i] + '" name="assignDate' + data.programList[i].id + '" class="assignDate" id="' + data.programList[i].id + '"><input type="hidden" id="hidden' + data.programList[i].id + '" value="' + data.dateList[i] + '" name="hidden' + i + '"/></td></tr>')

                }
            }


        });
    }
    else {
        $('#datepicker').prop("disabled", true)
        $("#courseList thead").empty()
        $("#courseList tbody").empty()
    }

}
function filterProgramforRemove(t) {
    var type = $(t).val();

//    alert(type)
    if (type) {
        $.ajax({
            type: "post",
            url: url('admin', 'loadProgram', ''),
            data: {type: type},
            success: function (data) {
                $('#datepicker').prop("disabled", false)
                $("#courseList thead").empty().append('<tr><th class="university-size-1-3">' + '<input type="checkbox" id="chkAll" name="all" onclick="selectAllCheckforRemove(this)">Select All</input>' + '</th><th class="university-size-1-3">' + "Course Name" + '</th><th class="university-size-1-3"></th></tr>')
                $("#courseList tbody").empty()
                for (var i = 0; i < data.programList.length; i++) {
                    $("#courseList tbody").append('<tr><td><input type="checkbox" name="programs" class="course" value="' + data.programList[i].id + '" id="course' + data.programList[i].id + '"/></td>' + '<td>' + data.programList[i].courseName + '</td><td><input type="text" readonly value="' + data.dateList[i] + '" name="assignDate' + data.programList[i].id + '" class="assignDate" id="' + data.programList[i].id + '"></td></tr>')

                }
            }


        });
    }
    else {
        $('#datepicker').prop("disabled", true)
        $("#courseList thead").empty()
        $("#courseList tbody").empty()
    }

}

function filterProgramsForSelect(t) {
    var type = $(t).val();

//    alert(type)
    if (type) {
        $.ajax({
            type: "post",
            url: url('admin', 'loadProgram', ''),
            data: {type: type},
            success: function (data) {

                $("#program").empty().append('<option value="">Select Programme</option>')
                for (var i = 0; i < data.programList.length; i++) {
                    $("#program").append('<option value="' + data.programList[i].id + '">' + data.programList[i].courseName + '</option>')

                }
            }
        });
    }
    else {
        $("#program").empty()
    }

}

function loadAdmissionDate(t) {
//    alert("sdsds")
    var programCode = $(t).val()
    if (programCode) {
        $.ajax({
            type: "post",
            url: url('admin', 'getAdmissionDate', ''),
            data: {programCode: programCode},
            success: function (data) {
                if (data) {
                    $("#startAdmission_D").datepicker({ dateFormat: "dd/mm/yy" }).val(data.startDate)
                    $("#endAdmission_D").datepicker({ dateFormat: "dd/mm/yy" }).val(data.endDate)
                    $("#admissionYear").val(data.year)

                }
                else {
                    $("#startAdmission_D").val("")
                    $("#endAdmission_D").val("")
                    $("#admissionYear").val("")
                }
            }

        });
    }
    else {
        $("#program").empty()
    }
}
function loadAssignDate(t) {
    var valueDate = $(t).val()
    $('input[name="programs"]:checked').each(function () {
//        selectedStudentId.push($(this).attr('id'));
        var checkboxValue = $(this).val()
//        alert(checkboxValue)
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
function selectAllCheckforRemove(t) {
    $(".course").prop("checked", $("#chkAll").prop("checked"))
//    if ($(t).is(':checked')) {
////        $(".assignDate").prop("readonly", false)
//        if ($('#datepicker').val() != "") {
//            $(".assignDate").val($('#datepicker').val())
//        }
//        $('input[name="programs"]:checked').each(function () {
//            var checkboxValue = $(this).val()
//        })
//    }
//    else {
//        $(".assignDate").prop("readonly", true)
//        $('input[name="programs"]').each(function () {
//            var checkboxValue = $(this).val()
//            $("#" + checkboxValue).val($("#hidden" + checkboxValue).val())
//        })
//    }

}
function enableTextField(t) {
    var txtId = $(t).val()
//    alert(txtId)
    if ($(t).is(':checked')) {
        $('#' + txtId).prop('readonly', false);
        if ($('#datepicker').val() != "") {
            $('#' + txtId).val($('#datepicker').val())
        }
    }
    else {
        $('#' + txtId).prop('readonly', true);
        $("#" + txtId).val($("#hidden" + txtId).val())
    }

}
function clearFields() {
    $('#programCategory').val('')
    $('#programList').val('')
    $('#semesterList').val('')
    if ($('#allProgram').prop('checked')) {
        $('#programCategory').prop('disabled', true)
    } else {
        $('#programCategory').prop('disabled', false)
    }
    $('#studyCenterFeeEntryTable').prop('hidden', true);
    $('#rangeRadioButtons').prop('hidden', true);
    document.getElementById("paginationDiv").style.visibility = "hidden";
    document.getElementById("paymentDetails").style.visibility = "hidden"
    document.getElementById("generateFeeChallan").style.visibility = "hidden"
}

function loadProgram(t) {
    document.getElementById("paginationDiv").style.visibility = "hidden";
    $('#studyCenterFeeEntryTable').prop('hidden', true)
    $('#allProgram').prop('checked', false)
    $('#rangeRadioButtons').prop('hidden', true)
    document.getElementById("paymentDetails").style.visibility = "hidden"
    document.getElementById("generateFeeChallan").style.visibility = "hidden"
    var type = $(t).val();
    $.ajax({
        type: "post",
        url: url('feeDetails', 'loadProgram', ''),
        data: {type: type},
        success: function (data) {
            $("#programList").empty().append('<option value="">Select Programme</option>')
            for (var i = 0; i < data.programList.length; i++) {
                $("#programList").append('<option value="' + data.programList[i].id + '">' + data.programList[i].courseName + '</option>')
            }

        }
    });
}

function loadProgramList(t) {
    document.getElementById("courseTable").style.visibility = "hidden"
    document.getElementById("paginationDiv").style.visibility = "hidden";
    $('#errorMsg').text('')
    $('#courseTable tbody').empty()
    var type = $(t).val();
    $.ajax({
        type: "post",
        url: url('admin', 'loadSubject', ''),
        data: {type: type},
        success: function (data) {
//<<<<<<< HEAD
             if(data.subSession.length>0) {

                for (var i=0;i<data.subSession.length;i++){

                    var count=i+1
                    $('#courseTable tbody').append('<tr><td>'+data.subject[i][0].subjectCode+'</td><td>'+ data.subject[i][0].subjectName+'</td><td>'+ data.subSession[i][0].sessionOfSubject+'</td>' +
                        '<td><input type="button" class="university-button"  onclick="deleteCourse(' +  data.subSession[i][0].id  + ')" value="Delete"/> <input type="button" class="university-button" onclick="editCourse(' + data.subSession[i][0].id  + ')" value="Edit"/></td>' +
                        '</tr>')


//=======
//            if (data.subjectList.length > 0) {
//                for (var i = 0; i < data.subjectList.length; i++) {
//                    var count = i + 1
//                    $('#courseTable tbody').append('<tr><td>' + count + '</td><td>' + data.subjectList[i].subjectName + '</td><td><input type="button" class="university-button" onclick="deleteCourse(' + data.subjectList[i].id + ')" value="Delete"/> <input type="button" class="university-button" onclick="editCourse(' + data.subjectList[i].id + ')" value="Edit"/></td></tr>')
//>>>>>>> origin/damyant
                }
                document.getElementById("paginationDiv").style.visibility = "visible";
                document.getElementById("courseTable").style.visibility = "visible"
                $table_rows = $('#courseTable tbody tr');
                var table_row_limit = 10;
                var page_table = function (page) {
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;
                    $table_rows.hide();
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }
            else {
                document.getElementById("courseTable").style.visibility = "hidden"
                $('#courseTable tbody').empty()
                $('#errorMsg').text('No Result Found !')
            }
        }
    });
}
function editCourse(data) {
    window.open('/UniversityProject/admin/addCourses/' + data, '_self', false)
}
function deleteCourse(data) {
    $('#deleteCityId').val(data)
    $('#deleteCityInst').submit()
}
function loadStudents(t) {
    var challanNo = $(t).value();
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
    if ($("#feeCategory").val() != "") {
        $("#programList").val('')
        $("#semesterList").val('')
        $('#programCategory').attr('disabled', false);
        $('#programList').attr('disabled', false);
        $('#allProgram').attr('disabled', false);
        if ($('#feeCategory').val() != '2') {
            $('#semesterList').attr('disabled', false);
        }
    }
    else {
        $("#programList").val('')
        $("#programCategory").val('')
        $("#semesterList").val('')
        $('#programCategory').attr('disabled', true);
        $('#programList').attr('disabled', true);
        $('#allProgram').attr('disabled', true);
        $('#semesterList').attr('disabled', true);
    }

}

function showChallanNumberStatus() {
    var challanNo = $("#challanNoText").val()
    if (challanNo) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'challanDetails', ''),
            data: {challanNo: challanNo},
            success: function (data) {
                if (data.challanError) {
                    $('#errorMsg').html(data.challanError)
                    $("#challanStatus thead").empty()
                    $("#challanStatus tbody").empty()
                    $('#challanStatusStaticData').empty()
                    document.getElementById("horizontalLine").style.visibility = "hidden";
                }
                else {
                    $('#errorMsg').html('')
                    $("#challanStatus thead").empty().append('<tr><th>Roll Number</th><th>Course</th><th>Amount</th></tr>')
                    $("#challanStatus tbody").empty()
                    for (var i = 0; i < data.challanInst.length; i++) {
                        $("#challanStatus tbody").append('<tr><td>' + data.rollNo[i] + '</td><td>' + data.program[i] + '</td><td>' + data.feeAmount[i] + '</td></tr>')
                    }
                    $('#challanStatusStaticData').empty().append('<tr><td class="university-size-1-3">Total Amount</td><td class="university-size-2-3">' + data.total + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Payment Date</td><td class="university-size-2-3">' + data.paydate + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Payment Mode</td><td class="university-size-2-3">' + data.paymentMode + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Study Centre</td><td class="university-size-2-3">' + data.studyCentre + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Status</td><td class="university-size-2-3">' + data.status + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Bank</td><td class="university-size-2-3">' + data.bank + '</td></tr>')
                    $('#challanStatusStaticData').append('<tr><td class="university-size-1-3">Branch</td><td class="university-size-2-3">' + data.branch + '</td></tr>')
                }
            }
        });
    }
    else {
        $('#errorMsg').html("Please Enter Challan Number")
    }
}
function editChallanDetails() {
    var challanNo = $("#challanNoText").val()
    if (challanNo) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'challanDetails', ''),
            data: {challanNo: challanNo},
            success: function (data) {
                if (data.challanError) {
                    $('#errorMsg').html(data.challanError)
                    $("#challanStatus thead").empty()
                    $("#challanStatus tbody").empty()
                    $('#challanStatusStaticData').empty()
                    $('#challanDataEdit').empty()
                    document.getElementById("horizontalLine").style.visibility = "hidden";
                }
                else {
                    if (data.editable) {
                        $('#errorMsg').html('')
                        $("#challanStatus thead").empty().append('<tr><th>Roll Number</th><th>Course</th><th>Amount</th></tr>')
                        $("#challanStatus tbody").empty()
                        for (var i = 0; i < data.challanInst.length; i++) {
                            $("#challanStatus tbody").append('<tr><td>' + data.rollNo[i] + '</td><td>' + data.program[i] + '</td><td>' + data.feeAmount[i] + '</td></tr>')
                        }
                        $('#challanStatusStaticData').empty()
                        $('#challanDataEdit').empty().append('<tr><td class="university-size-1-3">Payment Mode</td><td class="university-size-2-3"><select name="paymentMode" class="university-size-1-2" id="paymentMode"><option value="">Select Payment Mode</option> </select></td></tr>')
                        if (data.editPaydate != 'N/A') {
                            $('#challanDataEdit').append('<tr><td class="university-size-1-3">Payment Date</td><td class="university-size-2-3"><input type="text" class="university-size-1-2" id="paymentDate" value="' + data.editPaydate + '" name="paymentDate"></td></tr>')
                        }
                        if (data.refNo != 'N/A') {
                            $('#challanDataEdit').append('<tr><td class="university-size-1-3">Reference No</td><td class="university-size-2-3"><input type="text" name="paymentReferenceNumber" maxlength="10" value="' + data.refNo + '" onkeypress="return isNumber(event)" class="university-size-1-2" id="paymentReferenceNumber" value=""></td></tr>')
                        }
                        $('#challanDataEdit').append('<tr><td class="university-size-1-3">Bank</td><td class="university-size-2-3"><select name="bankName"  class="university-size-1-2" id="bankName"  onchange="loadBranch(this)"><option value="">Select Bank</option></select></td></tr>')
                        $('#challanDataEdit').append('<tr><td class="university-size-1-3">Branch</td><td class="university-size-2-3"><select name="branchLocation"  class="university-size-1-2" id="branchLocation"><option value="">Select Branch</option></select></td></tr>')
                        $('#challanDataEdit').append('<tr><td class="university-size-1-3"></td><td class="university-size-2-3"><input type="submit" value="Update"  class="university-button"></td></tr>')
                        for (var i = 0; i < data.allPaymentMode.length; i++) {
                            $("#paymentMode").append('<option value="' + data.allPaymentMode[i].id + '">' + data.allPaymentMode[i].paymentModeName + '</option>')
                        }
                        for (var i = 0; i < data.allBank.length; i++) {
                            $("#bankName").append('<option value="' + data.allBank[i].id + '">' + data.allBank[i].bankName + '</option>')
                        }
                        for (var i = 0; i < data.allBranch.length; i++) {
                            $("#branchLocation").append('<option value="' + data.allBranch[i].id + '">' + data.allBranch[i].branchLocation + '</option>')
                        }
                        if (data.paymentModeId != 'N/A') {
                            $("#paymentMode").val(data.paymentModeId)
                        }
                        if (data.bankId != 'N/A') {
                            $("#bankName").val(data.bankId)
                        }
                        if (data.branchId != 'N/A') {
                            $("#branchLocation").val(data.branchId)
                        }
                        $("#paymentDate").datepicker({
                            changeMonth: true,
                            changeYear: true,
                            dateFormat: "dd/mm/yy",
                            maxDate: 0
                        });
                    }
                    else {
                        $('#errorMsg').html("Challan Not Paid.")
                        $("#challanStatus thead").empty()
                        $("#challanStatus tbody").empty()
                        $('#challanStatusStaticData').empty()
                        $('#challanDataEdit').empty()
                        document.getElementById("horizontalLine").style.visibility = "hidden";
                    }
                }
            }
        });
    }
    else {
        $('#errorMsg').html("Please Enter Challan Number")
    }
}
function removeStudentDetails() {
    var challanNo = $("#challanNoText").val()
    if (challanNo) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'challanDetailsRemove', ''),
            data: {challanNo: challanNo},
            success: function (data) {
                if (data.challanError) {
                    $('#errorMsg').html(data.challanError)
                    $("#challanStatus thead").empty()
                    $("#challanStatus tbody").empty()
                    $('#challanStatusStaticData').empty()
                    $('#challanDataEdit').empty()
                    document.getElementById("horizontalLine").style.visibility = "hidden";
                }
                else {
                    $('#errorMsg').html('')
                    $("#challanStatus thead").empty().append('<tr><th>Roll Number</th><th>Course</th><th>Amount</th><th></th></tr>')
                    $("#challanStatus tbody").empty()
                    for (var i = 0; i < data.challanInst.length; i++) {
                        $("#challanStatus tbody").append('<tr><td>' + data.rollNo[i] + '</td><td>' + data.program[i] + '</td><td>' + data.feeAmount[i] + '</td><td><input type="button" value="Delete" class="university-button" onclick="removeStuFromChallan(' + data.studentIDList[i] + ')"></td></tr>')
                    }
                    $('#challanStatusStaticData').empty()
                }
            }
        });
    }
    else {
        $('#errorMsg').html("Please Enter Challan Number")
    }
}
function removeStuFromChallan(studentID) {
    var data = studentID
    window.location.href = '/UniversityProject/feeDetails/deleteStudentFromChallan?studentId=' + data;
}
