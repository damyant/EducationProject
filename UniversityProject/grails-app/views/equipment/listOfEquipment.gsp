<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 22/9/14
  Time: 11:49 AM
--%>


<html>
<head>
    <meta name="layout" content="main"/>
    <title>Equipment List</title>
    <script type="text/javascript">
        function confirmBeforeDelete(equipmentId){

            var status= confirm("Are you sure want to delete")
            if(status){
                $.ajax({
                    type: "post",
                    url: url('equipment', 'deleteEquipment', ''),
                    data: '&equipmentId=' + equipmentId,
                    success: function (data) {
                        if (data.status == true) {
                            location.reload(true);
                        }
                        else {
//                            $("#successMessage").html("")
                        }
                    }
                });

            }
        }
    </script>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-size-full-1-1 university-table-1-4 university-table-text-left ">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-size-full-1-1 university-table-1-3 university-table-text-left " style="border: 1px solid #f2f5f7">
        </g:else>
        <thead>
        <tr>
            <th>Equipment Name</th>
            <th>Equipment Id</th>
            <th>Manufacturer</th>
            <th>Equipment Type</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>

        <g:each var="equipment"  in="${equipmentObjList}">
            <tr>
                <td>${equipment.equipmentName}</td>
                <td>${equipment.equipmentId}</td>
                <td>${equipment.manufacturer}</td>
                <td>${equipment.equipmentType}</td>
                <td><button class="university-button"> <g:link  controller="equipment" action="viewEquipmentDetail"   class="university-text-decoration-none" params="[id:equipment.id]">View</g:link></button></td>
                <td><button class="university-button"> <g:link  controller="equipment" action="createEquipment" class="university-text-decoration-none" params="[id:equipment.id]">Update</g:link></button></td>
                <td><button class="university-button" onclick="confirmBeforeDelete('${equipment.id}')"> Delete</button></td>
            </tr>
        </g:each>
        </tbody>
    </table>
        %{--<div class="paginateButtons">--}%
        %{--<g:if test="${params.type == 'update'}">--}%
        %{--<g:paginate controller="course" action="listOfCourses"  total="${courseInstanceTotal}" params="['type':'update']"/>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
        %{--<g:paginate controller="course" action="listOfCourses"  total="${courseInstanceTotal}" />--}%
        %{--</g:else>--}%
        %{--</div>--}%
    </fieldset>
</div>

</body>

</html>