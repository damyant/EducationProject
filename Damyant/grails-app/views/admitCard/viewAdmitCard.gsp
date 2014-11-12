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
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.core.js')}"></script>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>

</head>

<body>
<div id="main">
    <h3>STUDENT ADMIT CARD</h3>
    <g:uploadForm controller="admitCard" action="createAdmintCard" method='post' enctype="multipart/form-data">
        <div id="data">
            <div style="float: left" id="info">
                <table align="center" cellpadding="10">

                    <!----- First Name ---------------------------------------------------------->
                    <tr>
                        <td>Name:</td>
                        <td><input type="text" name="name" maxlength="30" class="textInput1" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Roll Number:</td>
                        <td><input type="text" name="rollno" maxlength="30" class="textInput1" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Course:</td>
                        <td><input type="text" name="course" maxlength="30" class="textInput1" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Session:</td>
                        <td><input type="text" name="session" maxlength="30" class="textInput1" required="true"/>
                        </td>
                    </tr>
                </table>
            </div>


            <div style="float: right" id="image">
                <table>
                    <tr><td>
                        <img id="picture" src="#" alt="Click to Add Photo "
                             style="height: 200px; width:150px; border:1px solid black; background-color: white;display: block"
                             required="true">
                    </img>
                        <input type='file' onchange="readURL(this, 'picture');" name="photograph"/></td>
                    </tr>
                </table>
            </div>


            <div id="papers">
                <table align="center">
                    <tr>
                        <th>Subject</th>
                        <th>Sub Code</th>
                        <th>Date of Exam</th>
                        <th>Time of Exam</th>
                        <th>Centre</th>

                    </tr>
                    <tr>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                    </tr>
                    <tr>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                    </tr>
                    <tr>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                    </tr>
                    <tr>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                        <td><input type="text"/></td>
                    </tr>

                </table>

                <table style="align-content: center;margin-left: 10px">
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Submit">
                            <input type="reset" value="Reset" onclick="resetImage()">
                        </td>
                    </tr>
                </table>

            </div>
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

    jQuery(function ($) {
        $("#datePick").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "yy-mm-dd"
        });
    });
    $('#signatureFile').bind('change', function () {
//    alert('This file size is: ' + this.files[0].size/1024/1024 + "MB");
    });

    function readURL(input, type) {


        if (input.files && input.files[0]) {
            var reader = new FileReader();

            if (type == 'picture')
                reader.onload = function (e) {
                    $('#picture')
                            .attr('src', e.target.result)
                            .width(150)
                            .height(200);
                };
            if (type == 'signature')
                reader.onload = function (e) {
                    $('#signature')
                            .attr('src', e.target.result)
                            .width(250)
                            .height(80);
                };


            reader.readAsDataURL(input.files[0]);
        }
    }
    function resetImage() {
        $("#signature").attr('src', '#')
        $("#picture").attr('src', '#')
    }

</script>
</body>
</html>