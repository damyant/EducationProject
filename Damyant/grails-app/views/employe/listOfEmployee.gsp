<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 22/9/14
  Time: 11:49 AM
--%>


<html>
<head>
    <meta name="layout" content="main"/>
    <title>View Course</title>
    <script type="text/javascript">
        function confirmBeforeDelete(empId){

           var status= confirm("Are you sure want to delete")
            if(status){
                $.ajax({
                    type: "post",
                    url: url('employe', 'deleteEmployee', ''),
                    data: '&empId=' + empId,
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
            <th>Employee Name</th>
            <th>Employee Code</th>
            <th>Designation</th>
            <th>Contact No.</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>

            <g:each var="employee"  in="${employeeList}">
                <tr>
                    <td>${employee.firstName}</td>
                    <td>${employee.employeeCode}</td>
                    <td>${employee.designation}</td>
                    <td>${employee.mobileNo}</td>
                    <td><button class="university-button"> <g:link  controller="employe" action="viewEmployeeDetail"   class="university-text-decoration-none" params="[id:employee.id]">View</g:link></button></td>
                    <td><button class="university-button"> <g:link  controller="employe" action="createEmployee" class="university-text-decoration-none" params="[id:employee.id]">Update</g:link></button></td>
                    <td><button class="university-button" onclick="confirmBeforeDelete('${employee.id}')"> Delete</button></td>
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