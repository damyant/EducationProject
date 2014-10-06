/**
 * Created by chandan on 6/10/14.
 */


$(document).ready(function(){

    $('#allBookList').bind('change',function () {

        $("#quantity option").removeAttr("selected");
      var index= $("#allBookList option:selected").index();
//
        $('#quantity > option').eq(index).attr('selected','selected')

    });
})

function getSubjects(){
    $.ajax({
        type: "post",
        url: url('admin', 'getBooksName', ''),
        data: "catalogType="+$("#catalogType").val()+"&catalogCategory="+$("#catalogCategory").val(),
        success: function (data) {
            if(data.length>0) {

                $('#allBookList option').remove()
                for (var i = 0; i < data.length; i++) {
                    if(data[i].availableCatalog<1){
                        $("#allBookList").append('<option disabled value="' + data[i].id + '">' + data[i].title +'</option>')
                    }
                    else{
                        $("#allBookList").append('<option value="' + data[i].id + '">' + data[i].title +'</option>')
                    }
                    $("#quantity").append('<option>' + data[i].availableCatalog +'</option>')
                }



            }else{
                $('#allBookList option').remove()
            }
        }
    })
}

function addToList() {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#allBookList :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;

        $('#selectedBookList option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#selectedBookList').append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()

            $('#allBookList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allBookList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateLength();
}

function removeFromList() {
    $('#selectedBookList option:selected').each(function () {
        $(this).remove();
        $('#selectedBookList option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()

            $('#allBookList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allBookList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateLength();
}
function validateLength() {
    var validate;
    var length = document.getElementById('selectedBookList').options.length;
    if (length == 0) {
        $('#error-select-').html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-').html("");
        validate = true;
    }

    return validate;
}

function saveData(){
    var bookList=[]
    $('#selectedBookList option').each(function () {

        bookList.push($(this).val() || '');

    })

    $.ajax({
        type: "post",
        url: url('admin', 'saveBookIssue', ''),
        data:$("#bookIssueForm").serialize()+"&bookList="+bookList,
        success: function (data) {
            if(data.flag=="true") {
                 location.reload();

            }else{
                $('#allBookList option').remove()
            }
        }
    })


}



