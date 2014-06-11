<div>
<div class="logo">
    <div class="header-parts"><img src="${resource(dir: 'images', file: 'logo.png')}" class="logo-image"></div>

    <div class="header-parts" name="logout">
        <sec:ifLoggedIn>
            <div class="university-session-management">
                <sec:username/> || <g:link controller="logout">Logout</g:link>
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR">
                    ||<g:link controller="user" action="index">Manage User</g:link>
                </sec:ifAnyGranted>
            </div>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <div class="university-session-management">
                |<g:link controller="login" action="auth">Login</g:link>|
            </div>
        </sec:ifNotLoggedIn>
    </div>
</div>

<div id="header-Menu">
%{--<div id="header-Menu">--}%
<ul id="menu">
<li><a href="#"><g:message code="default.mainMenu1"/></a></li>
<li><a href="#"><g:message code="default.mainMenu2"/></a>
    <ul>
      <sec:ifLoggedIn>
        <li><g:link controller="student" action="registration"><g:message
                code="default.mainMenu2.subMenuStudyCentre"/></g:link></li>
      </sec:ifLoggedIn>
     <sec:ifNotLoggedIn>
     <li><g:link controller="student" action="registration"><g:message
             code="default.mainMenu2.subMenu1"/></g:link></li>
         </sec:ifNotLoggedIn>
        <li><g:link controller="student" action="enrollmentAtIdol"><g:message
                code="default.mainMenu2.enrollAtIdol"/></g:link></li>
        %{--<li><a class="viewResulttopopup"><g:message code="default.mainMenu2.subMenu2"/></a></li>--}%
        %{--<li><a class="admitCardPopup" href="#"><g:message code="default.mainMenu2.subMenu3"/></a></li>--}%
        <li><g:link controller="student" action="downloadAdmitCard"><g:message
                code="default.mainMenu2.subMenu3"/></g:link></li>
        %{--<li><a href="#"><g:message code="default.mainMenu2.subMenu4"/></a></li>--}%
        <li><a class="statustopopup" href="#"><g:message code="default.mainMenu2.subMenu5"/></a></li>
    </ul>
