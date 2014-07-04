<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/15/2014
  Time: 5:27 PM
--%>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Student Address Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    <script type="text/javascript">
    </script>
    <style type="text/css">
    @page {
        size: 220mm 100mm;
        margin: 10px;
    }
    div.break {
        page-break-after: always;
    }
    </style>
</head>

<body>
<g:each in="${0..studentName.size()-1}" var="i">
    <div style="width: 790px;height:335px; border: 0px solid black">
        <div style="padding-left: 380px;padding-top:70px; border: 0px solid black">
             <div>${studentName[i]}</div>
            <div>${studentAddress[i]}</div>
            <div>${studentTown[i]}, ${studentDistrict[i]} </div>
            <div>${studentState[i]}, Pin Code:${studentPin[i]} </div>
            <div>Contact No:  ${studentMobNo[i]}</div>
        </div>

    </div>
    <g:if test="${studentName.size()-1!=i}">
        <div class="break"></div>
    </g:if>
</g:each>
</body>
</html>