<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 31/3/14
  Time: 11:58 AM
--%>

<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script>

          function funload(){
            var status= window.confirm('Do you want to print this Application ?')
            if(status){
               var printing = window.print();
                if(printing){
                    window.close();
                }
            }
          }



    </script>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>
    <style type="text/css">

    </style>
</head>

<body onload="funload()">
<div id="main" style="text-transform: capitalize">
    <div>
        <table align="center" cellpadding="15" style="width: 100%;height:150px">
            <tr>
                <td style="width: 80%; vertical-align: top;">
                    <div class="preview-header" style="text-align: center; top:2px;">
                        <div><strong>Institute Of Distance And Open Learning (IDOL)</strong></div>

                        <div>Guwahati University,Gopinath Bardoloi Nagar</div>

                        <div>Jalukbari, Guwahati - 781014</div>

                        <div>Toll Free No. 1800-345-3614</div>

                        <div>Phone : (0361) 2673728,2679911</div>

                        <div>Fax: (0361) 2573887</div>

                        <div>Email: idol.gauhatiuniversity@gmail.com</div>

                    </div>
                </td>
                <td style="width: 80%;">
                    <div style="margin:auto; height: 150px;width:117px;text-align: center ; vertical-align:middle;border: 1px solid;">
                        <g:if test="${studentInstance?.getStudentImage()}">
                            <rendering:inlineJpeg bytes="${studentInstance?.getStudentImage()}" style="margin:auto; height: 150px;width: 117px;border: 1px solid black;    display: block;
                                                  background-position: bottom; background-size: 100%; text-align: center;"/>
                        </g:if>
                        <g:else>
                            <div style="margin: 68px 0px;">
                                Affix Passport Size Photo
                            </div>
                        </g:else>
                    </div>
                    <div style="margin: 3px auto;padding: 2px;width: 117px; font-size: 12px;border: 1px solid;"><label>Ref No : </label><label>${studentInstance?.referenceNumber}</label>
                    </div>
                </td>
            </tr>
        </table>
        <table class="university-size-1-1" style="border: 1px solid brown;margin:auto;font-size: 9px;">
            <tr>
                <td>
                    <i>* Take print of this PDF and send to</i>
                    <b>IDOL, Gauhati University</b><i> along with the </i><b>Bank Draft</b>.
                </td>
            </tr>
        </table>
        <table align="center" cellpadding="4" id="preview-pdf" class="university-table-1-2"
               style="width: 100%;margin: auto; border: 1px solid; ">
            <tr>
                <td style="width: 60%;">
                    <label>Name</label>
                </td>
                <td style="width: 40%;">
                    <label>${studentInstance?.firstName} ${studentInstance?.middleName} ${studentInstance?.lastName}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Date of Birth</label>
                </td>
                <td>
                    <label><g:formatDate date="${studentInstance?.dob}" format="dd MMM yyyy"/></label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Programme</label>
                </td>
                <td>
                    <label>${studentInstance?.programDetail?.courseName[0]}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Category</label>
                </td>
                <td>
                    <label>${studentInstance?.category}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Nationality</label>
                </td>
                <td>
                    <label>${studentInstance?.nationality}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Gender</label>
                </td>
                <td>
                    <label>${studentInstance?.gender}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>State of Domicile</label>
                </td>
                <td>
                    <label>${studentInstance?.state}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Mobile Number</label>
                </td>
                <td>
                    <label>${studentInstance?.mobileNo}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Study Centre</label>
                </td>
                <td>
                    <label>${studentInstance?.studyCentre?.name[0]}</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>Preferred  examination centre</label>
                </td>
                <td>
                    <label>${studentInstance?.city?.cityName[0]}</label>
                </td>
            </tr>
            <g:if test="${studentInstance?.addressTown}">
                <tr>
                    <td style="vertical-align: top;">
                        <label>Complete Mailing Address</label>
                    </td>
                    <td style="vertical-align: top;">
                        <div>${studentInstance?.studentAddress}, ${studentInstance?.addressTown}</div>

                        <div>${studentInstance?.addressPO} ${studentInstance?.addressDistrict}</div>

                        <div>${studentInstance?.addressState},  ${studentInstance?.addressPinCode}</div>
                    </td>
                </tr>
            </g:if>
        </table>
        <hr style="border-top: dashed 2px;" />

        <table cellpadding="4"  class="university-table-1-2"
               style="width: 100%;margin: auto; border: 1px solid; ">
            <tr><td style="width: 60%;">Challan Number</td><td style="width: 40%;">${feeDetails.challanNo}</td></tr>
            <tr><td>Fee</td><td>${feeDetails.admissionFeeAmount}</td></tr>

            <tr><td>Payment Mode</td><td>${feeDetails.paymentMode}</td></tr>
            <tr><td>Payment Date</td><td>${feeDetails.paymentDate}</td></tr>
            <tr><td>Payment Ref Number</td><td>${feeDetails.feeReferenceNumber}</td></tr>
            <tr><td>Bank</td><td>${feeDetails.bankName}</td></tr>
            <tr><td>Branch</td><td>${feeDetails.branchName}</td></tr>
        </table><br/><br/>
        <table style="width:100%;margin: auto;">
            <tr>
                <td style="width:50%;height: 70px;"><label style="float: left; margin-left:10px;">Date:</label></td>
                <td style="width:50%;"><label style="float: right; margin-right:10px;">Signature</label></td>
            </tr>
        </table>

    </div>
</div>
</body>
</html>