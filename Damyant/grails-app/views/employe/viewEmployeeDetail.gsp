<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="java.text.SimpleDateFormat;javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Employee Details</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script>
        $(function () {
            var icons = {
                header: "ui-icon-circle-arrow-e",
                activeHeader: "ui-icon-circle-arrow-s"
            };
            $("#accordion").accordion({
                icons: icons,
                heightStyle: "content"
            });
        });
    </script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Employee INFORMATION</h3><br/>

        <div id="accordion">
            <h3>Personal Details</h3>

            <div>
                <p>
                <table class="inner " style="width: 100%;margin: auto;">
                    <tr>
                        <td class="university-size-2-3">
                            <div class="main-Content">
                                <g:if test="${employeeObj?.firstName}">
                                    <div class="label-header">Name</div>

                                    <div class="label-content">${employeeObj?.firstName} ${employeeObj?.middleName} ${employeeObj?.lastName}</div>
                                </g:if>
                                <g:if test="${employeeObj?.dob}">
                                    <div class="label-header">Date of Birth</div>

                                    <div class="label-content"><g:formatDate format="dd-MMM-yyyy"
                                                                             date="${employeeObj?.dob}"/></div>
                                </g:if>
                                <g:if test="${employeeObj?.mobileNo}">
                                    <div class="label-header">Mobile Number</div>

                                    <div class="label-content">${employeeObj?.mobileNo}</div>
                                </g:if>
                               <g:if test="${employeeObj?.gender}">
                                    <div class="label-header">Gender</div>

                                    <div class="label-content">${employeeObj?.gender}</div>
                                </g:if>
                                <g:if test="${employeeObj?.currentAddress}">
                                    <div class="label-header">Current Address</div>

                                    <div class="label-content">
                                        ${employeeObj?.currentAddress}
                                    </div>
                                </g:if>
                                <g:if test="${employeeObj?.permanentAddress}">
                                    <div class="label-header">Permanent Address</div>

                                    <div class="label-content">
                                        ${employeeObj?.permanentAddress}
                                    </div>
                                </g:if>
                            </div>
                        </td>
                        %{--<td class="university-size-1-3">--}%
                            %{--<g:if test="${studInstance?.studentImage}">--}%
                                %{--<img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id, mime: 'image/jpeg')}"--}%
                                     %{--class="university-registration-photo" id="picture1"/>--}%
                            %{--</g:if>--}%
                        %{--</td>--}%
                    </tr>
                </table>
            </p>
            </div>

            <h3>Office Details</h3>

            <div>
                <p>
                <table class="inner " style="width: 100%;margin: auto;">
                    <tr>
                        <td class="university-size-1-2">
                            <div class="main-Content">
                                <g:if test="${employeeObj?.employeeCode}">
                                    <div class="label-header">Employee Code</div>

                                    <div class="label-content">${employeeObj?.employeeCode}</div>

                                    <div class="label-header">Designation</div>

                                    <div class="label-content">${employeeObj?.designation}</div>

                                    <div class="label-header">PAN Number</div>

                                    <div class="label-content">${employeeObj?.panNumber}</div>

                                    <div class="label-header">Date of Joining</div>

                                    <div class="label-content"><g:formatDate format="dd-MMM-yyyy" date="${employeeObj?.dateOfJoining}"/></div>


                                </g:if>

                            </div>
                        </td>

                    </tr>
                </table>
            </p>
            </div>





        </div>
    </fieldset>

</div>

</body>
</html>