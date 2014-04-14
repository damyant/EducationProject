
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>View Course</title>
        %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
    </head>

    <body>
    <div id="main">
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-table-1-4 university-table-text-left">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-table-1-3 university-table-text-left">
        </g:else>
        <thead>
        <tr>
            <th>Course Name</th>
            <th>Course Type</th>
            <th>Course Mode</th>
            <th>&nbsp;</th>
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
                    <button class="university-button"> <g:link  controller="course" action="createNewCourse" params="[courseId:course.id,semester:course.noOfTerms]" class="university-text-decoration-none">Update</g:link></button>
                    <button class="university-button"> <g:link  controller="course" action="deleteCourse" params="[courseId:course.id,semester:course.noOfTerms]" class="university-text-decoration-none">Delete</g:link></button>
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
                <td><div class="university-float-right">
                    <button class="university-button"> <g:link  controller="course" action="viewCourses" params="[courseId:course.id,semester:course.noOfTerms,type:'view']" class="university-text-decoration-none">View</g:link></button>
                </div></td>
            </tr>
           </g:each>
        </g:else>
        </tbody>
    </table>
        <div class="paginateButtons">
            <g:paginate  total="${courseInstanceTotal}" />
        </div>
    </div>

    </body>

</html>