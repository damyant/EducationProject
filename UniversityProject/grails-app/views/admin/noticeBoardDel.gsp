<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/19/14
  Time: 4:40 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>
<body><div id="main">
    <fieldset class="form">
        <h3>Edit Notice</h3>


        <table>

            <thead>
            <tr>
                <g:sortableColumn property="noticeHeader" title="Date" class="university-size-1-4"/>
                <g:sortableColumn property="noticeHeader" title="Description" class="university-size-1-2"/>
                <g:sortableColumn property="fileName" title="Edit" class="university-size-1-4"/>
                <g:sortableColumn property="fileName" title="Delete" class="university-size-1-4"/>
                <g:hiddenField name="id" value="id"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${noticeList}" status="i" var="noticeInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${noticeInst.noticeDate.format('dd-MM-yyyy')}</td>
                    <td>${noticeInst.noticeHeader}</td>
                    <td><g:link controller="admin" action="noticeBoard" params="[noticeInstId:noticeInst.id]">Edit</g:link></td>

                    <td><g:link controller="admin" action="delNotice" params="[noticeInstId:noticeInst.id]">Delete</g:link></td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>
</body>
</html>