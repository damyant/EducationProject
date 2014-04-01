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
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>
    <style type="text/css">

    </style>
</head>
 <body>
<div id="main" style="text-transform: capitalize">
<div>
    <table align="center" cellpadding="20"  style="width: 100%;height:150px">
        <tr>
            <td style="width: 80%; vertical-align: top;">
               <div class="preview-header" style="text-align: center; top:2px;">
                  <div><strong>Institute Of Distance And Open Learning (IDOL)</strong></div>
                   <div>Guwahati University,Gopinath Bardoloi Nagar</div>
                   <div>Jalukbari, Guwahati - 781014</div>
                   <div>Toll Free No. 1800-345-3614</div>
                   <div>Phone : (0361) 2673728,2679911</div>
                   <div>Fax: (0361) 2573887</div>
                   <div> Email: idol.gauhatiuniversity@gmail.com</div>
               </div>
            </td>
            <td style="width: 80%;">
                <rendering:inlineJpeg bytes="${studentInstance?.getStudentImage()}" class="university-registration-photo"   style="margin:auto; height: 150px;"/>
                <div style="margin: 3px auto;padding: 2px; font-size: 12px;border: 1px solid;"> <label>Reference No : </label><label>${studentInstance?.referenceNumber}</label></div>
            </td>
            </tr>
        </table>
<table align="center" cellpadding="18" class="university-table-1-2" style="width: 100%;margin: auto; border: 1px solid; ">
    <tr>
        <td>
            <label>Name</label>
        </td>
        <td>
            <label>${studentInstance?.name}</label>
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
            <label>Program</label>
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
            <label>Prefered of examination centre </label>
        </td>
        <td>
            <label>${studentInstance?.examinationCentre?.name[0]}</label>
        </td>
    </tr>
    <tr >
        <td style="vertical-align: top;">
            <label>Complete Mailing Address</label>
        </td>
        <td style="vertical-align: top;">
            <div>${studentInstance?.addressTown}</div>
            <div>${studentInstance?.addressPO},${studentInstance?.addressDistrict}</div>
            <div>${studentInstance?.addressState}, ${studentInstance?.addressPinCode}</div>
        </td>
    </tr>
</table>
</div>
</div>
</body>
</html>