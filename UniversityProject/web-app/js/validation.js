/**
 * Created by chandan on 3/12/14.
 */
function validate() {
    $("#createStudyCenter,#studentRegister,#createCourse,#generateFeeVoucher,#generateExamFeeVoucher,#createNewFee").validate({

        rules: {

//            Study Center
            name: {
                required: true,
                textonly: true
            },
            address: "required",
            district: "required",
            city: "required",
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
            nameOfAsstCoordinator: {
                required: true,
                textonly: true
            },
            phoneNoOfAsstCoordinator: {
                required: true,
                minlength: 10,
                number: true
            },
            emailIdOfAsstCoordinator: {
                required: true,
                email: true
            },

//            Study Center

//            Student Enroll

            programDetail: "required",

            nameOfApplicant: {
                required: true
            },
            program: {
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

//            location:{
//                required:true
//            },
//            studentName:{
//                required:true
//            },
//            town:{
//                required:true
//            },
//            po:{
//                required:true
//            },
//            districtOfCandidate:{
//                required:true
//            },
//            stateOfCandidate:{
//                required:true
//            },
//            pinCode:{
//                required:true
//            },

//            photograph:{
//                required:true
//            },
            declaration: {
                required: true
            }, courseName: {
                required: true,
                textonly: true
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
//            addressStudentName: "required",
//            addressTown: "required",
//            addressPO: "required",
//            addressDistrict: "required",
//            addressState: "required",
//            addressPinCode: "required"


//              Student Enroll End

//            Exam Center Create
            examinationCentreName: {
                required: true,
                textonly: true
            },


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
                number: true
            },
            feeType: {
                required: true
            }

        },
        messages: {
            name: "Please enter study center name",
            address: "Please enter study center address",
            district: "Please select district of study center",
            city: "Please select city of study center",
            nameOfHeadIns: "Please enter name of the Principal",
            phoneNoOfHeadIns: "Please enter Contact No of Principal",
            emailIdOfHeadIns: "Please enter email of Principal",
            nameOfCoordinator: "Please enter Name of Coordinator",
            phoneNoOfCoordinator: "Please enter Phone No of Coordinator",
            emailIdOfCoordinator: "Please enter Email of Coordinator",
            nameOfAsstCoordinator: "Please enter Name of Asst. Coordinator",
            phoneNoOfAsstCoordinator: "Please enter Phone No of Asst. Coordinator",
            emailIdOfAsstCoordinator: "Please enter Email of Asst. Coordinator",
            websiteUrl: "Please Enter Website URL",
            nameOfApplicant: "Please enter Name of an Applicant",
            date_of_birth: "Please Enter Date of birth",
            centerCode: "Please Enter Center Code",
            d_o_b: "Please Enter Date of birth",
            program: "Please enter Program",
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
            examiNationCentre: "Please Enter your Examination Center Name",
            addressStudentName: "Please enter Student Name",
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
            rollNo: "Please Enter a Roll Number",
            feeType: "Please Select Fee type"
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


    })
    jQuery.validator.addMethod("textonly", function (value, element) {
            valid = false;
            check = /[^-\.a-zA-Z\s\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02AE]/.test(value);
            if (check == false)
                valid = true;
            return this.optional(element) || valid;
        },
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
    if ((charCode == 8 || (charCode > 48 && charCode < 57) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))) {
        return true;
    }
    return false;
}