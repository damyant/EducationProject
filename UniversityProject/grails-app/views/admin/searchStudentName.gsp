<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 6/11/2014
  Time: 3:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    %{--<script type="text/javascript">--}%
    %{--$(function() {--}%
%{--//        var name--}%
%{--//        alert("fgfgf")--}%
%{--//        alert(nameList[0])--}%
        %{--for(var i=0;i<${nameList.size()};i++){--}%
           %{--if(i=0) {--}%
               %{--name = "[" + '"${nameList[i]}", '--}%
           %{--}--}%
            %{--else if(${nameList.size()-1}){--}%
               %{--name +='"${nameList[i]}"]'--}%
           %{--}--}%
            %{--else{--}%
               %{--name +='"${nameList[i]}", '--}%
           %{--}--}%

        %{--}--}%
%{--//        alert (name)--}%
        %{--var availableTags = "chandan";--}%
        %{--$("#searchStudent").autocomplete({--}%
            %{--source: availableTags--}%
        %{--});--}%
    %{--})--}%
    %{--</script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Search Student By Name</h3>
        <br/>
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-7">Search By Name</td>
                <td class="university-size-1-4"><input type="search"  class="university-size-1-1" id="searchStudent" placeholder="Enter First / Middle / Last Name"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7">Session</td>
                <td class="university-size-1-4">
                    <select id="session" name="programSession" class="university-size-1-1">
                        <option value="">Choose Session</option>
                        <g:each in="${sessionList}" var="session">
                            <option value="${session}">${session}</option>
                        </g:each>
                    </select>
                </td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7"></td>
                <td class="university-size-1-4"><input type="button" class="university-button" onclick="searchStudentList()" value="Search"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
        </table>
        <table class="university-size-full-1-1 inner" id="studentListTable">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </fieldset>
</div>
</body>
</html>