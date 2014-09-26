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
    <script src="${resource(dir: 'js', file: 'monthpicker.js')}"></script>
    <script src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <title></title>

</head>

<body><div id="main">
    <fieldset class="form">
        <h3>Notice Board</h3>
        <table>

            <thead>
            <tr>
                <g:sortableColumn property="noticeHeader" title="Date" class="university-size-1-4"/>
                <g:sortableColumn property="noticeHeader" title="Description" class="university-size-1-2"/>
                <g:sortableColumn property="fileName" title="Link" class="university-size-1-4"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${noticeList}" status="i" var="noticeInst">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td>${noticeInst.noticeDate.format('dd-MM-yyyy')}</td>
                        <td>${noticeInst.noticeHeader}</td>
                        <td><g:link controller="admin" action="download" params="[fileName: noticeInst.fileName]"
                                    target="_blank"><img style="width:30px; "
                                                         src="${resource(dir: 'images', file: 'download.png')}"
                                                         class="window"></g:link></td>

                    </tr>
            </g:each>
            </tbody>
        </table>

        <div style="margin-top: 10px;"><label>Previous Notices :</label>
            <g:form controller="admin" action="loadArchiveNotice" id="loadArchiveNoticeForm">
            <input id="Html5Month" name="month" type="month"/>
                <input type="submit" value="Load">
            </g:form>
        </div>

    </fieldset>
</div>
</body>
</html>