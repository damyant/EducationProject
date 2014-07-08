<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 4/6/14
  Time: 3:05 PM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>assign course to tabulator</title>
    <meta name="layout" content="main"/>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js/jquery', file: 'jquery.multiple.select.js')}'></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'multiple-select.css')}" type='text/css'>

</head>

<body>
<div id="main">
    %{--<table id="multiSelectTab" name="multiSelectTab">--}%
        %{--<tr>--}%
            %{--<td style="width:40% ">--}%
                    %{--<label>All Course <span class="university-obligatory">*</span>--}%
                    %{--</label>--}%
                   %{--<select style="width: 60%" name="allsubjectList" multiple="multiple" id="allsubjectList" />--}%
            %{--</td>--}%
            %{--<td style="width:20% ">--}%
                   %{--<button type="button" class="multiSelect-buttons-button" onclick="addToList()" name="add"  id="add">Add</button> +--}%
                   %{--<button type="button" class="multiSelect-buttons-button" onclick="removeFromList()" name="remove"  id="remove">Remove</button>--}%
            %{--</td>--}%
            %{--<td style="width:40%;">--}%
                %{--<select class="select-to" style="width: 50%"  name="semester" id="semester"  multiple="true"  />--}%
               %{--<div id="upload-syllabus" style="width: 30%;float:right;">--}%
                  %{--<input type="button" style="float: right; margin-top:20%" id="Syllabus_link" value="Syllabus" onclick="syllabusUpload()" />--}%
               %{--</div>--}%
               %{--<div id="error-select"></div>--}%
            %{--</td>--}%
        %{--</tr>--}%
    %{--</table>--}%
    <div class="form-group">
        <label>Select Programme</label>
        <g:select id="ms" multiple="multiple" name="ms" class="university-size-1-7 form-control" onchange="select(this)"
                  optionKey="id" optionValue="courseName"
                   from="${ProgramDetail.list(sort: 'courseCode')}">

            %{--<option value="1">January</option>--}%
            %{--<option value="2">February</option>--}%
            %{--<option value="3">March</option>--}%
            %{--<option value="4">April</option>--}%
            %{--<option value="5">May</option>--}%
            %{--<option value="6">June</option>--}%
            %{--<option value="7">July</option>--}%
            %{--<option value="8">August</option>--}%
            %{--<option value="9">September</option>--}%
            %{--<option value="10">October</option>--}%
            %{--<option value="11">November</option>--}%
            %{--<option value="12">December</option>--}%
        </g:select>
    </div>
    <div id="selectedItems" class="university-size-1-7" style="background: yellow; height: 200px; width: 100%">

    </div>
</div>
<script>
    $(function() {
        $('#ms').change(function() {
            console.log($(this).val());
        }).multipleSelect();
    });
    function select(t){
//      alert($(t))
    }
</script>
</body>
</html>