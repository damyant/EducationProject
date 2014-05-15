<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/5/2014
  Time: 11:21 AM
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
        <g:if test="${params?.type}">
            <h3>Study Centre Payment Fee Entry</h3>
        </g:if>
        <g:else>
            <h3>Study Centre Payment Fee Entry</h3>
        </g:else>
        <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
        <table class="inner university-size-full-1-1" id="scStudnetList" style="margin: auto">
            <thead>

            <tr>
                <td>Enter Challan Number</td>
             <td> <input type="text" name="searchChallanNo"id="searchChallanNo"   value=""/></td>
                <td><input type="button" value="Show Students" onclick="showStudents()"/> </td>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
        <br/>
        <div class="university-size-1-2"  style="margin: 5px auto;width:98%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;" id="rangeRadioButtons" hidden="hidden">
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="rangeEntry" name="entry" value="Range"> <label for="rangeEntry">Enter Fee By Range</label> </div>
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="individualEntry" name="entry" value="Range"> <label for="individualEntry">Enter Fee Individually</label></div>
        </div>
        <br/>
        <table id="paymentDetails" hidden="hidden" style="margin: auto;border:1px solid #dddddd; " >

        </table>
        <br/>
        <div style="width:50%;margin:auto;">
                <input type="button" class="university-size-1-3 ui-button" id="generateFeeChallan" value="Generate Fee Challan" style="display: none; float: left;"/>
                <input type="button" class="university-size-1-3 ui-button" id="PayByChallan" value="Pay" style="display: none;float:  right;"/>

        </div>
        </fieldset>
</div>
</body>
</html>