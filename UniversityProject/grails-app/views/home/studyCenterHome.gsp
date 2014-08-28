<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 8/28/14
  Time: 6:27 PM
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
<sec:ifAnyGranted roles="ROLE_STUDY_CENTRE">
    <div style="text-align: center"><h1>Important instructions to apply for online application</h1>
        <h3>GU-IDOL Admission 2014</h3></div>
    <div style="text-align: justify;">
    <p><ol>
    <li>Visit the website www.idolsrm.in . Please note that the browser should be always Google Chrome.
%{--<div class="university-clear-both"></div>--}%
%{--<img src="${resource(dir: 'images', file: 'pic1.jpg')}" style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>--}%
%{--<div class="university-clear-both"></div>--}%
    </li>
    <li>Login using username and password given from IDOL Authority.</li>
    <li>To apply for online, click on button - <strong>"Student">”Study Centre Enrollment”</strong>.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC1.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>Provide required information on the <strong>“Student Information Sheet”</strong>.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC2.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>Do review student’s personal and educational details and proceed. Please note that <label style="color: #ff0000;">*</label> marked fields are mandatory.</li>
    <li>Submit the information accordingly to proceed to apply. If student’s Registration is successful, you will get the Roll No of the student enrolled.</li>
    <li>Now to generate the Fee Challan, click on button - <strong>"Fee">”Study Centre Fee”> “Generate Challan”</strong>.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC3.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>You will get the following page where you have to enter the information accordingly.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC4.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>Study Centres can generate the Fee Challan by using the both options <strong>“Generate challan By Range”</strong> or <strong>“Generate challan Individually”</strong>. </li>
    <li>For generate challan by range use the serial number range. (e.g. 1-20)</li>
    <li>For generate challan by individually use the Roll number. (e.g. 19140007)</li>
    <li>Now your challan is generated in PDF format. Please note down the Challan No for future use.</li>
    <li>Now to pay the Admission Fee, click on button - <strong>"Fee">”Pay Challan”> “Admission Fee”</strong>.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC3.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>Now enter the Challan Number and click on the button <strong>“Show Students”</strong>.
        <div class="university-clear-both"></div>
        <img src="${resource(dir: 'images', file: 'SC4.jpg')}"
             style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

        <div class="university-clear-both"></div>
    </li>
    <li>Now the select the <strong>“Payment Mode”</strong> from the drop down menu and enter other information accordingly. </li>
    <li>Now click on the <strong>“Pay”</strong> button and generate the Admission Fee Challan. Take a print out of this Challan and send it to IDOL Office along with the Demand Draft and Students OMR Sheet.</li>
    </ol></p>
    </div>
</sec:ifAnyGranted>
</fieldset>
</div>
</body>
</html>