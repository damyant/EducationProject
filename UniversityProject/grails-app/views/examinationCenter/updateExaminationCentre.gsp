<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Center</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery.min.js')}'></script>

</head>

<body>
<div id="main">
    <div>
        Please Select The Location:
        <select id="location" name="centreLocation" onchange="showList()">
            <option value="0">Select Location</option>
            <option value="Guahati">Guahati</option>
            <option value="Noida">Noida</option>
            <option value="Golaghati">Golaghat</option>
            <option value="Jaipur">Jaipur</option>
        </select>
    </div>
    <div id="centreList" style="text-align: center; width: 100%">
    </div>
</div>
<script>
    function showList(){
        var valueOf= $('#location').val()
        jQuery("#centreListTable").css({display: "block"});
        $.ajax({
            type:"post",
            url:'${createLink(controller: 'examinationCenter', action: 'getCentreList')}',

            data:{valueOf:valueOf, edit:'edit'},
            success:function (response) {   //

                $("div#centreList").html(response)

            } ,
            error:function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>
</body>
</html>