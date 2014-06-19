var studentIdList = [];
var subjectIdList = [];
var feeTypeList = [];
$(document).ready(function () {
    $(document).ajaxStart(function () {

        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);

    $("#submit").click(function () {
        var rollNo = $("#rollNo").val()
        var feeType = $("#feeType").val()
//            alert(feeType)
        if (rollNo.length == "") {
            $("#rollNo").after('<label class="error">Please Enter Roll Number</label>')
            return false
        }
        if (feeType == "") {
            $("#type").after('<label class="error">Please Select Fee Type</label>')
            return false
        }


        window.open('/UniversityProject/admin/generateFeeVoucher/?rollNo=' + rollNo + '&feeType=' + feeType);
    });


    $(document).on('click', '#assignRollNo', function () {
//        alert("hi")
        if ($("input[name=rollno_checkbox]:checked").length != 0) {
//            $.blockUI({ message: '<h1><img src="busy.gif" /> Please Wait...</h1>' });
            $("input[name=rollno_checkbox]:checked").each(function (i) {

                if ($(this).attr("checked", true)) {
                    studentIdList[i] = $(this).attr("id");
                    $("#studentId").val(studentIdList);
                }

            })
            $.ajax({
                type: "post",
                url: url('admin', 'generateRollIsAllow', ''),
                success: function (data) {
                    if (data.status) {
                        generateRollNo(this.value)
                    }
                    else {
                        alert("Roll No Generation Date has Expired/Date Are Not Set Yet.")
                        $.unblockUI();
                        $('#generateRollNo').reset();

                        return false;
                    }
                }
            })
//            document.forms["generateRollNo"].submit();
        }
        else {
            alert("Select the student first.");
            return false;
        }
    });


    $("#feeType").append('<option value="0">' + "Admission Fee" + '</option>')


});


function a(id) {
    window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' + id);
}

function saveExamDate() {
//    alert("submit")
    var course = $('#programList').val();
    $.ajax({
        type: "post",
        url: url('admin', 'saveExamDate', ''),
        data: $('#assignDate').serialize() + '&subjectIdList=' + subjectIdList,
        success: function (data) {
            if (data.saveFlag == true) {
                $('#assignDate')[0].reset();
                $('#assignDate').find(':input').each(function () {
                    switch (this.type) {
                        case 'text':
                            $(this).val('');
                            break;
                    }
                });
                $("#successMessage").html("Examination Date is saved")
                $("html, body").animate({ scrollTop: 0 }, "slow");
            }
            else {
                $("#successMessage").html("")
            }
        }
    });

}
function getStudents() {
    var program = $('#programId').val()
//    alert("*******"+program)
    if (program == 'null') {
        $('#studentList thead tr').remove()
        $('#studentList tbody tr').remove()
        return false;
    }
    else {
        $.ajax({
            type: "post",
            url: url('admin', 'getStudentList', ''),
            data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), pageType: $('#pageType').val()},
            success: function (data) {
                appendTable(data)
            }
        });
    }
}

function checkRollNo() {
    $('#msgDiv').html('')
    var rollNo = $("#StudentRollNo").val()
    $.ajax({
        type: "post",
        url: url('admin', 'getStudentByRollNo', ''),
        data: {rollNo: rollNo},
        success: function (data) {
            if (data.student) {
                $('#rollNo').prop('disabled', false)
                $('#view').prop('disabled', false)
                $('#msgDiv').html('')

            }
            else if (data.noStudent) {
                $('#rollNo').prop('disabled', true)
                $('#view').prop('disabled', true)
                $('#msgDiv').html('' + data.noStudent)
            }

        }

    });
}

function updateStudentByRollNo() {
//    alert("dfdfdf")
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert($('#individualStudentUpdate').valid())
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length == 8) {
            window.location.href = '/UniversityProject/student/updateStudent?rollNo=' + rollNo;
        }
        else {
            alert("Please enter 8 digit roll number!")
        }
    }

}
function viewStudentByRollNo() {
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert($('#individualStudentUpdate').valid())
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length == 8) {
            window.location.href = '/UniversityProject/student/viewStudent?studentId=' + rollNo;
        }
        else {
            alert("Please enter 8 digit roll number!")
        }
    }
}
function enableProgram(t) {
    var op = $(t).val();
    $('#programId').val('');
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    if (op != 'null') {
        $('#programId').prop('disabled', false);
    } else {
        $('#programId').val('');
        $('#programId').prop('disabled', true);
        $('#studentList thead tr').remove()
        $('#studentList tbody tr').remove()


    }
}
function toggleChecked(status) {
    $(".checkbox").each(function () {
//        $('input:checkbox:not(:disabled)').attr("checked", status)
        $('input:checkbox:not(:disabled)').prop("checked", status);
    })
}

