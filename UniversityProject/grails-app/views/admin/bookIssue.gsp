<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 1/10/14
  Time: 12:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Book Issue</title>
    <meta name="layout" content="main"/>
    <g:javascript src='bookIssue.js'/>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Book Issue</h3>
        <g:form controller="admin" action="saveBookIssue" name="bookIssueForm" id="bookIssueForm">
        <table class="university-size-full-1-1 inner spinner">
            <tr>
                <td class="new-university-size-1-3">Book Allocation To<span class="university-obligatory">*</span></td>
                <td class="new-university-size-1-3">
                    <select class="university-size-1-2" name="type">
                    <option value="student">Student</option>
                    <option  value="faculty">Faculty</option>
                    </select>
               </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Enter RollNumber/ EmpId<span class="university-obligatory">*</span></td>
                <td class="new-university-size-1-3"><input type="text" name="id" class="university-size-1-2"></td>
            </tr>
            <tr>
               <td class="new-university-size-1-3">Type<span class="university-obligatory">*</span></td>
                <td class="new-university-size-1-3">
                    <g:select name="catalogType" id="catalogType" class="university-size-1-2" optionKey="id"
                              optionValue="catalogTypeName"  value="${catalogIns?.type?.id}"
                              from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                              onchange=""/>

                </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Category<span class="university-obligatory">*</span></td>
                <td class="new-university-size-1-3">

                    <g:select name="catalogCategory" id="catalogCategory" class="university-size-1-2" optionKey="id"
                              optionValue="catalogCatagoryName" value="${catalogIns?.catagory?.id}"
                              from="${catalogCatagoryList}" noSelection="['': ' Select Catalog Category']"
                              onchange="getSubjects()"/>
                </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Books List<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="allBookList" id="allBookList" class="new-university-size-1-2" optionKey="id"
                              optionValue="catalogCatagoryName" value=""
                              from="" onchange="" multiple="true"/>
                </td>
                <td>
                    <g:select name="quantity" id="quantity" class="new-university-size-1-2" optionKey="id"
                              optionValue="catalogCatagoryName" value=""
                              from="" onchange="" multiple="true"/>
                </td>
                <td style="width:12% "><input type="button" value="Add" class="multiSelect-buttons-button1" onclick="addToList()">
                    <input type="button" value="Remove" class="multiSelect-buttons-button1" onclick="removeFromList()">
                </td>
                <td>
                    <g:select name="selectedBookList" id="selectedBookList" class="university-size-1-2" optionKey="id"
                              optionValue="catalogCatagoryName" value=""
                              from=""  onchange="" multiple="true"/>
                </td>

            </tr>

            <tr>
                <td>
                    <input type="button" value="Save" onclick="saveData()" class="university-button">
                </td>
            </tr>
            </table>
        </g:form>
        </fieldset>

</div>
</body>
</html>