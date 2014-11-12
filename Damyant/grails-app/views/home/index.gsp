<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 7/4/14
  Time: 5:49 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">

        <sec:ifLoggedIn>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <div class="university-size-full-1-1">
                    <table class="university-size-full-1-1 inner">
                        <tr>
                            <td class="university-size-1-2" style="text-align: center;"><input type="button"
                                                                                               style="margin: auto;"
                                                                                               class="university-size-1-3 university-button"
                                                                                               value="Self Enrollment Status"
                                                                                               id="checkSelfEnrollmentStatus"
                                                                                               onclick="checkSelfEnrollmentStatus()">
                            </td>
                            <td class="university-size-1-2" style="text-align: center;"><input type="button"
                                                                                               style="margin: auto;"
                                                                                               class="university-size-1-3 university-button"
                                                                                               value="Fee Status"
                                                                                               onclick="checkFeeStatus()">
                            </td>
                            %{--<td class="university-size-1-3" style="text-align: center;"><input type="button"--}%
                                                                                               %{--style="margin: auto;"--}%
                                                                                               %{--class="university-button"--}%
                                                                                               %{--value="Study Centre Registration Status"--}%
                                                                                               %{--onclick="checkStudyCentreStatus()">--}%
                            %{--</td>--}%
                        </tr>
                    </table>
                    <table id="showCounts" class="university-size-2-3 inner" style="margin: auto;">
                        <thead></thead>
                        <tbody></tbody>
                    </table>
                    <table id="showStudyCenterCount" class="university-size-2-3 inner" style="margin: auto;">
                        <thead></thead>
                        <tbody></tbody>
                    </table>
                </div>

                <div class="university-clear-both"></div>
            %{--<script type="text/javascript">--}%
            %{--$( document ).ready(function() {--}%
            %{--$('#checkSelfEnrollmentStatus').click()--}%
            %{--});--}%
            %{--</script>--}%
            </sec:ifAnyGranted>
        </sec:ifLoggedIn>
    </fieldset>
</div>
</body>
</html>