</li>
<li><a href="#"><g:message code="default.mainMenu3"/></a>
    <ul>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu3"/></a>
            <ul>
                <li><g:link controller="studyCenter" action="createNewStudyCenter"><g:message
                        code="default.mainMenu3.subMenu3.subMenu1"/></g:link></li>
                <li><g:link controller="studyCenter" action="viewStudyCentre"><g:message
                        code="default.mainMenu3.subMenu3.subMenu2"/></g:link></li>
                <li><g:link controller="studyCenter" action="updateStudyCentre"><g:message
                        code="default.mainMenu3.subMenu3.subMenu3"/></g:link></li>
            </ul>
        </li>
        <li>
            <a href="#">Examination Centre</a>
            <ul>
                <li><g:link controller="examinationCenter" action="createExamCentre">Create</g:link></li>
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu1"/></a>
            <ul>
                <li><g:link controller="examinationCenter" action="create"><g:message
                        code="default.mainMenu3.subMenu1.subMenu1"/></g:link></li>
                <li><g:link controller="examinationCenter"
                            action="viewExaminationCentre"><g:message
                            code="default.mainMenu3.subMenu1.subMenu2"/></g:link></li>
                <li><g:link controller="examinationCenter"
                            action="updateExaminationCentre"><g:message
                            code="default.mainMenu3.subMenu1.subMenu3"/></g:link></li>
            </ul>
        </li>

        <li><a href="#"><g:message code="default.mainMenu3.subMenu11"/></a>

            <ul>
                %{--<li><g:link controller="examinationCenter" action="createNewCity"><g:message--}%
                        %{--code="default.mainMenu3.subMenu11.subMenu1"/></g:link></li>--}%
                %{--<li><g:link controller="examinationCenter"--}%
                            %{--action="listOfCity"><g:message--}%
                            %{--code="default.mainMenu3.subMenu11.subMenu2"/></g:link></li>--}%
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu6"/></a>

        <ul>
            <li><g:link controller="course" action="createNewCourse"><g:message
                    code="default.mainMenu3.subMenu6.subMenu1"/></g:link></li>
            <li><g:link controller="course"
                        action="listOfCourses"><g:message
                        code="default.mainMenu3.subMenu6.subMenu2"/></g:link></li>
            <li><g:link controller="course"
                        action="updateCourses"><g:message
                        code="default.mainMenu3.subMenu6.subMenu3"/></g:link></li>
        </ul>
    </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu9"/></a>

            <ul>
                <li><g:link controller="admin"
                            action="addCourses"><g:message
                            code="default.mainMenu6.subMenu7"/></g:link></li>
                %{--<li><g:link controller="admin"--}%
                %{--action="updateCourses"><g:message--}%
                %{--code="default.mainMenu6.subMenu8"/></g:link></li>--}%
            </ul>
        </li>
        <li><a href="#"><g:message code="default.mainMenu3.subMenu2"/></a>
            <ul>
                <li><a href="#" class="newAdmitCardPopup"><g:message
                        code="default.mainMenu3.subMenu2.submenu1.submemu1"/></a></li>
                <li><g:link controller="admitCard" action="bulkCreationOfAdmitCard"><g:message
                        code="default.mainMenu3.subMenu2.submenu1.submenu2"/></g:link>

                </li>
            </ul>
        </li>
        %{--<li><a href="#"><g:message code="default.mainMenu3.subMenu8"/></a>--}%

        %{--<ul>--}%
        %{--<li><g:link controller="admin" action="uploadInternalMarks"><g:message--}%
        %{--code="default.mainMenu3.subMenu8.subMenu1"/></g:link>--}%
        %{--</li>--}%
        %{--<li><g:link controller="course"--}%
        %{--action="listOfCourses"><g:message--}%
        %{--code="default.mainMenu3.subMenu8.subMenu2"/></g:link></li>--}%
        %{--<li><g:link controller="course"--}%
        %{--action="updateCourses"><g:message--}%
        %{--code="default.mainMenu3.subMenu8.subMenu3"/></g:link></li>--}%
        %{--</ul>--}%
        %{--</li>--}%
        %{--<!----}%
        %{--<li>--}%
        %{--<ul>--}%
        %{--<li><g:link controller="course" action="createNewCourse"><g:message--}%
        %{--code="default.mainMenu3.subMenu6.subMenu1"/></g:link></li>--}%
        %{--<li><g:link controller="course"--}%
        %{--action="viewCourses"><g:message--}%
        %{--code="default.mainMenu3.subMenu6.subMenu2"/></g:link></li>--}%
        %{--<li><g:link controller="course"--}%
        %{--action="updateCourses"><g:message--}%
        %{--code="default.mainMenu3.subMenu6.subMenu3"/></g:link></li>--}%
        %{--</ul>--}%
        %{--</li>--}%
        %{---->--}%

    </ul>
</li>

