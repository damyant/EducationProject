<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/27/14
  Time: 4:04 PM
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
        <h3>Edit Catalog</h3>


        <table>

            <thead>
            <tr>
                <g:sortableColumn property="title" title="Type" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Category" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="ISBN" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Title" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Author" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Publisher" class="university-size-1-6"/>
                <g:sortableColumn property="title" title="Year" class="university-size-1-2"/>
                <g:sortableColumn property="title" title="Quantity" class="university-size-1-2"/>
                <g:sortableColumn property="title" title="Edit" class="university-size-1-4"/>
                <g:sortableColumn property="title" title="Delete" class="university-size-1-4"/>
                <g:hiddenField name="id" value="id"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${catalogList}" status="i" var="catalogInst">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>${catalogInst.type.catalogTypeName}</td>
                    <td>${catalogInst.catagory.catalogCatagoryName}</td>
                    <td>${catalogInst.isbn}</td>
                    <td>${catalogInst.title}</td>
                    <td>${catalogInst.author}</td>
                    <td>${catalogInst.publisher}</td>
                    <td>${catalogInst.year}</td>
                    <td>${catalogInst.quantity}</td>
                    <td><g:link controller="admin" action="addCatalog" params="[catalogInstId:catalogInst.id]">Edit</g:link></td>

                    <td><g:link controller="admin" action="delCatalog" params="[catalogInstId:catalogInst.id]">Delete</g:link></td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </fieldset>
</div>

</body>
</html>