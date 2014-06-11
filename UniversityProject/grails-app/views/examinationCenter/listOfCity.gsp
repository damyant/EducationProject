<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/29/2014
  Time: 11:45 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of City</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <table class="inner">
            <tr>
                <td class="university-size-1-4">
                    <label>District <span class="university-obligatory">*</span></label>
                </td>
                <td class="university-size-3-4">
                    <g:select name="district" id="district" onselect="" optionKey="id"
                              class="university-size-1-3" onchange="showDistrictsCityList(this)" optionValue="districtName"
                              from="${districtList}" noSelection="['':' Select District']"/>
                </td>
            </tr>
        </table>
        <table class="inner university-size-full-1-1" id="cityListTable">
            <thead>
            <tr>
                <th class="university-size-1-4">Serial No</th><th class="university-size-1-2">City Name</th><th class="university-size-1-4"></th>
            </tr>
            </thead>
           <tbody></tbody>
        </table>
    </fieldset>
</div>
</body>
</html>