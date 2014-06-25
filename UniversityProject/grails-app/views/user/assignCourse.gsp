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
                   from="${ProgramDetail.findAll()}">


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

    %{--<select multiple="multiple" style="width: 100%">--}%
       %{--<g:each in="${programList}" status="i" var="program">--}%
            %{--<optgroup label="${program.courseName}    ${program.noOfTerms}">--}%

                %{--<g:each in="${1..program.noOfTerms}" status="j" var="index">--}%

                    %{--<option value="${j}">${index} Semester</option>--}%
                    %{--<g:checkBox name="abc">abc</g:checkBox>--}%
                %{--</g:each>--}%
            %{--</optgroup>--}%
       %{--</g:each>--}%
    %{--</select>--}%
  <g:form >
    <div style="height: 200px; overflow-y: scroll; background: snow; border: 1px solid #000000">
      <g:each in="${programList}" status="i" var="program">
          <div>
          <label style="color: #0000ff">${program.courseName}</label>
          </div>
          <g:each in="${1..program.noOfTerms}" status="j" var="index">
              <div id="checkboxes">
              <input type="checkbox" id="" name= "${program.id}" value="${index}"/>
              <label>${j+1} Semester</label>`
              </div>
          </g:each>
      </g:each>
    </div>
      <button name="submit" onclick="return submitCourses()">Submit</button>
  </g:form>

  </div>

</div>
<script>
    $(function() {
        $('#ms').change(function() {
            console.log($(this).val());
        }).multipleSelect();

        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center',0],
//        maxWidth:600,
//        maxHeight: 500,
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title:'Fee Voucher',
            close: function(ev, ui) {
                $.unblockUI();
//            getStudentsList()
            }

        });
    });

//    $("select").multipleSelect({
//        multiple: true,
//        multipleWidth: 55,
//        width: '50%'
//    });
    function select(t){
//<<<<<<< HEAD
//      alert($(t).val())
//    }
//    function submitCourses(){
//        alert('in submit function')
//        var n = $( "input:checked" ).length;
//        var data = {};
//        $('#checkboxes input:checked').each(function () {
//            alert('hello kuldeep')
//            var sThisVal = (this.checked ? $(this).val() : "");
//            var name =  $(this).attr('name');
//            var value = $(this).val()
//            data[name]= value
//        });
//        debugger;
//        $.ajax({
//            type: "post",
//            url: url('user', 'saveCourseForTabulator', ''),
//            data: data,
////            contentType: 'application/json; charset=utf-8',
//            dataType: 'json',
//            success: function (data) {
//                if (data.response1=='updated') {
//                    document.getElementById("statusMessage").style.display = "block";
//                }
//                else if(data.response1=='Created'){
//                    document.getElementById("statusMessage").style.display = "block";
//                    clearField()
//                }
//                $("html, body").animate({ scrollTop: 0 }, "slow");
//            }
//        });
//        return false;
//
//=======
////      alert($(t))
//>>>>>>> damyant
    }
</script>
</body>
</html>