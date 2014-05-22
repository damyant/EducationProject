<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 5/21/2014
  Time: 10:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Assign Roll Number Generation Date</h3>
        <g:form controller="admin" action="saveRollNoGenerationDate" id="rollNoGenerationDate" name="rollNoGenerationDate">
        <table class="inner">
            <tr>
                <td class="university-size-1-3">
                    Enter Start Date <span class="university-obligatory">*</span>
                </td>
                <td class="university-size-2-3">
                    <input type="text" name="startD" id="startD" value="<g:formatDate format="dd/MM/yyyy" date="${rollDateInst.startD}"/>" class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-3">
                    Enter End Date <span class="university-obligatory">*</span>
                </td>
                <td class="university-size-2-3">
                    <input type="text" name="endD" id="endD" value="<g:formatDate format="dd/MM/yyyy" date="${rollDateInst.endD}"/>" class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-3">

                </td>
                <td class="university-size-2-3">
                    <input type="submit" value="Save" onclick="validate()" class="university-size-1-5 ui-button"/>
                </td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>

</body>
</html>