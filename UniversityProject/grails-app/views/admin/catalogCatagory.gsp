<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/30/14
  Time: 6:22 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.view}">
            <h3>Catagory  List</h3><br/>
            <table class="university-size-1-3" style="margin: auto;">
                <tr><th style="text-align: center">Name</th><th  style="text-align: center">Edit</th><th  style="text-align: center">Delete</th></tr>
                <g:each in="${catagoryCatagoryList}" var="catInst">
                    <tr>
                        <td style="text-align: center">${catInst.catalogCatagoryName}</td><td
                            style="text-align: center"><g:link controller="admin" action="catalogCatagory"
                                                               params="[catId: catInst.id]">Edit</g:link></td><td
                            style="text-align: center"><g:link controller="admin" action="delCatalogCatagory"
                                                               params="[catId: catInst.id]">Delete</g:link></td>
                    </tr>
                </g:each>
            </table>
        </g:if>
        <g:else>
            <h3>Catalog Catagory</h3><br/>
            <g:form controller="admin" action="saveCatalogCatagory" name="saveCatalogCatagory" id="saveCatalogCatagory">
                <g:if test="${catagoryCatagoryInst}">
                    <input type="hidden" name="catgoryCatagoryId" value="${catagoryCatagoryInst?.id}">
                </g:if>
                <table class="inner university-size-1-2" style="margin: auto;">
                    <tr>
                        <td class="university-size-1-3">Catalog Catagory</td>
                        <td class="university-size-2-3"><input type="text" class="university-size-1-1"
                                                               value="${catagoryCatagoryInst?.catalogCatagoryName}"
                                                               name="catalogCatagory"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" class="university-button" value="Save"/></td>
                    </tr>
                </table>
            </g:form>
        </g:else>
    </fieldset>
</div>

</body>
</html>