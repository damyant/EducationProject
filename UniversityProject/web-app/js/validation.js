/**
 * Created by chandan on 3/12/14.
 */
function validate() {
    $("#tempEnrollment,#saveExaminationCentre,#createStudyCenter,#individualDownloadAdmitCard,#studentRegister,#createCourse,#generateFeeVoucher,#generateExamFeeVoucher,#createNewFee, #createFeeDetail").validate({
        rules: {
//              tempEnroll
            examCentre:"required",
            examVenue:"required",
//            Study Center
            name: {
                required: true,
                textonly: true
            },

            address: "required",
            district: "required",
            city: "required",
            examDistrict:"required",
            examCity:"required",
            examinationCentre:"required",
            centerCode: {
                required: true,
                alphanumeric: true
            },
            nameOfHeadIns: {
                required: true,
                textonly: true
            },
            emailIdOfHeadIns: {
                required: true,
                email: true
            },
            nameOfCoordinator: {
                required: true,
                textonly: true
            },
            emailIdOfCoordinator: {
                required: true,
                email: true
            },
            websiteUrl: {
                required: true,
                url: true
            },
            phoneNoOfCoordinator: {
                required: true,
                minlength: 10,
                number: true
            },
            phoneNoOfHeadIns: {
                required: true,
                minlength: 10,
                number: true
            },
            asstCoordinator: {
                required: true,
                textonly: true
            },
            asstMobile: {
                required: true,
                minlength: 10,
                number: true
            },
            asstEmail: {
                required: true,
                email: true
            },

//            Study Center

//            Student Enroll

            programDetail: "required",

            nameOfApplicant: {
                required: true
            },
            programId: {
                required: true
            },
            category: {
                required: true
            },
            nationality: {
                required: true
            },
            gender: {
                required: true
            },
            state: {
                required: true
            },
            contactNo: {
                required: true,
                number: true
            },
            contactCentre: {
                required: true
            },
            location: {
                required: true
            },
            studentName: {
                required: true,
                textonly: true
            },

//
            declaration: {
                required: true
            }, courseName: {
                required: true
            },
            courseMode: {
                required: true
            },
            courseType: {
                required: true
            },
            noOfTerms: {
                required: true,
                number: true
            },
            courseCode: {
                required: true,
                number: true
            },
            noOfAcademicYears: {
                required: true,
                number: true
            },
            totalMarks: {
                required: true,
                number: true
            },
            noOfPapers: {
                required: true,
                number: true
            },
            marksPerPaper: {
                required: true,
                number: true
            },
            totalCreditPoints: {
                required: true,
                number: true
            },

            d_o_b: {
                required: true,
                date: true
            },
            mobileNo: {
                required: true,
                number: true,
                minlength: 10
            },

            studyCentre: "required",

            examiNationCentre: "required",

//              Student Enroll End

//            Exam Center Create
            examinationCentreName: {
                required: true,
                textonly: true
            },
            applicationNo:"required",


//            CreateNewFeeType
            feeAmountAtIDOL: {
                required: true,
                number: true
            },
            feeAmountAtSC: {
                required: true,
                number: true
            },
            lateFeeAmount: {
                required: true,
                number: true,
                min: 0
            },
            examinationFee: {
                required: true,
                number: true
            },
            certificateFee: {
                required: true,
                number: true
            },
//            fee voucher
            rollNo: {
                required: true,
                number: true,
                minlength: 8
            },
            feeType: {
                required: true
            },
            paymentMode: {
                required: true
            },
            draftNumber: {
                required: true,
                number: true
            },
            paymentDate: {
                required: true
            },
            draftDate: {
                required: true
            },
            issuingBank: {
                required: true
            },
            issuingBranch: {
                required: true
            },
//              Download Admit Card
            rollNumber:{
                required:true,
                minlength: 8
            },
            dob:{
                required:true,
                date: true
            },

            //Exam Centre
//            examinationCentreName: {required: true, textonly: true},
            examinationCentreCode: {required: true,
                number: true},
            examinationCentreCapacity: {required: true,
                number: true},
            examinationCentreIncharge: {required: true,
                textonly: true},
            examinationCentreContactNo: {required: true,
                number: true},
            examinationCentreAddress: {required: true},
            examCentreName: {required: true,
                textonly: true}

        },
        messages: {
            rollNumber:{
                required:"Please Enter Roll Number",
                minlength:"Please Enter 8 digit Roll Number"
            },
            dob:{
                required:"Please Enter Date Of Birth",
                date: "Please Enter Date Of Birth in Correct Date Format"
            },
            examCentreName: {required: "Please Enter Centre Name",
                textonly: "Please Enter Alphabets Only"},
            applicationNo:"Please Enter Application Number",
            examCentre: "Please Select Examination Centre",
            examVenue: "Please Select Examination Venue",
            profileImage: "File must be JPG, GIF or PNG, less than 1MB",
            name: "Please enter study center name",
            address: "Please enter study center address",
            district: "Please select district",
            city: "Please select city",
            nameOfHeadIns: "Please enter name of the Principal",
            phoneNoOfHeadIns: "Please enter Contact No of Principal",
            emailIdOfHeadIns:{
                required: "Please enter email of Principal",
                email: "Please Enter Valid Email"
            },
            nameOfCoordinator: "Please enter Name of Coordinator",
            phoneNoOfCoordinator: "Please enter Phone No of Coordinator",
            emailIdOfCoordinator:{
                required: "Please enter Email of Coordinator",
                email: "Please Enter Valid Email"
            },
            asstCoordinator: "Please enter Name of Asst. Coordinator",
            asstMobile: "Please enter Phone No of Asst. Coordinator",
            asstEmail:{
                required:"Please enter Email of Asst. Coordinator",
                email: "Please Enter Valid Email"
            },
            websiteUrl:{
                required: "Please Enter Website URL",
                url: "Please Enter Valid website Url(eg:http://www.idolgu.in)‎"
            },
            nameOfApplicant: "Please enter Name of an Applicant",
            date_of_birth: "Please Enter Date of birth",
            centerCode: "Please Enter Center Code",
            d_o_b: "Please Enter Date of birth",
            programId: "Please select Program",
            programDetail: "Please enter Program",
            category: "Please select one of these categories",
            nationality: "please select Nationality",
            gender: "Please select your gender",
            state: "Please select your State",
            contactNo: "Please enter your Contact Number",
            mobileNo: "Please enter your Contact Number",
            contactCentre: "Please enter your contact Centre",
            location: "Please Select your location",
            photograph: "Please upload your PhotoGraph",
            declaration: "Please declare before you proceed",
            courseName: "Please Enter your Name",
            studyCentre: "Please Enter your Study Center Name",
            examinationCentre: "Please Select Examination Venue",
            examDistrict:"Please Select Examination Center District",
            examCity:"Please Select Examination Center City",
            addressStudentName: "Please enter Student Name",
            studentName: "Please enter Student Name",
            addressTown: "Please Enter your town",
            addressPO: "Please enter your Post Office",
            addressDistrict: "Please enter your District",
            addressState: "Please enter your State",
            addressPinCode: "Please enter your PinCode",
            courseMode: "Please Enter course mode",
            courseType: "Please Enter your Course Type",
            noOfTerms: "Please Enter Number of terms",
            courseCode: "Please Enter your Course Code ",
            noOfAcademicYears: "Please enter your Academic years",
            totalMarks: "Please Enter Total Marks",
            noOfPapers: "Please Enter Number of papers",
            marksPerPaper: "Please Enter Passing Marks",
            totalCreditPoints: "Please Enter total Credit Points",
            examinationCentreName: "Please Enter examination Centre Name",
            rollNo:{ required:"Please Enter a Roll Number",
                minlength:"Please Enter 8 digit Roll Number"
            },
            feeType: "Please Select Fee type",
            feeAmountAtIDOL: "Please Enter Fee amount at IDOL",
            feeAmountAtSC: "Please Enter Fee amount at Study Centre",
            lateFeeAmount: "Please Enter Late Fee amount",
            examinationFee: "Please Enter Examination Fee amount",
            certificateFee: "Please Enter Certificate Fee amount",
            paymentMode: "Please Enter Payment Mode",
            draftNumber: "Please Enter Draft Number",
            paymentDate: "Please Enter Payment Date",
            draftDate: "Please Enter Draft Date",
            issuingBank: "Please Enter Issuing Bank Name",
            issuingBranch: "Please Enter Issuing Branch Name",
            examinationCentreIncharge: "Please Enter Examination Centre Name",
            examinationCentreContactNo: "Please Enter Examination Centre Contact Number",
            examinationCentreAddress: "Please Enter Examination Centre Address"

        },
        errorPlacement: function (error, element) {
            if (element.is("input:radio")) {
                element.parents(".radio_options").after(error);
            } else if (element.is("input:checkbox")) {
                element.parents("#declaration-label").after(error);
            } else {
                element.after(error);
            }
        }


    });

    jQuery.validator.addMethod("textonly", function (value, element) {
            valid = false;
            check = /[^-\.a-zA-Z\s\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02AE]/.test(value);
            if (check == false)
                valid = true;
            return this.optional(element) || valid;
        },

//        $.validator.addMethod('filesize', function(value, element, param) {
//            // param = size (en bytes)
//            // element = element to validate (<input>)
//            // value = value of the element (file name)
//            return this.optional(element) || (element.files[0].size <= 50)
//        }),




        jQuery.format("Please only enter letters, spaces, periods, or hyphens.")
    );
    $.validator.addMethod('minStrict', function (value, element, param) {
            return value > param;
        },
        $.format("Please larger value.")
    );
    $.validator.addMethod("alphanumeric", function (value, element) {
        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value);
    }, "Username must contain only letters, numbers, or dashes.");


}


function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) || charCode == 8) {
        return true;
    }
    return false;
}
function onlyAlphabets(e, t) {
    try {
        if (window.event) {
            var charCode = window.event.keyCode;
        }
        else if (e) {
            var charCode = e.which;
        }
        else {
            return true;
        }
        if (charCode == 8 || charCode == 32 || charCode == 46 || charCode == 45 || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
            return true;
        else
            return false;
    }
    catch (err) {
        alert(err.Description);
    }
}

function isAlphaNumeric(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode == 8 || (charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))) {
        return true;
    }
    return false;
}

function isTime(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode == 58 || charCode == 8 || charCode == 32 || ( charCode > 47 && charCode < 58) || charCode == 65 || charCode == 97 || charCode == 77 || charCode == 109 || charCode == 80 || charCode == 112)) {
        return true;
    }
    return false;
}

function onlyAlphabetsWithSplChar(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 8 || charCode == 32 || charCode == 40 || charCode == 41 || ( charCode > 64 && charCode < 91 ) || ( charCode > 96 && charCode < 123 ) ||  charCode == 45 || charCode == 46 || charCode == 47 )
        return true;
    else
        return false;
}