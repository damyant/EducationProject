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
                <td class="new-university-size-2-3">
                    <select class="university-size-1-2" name="type" id="type" onchange="getTotalBooks()">
                    <option value="">Sellect Allocation Type</option>
                    <option value="student">Student</option>
                    <option  value="faculty">Faculty</option>
                    </select>
               </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Enter RollNumber/ EmpId<span class="university-obligatory">*</span></td>
                <td class="new-university-size-2-3"><input type="text" name="id" id="id" class="university-size-1-2" onblur="getIssuedBooks()"></td>
            </tr>
            <tr>
               <td class="new-university-size-1-3">Type<span class="university-obligatory">*</span></td>
                <td class="new-university-size-2-3">
                    <g:select name="catalogType" id="catalogType" class="university-size-1-2" optionKey="id"
                              optionValue="catalogTypeName"  value="${catalogIns?.type?.id}"
                              from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                              onchange=""/>

                </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Category<span class="university-obligatory">*</span></td>
                <td class="new-university-size-2-3">

                    <g:select name="catalogCategory" id="catalogCategory" class="university-size-1-2" optionKey="id"
                              optionValue="catalogCatagoryName" value="${catalogIns?.catagory?.id}"
                              from="${catalogCatagoryList}" noSelection="['': ' Select Catalog Category']"
                              onchange="getSubjects()"  disabled=""/>

                </td>
            </tr>
            <tr>
                <td class="new-university-size-1-3">Max. Books Allocated</td>
                <td class="new-university-size-2-3"><input type="text" disabled="disabled" id="maxBooks"> </td>

            </tr>
            <tr>
                <td class="new-university-size-1-3">Books Already Issued</td>
                <td class="new-university-size-2-3"><input type="text" disabled="disabled" id="issuedBooks"> </td>

            </tr>
            <tr>
                <td class="new-university-size-1-3">Books List<span class="university-obligatory">*</span></td>
                <td  class="new-university-size-2-3">
                    <table class="university-size-full-1-1 inner">
                        <tr>
                            <td style="width: 35%;">
                                <g:select name="allBookList" id="allBookList" class="university-size-1-1" optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from="" onchange="" multiple="true"/>
                            </td>
                            <td  style="width: 10%;">
                                <g:select name="quantity" id="quantity" class="university-size-1-1" optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from="" onchange="" multiple="true"/>
                            </td>
                            <td  style="width: 20%;">
                                <input type="button" value="Add" class=" multiSelect-buttons-button1" onclick="addToList()">
                                <input type="button" value="Remove" class="multiSelect-buttons-button1" onclick="removeFromList()">
                            </td>
                            <td  style="width: 40%;">
                                <g:select name="selectedBookList" id="selectedBookList" class="university-size-1-1" optionKey="id"
                                          optionValue="catalogCatagoryName" value=""
                                          from=""  onchange="" multiple="true"/>
                            </td>
                        </tr>
                    </table>
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