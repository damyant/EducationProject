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
<div id="main">


<table align="center" cellpadding="20" class="university-table-1-3" id="examCenterSelect" style="width: 80% ">
    <tr>

        <td>
            <label>Reference No.</label>
        </td>
        <td>
            <label>${studentInstance?.referenceNumber}</label>
        </td>
        <td>
            <label>Name</label>
        </td>
        <td>
            <label>${studentInstance?.name}</label>
        </td>
        <td rowspan="5">
            <rendering:inlineJpeg bytes="${studentInstance?.getStudentImage()}" height="200px"/>
            %{--<img id="picture" src="" class="university-registration-photo" style="margin: auto;"/>--}%
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
        <td>
        </td>
    </tr>
    <tr>
        <td>
            <label>State of Domicile</label>
        </td>
        <td>
            <label>${studentInstance?.state}</label>
        </td>
        <td></td>
    </tr>
    <tr>
        <td>
            <label>Mobile Number</label>
        </td>
        <td>
            <label>${studentInstance?.mobileNo}</label>
        </td>
        <td></td>
    </tr>
    <tr>
        <td>
            <label>Study Centre</label>
        </td>
        <td>
            <label>${studentInstance?.studyCentre?.name[0]}</label>
        </td>
        <td></td>
    </tr>
    <tr>
        <td>
            <label>Prefered of examination centre </label>
        </td>
        <td>
            <label>${studentInstance?.examinationCentre?.name[0]}</label>
        </td>
        <td></td>
    </tr>
    <tr >
        <td style="vertical-align: top;">
            <label>Complete Mailing Address</label>
        </td>
        <td style="vertical-align: top;">
            <p>${studentInstance?.addressTown}</p>
            <p>${studentInstance?.addressDistrict}</p>
            <p>${studentInstance?.addressPO}</p>
            <p>${studentInstance?.addressState}, ${studentInstance?.addressPinCode}</p>

        </td>
        <td></td>
    </tr>
</table>
</div>
</body>
</html>