function generateRollNo(value) {
    $.ajax({
        type: "post",
        url: url('admin', 'generateRollNo', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), studentList: $("#studentId").val(), pageType: value},
        success: function (data) {
            getStudents()
//            appendTable(data)
        }
    });

}


function appendTable(data) {

    document.getElementById("studentList").style.visibility = "hidden";
    document.getElementById("paginationDiv").style.visibility = "hidden"
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    $('#studentListButton tbody tr').remove()
    if (data.stuList.length > 0) {
        $('#msg').html("")
        document.getElementById("studentList").style.visibility = "visible";
        document.getElementById("paginationDiv").style.visibility = "visible";
        $('#studentList thead').append('<tr><th><input type="checkbox" name="chkbox" onchange="toggleChecked(this.checked)"/> <label for="chkbox">Select All</label> </th><th>' + "Student Name" + '</th><th>' + "Reference Number" + '</th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data.stuList[i].id + '"/></td><td>' + data.stuList[i].firstName + ' ' + (data.stuList[i].middleName ? data.stuList[i].middleName : '') + ' ' + data.stuList[i].lastName + '</td><td>' + data.stuList[i].referenceNumber + '</td></tr>')
        }
        $table_rows = $('#studentList tbody tr');
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
        $('#studentListButton tbody').empty().append('<tr><td colspan="3"><input type="button" value="Assign Roll No" id="assignRollNo"></td></tr>')


    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }
}

function getSemesterAndSubjectList() {
    var session = $('#SessionList').val()
    var sessionType = $("#sessionType").val()
    if (session && sessionType != '0') {
        $.ajax({
            type: "post",
            url: url('admin', 'getSubjectList', ''),
            data: {sessionId: $('#SessionList').val(), sessionTypeId: $("#sessionType").val()},
            success: function (data) {

                if (data.noSubjects == true) {
                    $("#subjectList tr").remove()
                    $("#msgDiv").html("There is no Course associated with the program")

                }
                else {
                    $("#msgDiv").html("")
                    appendSubjects(data)
                }
            }
        });
    }
    else {
        $("#subjectList").empty();
    }
}
function appendSubjects(obj) {
    var count = 1;
    var counter = 0;
    $("#subjectList").empty();

    for (var i = 0; i < obj.allSubjects.length; i++) {

        $("#subjectList").append('<tr><th>' + "Term" + obj.semesterNoList[i][0].semesterNo + " Courses" + '</th><th>Examination Date</th><th>Examination Time</th></tr>')
        for (var j = 0; j < obj.allSubjects[i].length; j++) {
            subjectIdList[counter] = obj.allSubjects[i][j].id
            var datesInNewFormat = "", examTime = ""

            if (obj.dateList[counter] != undefined && obj.dateList[counter].toString() != 'noo') {
                var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString());
                datesInNewFormat = $.datepicker.formatDate("dd/mm/yy", d);
            }

            if (obj.allSubjects[i][j].toString() != null) {
                examTime = obj.examTimeList[i][j]

            }


            $("#subjectList").append('<tr id="subjectRows' + counter + '"><td class="university-size-1-3">' + obj.allSubjects[i][j].subjectName + '</td><td class="university-size-1-3">' +
                '<input type="text"  name="examinationDate" maxlength="10" onkeypress="disableKeyInput(this)" id="examDate' + counter + '"  onchange="clearError(this)" class="datePickers university-size-1-2 "  value=' + datesInNewFormat + '></input><label id="dateError' + counter + '" class="error3">&nbsp;</label></td>' +
                '<td class="university-size-1-3"><input type="text" maxlength="8" id="examTime' + counter + '" onkeypress="disableKeyInput(this)"  onchange="clearError(this)"  name="examinationTime" style="width: 70px;" class="timePicker_6" value="' + examTime + '" /><label id="timeError' + counter + '" class="error4">&nbsp;</label></td>' +
                '</tr>')
            ++counter;
        }
        count++;
    }
    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" class="university-button" value="Submit" onclick="validateFields(' + counter + ')"/></td></tr>')
    $(".datePickers").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        minDate: 0
    });
    $('.timePicker_6').timepicker({
        showPeriod: true,
        showLeadingZero: true
    });
}
function validateFields(counter) {
    var date = null;
    var time = null;
    var bool = true;
    for (var i = 0; i < counter; i++) {
        date = $('#examDate' + i).val();
        time = $('#examTime' + i).val()
        if ((date == "null" || date.length == 0)) {
            $('#dateError' + i).text("Please Select Examination Date")
            bool = false
        }
        else if (date.length != 10) {
            $('#dateError' + i).text("Please Enter Proper Date")
            bool = false
        }
        if ((time == "null" || time == "")) {
            $('#timeError' + i).text("Please Select Examination Time")
            bool = false
        }
        else if (time.length != 8) {
            $('#timeError' + i).text("Please Enter Proper Date")
            bool = false
        }
    }
    if (bool) {
        saveExamDate();
    }
    return bool;
}

