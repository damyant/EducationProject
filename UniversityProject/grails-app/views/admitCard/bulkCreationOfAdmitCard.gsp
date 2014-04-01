<%--
  Created by IntelliJ IDEA.
  User: Shweta
  Date: 31/3/14
  Time: 12:56 PM
--%>

<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
</head>

<body>
     <div id="main">
       <g:form controller="admitCard" action="bulkCreationOfAdmitCard" method="post">
           <table class="university-table-1-3">
               <tr>
                   <td>
                       <g:select name="examinationCenter" id="examinationCenter" optionKey="id" optionValue="name" from="${ExaminationCentre.findAll()}" noSelection="['':' Select Examination Center']"></g:select>
                   </td>
                   <td>
                       <g:select name="program" id="program" optionKey="id" optionValue="courseName" from="${ProgramDetail.findAll()}" onchange="getSemester();" noSelection="['':' Select Program']"></g:select>
                   </td>
                   <td>
                       <g:select name="semesterList" id="semesterList" optionKey="id" optionValue="" from="" onchange="showStudentInfo();" noSelection="['':' Select Semester']"></g:select>
                   </td>
               </tr>
           </table>
       </g:form>
         <table id="admitCardTab" class="university-table-1-3"  style="display: none">
         </table>
         <table id="subjectTab" class="university-table-1-4" style="display: none">
         </table>
          </div>
     %{--<table class="university-table-1-4">--}%
         %{--<thead id="admitCardTab"></thead>--}%
         %{--<tbody></tbody>--}%
     %{--</table>--}%


</body>
</html>