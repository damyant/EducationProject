<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 6/11/14
  Time: 12:41 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'homeAssignment.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
<div id="main">
    <div id="errorMsgForRollNo" class="university-status-message"></div>
    <div id="statusMsgForRollNo" class="university-status-message"></div>
    <h3>HOME ASSIGNMENT SUBMISSION</h3>
    <g:form controller="homeAssignment" action="saveHomeAssignment" name="postExamFee" id="postExamFee">
        <table>
            <tr>
                <td><label> Enter RollNumber</label></td>
                <td><input type="text" name="rollNumberInput" id="rollNumberInput" onchange="searchHomeAssignmentByRollNumber()" ></td>
            </tr>
            <tr>
                <td><label>Course</label></td>
                <td><input type="text" name="course" id="course" readonly></td>
            </tr>
            <tr>
                <td><label>Semester/ Term</label></td>
                <td>
                    <div id="checkTerms">
                    </div>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Save" id="saveHomeAssignment"> </td>
            </tr>
        </table>
    </g:form>

    <div id="challanDiv" class="dialog" style="width: 200px;margin:auto;">
        <input type="button" id="print" value="Print" onclick="printHomeAssignment('#feeChallanDiv')" style="text-align: center;">
        <div id="feeChallanDiv" style="font-family: 'Lucida Sans Typewriter', 'Lucida Console', Monaco, 'Bitstream Vera Sans Mono', monospace;border:0px;height: 400px;margin: auto;font-weight:100; margin-bottom: 80px">
            <div style="border: 0px solid;">
                <div class="university-clear-both"></div>
                <div style="clear: both; margin-bottom: 10px;"></div>
                <table  style="width:100%;margin:auto;border: solid 0px black;letter-spacing:1px;font-family: 'Lucida Sans Typewriter', 'Lucida Console', Monaco, 'Bitstream Vera Sans Mono', monospace;font-style: normal;font-weight:100 ;">
                    <tr><td colspan="2" style="text-align: center;font-size: 10px;">IDOL, GU</td></tr>
                    <tr><td colspan="2" style="text-align: center;font-size: 10px;">H/A Acknowledgment Slip</td></tr>
                    <tr><td style="width:151px;font-size: 10px;padding-left: 5px;"><label>Name:</label></td><td style="width: 227px;font-size: 10px;"><label id="studentName"></label></td></tr>
                    <tr><td  style="width: 151px;font-size: 10px;padding-left: 5px;"><label>Roll No:</label></td><td style="width: 227px;font-size: 10px;letter-spacing:2px;"><label id="studentRollNo"></label></td></tr>
                    %{--<tr><td  style="width: 151px;font-size: 10px;padding-left: 5px;"><label>Programme:</label></td><td style="width:227px;font-size: 10px;letter-spacing:2px;"><label id="courseName"></label></td></tr>--}%
                    <tr><td  style="width: 151px;font-size: 10px;padding-left: 5px;">Semester/Term:</td><td style="width: 227px;font-size: 10px;"><label id="homeAssignment"></label></td></tr>
                    <tr><td  style="width: 151px;font-size: 10px;padding-left: 5px;">Submission Date:</td><td style="width:227px;font-size: 10px;"><label id="date"></label></td></tr>
                    %{--<tr><td  style="height:60px">&nbsp;</td><td  style="height:60px">&nbsp;</td></tr>--}%
                        %{--<td style="vertical-align: bottom;">--}%
                            %{--<div style="width:95%;margin:auto;text-align: right; bottom: 2px;font-size: 9px;"><label style="float:right;">Cashier's Signature</label></div>--}%
                        %{--</td>--}%
                    %{--</tr>--}%
                </table>
            </div>
        </div>
    </div>




</div>
   <script type="text/javascript">
       var name = "${studentName}";
       var rollNo = "${rollNo}" ;
       var dateObj= new Date() ;
       var month = dateObj.getMonth()  ;
       var day = dateObj.getDate()  ;
       var year = dateObj.getFullYear()  ;
       var date = (day + "/" + month + "/" + year) ;
       var course = "${courseName}" ;
       var semList = "${semList?.size()}";
       var semesters="${semList}";
       $(function() {
           $(".dialog").dialog({
               autoOpen: false,
               draggable: false,
               position: ['center', 0],
               width: 500,
               resizable: false,
               height: 300,
               modal: true,
               title: 'Home Assignment',
               close: function (ev, ui) {
                   $.unblockUI();
                   window.open("/UniversityProject/homeAssignment/submitHomeAssignment",'_self',false)
               }
           });

           if(name){
               document.getElementById("postExamFee").reset();
               $('#studentName').text(''+name)
               $('#studentRollNo').text(''+rollNo)
               $('#homeAssignment').text(semesters)
               $('#courseName').text(course)
               $('#date').text(''+date);
               $('#challanDiv').dialog('open')
           }

       });

   </script>
</body>
</html>