function checkTimeFormat(count) {

    re = /^\d{1,2}:\d{2}([ap]m)?$/;

    var val = $('#examTime' + count).val();

    if (val != '' && !val.match(re)) {
//        alert("Invalid time format: " + val);
        form.timeVal.focus();
        return false;
    }
    return true;

}

function saveExamVenue() {
    var venueList = []
    if ($("#addExamCentre").html() == "") {
        alert("Please assign at least one venue");
        return false
    }


    $('#addExamCentre option').each(function () {
        venueList.push($(this).val() || '');

    });

    $.ajax({
        type: "post",
        url: url('admin', 'saveExamVenue', ''),
        data: $("#assignExamVenue").serialize() + "&venueList=" + venueList,
        success: function (data) {
            $('#assignExamVenue')[0].reset();
            $('#courseForExamVenue').html('');
            $('#CentreForExamVenue').html('');
            $('#examCenterList').empty();
            $('#addExamCentre').empty();
            $("#examinationCentre").prop("disabled", false)
            $('#successMessage').html('Successfully Assigned Examination Venue');
        }
    });
}

function generateStudentsList() {

    $.ajax({
        type: "post",
        url: url('admin', 'generateStudentList', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), session: $('#session').val()},
        success: function (data) {
            $('#studentList thead tr').remove()
            $('#studentList tbody tr').remove()
            if (data.studList.length > 0) {
                $('#msg').html("");
                document.getElementById("studentList").style.visibility = "visible";
                document.getElementById("paginationDiv").style.visibility = "visible";
//                $('#paginationDiv').after(data.studList[0].firstName)
                $('#studentList thead').append('<tr><th>' + "Roll Number" + '</th><th>' + "Student Name" + '</th><th>' + "Date of Birth" + '</th><th>' + "Gender" + '</th><th>' + "Mobile No" + '</th><th>Fee Status</th><th>Admission Status</th><th>&nbsp;</th></tr>')
                for (var i = 0; i < data.studList.length; i++) {
                    if (data.studList[i].middleName != null) {
                        if (data.studList[i].rollNo != null) {
                            $('#studentList tbody').append('<tr><td>' + data.studList[i].rollNo + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')
                        }
                        else {
                            $('#studentList tbody').append('<tr><td>' + "Not Generated Yet" + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')
                        }
                    }
                    else {
                        if (data.studList[i].rollNo != null) {
                            $('#studentList tbody').append('<tr><td>' + data.studList[i].rollNo + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')
                        }
                        else {
                            $('#studentList tbody').append('<tr><td>' + "Not Generated Yet" + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')
                        }
                    }
                }
                $('#studentList tbody tr:not(:first)').hide();
                $table_rows = $('#studentList tbody tr');

                var table_row_limit = 15;

                var page_table = function (page) {

                    // calculate the offset and limit values
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
//                alert(5%5)
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);


            }
            else {
                document.getElementById("paginationDiv").style.visibility = "hidden";
                document.getElementById("studentList").style.visibility = "hidden";
                $('#msg').html("<div class='university-status-message'>No Students Found</div>")
            }

        }
    });
}
function viewStudent(studentId) {
    var data = studentId
    window.location.href = '/UniversityProject/student/viewStudent?studentId=' + data;
}
function updateStudent(studentId) {
    var data = studentId
    window.location.href = '/UniversityProject/student/registration?studentId=' + data;
}
function clearError(t) {
    $(t).next("label").text("");

}
function updateFeeType(feeTypeId) {
//    alert(feeTypeId)
    var data = feeTypeId
    window.location.href = '/UniversityProject/programFee/addFeeType?feeTypeId=' + data;

}
function deleteFeeType(feeTypeId) {
    var result = confirm("Are you sure you want to delete this item?", "Confirm Delete");

    if (result == true) {
        var data = feeTypeId;
//        alert(result)
        $.ajax({
            type: "post",
            url: url('programFee', 'deleteFeesType', ''),
            data: {data: data},
            success: function (data) {
                //document.location.reload();
                window.location.href = '/UniversityProject/programFee/viewExistingFeeType';
            }
        });
    }
}
function loadBranch(t) {
    var bank = $(t).val();
//    alert(bank)
    if (bank) {
        $.ajax({
            type: "post",
            url: url('admin', 'getBranchList', ''),
            data: {bank: bank},
            success: function (data) {
                //document.location.reload();
                $("#branchLocation").empty().append('');
                $("#branchLocation").append('<option value="">Select Branch</option>');
                for (var i = 0; i < data.length; i++) {
                    $("#branchLocation").append('<option value="' + data[i].id + '">' + data[i].branchLocation + '</option>')
                }
            }
        });
    }
}


function loadSession(t) {
    var program = $(t).val();
//    alert(bank)
    if (program) {
        $.ajax({
            type: "post",
            url: url('programFee', 'getProgramSession', ''),
            data: {program: program},
            success: function (data) {
                //document.location.reload();
                $("#session").empty().append('<option value="">Choose Session</option>');
                for (var i = 0; i < data.length - 1; i++) {
                    $("#session").append('<option value="' + data[i].sessionOfProgram + '">' + data[i].sessionOfProgram + '</option>')
                }
            }
        });
    }
}

function approvePayInSlip() {
    $.ajax({
        type: "post",
        url: url('admin', 'saveApprovePayInSlip', ''),
        data: {rollNo: $('#rollNo').val(), bankId: 10, paymentModeId: 5, branchId: 21,
            paymentDate: $('#datePick').val(), paymentReferenceNumber: $('#payInSlipNo').val(), feeTypeId: 1},
        success: function (data) {
            if (data.flag) {
                $('#rollNo').val('');
                $('#payInSlipNo').val('');
                $('#datePick').val('');
                $('#approvePayInSlip')[0].reset();
                $('#statusMessage').html("Approved Succesfully")
            }
        }

    })
}

function submitProgramFee() {

    validateProgramFee()
    var status = $("#createNewFee").valid();
//    alert(status)
    if (status) {
        var programId = $("#programDetail").val()
//        alert(programId)

        $.ajax({
            type: "post",
            url: url('programFee', 'saveProgramFee', ''),
            data: $("#createNewFee").serialize() + "&feeTypeList=" + feeTypeList + "&programDetail=" + programId,

            success: function (data) {
                if (data.status) {
                    $('#createNewFee')[0].reset();
                    document.getElementById("statusMessage").style.visibility = "visible";
                    $('#statusMessage').html("Saved Successfully")
                }

            }

        })
    }

}

function updateProgramFee() {

    var programId = $("#programId").val()
    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#updateFee").serialize() + "&feeTypeList=" + feeTypeList + "&programDetail=" + programId,

        success: function (data) {
            if (data.status) {
                $('#updateFee')[0].reset();
                document.getElementById("statusMessage").style.visibility = "visible";
                $('#statusMessage').html("Updated Successfully")
            }
        }

    })

}


function generateChallanForRange() {
    var selectedStudentId = []
    $('#rollNoError').html("")
    var from = $("#serialNoFrom").val()
    var to = $("#serialNoTo").val()

    if (from != undefined) {
        if (from.length == 0) {
            $('#rollNoError').html("Please Enter Range/Roll Number From Above List.")
            return false
        }
        var selectedRange = 0;
        if (to >= from) {
            selectedRange = (to - from)
        } else {
            $('#rollNoError').html("Please enter range correctly")
            return false
        }

        var rangeCount = parseInt(from) + selectedRange;
        var srNoCount = $('input[name="studentCheckbox"]').length;
        if (rangeCount > srNoCount) {
            $('#rollNoError').html("Please enter range correctly")
            return false
        }

        for (i = from - 1; i < rangeCount; i++)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', true)

        for (i = to; i < totalRows; i++)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', false)
        for (i = from - 2; i >= 0; i--)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', false)
//        selectedStudentId.clean()
        $('input[name="studentCheckbox"]:checked').each(function () {

            selectedStudentId.push($(this).attr('id'));
        });
//    $("#studentListId").val("")
        $("#studentListId").val(selectedStudentId)

        if (selectedStudentId != null) {
            checkValidation()
            if ($("#challanForStudyCenter").valid()) {
                $("#challanForStudyCenter").submit()
            }
        }
    }
    else {

    }
}


function showStudents() {


    $.ajax({
        type: "post",
        url: url('admin', 'searchByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for (var i = 0; i < data.stuList.length; i++) {
                $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"</td><td>' + data.courseNameList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
            }
        }

    })

}
function clearTable() {
    document.getElementById("scStudnetList").style.visibility = "hidden";
    document.getElementById("studentPayList").style.visibility = "hidden";//
    document.getElementById("paySubmit").style.visibility = "hidden";
    document.getElementById("payClear").style.visibility = "hidden";
}


