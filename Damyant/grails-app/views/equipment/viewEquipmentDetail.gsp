<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="java.text.SimpleDateFormat;javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Employee Details</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script>
        $(function () {
            var icons = {
                header: "ui-icon-circle-arrow-e",
                activeHeader: "ui-icon-circle-arrow-s"
            };
            $("#accordion").accordion({
                icons: icons,
                heightStyle: "content"
            });
        });
    </script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Equipment Information</h3><br/>

        %{--<div id="accordion">--}%
            %{--<h3>Personal Details</h3>--}%

            <div>
                <p>
                <table class="inner " style="width: 100%;margin: auto;">
                    <tr>
                        <td class="university-size-2-3">
                            <div class="main-Content">
                                <g:if test="${equipmentObj?.equipmentName}">
                                    <div class="label-header">Equipment Name</div>

                                    <div class="label-content">${equipmentObj?.equipmentName}</div>
                                </g:if>
                                <g:if test="${equipmentObj?.equipmentId}">
                                    <div class="label-header">Equipment Id</div>

                                    <div class="label-content">${equipmentObj?.equipmentId}  </div>
                                </g:if>
                                <g:if test="${equipmentObj?.labInventoryId} ">
                                    <div class="label-header">Lab Inventory Id</div>

                                    <div class="label-content">${equipmentObj?.labInventoryId} </div>
                                </g:if>
                                <g:if test="${equipmentObj?.manufacturer} ">
                                    <div class="label-header">Manufacturer</div>

                                    <div class="label-content">${equipmentObj?.manufacturer} </div>
                                </g:if>
                                <g:if test="${equipmentObj?.dateOfPurchase} ">
                                    <div class="label-header">Date of Purchase</div>

                                    <div class="label-content">
                                      <g:formatDate format="dd-MMM-yyyy" date="${equipmentObj?.dateOfPurchase}"/>
                                    </div>
                                </g:if>
                                <g:if test="${equipmentObj?.description} ">
                                    <div class="label-header">Description</div>

                                    <div class="label-content">
                                        ${equipmentObj?.description}
                                    </div>
                                </g:if>
                                <g:if test="${equipmentObj?.warranty} ">
                                    <div class="label-header">Warranty</div>

                                    <div class="label-content">
                                        ${equipmentObj?.warranty}
                                    </div>
                                </g:if>
                                <g:if test="${equipmentObj?.documentImage}">
                                    <div class="label-header">Attached Document</div>
                                    <img src="${createLink(controller: 'equipment', action: 'showImage', id: equipmentObj?.id, mime: 'image/jpeg')}"
                                         class="image-preview-div" id="picture1"/>
                                </g:if>
                            </div>
                        </td>

                    </tr>
                </table>
            </p>
            </div>


    </fieldset>

</div>

</body>
</html>