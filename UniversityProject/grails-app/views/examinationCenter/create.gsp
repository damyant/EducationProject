<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Venue</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.js')}'></script>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'examinationCentre.js')}"></script>
    <g:javascript src='studyCenter.js'/>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Creation Of Examination Venue</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div id="msg" class="university-status-message"></div>

        <div style="margin-left: 10px; margin-bottom: 10px"><label><h6>All [<span
                class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

        <form id="examinationCenterForm" name="examinationCenterForm">

            <div class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createStudy.district"/><span class="university-obligatory">*</span>
                    </label></div>
                <g:select name="district" id="district" onselect="" optionKey="id" value=""
                          class="university-size-1-3" onchange="showExamCenterList(),clearErrorMsg(this)"
                          optionValue="districtName"
                          from="${districtList}" noSelection="['': ' Select District']"/><label id="districtError"
                                                                                                class="error5">&nbsp;</label>
            </div>

            <div id="cityList" class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createExamVenue.centre"/><span
                            class="university-obligatory">*</span></label></div>
                <g:select name="examinationCentre" id="examinationCentre" optionKey="id" value=""
                          class="university-size-1-3" optionValue="cityName" onchange="clearErrorMsg(this), showListOfStudyCenter(this)"
                          from=""
                          noSelection="['': ' Select Examination Centre']"/><label id="cityError"
                                                                                   class="error5">&nbsp;</label>
            </div>
            <div id="studyCentreForEV" style="visibility: hidden;width: 98%;margin: auto;border: 1px solid #D9E2F2;">
                %{--<div style="text-align: center;font-weight: bold; font-size: 20px;margin-bottom: 20px;">Study Center List</div>--}%
            <table id="studyCenterTab" class="university-table-1-4" style="width: 100%;margin: auto;">
                <thead>
                </thead>
                <tbody>
                </tbody>
            </table>
            </div>
            <div class="university-status-message"><div id="msgDiv"></div></div>


            <div id="VenueDiv" class="middleDiv">
            </div>
            <table style="border: none" id="examButton">

                <tr>
                    <td colspan="4">

                        <input type="button" id="submitButton" value="Submit" onclick="validateAndSubmitForm()"
                               class="${classs} buttonCss ui-button">

                        <input type="reset" value="Cancel" onclick="reset1()" class="${classs} buttonCss ui-button"/>
                        <input type="hidden" id="totalIndex" value=""/>
                    </td>
                </tr>
            </table>
        </form>
    </fieldset>
</div>
</body>
</html>