<li><a href="#"><g:message code="default.mainMenu4"/></a>
 %{--ADDED BY DIGVIJAY ON 3rd JUNE 2014--}%
    <ul>
        <li>
            <g:link controller="postExamination"
                    action="createMarksFoil"><g:message
                    code="default.mainMenu4.subMenu1"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="marksEntering"><g:message
                    code="default.mainMenu4.subMenu2"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="markMismatchReport"><g:message
                    code="default.mainMenu4.subMenu3"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="absenteeProcessing"><g:message
                    code="default.mainMenu4.subMenu4"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="marksUpdation"><g:message
                    code="default.mainMenu4.subMenu5"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="resultProcessing"><g:message
                    code="default.mainMenu4.subMenu6"/>
            </g:link>
        </li>
        <li>
            <g:link controller="postExamination"
                    action="finalResult"><g:message
                    code="default.mainMenu4.subMenu7"/>
            </g:link>
        </li>
        %{--<li>--}%
            %{--<g:link controller="postExamination"--}%
                    %{--action=""><g:message--}%
                    %{--code="default.mainMenu4.subMenu8"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%
        %{--<li>--}%
            %{--<g:link controller="postExamination"--}%
                    %{--action=""><g:message--}%
                    %{--code="default.mainMenu4.subMenu9"/>--}%
            %{--</g:link>--}%
        %{--</li>--}%

        <li><a href="#"><g:message code="default.mainMenu4.subMenu8"/></a>
            <ul>
                <li>
                    <g:link controller="postExamination"
                            action="bulkMarksSheet"><g:message
                            code="default.mainMenu4.subMenu8.submenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination"
                            action="singleMarksSheet"><g:message
                            code="default.mainMenu4.subMenu8.submenu2"/>
                    </g:link>
                </li>
            </ul>
        </li>

    </ul>
</li>

<li><a href="#"><g:message code="default.mainMenu5"/></a></li>
<li><a href="#"><g:message code="default.mainMenu6"/></a>
    <ul>
        <li><a href="#"><g:message code="default.mainMenu6.subMenu11"/></a>
            <ul>
                <li><g:link controller="admin"
                            action="assignAdmissionPeriod"><g:message
                            code="default.mainMenu3.subMenu7.subMenu8"/></g:link></li>
                <li><a href="#"><g:message code="default.mainMenu3.subMenu7.subMenu7"/></a>
                    <ul>
                        <li><g:link controller="admin" action="assignLateFeeDate">
                            <g:message code="default.mainMenu3.subMenu7.subMenu7.subMenu1"/></g:link>
                        </li>
                        <li><g:link controller="admin" action="removeLateFeeDate">
                            <g:message code="default.mainMenu3.subMenu7.subMenu7.subMenu2"/></g:link></li>
                    </ul>
                </li>
                <li><g:link controller="admin"
                            action="assignExaminationDate"><g:message
                            code="default.mainMenu6.subMenu4"/></g:link></li>
                <li><g:link controller="admin" action="assignRollNoGenerationDate"><g:message

                        code="default.mainMenu6.subMenu9"/></g:link></li>
            </ul>
        </li>
        <li><g:link controller="admin" action="viewListGenerateRollNumber"><g:message
                code="default.mainMenu6.subMenu1"/></g:link></li>

        <li><g:link controller="admin"
                    action="assignExaminationVenue"><g:message
                    code="default.mainMenu6.subMenu5"/></g:link></li>
        <li><g:link controller="student" action="studentListView"><g:message
                code="default.mainMenu2.subMenu6"/></g:link></li>
        <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                code="default.mainMenu2.subMenu12"/></g:link></li>

        <li><g:link controller="admin"
                    action="downloadAttendanceSheet"><g:message
                    code="default.mainMenu6.subMenu6"/></g:link></li>

        <li><g:link controller="admin"
                    action="studyMaterial"><g:message
                    code="default.mainMenu6.subMenu10"/></g:link></li>

    </ul>
</li>

<li><a href="#"><g:message
        code="default.mainMenu7"/></a>

    <ul>

        <li><a href="#"><g:message
                code="default.mainMenu8.subMenu5"/></a>
            <ul>
                <li><g:link controller="programFee"
                            action="addFeeType"><g:message
                            code="default.mainMenu3.subMenu7.subMenu4"/></g:link></li>
                <li><g:link controller="programFee"
                            action="viewExistingFeeType"><g:message
                            code="default.mainMenu3.subMenu7.subMenu5"/></g:link></li>
            </ul>
        </li>

        <li><a href="#"><g:message
                code="default.mainMenu8.subMenu6"/></a>
            <ul>
                <li><g:link controller="programFee" action="createNewFeeType"><g:message
                        code="default.mainMenu3.subMenu7.subMenu1"/></g:link></li>
                <li><g:link controller="programFee"
                            action="listOfFeeType"><g:message
                            code="default.mainMenu3.subMenu7.subMenu2"/></g:link></li>

            </ul>

        </li>

        <li><a href="#"><g:message
                code="default.mainMenu8.subMenu3"/></a>
            <ul>
                <li>
                    <g:link controller="feeDetails"
                            action="generateChallanSCAdmissionFee"><g:message
                            code="default.mainMenu8.subMenu3.subMenu1"/></g:link>
                </li>
                <li>
                    <g:link controller="feeDetails"
                            action="payAdmissionFee"><g:message
                            code="default.mainMenu8.subMenu3.subMenu3"/></g:link>
                </li>

            </ul>
        </li>
        <li><a href="#"><g:message
                code="default.mainMenu7.subMenu1"/></a>
            <ul>
                <li><g:link controller="admin"
                            action="feeVoucher"><g:message
                            code="default.mainMenu7.subMenu1.subMenu1"/></g:link>
                </li>
            </ul>
        </li>
        <li><a href="#"><g:message
                code="default.mainMenu8.subMenu4"/></a>
            <ul>
                <li>
                    <g:link controller="feeDetails"
                            action="challanForMiscellaneousFee"><g:message
                            code="default.mainMenu8.subMenu4.subMenu1"/></g:link></li>
                <li>
                    <g:link controller="feeDetails"
                            action="payMiscellaneousFee"><g:message
                            code="default.mainMenu8.subMenu4.subMenu2"/></g:link></li>
            </ul>
        </li>

        <li><g:link controller="admin"
                    action="approvePayInSlip"><g:message
                    code="default.mainMenu7.subMenu2"/></g:link></li>
        <li><g:link controller="feeDetails"
                    action="feeStatusForRollNumber"><g:message
                    code="default.mainMenu7.subMenu3"/></g:link></li>
    </ul>
</li>

%{--<li><a href="#"><g:message code="default.mainMenu8"/></a>--}%
%{--<ul>--}%
%{--<li><g:link controller="feeDetails"--}%
%{--action="createFeeDetails"><g:message--}%
%{--code="default.mainMenu8.subMenu1"/></g:link></li>--}%
%{--<li><g:link controller="feeDetails"--}%
%{--action="bulkFeeEntry"><g:message--}%
%{--code="default.mainMenu8.subMenu2"/></g:link></li>--}%
%{--<li><a href="#"><g:message--}%
%{--code="default.mainMenu8.subMenu3"/></a>--}%
%{--<ul>--}%
%{--<li>--}%
%{--<g:link controller="feeDetails"--}%
%{--action="generateChallanSCAdmissionFee"><g:message--}%
%{--code="default.mainMenu8.subMenu3.subMenu1"/></g:link>--}%
%{--</li>--}%
%{--<li>--}%
%{--<g:link controller="feeDetails"--}%
%{--action="payAdmissionFee"><g:message--}%
%{--code="default.mainMenu8.subMenu3.subMenu3"/></g:link>--}%
%{--</li>--}%

%{--</ul>--}%
%{--</li>--}%
%{--<li><a href="#"><g:message--}%
%{--code="default.mainMenu8.subMenu4"/></a>--}%
%{--<ul>--}%
%{--<li>--}%
%{--<g:link controller="feeDetails"--}%
%{--action="challanForMiscellaneousFee"><g:message--}%
%{--code="default.mainMenu8.subMenu4.subMenu1"/></g:link></li>--}%
%{--<li>--}%
%{--<g:link controller="feeDetails"--}%
%{--action="payMiscellaneousFee"><g:message--}%
%{--code="default.mainMenu8.subMenu4.subMenu2"/></g:link></li>--}%
%{--</ul>--}%
%{--</li>--}%

%{--</ul>--}%
%{--</li>--}%
<li><g:link controller="report" action="reportIndex"><g:message code="default.mainMenu9"/></g:link></li>

</ul>
</div>
%{--</div>--}%


<div class="scroller"><!-- this is for emulating position fixed of the nav -->
    <div class="scroller-inner">
        <g:render template="/layouts/viewResultPopup"/>
        <g:render template="/layouts/newAdmitCardPopup"/>
        <g:render template="/layouts/viewAdmitCardPopup"/>
        <g:render template="/layouts/editAdmitCardPopup"/>
        <g:render template="/layouts/deleteAdmitCardPopup"/>
        <g:render template="/layouts/statusPopup"/>
    </div><!-- /scroller-inner -->
</div><!-- /scroller -->

</div>