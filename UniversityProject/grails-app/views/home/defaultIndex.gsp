<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 8/28/14
  Time: 6:26 PM
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
        <sec:ifNotLoggedIn>
            <div style="text-align: center"><h2>Step-by-step instructions for online admission process at</h2>
                <h1>Gauhati University Institute of Distance and Open Learning</h1></div>
            <div style="text-align: justify;">
            <p>     Gauhati University IDOL, has introduced the online admission or self enrolment process through our dedicated online portal from the academic session 2014-15. Here we will demonstrate step by step process for the online admission or self enrolment.
            Eligibility Criteria: for the details of the eligibility criteria please visit our website www.idolgu.in
            Admission Fees: While selecting the Programme for admission the required admission fees will be reflected at the fees detail section of the application fees
            Mode of Payment: for online application we accept only Demand Draft as the mode of payment. Demand Draft should be drawn in favour of IDOL, Gauhati, payable at SBI Guwahati Universsity Branch (Code-2060).
            <ol>
            <li>To apply for online, click on button - "Student">”Self Enrollment”.
                <div class="university-clear-both"></div>
            <img src="${resource(dir: 'images', file: 'pic1.jpg')}" style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>
            <div class="university-clear-both"></div>
            </li>
            <li>Provide required information on the “Student Enrollment Sheet”.
                <div class="university-clear-both"></div>
                <img src="${resource(dir: 'images', file: 'pic2.jpg')}"
                     style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

                <div class="university-clear-both"></div>
            </li>

            <li>Do review your personal and educational details and proceed. Please note that * marked fields are mandatory.</li>
            <li>Submit your information accordingly to proceed to apply. Save the Application Form.</li>
            <li>Take print of the Application Form and send to IDOL, Gauhati University along with the Bank Draft and other required document as listed.</li>
            <li>Note the Reference Number from the Application Form. Reference Number will be required to trace the status of the Applicant further.</li>
            <li>Applicant can trace the status of the Application by using the button- "Student">”Status of Applicant”.
                <div class="university-clear-both"></div>
                <img src="${resource(dir: 'images', file: 'pic3.jpg')}"
                     style="height: 250px;float: left;margin: 10px;border: 1px solid #000000;"/>

                <div class="university-clear-both"></div>
            </li>
            <li>If the Application is approved Applicant will get the roll number and his/her admission process is completed.</li>
            </ol></p>
            </div>
        </sec:ifNotLoggedIn>
        </fieldset>
    </div>
</body>
</html>