function showListOfStudents() {
    document.getElementById("paginationDiv").style.visibility = "hidden";
    $.ajax({
        type: "post",
        url: url('admin', 'searchListStudentByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
            $('#msgDiv').html("")
//            alert(data.stuList.length)
            if (data.stuList.length > 0) {
                document.getElementById("studentPayList").style.visibility = "visible";
//
                document.getElementById("paySubmit").style.visibility = "visible";
                document.getElementById("payClear").style.visibility = "visible";
                $("#scStudnetList thead").empty().append('')
                $("#scStudnetList thead").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
                $("#scStudnetList tbody").empty().append('')
                for (var i = 0; i < data.stuList.length; i++) {
                    $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" readonly name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"/></td><td>' + data.courseNameList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
                }
                document.getElementById("paginationDiv").style.visibility = "visible";
                $table_rows = $('#scStudnetList tbody tr');
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
                document.getElementById("paginationDiv").style.visibility = "hidden";
                $('#msgDiv').html("Challan is already paid or Invalid Challan.")
            }
        }
    })
}

function showMiscFeeListOfStudents() {
    document.getElementById("studentPayList").style.visibility = "visible";
    document.getElementById("paySubmit").style.visibility = "visible";
    document.getElementById("paginationDiv").style.visibility = "hidden";
    $.ajax({
        type: "post",
        url: url('admin', 'searchMiscFeeListByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList thead").empty().append('')
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList thead").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for (var i = 0; i < data.stuList.length; i++) {
                $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" readonly="true" name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"</td><td>' + data.courseNameList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
            }
            document.getElementById("paginationDiv").style.visibility = "visible";
            $table_rows = $('#scStudnetList tbody tr');

            var table_row_limit = 10;

            var page_table = function (page) {

                // calculate the offset and limit values
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
//                alert(5%5)
            $('.pagination').jqPagination({
                max_page: pageNo,
                paged: page_table
            });
            page_table(1);
        }
    })
}

