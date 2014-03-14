<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/10/14
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.7.1.js')}"></script>

    <title>Create Examination Center</title>
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
            data:{valueOf:valueOf},
//            contentType: "application/json; charset=utf-8",
//            dataType: "json",
            success:function (response) {
                console.log("<><><><><><><><> "+response)
                $("div#centreList").html(response)


            } ,
            error:function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>
</body>
</html>