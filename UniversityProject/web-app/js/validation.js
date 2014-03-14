/**
 * Created by chandan on 3/12/14.
 */
function validate(){
    $("#createStudyCenter").validate({
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
            websiteUrl:"Please enter website url"
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
