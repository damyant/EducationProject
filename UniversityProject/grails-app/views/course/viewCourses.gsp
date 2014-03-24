
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>View Course</title>
        %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
    </head>

    <body>
    <div id="main">
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-table-1-4">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-table-1-3">
        </g:else>
        <thead>
        <tr>
            <th>Course Name</th>
            <th>Course Type</th>
            <th>Course Mode</th>
            <g:if test="${params.type == 'update'}">
                <th>&nbsp;</th>
            </g:if>
        </tr>
        </thead>
        <tbody>
        <g:if test="${params.type == 'update'}">
            <g:each var="course"  in="${courseList}">
                <tr>
                    <td>${course.courseName}</td>
                    <td>${course.courseType.courseTypeName}</td>
                    <td>${course.courseMode.modeName}</td>
                <td><div class="university-float-right">
                    %{--<input type="submit" value="Update" class="university-button"/>--}%
                    <g:link  controller="course" action="createNewCourse" params="[courseId:course.id,semester:course.noOfTerms]" class="university-button">Update</g:link>
                    <g:link  controller="course" action="deleteCourse" params="[courseId:course.id,semester:course.noOfTerms]" class="university-button">Delete</g:link>
                    %{--<input type="button" value="Delete"  class="university-button"/>--}%
                </div></td>
            </tr>
           </g:each>
        </g:if>
        <g:else>
           <g:each var="course"  in="${courseList}">
            <tr>
                <td>${course.courseName}</td>
                <td>${course.courseType.courseTypeName}</td>
                <td>${course.courseMode.modeName}</td>
            </tr>
           </g:each>
        </g:else>
        </tbody>
    </table>
    </div>
    </body>

</html>