function checkChallan(challan) {
    if (challan.length != 10) {
        return false
    }
}

function populateChallanDetail(){
    $('#statusMessage').html('');
    var challanNo=$("#payInSlipNo").val();
    if(challanNo.length==10){

        $.ajax({
            type: "post",
            url: url('admin', 'getChallanDetailsforStudent', ''),
            data: {challanNo: challanNo},

            success: function (data) {
                if (data.stuList.length > 0) {
//                console.log("error")
                    $("#allStudentList tbody").empty().append('<tr><th>Student name</th><th>Study Center</th><th>Roll Number</th><th>Course Name</th><th>Payment Ref. No.</th><th>Bank</th><th>Branch</th><th>Amount</th></tr>')
                    for (var i = 0; i < data.stuList.length; i++) {
                        if (!data.stuList[i].middleName) {
                            $("#allStudentList tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.stuList[i].id + '"/> ' + data.stuList[i].firstName + '&nbsp;' + data.stuList[i].lastName + '</td><td>' + data.studyCentreList[i] + '</td><td>' + data.stuList[i].rollNo + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.paymentReferenceNumber + '</td><td>' + data.bank + '</td><td>' + data.branch + '</td><td>' + data.courseFee[i] + '</td></tr>')
                        }
                        else {
                            $("#allStudentList tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.stuList[i].id + '"/> ' + data.stuList[i].firstName + '&nbsp;' + data.stuList[i].middleName + '&nbsp;' + data.stuList[i].lastName + '</td><td>' + data.studyCentreList[i] + '</td><td>' + data.stuList[i].rollNo + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.paymentReferenceNumber + '</td><td>' + data.bank + '</td><td>' + data.branch + '</td><td>' + data.courseFee[i] + '</td></tr>')
                        }
                    }
                    $("#allStudentList tbody").append('<tr><td><input type="button" value="Approve" onclick="submitStudents()"/> </td></tr>')
                    $("#error").hide()
                } else {
                    alert(data.rollStatus)
                    if (!data.rollStatus) {
                        $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">PLease Generate Roll Number Before Approving Pay-In-Slip</td></tr>')
                    }
                    else {
                        $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">Already Approved or Wrong Challan Number</td></tr>')
                    }

                $("#allStudentList tbody").append('<tr><td><input type="button" value="Approve" onclick="submitStudents()"/> </td></tr>')
                $("#error").hide()
//            }else{
////                alert(data.rollStatus)
//                if(!data.rollStatus){
//                    $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">PLease Generate Roll Number Before Approving Pay-In-Slip</td></tr>')
//
                }
            }
        });
    } else {
        alert("please enter 10 digit valid challan number")
        return false
    }
}
//Added By DIgvijay on 22 May 2014
function populateCourseDetail() {
    var courseId = $("#courseId").val();
//    alert("courseId--->"+courseId)

    $.ajax({
        type: "post",
        url: url('admin', 'updateCourses', ''),
        data: {courseId: courseId},

        success: function (data) {
//            alert("Inside ajax call.....")
            if (data.programDetail) {
                $("#allCourseList tbody").empty().append('<tr><th>Course Id</th><th>Course Code</th><th>Course Name</th></tr>')
                for (var i = 0; i < data.programDetail.length; i++) {
                    $("#allCourseList tbody").append('<tr><td><input type="text" name="courseListId" hidden="hidden" value="' + data.programDetail[i].courseId + '"/> ' + data.programDetail[i].courseCode + ' &nbsp;' + data.programDetail[i].courseName + '</td></tr>')
                }
                $("#allCourseList tbody").append('<tr><td><input type="button" value="Approve" onclick="submitStudents()"/> </td></tr>')
                $("#error").hide()
            } else {
                $("#error").show()
            }
        }
    });
}

function submitStudents() {
    $("#approvePayInSlip").submit()
}


$(document).ready(function () {
    $("#rollNoGenerationDate").ready(function () {
        $("#startD").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            minDate: 0
        });
        $("#endD").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            minDate: 0
        });
    })
});


