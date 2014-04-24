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
                <table class="inner university-size-1-1">
                    <tr>
                        <td class="university-size-1-3">Name of the applicant <span class="university-obligatory">*</span></td>
                        <td class="university-size-2-3"><input type="text" name="name" style="text-transform:uppercase" onkeypress="return onlyAlphabets(event, this);"
                                   maxlength="50" class="university-size-1-2" value="${studInstance?.name}"/>

                        </td>
                    </tr>
                    <tr>
                        <td>Date of Birth <span class="university-obligatory">*</span></td>



                        <td>
                            %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
                            <input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datepicker"
                                   value="<g:formatDate format="MM/dd/yyyy" date="${studInstance?.dob}"/>">

                        </td>
                    </tr>
                </table>
            </form>
        </fieldset>
    </div>
</body>
</html>