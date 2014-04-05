jQuery( document ).ready(function() {
    $(function(){
        // set onblur event handler
        $("#rollNo").blur(function(){
            // if some username was entered - this == #username
            if($(this).length > 0) {
                // use create link so we don't have to hardcode context
                var url = "${createLink(controller:'feeDetails', action:'checkStudentByRollNo')}"
                // async ajax request pass username entered as id parameter
                $.getJSON(url, {rollNo:$(this).val()}, function(json){
                    if(!json.available) {
                        // highlight field so user knows there's a problem
                        $("#rollNo").css("border", "1px solid red");
                        // maybe throw up an alert box
                        alert("This username is taken");
                        // populate a hidden div on page and fill and display it..
                        $("#somehiddendiv").html("This ID is already taken").show();
                    }
                });
            }
        });
    });
});









 