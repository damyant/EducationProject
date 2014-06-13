<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 9/6/14
  Time: 5:20 PM
--%>

<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Download Marks Foil Sheet</title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<script>
    $(document).ajaxStart(function(){
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
</script>

<div id="main">
    <fieldset class="form">
        <h3>Final result Sheet</h3>
        <g:form name="" id="" controller="postExamination" action="">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Program<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                                  value="${studInstance?.programDetail?.id?.get(0)}"
                                  optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"
                                  onchange="loadSession(this)"
                        />
                    </td>
                </tr>

                <!----------------------------------------- Session Name --------------------------------------------->
                <tr>
                    <td>Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="session" id="session" optionKey="id" class="university-size-1-2"
                                  value=""
                                  optionValue="session" from="" noSelection="['': ' Select Session']"
                                  onchange="loadSemester(this)"
                        />
                    </td>
                </tr>

                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td>Semester<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-2"
                                  value=""
                                  optionValue="" from="" noSelection="['': ' Select Semester']"
                                  onchange="loadCourse(this)"
                        />
                    </td>
                </tr>

               <tr>
                    <td colspan="2" style="text-align: center">
                        <input type="button" value="Final Result" onclick="validate()" class="university-button" tag="1">
                        %{--<input type="button" id="excelid" value="Download Marks in excel" onclick="validate()" class="university-button" tag="2">--}%
                        <input type="reset" value="Cancel" onclick="resetImage()" class="university-button">
                    </td>
                </tr>


            </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>