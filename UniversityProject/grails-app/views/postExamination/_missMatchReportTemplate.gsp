<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Miss Match Report</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
</head>

<body>
<div style="width: 80%;float:left;border:0px solid #000000;min-height: 100px;">
    <g:set var="today" value="${new Date()}"/>
    <div style="text-align: center; width: 96%; font-size: 14px"><b>GAUHATI UNIVERSITY</b></div>

    <div style="text-align: left; float: left; width: 96%; font-size: 12px">SEMESTER EXAMINATION -  ${today[Calendar.YEAR]} ,Held</div>

    <div style="text-align: left; float: left; width: 48%; font-size: 12px">Institute of Distance and Open Learning, G.U</div>

    <div style="text-align: left; float: left; width: 48%; font-size: 12px">MIS-MATCH Report</div>

    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Program: ${programName}</div>

    <div style="text-align: left; float: left; width: 48%; font-size: 12px">Semester:${semester}</div>

    <div style="clear: both"></div>

    <table cellspacing="0" border="1" style="width: 98%;margin: auto;">

        <tr>
            <th></th>
            <th></th>
            <g:each in="${subjectList?.subjectId?.subjectName}" var="subject">
                <th></th>
            </g:each>

            <g:each in="${groupIns?.groupName}" status="i" var="groupName">

                <th colspan=" ${groupSubjectCount[i]}">${groupName}</th>
            </g:each>

        </tr>

        <tr>
            <th>S.No</th>
            <th>Roll No</th>

            <g:each in="${subjectList?.subjectId?.subjectName}" var="subject">
                <th>${subject}</th>
            </g:each>
            <g:each in="${groupSubList}" var="groupSubject">
                <g:if test="${groupSubject instanceof java.util.List}">
                    <g:each in="${groupSubject}" var="subList">
                        <th>${subList?.subjectId?.subjectName}</th>
                    </g:each>

                </g:if>
            </g:each>
            <th>Full/ Repeat</th>
        </tr>

        <g:each in="${finalList}" var="stuList">
            <tr>
                <g:if test="${stuList instanceof java.util.List}">
                    <g:each in="${stuList}" var="subList">
                        <td>
                            ${subList}</td>
                    </g:each>
                </g:if>

            </tr>
        </g:each>

    </table>

    <br/>

</div>

</body>
</html>