<%--
  Created by IntelliJ IDEA.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Study Center</title>
    <meta name="layout" content="main"/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <title>Create Study Center</title>
</head>

<body>
<div id="main">
<fieldset class="form">
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${studyCentreInstance}">
    <div class="errors">
        <g:renderErrors bean="${studyCentreInstance}" as="list"/>
    </div>
</g:hasErrors>
<g:if test="${params.status == 'created'}"><div class="university-status-message"><g:message
        code="studyCenter.create.message"/></div></g:if>
<g:elseif test="${params.status == 'updated'}"><div class="university-status-message"><g:message
        code="studyCenter.update.message"/></div></g:elseif>
<g:form controller="studyCenter" action="saveStudyCenter" method="post" name="createStudyCenter"
        id="createStudyCenter">

    <g:if test="${params.type == 'view'}">
        <table class="university-table-1-2 inner" style="width: 98%; margin: auto;">
        <tr>
            <td><label><g:message code="default.createStudy.nameOfCenter"/> :</label></td>
        <td><label>${studyCentreInstance?.name}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.address"/> :</label></td>
            <td><label>${studyCentreInstance?.address}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.district"/> :</label></td>
            <td><label>${studyCentreInstance?.city?.district?.districtName}</label>
            </td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.city"/> :</label></td>
            <td><label>${studyCentreInstance?.city?.cityName}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.centerCode"/> :</label></td>
            <td><label>${studyCentreInstance?.centerCode}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.websiteUrl"/> :</label></td>
            <td><label>${studyCentreInstance?.websiteUrl}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.nameOfHeadIns"/> :</label></td>
            <td><label>${studyCentreInstance?.nameOfHeadIns}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.phoneNoOfHeadIns"/></label></td>
            <td><label>${studyCentreInstance?.phoneNoOfHeadIns}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.emailIdOfHeadIns"/> :</label></td>
            <td><label>${studyCentreInstance?.emailIdOfHeadIns}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.nameOfCoordinator"/> :</label></td>
            <td><label>${studyCentreInstance?.nameOfCoordinator}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.phoneNoOfCoordinator"/> :</label></td>
            <td><label>${studyCentreInstance?.phoneNoOfCoordinator}</label></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createStudy.emailIdOfCoordinator"/> :</label></td>
            <td><label>${studyCentreInstance?.emailIdOfCoordinator}</label></td>
        </tr>
    </g:if>

%{--for  Create new Study Center--}%

    <g:else>
        <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label>
        <table class="university-table-1-2 inner" style="width: 100%; margin: auto;">
        <tr>
            <td><label><g:message code="default.createStudy.nameOfCenter"/><span
            class="university-obligatory">*</span> </label></td>
        <td>
            <input type="text" name="name" id="name" value="${studyCentreInstance?.name}"
                   class="university-size-1-3" onkeypress="return onlyAlphabets(event, this);"/>
        </td
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.address"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="address" value="${studyCentreInstance?.address}" id="address"
                               maxlength="" class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.district"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td>
                        <g:select name="district" id="district" optionKey="id"
                                  value="${studyCentreInstance?.city?.district?.id}" class="university-size-1-3"
                                  onchange="showCityList()" optionValue="districtName" from="${District.findAll()}"
                                  noSelection="['': ' Select District']"/>
                    </td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.city"/> <span class="university-obligatory">*</span>
                    </label></td>
                    <td>
                        <g:if test="${params.type != 'edit'}">
                            <g:select name="city" id="city" optionKey="id" class="university-size-1-3"
                                      optionValue="cityName" from="" noSelection="['': ' Select City']"/>
                        </g:if>
                        <g:else>
                            <g:select name="city" id="city" optionKey="id" value="${studyCentreInstance?.city?.id}"
                                      class="university-size-1-3" optionValue="cityName"
                                      from="${City.findAllByDistrict(District.get(studyCentreInstance?.city?.district?.id))}"
                                      noSelection="['': ' Select City']"/>
                        </g:else>
                    </td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.centerCode"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" id="centerCode" name="centerCode" onkeypress="return isAlphaNumeric(event)" onchange="checkCenterCode(this)"
                               value="${studyCentreInstance?.centerCode}" maxlength=""
                               class="university-size-1-3"/><label id="errorMsg" class="error1" ></label></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.websiteUrl"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="websiteUrl" value="${studyCentreInstance?.websiteUrl}" id="websiteUrl"
                               class="university-size-1-3" onblur="checkWebsiteUrl(this)"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.nameOfHeadIns"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="nameOfHeadIns" onkeypress="return onlyAlphabets(event, this);"
                               value="${studyCentreInstance?.nameOfHeadIns}"
                               class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.phoneNoOfHeadIns"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="phoneNoOfHeadIns" value="${studyCentreInstance?.phoneNoOfHeadIns}"
                               id="phoneNoOfHeadIns" class="university-size-1-3" maxlength="10"
                               onkeypress="return isNumber(event)"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.emailIdOfHeadIns"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="email" name="emailIdOfHeadIns" value="${studyCentreInstance?.emailIdOfHeadIns}"
                               class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.nameOfCoordinator"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="nameOfCoordinator" onkeypress="return onlyAlphabets(event, this);"
                               value="${studyCentreInstance?.nameOfCoordinator}"
                               class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.phoneNoOfCoordinator"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="phoneNoOfCoordinator"
                               value="${studyCentreInstance?.phoneNoOfCoordinator}" class="university-size-1-3"
                               maxlength="10" onkeypress="return isNumber(event)"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.emailIdOfCoordinator"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="email" name="emailIdOfCoordinator"
                               value="${studyCentreInstance?.emailIdOfCoordinator}" class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.nameOfAsstCoordinator"/> <span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" name="asstCooirdinator" onkeypress="return onlyAlphabets(event, this);"
                               value="${studyCentreInstance?.asstCooirdinator}"
                               class="university-size-1-3"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.phoneNoOfAsstCoordinator"/> <span
                            class="university-obligatory">*</span></label>
                    </td>
                    <td><input type="text" name="asstMobile"
                               value="${studyCentreInstance?.asstMobile}" class="university-size-1-3"
                               maxlength="10" onkeypress="return isNumber(event)"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createStudy.emailIdOfAsstCoordinator"/> <span
                            class="university-obligatory">*</span></label>
                    </td>
                    <td><input type="email" name="asstEmail"
                               value="${studyCentreInstance?.asstEmail}" class="university-size-1-3"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <input type="hidden" value="${studyCentreInstance?.id}" name="studyCenterId">
                        <input type="button" id="submitButton" value="<g:if test="${params.type != 'edit'}"><g:message
                                code="default.button.create"/></g:if><g:else><g:message
                                code="default.button.save"/></g:else>" class="university-button" onclick="validate()">

                <g:if test="${params.status != 'updated'}"><input type="reset" value="<g:message
                        code="default.button.clear"/>" class="university-button"></g:if> <g:else><input
                    type="reset" value="<g:message code="default.button.clear"/>" class="university-button"
                    disabled></g:else></td>
        </tr>
    </g:else>
    </table>
</g:form>
</fieldset>
</div>
</body>
</html>