<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 6/18/14
  Time: 4:23 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
<div id="main">


<table>
    <tr>
        <td>Enter ChallanNumber</td> <td><input type="text" id="challanNoText"> </td><td><input type="button" value="Show"  onclick="showChallanNumberStatus()"> </td>
    </tr>
</table>

    <table id="challanStatus">

    </table>
</div>

</body>
</html>