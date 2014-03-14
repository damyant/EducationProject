/**
 * Created by sonali on 3/13/14.
 */


function  showSelect(){
    var data=$('#terms').val();
    $('div#mainDiv').html('');

    for(var i= 0;i<data;i++){
        alert("For index : "+i);
        $('#multiSelectDiv').load('/UniversityProject/course/multiSelect', {index:i}, function(data){
            alert('loaded >> '+$('#multiSelectDiv').html());
            $('#mainDiv').append($('#multiSelectDiv').html());
            $('#multiSelectDiv').html('');
        });
    }
}