function studentForStudyMaterial() {
    var result = $('#studyMaterialPage').valid()
    $.ajax({
        type: "post",
        url: url('admin', 'getStudentForStudyMaterial', ''),
        data: $("#studyMaterialPage").serialize(),
        success: function (data) {
            if (data.studentList) {
                $("#msgDiv").html(" ")
                $("#studentRecordDiv").empty().append("<table id='studentRecord' class='inner'><tbody></tbody></table>")
                $("#studentRecord tbody").empty().append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th></tr>')
                for (var i = 0; i < data.studentList.length; i++) {
                    $("#studentRecord tbody").append('<tr style="border:  1px solid gainsboro"><td><input type="text" name="studentListId" hidden="hidden" value="' + data.studentList[i].id + '"/> ' + data.studentList[i].firstName + ' &nbsp;' + data.studentList[i].lastName + '</td><td>' + data.studentList[i].rollNo + '</td><td>' + data.courseDetail[0].courseName + '</td></tr>')
                    $("#studentRecord tbody").append('<tr><td> </td><td> </td><td> </td></tr>')
                    $("#studentRecord tbody").append('<tr><th colspan="3">Current Semester Courses</th></tr>')

                    for (var j = 0; j < data.subjectsList[i].length; j++) {

                        $("#studentRecord tbody").append('<tr><td><input type="checkbox" name="subjectCheckBox" id="' + data.subjectsList[i][j].id + '" value="' + data.subjectsList[i][j].id + '" /></td><td>' + data.subjectsList[i][j].subjectName + '</td></tr>')
                    }
                }

                $("#studentRecord tbody").append('<tr><td><input type="button" value="Assign Study Material" onclick="assignStudyMaterial()"/> </td></tr>')

                for (var k = 0; k < data.assignedStudyMaterail.length; k++) {
                    $("#" + data.assignedStudyMaterail[k].id).attr('checked', true)
                }
                $("#error").hide()
            } else {
                $("#error").show()
                $("#studentRecord tbody").empty()
            }
        }
    });


}


