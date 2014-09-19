<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/18/14
  Time: 1:24 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Notice Upload</h3>
        <g:uploadForm id="ntc1" controller="admin" action="noticeBoardSave">
            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <td class="university-size-1-3">Name<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="Name">

                    </td>







                <tr>
                    <td class="university-size-1-3">Upload Pdf File<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <input type='file' id="fle" name="fle" >
                    </td>

                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Submit"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>


</body>
</html>