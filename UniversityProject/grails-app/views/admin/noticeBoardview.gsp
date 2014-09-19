<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/18/14
  Time: 6:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>
<body>

<div >
    <h3>Document List</h3>


    <table>
        <thead>
        <tr>
            <g:sortableColumn property="noticeHeader" title="Notice" />
            <g:sortableColumn property="fileName" title="File" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${f}" status="i" var="f">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${NoticeBoard.fileName}</td>
                <td><g:link action="download" id="${f.id}"> ${NoticeBoard.noticeHeader}</g:link></td>
            </tr>
        </g:each>
        </tbody>
    </table>

</div>
</body>
</html>