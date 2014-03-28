/**
 * Created by chandan on 3/12/14.
 */
function validate(){
    $("#createStudyCenter,#studentRegister,#createCourse").validate({

        rules: {

//            Study Center
            name: {
                required:true,
                textonly: true
            },
            address: "required",
            district: "required",
            city: "required",
            centerCode:{
                required: false,
                number: true
            },
            nameOfHeadIns: {
                required:true,
                textonly: true
            },
            emailIdOfHeadIns: {
                required: true,
                email: true
            },
            nameOfCoordinator: {
                required:true,
                textonly: true
            },
            emailIdOfCoordinator: {
                required: true,
                email: true
            },
            websiteUrl:{
                required: true,
                url: true
            },
            phoneNoOfCoordinator:{
                required: true,
                minlength: 10,
                number: true
            },
            phoneNoOfHeadIns:{
                required: true,
                minlength: 10,
                number: true
            },
//            Study Center

//            Student Enroll

            programDetail: "required",

            nameOfApplicant:{
                required:true
            },
            program: {
                required:true
            },
            category:{
                required:true
            },
            nationality:{
                required:true
            },
            gender :{
                required:true
            },
            state:{
                required:true
            },
            contactNo:{
                required:true,
                number: true
            },
            contactCentre:{
                required:true
            },
            location:{
                required:true
            },
            studentName:{
                required:true,
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
            declaration:{
                required:true
            }, courseName :{
                required:true,
                textonly: true
            },
            courseMode:{
                required:true
            },
            courseType :{
                required:true
            },
            noOfTerms:{
                required:true,
                number: true
            },
            courseCode:{
                required:true,
                number: true
            },
            noOfAcademicYears:{
                required:true,
                number: true
            },
            totalMarks:{
                required:true,
                number: true
            },
            noOfPapers:{
                required:true,
                number: true
            },
            marksPerPaper:{
                required:true,
                number: true
            },
            totalCreditPoints:{
                required:true,
                number: true
            },
            d_o_b:{
                required:true,
                date:true
            },
            mobileNo: {
                required:true,
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
             examinationCentreName:{
                 required:true,
                 textonly: true
             }
    },
        messages: {
            name: "Please enter study center name.",
            address: "Please enter study center address.",
            district: "Please select district of study center.",
            city: "Please select city of study center.",
            nameOfHeadIns: "Please enter name of the Head of the Institute.",
            phoneNoOfHeadIns: "Please enter Phone Number of the Head of the Institute.",
            emailIdOfHeadIns: "Please enter email of the Head of the Institute.",
            nameOfCoordinator: "Please enter Name of Coordinator.",
            phoneNoOfCoordinator: "Please enter Phone Number of Coordinator.",
            emailIdOfCoordinator: "Please enter Email of Coordinator.",
            websiteUrl:"Please enter website url",
            nameOfApplicant :"Please enter Name of an Apllicant",
            date_of_birth:"Please Enter Date of birth",
            d_o_b:"Please Enter Date of birth",
            program:"Please enter Program",
            programDetail:"Please enter Program",
            category:"Please select one of these categories",
            nationality:"please select Nationality",
            gender:"Please select your gender",
            state:"Please select your State",
            contactNo:"Please enter your Contact Number",
            mobileNo:"Please enter your Contact Number",
            contactCentre:"Please enter your contact Centre",
            location:"Please Select your location",
            photograph:"please upload your PhotoGraph",
            declaration:"please declare before you proceed",
            courseName:"please Enter your Name",
            studyCentre:"please Enter your Study Center Name",
            examiNationCentre:"please Enter your Examination Center Name",
            addressStudentName: "Please enter Student Name",
            addressTown: "please Enter your town",
            addressPO: "please enter your Post Office",
            addressDistrict: "please enter your District",
            addressState: "please enter your State",
            addressPinCode: "please enter your PinCode",
            courseMode:"please Enter course mode",
            courseType:"please Enter your Course Type",
            noOfTerms:"please Enter Number of terms",
            courseCode:"please Enter your Course Code ",
            noOfAcademicYears:"please enter your Academic years",
            totalMarks:"please Enter Total Marks",
            noOfPapers:"please Enter Number of papers",
            marksPerPaper:"please Enter Passing Marks",
            totalCreditPoints:"Please Enter total Credit Points",
            examinationCentreName:"Please Enter examination Centre Name"
        },
        errorPlacement: function(error, element) {
            if (element.is("input:radio")) {
                element.parents(".radio_options").after(error);
            }else if (element.is("input:checkbox")) {
                element.parents("#declaration-label").after(error);
            } else {
                element.after(error);
            }
        }


    })
    jQuery.validator.addMethod("textonly",function(value, element){
            valid = false;
            check = /[^-\.a-zA-Z\s\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02AE]/.test(value);
            if(check==false)
                valid = true;
            return this.optional(element) || valid;
        },
        jQuery.format("Please only enter letters, spaces, periods, or hyphens.")
    );

}

function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
