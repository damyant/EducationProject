<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 7/4/14
  Time: 5:49 PM
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
    <sec:ifLoggedIn>
    <sec:ifAnyGranted roles="ROLE_ADMIN">
    <div class="university-size-full-1-1">
            <div style="float: left; height: 100%;width:49%;">
                <table class="inner university-size-1-1" style="padding-top: 2px; top: 10;margin: auto;">
                    <tr>
                        <td colspan="2">
                            <h3>
                                Self Enrollment Student Count
                            </h3>
                        </td>

                    </tr>
                    <tr>
                        <td  colspan="2" style="text-align: center;">
                            <label>Total Number of Student:  ${selfEnrollmentCount}</label>
                        </td>
                    </tr>
                    <tr>
                        <td  colspan="2" style="text-align: center;">
                            <label>Total Number of Student without Roll No:  ${rollNoNotCount}</label>
                        </td>
                    </tr>

                    <tr>
                        <th>Program Name</th>
                        <th>
                            Total Student
                        </th>
                    </tr>
                    <g:each in="${0..programName.size()-1}" var="index">
                        <g:if test="${programCount[index]!=0}">
                        <tr>
                            <td>${programName[index]}</td>
                            <td>${programCount[index]}</td>
                        </tr>
                        </g:if>
                    </g:each>

                </table>
            </div>
            <div class="university-size-1-2"  style="float: right;margin: auto;">
                <table class="inner university-size-1-1" style="margin-top: 2px;top: 10;">
                    <tr>
                        <td colspan="3">
                            <h3>
                                Fee Challan Status Count
                            </h3>
                        </td>

                    </tr>
                    <tr>
                        <th>Study Centre Name</th>
                        <th>Approve Challan</th>
                        <th>Unapprove Challan</th>
                    </tr>
                    <g:each in="${0..studyCentreName.size()-1}" var="index">
                        <g:if test="${studyCentreFeeUnAppCount[index]!=0&&studyCentreFeeAppCount[index]!=0}">
                        <tr>
                            <td class="university-size-1-2">${studyCentreName[index]}</td>
                            <td class="university-size-1-4">${studyCentreFeeAppCount[index]}</td>
                            <td class="university-size-1-4">${studyCentreFeeUnAppCount[index]}</td>
                        </tr>
                            </g:if>
                    </g:each>
                </table>
            </div>
    </div>
<div class="university-clear-both"></div>
    </sec:ifAnyGranted>
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
    </sec:ifLoggedIn>
</fieldset>
</div>
</body>
</html>