<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/27/14
  Time: 2:32 PM
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
        <h3>Add Catalog</h3>

        <g:uploadForm id="ctl1" controller="admin" action="saveCatalog">
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <table class="university-size-full-1-1 inner spinner">
                <tr><g:if test="${catalogIns}">
                    <input type="hidden" value="${catalogIns?.id}" name="catalogUpdate"/>
                </g:if>

                    <td class="university-size-1-3">Type<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                              <g:select name="catalogType" class="university-size-1-2" optionKey="id"
                                  optionValue="catalogTypeName"  value="${catalogIns?.type?.id}"
                                  from="${catalogTypeList}" noSelection="['': ' Select Catalog Type']"
                                  onchange=""/>


                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Category<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">

                            <g:select name="catalogCategory" class="university-size-1-2" optionKey="id"
                                      optionValue="catalogCatagoryName" value="${catalogIns?.catagory?.id}"
                                      from="${catalogCatagoryList}" noSelection="['': ' Select Catalog Category']"

                </tr>

                <tr>

                    <td class="university-size-1-3">ISBN<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogIsbn" class="university-size-1-2" value="${catalogIns?.isbn}"></td>
                </tr>
                <tr>

                    <td class="university-size-1-3">Title<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogTitle" class="university-size-1-2" value="${catalogIns?.title}"></td>
                </tr>
                <tr>

                <td class="university-size-1-3">Author<span class="university-obligatory">*</span></td>
                <td class="university-size-1-3"><input type="text" name="catalogAuthor" class="university-size-1-2" value="${catalogIns?.author}"></td>
            </tr>
                <tr>

                    <td class="university-size-1-3">Publisher<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogPublisher" class="university-size-1-2" value="${catalogIns?.publisher}"></td>
                </tr>
                <tr>

                    <td class="university-size-1-3">Year<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogYear" class="university-size-1-2" value="${catalogIns?.year}"></td>
                </tr>
                <tr>

                    <td class="university-size-1-3">Quantity<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="catalogQuantity" class="university-size-1-2" value="${catalogIns?.quantity}"></td>
                </tr>


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