function assignStudyMaterial() {
    var subjectList = []
    if ($("input[name=subjectCheckBox]:checked").length != 0) {

        $.ajax({
            type: "post",
            url: url('admin', 'saveStudyMaterial', ''),
            data: $("#studyMaterialPage").serialize(),
            success: function (data) {
                if (data.status == 'true') {
                    $("#studentRecord tbody tr").remove()
                    $("#studyMaterialText").val('')
                    $("#msgDiv").html("Study Material has been assigned to student")
                }
                else {
                    alert("There is some problem in assigning Study Material")

                }

            }
        })

    }
    else {
        alert("Select the Subject first.");
        return false;
    }

}
function loadPayInSlipDetails(t) {
    var pMode = $(t).val()
    var challanNo = $('#searchChallanNo').val()
    if (pMode == '5') {
//        alert(pMode)
        $.ajax({
            type: "post",
            url: url('admin', 'loadPayInSlipDetail', ''),
            data: {pMode: pMode, challanNo: challanNo},
            success: function (data) {
                if (data.admissionDate) {
                    $('#datepicker').val(data.admissionDate)
                    $('#paymentReferenceNumber').val(data.refNo)
                    $('#bankName').val(data.bank)
                    $('#bankName').empty().append('<option value="' + data.bank + '">' + data.bankName + '</option>')
                    $('#branchLocation').empty().append('<option value="' + data.branch + '">' + data.branchName + '</option>')
                    $('#datepicker').prop('readonly', true)
                    $('#paymentReferenceNumber').prop('readonly', true)
                }
                else {
                    $(t).val('')
                    $('#datepicker').prop('readonly', false)
                    $('#paymentReferenceNumber').prop('readonly', false)
                    $('#bankName').prop('readonly', false)
                    $('#branchLocation').prop('readonly', false)
                }
            }
        })
    }
}

function checkFeeStatusForRollNo() {
    var rollNo = $('#rollNoForFeeStatus').val()
    if (rollNo.length == 8) {
        $('#errorLabel').html("")
        $.ajax({
            type: "post",
            url: url('feeDetails', 'checkRollNoFeeStatus', ''),
            data: {rollNo: rollNo},
            success: function (data) {
                if (!data.error) {
                    $('#showStatusForRollNo').empty().append('<table class="university-size-full-1-1" id="statusTable"></table>')
                    $('#statusTable').append('<tr><th>Challan No</th><th>Fee Type</th><th>Status</th></tr>')
                    if (data.admissionChallanIns) {
                        $('#statusTable').append('<tr><td>' + data.admissionChallanIns.challanNo + '</td><td>Admission Fee</td><td>' + data.admissionChallanStatus + '</td></tr>')
                    }
                    if (data.miscFeeList.length > 0) {
                        for (var i = 0; i <= data.miscFeeList.length; i++) {
                            $('#statusTable').append('<tr><td>' + data.miscFeeList[i].challanNo + '</td><td>' + data.miscFeetype[i] + '</td><td>' + data.miscFeeStatus[i] + '</td></tr>')

                        }

                    }
                }
                else {
                    $('#showStatusForRollNo').empty()
                    $('#errorLabel').html(data.error)
                }

            }
        })
    }
    else {
        $('#errorLabel').html("Please Enter Correct Roll Number")
    }
}

function editUser(userId) {
    window.open('/UniversityProject/user/editUser/' + userId, '_self', false)
}
function resetPassword(userId) {
    window.open('/UniversityProject/user/resetPassword/' + userId, '_self', false)
}
function showDistrictsCityList(t) {
    var data = $(t).val()
    if (data) {
        $.ajax({
            type: "post",
            url: url('studyCenter', 'getCityList', ''),
            data: {data: data},
            success: function (data) {
                var count = 1
//                alert(data.length)
                $('#cityListTable tbody').empty()
                for (var i = 0; i < data.length; i++) {
                    $('#cityListTable tbody').append('<tr><td>' + count++ + '</td><td>' + data[i].cityName + '</td><td><input type="button" class="university-button" onclick="deleteCity(' + data[i].id + ')" value="Delete"/> <input type="button" class="university-button" onclick="editCity(' + data[i].id + ')" value="Edit"/> </td></tr>')
                }
            }
        })
    }
}
function showDistrictsExamCentreList(t) {
    var data = $(t).val()
    if (data) {
        $.ajax({
            type: "post",
            url: url('examinationCenter', 'getExamCenterName', ''),
            data: {data: data},
            success: function (data) {
                if (data.length != 0) {
                    var count = 1
//                alert(data.length)
                    $('#cityListTable tbody').empty()
                    for (var i = 0; i < data.length; i++) {
                        $('#cityListTable tbody').append('<tr><td>' + count++ + '</td><td>' + data[i].cityName + '</td><td><input type="button" class="university-button" onclick="deleteExamCentre(' + data[i].id + ')" value="Delete"/> <input type="button" class="university-button" onclick="editExamCentre(' + data[i].id + ')" value="Edit"/> </td></tr>')
                    }
                }
                else {
                    $('#cityListTable tbody').empty().append('<tr><td></td><td>No Examination Centre In This District</td><td></td></tr>')
                }
            }
        })
    }
}

