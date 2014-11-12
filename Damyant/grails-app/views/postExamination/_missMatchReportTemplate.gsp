<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Miss Match Report</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
</head>

<body>
<div style="width: 100%;border:0px solid #000000;min-height: 100px;">
    <g:set var="today" value="${new Date()}"/>
    <div style="text-align: center; width: 100%; font-size: 14px"><b>GAUHATI UNIVERSITY</b></div>

    <div style="text-align: center; width: 100%; font-size: 12px">SEMESTER EXAMINATION -  ${today[Calendar.YEAR]}</div>

    <div style="text-align: center;  width: 100%; font-size: 12px">Institute of Distance and Open Learning, G.U</div>

    <div style="text-align: center;  width: 100%; font-size: 13px"><strong>MIS-MATCH Report</strong></div>

    <div style="text-align: center; width: 100%; font-size: 12px">Program: ${programName}</div>

    <div style="text-align: center;  width: 100%; font-size: 12px">Semester:${semester}</div>

    <div style="clear: both"></div>

    <table cellspacing="0" border="1" style="width: 98%;margin: auto;">
        <tr>
            <th style="font-size: 9px;width: 12%;text-align: center;">S.No</th>
            <th style="font-size: 9px;text-align: center;">Roll No</th>

            <g:each in="${0..headerList.size()-1}" var="index">
                <th style="font-size: 9px;width: 12%;text-align: center;">${headerList[index]}</th>
            </g:each>
            <th style="font-size: 9px;width: 12%;text-align: center;">Full/ Repeat</th>
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
    <table class="university-size-full-1-1">
        <tr>
            <td>
                <ul>
                   <label style="margin-left: -10px;font-weight: bold; text-decoration: underline;"> Note:</label>
                    <li>" " for Matching Entry</li>
                    <li>"?" for Mismatch Entry</li>
                    <li>"X" for Blank Entry</li>
                </ul>
            </td>
        </tr>
    </table>
    <br/>

</div>

</body>
</html>