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

    <div class="form-group">

        <label>Select Programme</label>
        <g:select id="ms" multiple="multiple" name="ms" class="university-size-1-7 form-control" onchange="select(this)"
                  optionKey="id" optionValue="courseName"
                   from="${ProgramDetail.list(sort: 'courseCode')}">


        </g:select>


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

    }
</script>
</body>
</html>