function editCity(data) {
    window.open('/UniversityProject/examinationCenter/createNewCity/' + data, '_self', false)
}
function deleteCity(data) {
    alert(data)
    $('#deleteCityId').val(data)
    alert($('#deleteCityId').val())
    $('#deleteCityInst').submit()
}

function editExamCentre(data) {
    window.open('/UniversityProject/examinationCenter/createExamCentre/' + data, '_self', false)
}
function deleteExamCentre(data) {
    alert(data)
    $('#deleteCityId').val(data)
    alert($('#deleteCityId').val())
    $('#deleteCityInst').submit()
}
function searchStudentList() {
    var session = $('#session').val()
    var student = $('#searchStudent').val()
    $.ajax({
        type: "post",
        url: url('admin', 'searchStudentList', ''),
        data: {student: student, session: session},
        success: function (data) {
            $('#studentListTable tbody').empty().append('')
            if (data.studentListByFName) {
                document.getElementById("studentListTable").style.visibility = "visible";
                for (var i = 0; i < data.studentListByFName.length; i++) {
                    if (data.studentListByFName[i].middleName != null) {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByFName[i].firstName + ' ' + data.studentListByFName[i].middleName + ' ' + data.studentListByFName[i].lastName + '</td><td>' + data.studentListByFName[i].rollNo + '</td><td>' + data.studyOfFName[i]+ '</td><td>' + data.courseOfFName[i] + '</td></tr>')
                    }
                    else {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByFName[i].firstName + ' ' + data.studentListByFName[i].lastName + '</td><td>' + data.studentListByFName[i].rollNo + '</td><td>' + data.studyOfFName[i] + '</td><td>' + data.courseOfFName[i] + '</td></tr>')
                    }
                }
            }
            if (data.studentListByMName) {
                document.getElementById("studentListTable").style.visibility = "visible";
                for (var i = 0; i < data.studentListByMName.length; i++) {
                    if (data.studentListByMName[i].middleName != null) {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByMName[i].firstName + ' ' + data.studentListByMName[i].middleName + ' ' + data.studentListByMName[i].lastName + '</td><td>' + data.studentListByMName[i].rollNo + '</td><td>' + data.studyOfMName[i] + '</td><td>' + data.courseOfMName[i] + '</td></tr>')
                    }
                    else {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByMName[i].firstName + ' ' + data.studentListByMName[i].lastName + '</td><td>' + data.studentListByMName[i].rollNo + '</td><td>' + data.studyOfMName[i] + '</td><td>' + data.courseOfMName[i] + '</td></tr>')
                    }
                }
            }
            if (data.studentListByLName) {
                document.getElementById("studentListTable").style.visibility = "visible";
                for (var i = 0; i < data.studentListByLName.length; i++) {
                    if (data.studentListByLName[i].middleName != null) {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByLName[i].firstName + ' ' + data.studentListByFName[i].middleName + ' ' + data.studentListByLName[i].lastName + '</td><td>' + data.studentListByLName[i].rollNo + '</td><td>' + data.studyOfLName[i] + '</td><td>' + data.courseOfLName[i] + '</td></tr>')
                    }
                    else {
                        $('#studentListTable tbody').append('<tr><td>' + data.studentListByLName[i].firstName + ' ' + data.studentListByFName[i].lastName + '</td><td>' + data.studentListByLName[i].rollNo + '</td><td>' + data.studyOfLName[i] + '</td><td>' + data.courseOfLName[i] + '</td></tr>')
                    }
                }
            }
        }
    });
}
