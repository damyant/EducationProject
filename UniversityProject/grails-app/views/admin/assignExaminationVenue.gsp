<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assign Examination Venue</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admitCard.js')}"></script>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <form id="assignExamVenue">
    <fieldset class="form">
<g:form  method='post' enctype="multipart/form-data" id="assignExamVenue" name="assignExamVenue">
        <table class="inner" style="width: 95%;margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Select a Course</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" class="university-size-1-1" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="setCourseLabel(this)"/>
                </td>
                <td class="university-size-1-2"><label id="courseForExamVenue" style="margin-left: 45%;"></label></td>
            </tr>
            <tr>
                <td class="university-size-1-4"><label>Select an Examination Centre </label></td>
                <td class="university-size-1-4">
                    <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                              optionValue="cityName" from="${examinationCenterList}" noSelection="['': ' Select City']"
                              onchange="showExamVenueList1()"/>
                </td>
                <td class="university-size-1-2"><label id="CentreForExamVenue" style="margin-left: 45%;"></label></td>
            </tr>
            <tr>
                <td class="university-size-1-4"><label>Select Examination Venue </label></td>
                <td colspan="2">
                    <table class="university-size-1-1 inner">
                        <tr><td class="university-size-1-3">
                    <g:select name="examinationCenter" class="university-size-1-1" id="examCenterList" from="" style="width: 90%"
                              noSelection="['': ' Select Examination Venue']" multiple="true"/>
                        </td><td  style="width: 12.5%">
                            <div id="moveButton" style="visibility: hidden">
                            <button type="button" class="multiSelect-buttons-button" onclick="addExamCenterToList()" name="add"  id="add">Add</button>
                            <button type="button" class="multiSelect-buttons-button" onclick="removeExamCenterFromList()" name="remove"  id="remove">Remove</button>
                            </div>
                        </td><td  class="university-size-1-3">
                            <div id="movetoSelect" style="visibility: hidden">
                            <g:select class="select-to" style="width: 90%"  name="addExamCentre" id="addExamCentre"  optionKey="" optionValue="" from="" multiple="true"/>
                    <div id="error-select-' + j + '"></div>
                                </div>
                        </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4">
                    <input type="button" value="Submit"  class="university-button" onclick="saveExamVenue()">
                </td>
                <td class="university-size-1-2"></td>
            </tr>

        </table>
    </g:form>

            %{--<table style="width: 95%;margin: auto" id="examVenueList">--}%
                %{--<tr>--}%
                    %{--<th class="university-size-1-3" style="padding-left: 10px;">Course</th>--}%
                    %{--<th class="university-size-1-3" style="padding-left: 10px;">Examination Centre</th>--}%
                    %{--<th class="university-size-1-3" style="padding-left: 10px;">Examination Venue</th>--}%
                %{--</tr>--}%
            %{--</table>--}%
    </fieldset>
        </form>
</div>
<script>
    $(function() {
        $(".datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "mm/dd/yy"
        });
    });
</script>
</body>
</html>
