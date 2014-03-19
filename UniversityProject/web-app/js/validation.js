/**
 * Created by chandan on 3/12/14.
 */
function validate(){
    $("#createStudyCenter,#studentRegister").validate({
        rules: {
            name: "required",
            address: "required",
            district: "required",
            city: "required",
            centerCode:{
                required: false
            },
            nameOfHeadIns: "required",
            emailIdOfHeadIns: "required",
            nameOfCoordinator: "required",
            emailIdOfCoordinator: "required",
            websiteUrl:{
                required: true,
                url: true
            },
            phoneNoOfCoordinator:{
                required: true,
                minlength: 10
            },
            phoneNoOfHeadIns:{
                required: true,
                minlength: 10
            },
            nameOfApplicant:{
                required:true
            },
            date_of_birth : {
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
                required:true
            },
            contactCentre:{
                required:true
            },
            location:{
                required:true
            },
//            registrationNo1:{
//                registrationNo1:true
//            },
//            registrationNo2:{
//                required:true
//            },
            studentName:{
                required:true
            },
            town:{
                required:true
            },
            po:{
                required:true
            },
            districtOfCandidate:{
                required:true
            },
            stateOfCandidate:{
                required:true
            },
            pinCode:{
                required:true
            },
            photograph:{
                required:true
            },
            declaration:{
                required:true
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
            program:"Please enter Program",
            category:"Please select one of these categories",
            nationality:"please select Nationality",
            gender:"Please select your gender",
            state:"Please select your State",
            contactNo:"Please enter your Contact Number",
            contactCentre:"Please enter your contact Centre",
            location:"Please Select your location",
//            registrationNo1:"please Enter your Registration Number",
//            registrationNo2:"please Enter your Registration Number",
            studentName:"please Enter your Name",
            town:"please Enter your town",
            po:"please enter your Post Office",
            districtOfCandidate:"please enter your District",
            stateOfCandidate:"please enter your State",
            pinCode:"please enter your PinCode",
            photograph:"please upload your PhotoGraph",
            declaration:"please declare before you proceed"

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

}

function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
