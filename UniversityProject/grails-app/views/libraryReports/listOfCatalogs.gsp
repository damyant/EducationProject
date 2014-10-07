<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 10/6/14
  Time: 1:06 PM
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

        <g:uploadForm>
            <g:if test="${params.by=="byType"}">
                <table class="university-size-full-1-1 inner spinner">
               <tr>

                   <td class="university-size-1-3">Type<span class="university-obligatory">*</span></td>
                   <td class="university-size-1-3">
                <g:select name="catalogType" class="university-size-1-2" optionKey="id"
                          optionValue="catalogTypeName" value="${catalogIns?.type?.id}"
                          from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                          onchange=""/>
                </td>
            </tr>

        </g:if>
           <g:if test="${params.by=="byCategory"}">
                <tr>
                    <td class="university-size-1-3">Category<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <g:select name="catalogCatagory" class="university-size-1-2" optionKey="id"
                                  optionValue="catalogCatagoryName" value="${catalogIns?.catagory?.id}"
                                  from="${catalogCatagoryList}" noSelection="['': ' Select Catalog Catagory']"
                                  onchange=""/>
                    </td>
                </tr>
           </g:if>

                <g:if test="${params.by=="byIsbn"}">
                <tr>

                    <td class="university-size-1-3">ISBN<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogIsbn" class="university-size-1-2" value="${catalogIns?.isbn}"></td>
                </tr>
                </g:if>
            <g:if test="${params.by=="byTitle"}">
                <tr>

                    <td class="university-size-1-3">Title<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogTitle" class="university-size-1-2" value="${catalogIns?.title}"></td>
                </tr>
            </g:if>
            <g:if test="${params.by=="byAuthor"}">
                <tr>

                    <td class="university-size-1-3">Author<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogAuthor" class="university-size-1-2" value="${catalogIns?.author}"></td>
                </tr>
            </g:if>



                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Submit"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>



</body>
</html>