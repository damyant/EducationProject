<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/23/2014
  Time: 5:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Enrollment At Idol</title>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
    <script type="text/javascript">
        $(function () {
            $(function () {
                $("#datepicker").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: "mm/dd/yy",
                    maxDate: 0
                });
            });
        });
    </script>
</head>

<body>
    <div id="main">
        <fieldset class="form">
            <h3>Student Enrollment</h3>
            <form id="tempEnrollment">
                <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
                <table class="inner university-size-full-1-1">
                    <tr>
                        <td class="university-size-1-3">Name of the applicant <span class="university-obligatory">*</span></td>
                        <td class="university-size-2-3"><input type="text" name="name" style="text-transform:uppercase" onkeypress="return onlyAlphabets(event, this);"
                                   maxlength="50" class="university-size-1-3" value="${studInstance?.name}"/>

                        </td>
                    </tr>
                    <tr>
                        <td>Program<span class="university-obligatory">*</span></td>
                        <td>

                            <g:select name="programId" id="programId" optionKey="id" class="university-size-1-3" value="${studInstance?.programDetail?.id?.get(0)}"
                                      optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"/>

                        </td>
                    </tr>
                    <tr>
                        <td>Date of Birth <span class="university-obligatory">*</span></td>



                        <td>
                            %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
                            <input type="text" name="d_o_b" maxlength="10" class="university-size-1-3" id="datepicker"
                                   value="<g:formatDate format="MM/dd/yyyy" date="${studInstance?.dob}"/>">

                        </td>
                    </tr>

                    <tr>
                        <td>Category <span class="university-obligatory">*</span></td>
                        <td>
                            <div class="radio_options">
                                <label><span>General</span><input type="radio" name="category" value="General" class="radioInput"/></label>

                                <label><span>MOBC</span><input type="radio" name="category" value="MOBC" class="radioInput"/></label>

                                <label><span>OBC</span><input type="radio" name="category" value="OBC" class="radioInput"/></label>

                                <label><span>SC</span><input type="radio" name="category" value=" SC" class="radioInput" style=""/></label>

                                <label><span>ST</span><input type="radio" name="category" value="S.T" class="radioInput"/></label>

                                <label><span>MINORITY</span><input type="radio" name="category" value="MINORITY COMMUNITY"
                                                                   class="radioInput"/>
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Gender <span class="university-obligatory">*</span></td>
                        <td>
                            <div class="radio_options">
                                <label><span>Male</span><input type="radio" name="gender" value="Male" class="radioInput"/></label>
                                <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/></label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Mobile Number <span class="university-obligatory">*</span></td>
                        <td>
                            <input type="text" id="mobileNoCntryCode" name="mobileNoCntryCode" maxlength="3" value="+91" readonly> - <input
                                type="text" id="mobileNo" name="mobileNo" maxlength="10" value="${studInstance?.mobileNo}"
                                onkeypress="return isNumber(event)"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Select Preference of examination Venue <span class="university-obligatory">*</span></td>
                        <td>

                            <table id="examCenterSelect" class="university-size-full-1-1">
                                <tr>
                                    <td style="width: 50%">

                                        <g:select name="district" id="district" optionKey="id"
                                                  value="${studInstance?.examinationCentre?.city?.district?.id?.get(0)}" class="university-size-1-1"
                                                  onChange="showCityList()" optionValue="districtName"
                                                  from="${districtList}" noSelection="['': ' Select District']"/>

                                    </td>
                                    <td style="width: 50%">
                                        <g:if test="${studInstance}">
                                            <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                                                      optionValue="cityName" value="${studInstance?.examinationCentre?.city?.id?.get(0)}"
                                                      from="${City.findAllByDistrict(District.get(studInstance?.examinationCentre?.city?.district?.id))}"
                                                      onchange="showCentreList()"
                                                      noSelection="['': ' Select City']"/></g:if>
                                        <g:else>
                                            <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                                                      optionValue="cityName"
                                                      from="" onchange="showCentreList()"
                                                      noSelection="['': ' Select City']"/>
                                        </g:else>
                                    </td>
                                </tr><tr>
                                <td>
                                    <g:if test="${studInstance}">
                                        <g:select name="examiNationCentre" id="examinationCentre" class="university-size-1-1" optionKey="id" optionValue="name"
                                                  from="${ExaminationCentre.findAllByCity(City.get(studInstance?.examinationCentre?.city?.id?.get(0)))}"
                                                  value="${studInstance?.examinationCentre?.id?.get(0)}"
                                                  noSelection="['': 'Select Examination Venue']"/>
                                    </g:if>
                                    <g:else>
                                        <g:select name="examiNationCentre" id="examinationCentre" class="university-size-1-1" from=" "
                                                  noSelection="['': 'Select Examination Venue']"/>
                                    </g:else>
                                </td>
                                <td></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Submit" onclick="validate()" class="university-button">
                            <input type="reset" value="Reset" onclick="resetImage()" class="university-button">
                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </div>
</body>
</html>