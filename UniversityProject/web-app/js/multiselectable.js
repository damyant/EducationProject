/**
 * Created by sonali on 3/13/14.
 */


function  showSelect(){
    var data=$('#terms').val();
    $('div#mainDiv').html('');

    for(var i= 0;i<data;i++){
        $('#multiSelectDiv').load('/UniversityProject/course/multiSelect', {index:i}, function(data){
            $('#mainDiv').append($('#multiSelectDiv').html());
            $('#multiSelectDiv').html('');
        });
    }
}