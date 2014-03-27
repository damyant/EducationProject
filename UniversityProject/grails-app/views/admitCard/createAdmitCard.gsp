<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Admit Card</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'admintcard.css')}" type="text/css">

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.7.1.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.core.js')}"></script>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>


</head>
<body>
<div id="main">
    <h3>STUDENT ADMIT CARD</h3>
    <g:uploadForm controller="admitCard" action="createAdmintCard" method='post' enctype="multipart/form-data">
        <div>
            <table>
                <tr>
                    <td>
                    <g:select name="programList"   optionKey="id" optionValue="courseName" from="${programList}" noSelection="['':' Select Program']" />
                    </td>
                    <td>
                        <g:select name="studyCentre"   optionKey="id" optionValue="name" from="${studyCentreList}" noSelection="['':' Select Study Centre']" />
                    </td>
                    <td>
                        <g:select name="examinationCentre"   optionKey="id" optionValue="name" from="${examinationCentre}" noSelection="['':' Select Examination Centre']" />
                    </td>
                </tr>




            </table>

        </div>
    </g:uploadForm>
</div>
<script>
    //    $(document).ready(function(){
    //        jQuery("#datePick").datepicker({
    //            dateFormat: "mm/dd/yy",
    //            defaultDate: "01/01/1970",
    //            minDate: "01/01/1925",
    //            maxDate: "12/31/2011",
    //            changeMonth: true,
    //            changeYear: true,
    //            yearRange: "1925:2011",
    //            onClose: function(dateText, inst) {
    //                var validDate = $.datepicker.formatDate( "mm/dd/yy", $('#my_date').datepicker('getDate'));
    //                $('#my_date').datepicker('setDate', validDate);
    //            }
    //        })
    //    })

    jQuery(function($) {
        $( "#datePick" ).datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "yy-mm-dd"
        });
    });
    $('#signatureFile').bind('change', function() {
//    alert('This file size is: ' + this.files[0].size/1024/1024 + "MB");
    });

    function readURL(input,type) {


        if (input.files && input.files[0]) {
            var reader = new FileReader();

            if(type=='picture')
                reader.onload = function (e) {
                    $('#picture')
                            .attr('src', e.target.result)
                            .width(150)
                            .height(200);
                };
            if(type=='signature')
                reader.onload = function (e) {
                    $('#signature')
                            .attr('src', e.target.result)
                            .width(250)
                            .height(80);
                };


            reader.readAsDataURL(input.files[0]);
        }
    }
    function resetImage(){
        $("#signature").attr('src','#')
        $("#picture").attr('src','#')
    }

</script>
</body>
</html>