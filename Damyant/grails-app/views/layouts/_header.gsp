<div>
<div class="logo">
    <div class="header-parts"><img id="logo-images" src="${resource(dir: 'images', file: 'd-edufocus.png')}"
                                   class="window"></div>

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

<div id="cssmenu">
%{--<div id="header-Menu">--}%
<ul id="menu">
<li><g:link controller="home" action="index"><g:message
        code="default.mainMenu1"/></g:link></li>
<sec:ifNotGranted roles="ROLE_LIBRARY">
<li><a href="#"><g:message code="default.mainMenu2"/></a>
    <ul>
        <sec:ifLoggedIn>
            <sec:ifNotGranted roles="ROLE_IDOL_USER">
                <li><g:link controller="student" action="registration"><g:message
                        code="default.mainMenu2.subMenuStudyCentre"/></g:link></li>
            </sec:ifNotGranted>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <li><g:link controller="student" action="registration"><g:message
                    code="default.mainMenu2.subMenu1"/></g:link></li>
        </sec:ifNotLoggedIn>
        %{--<sec:ifLoggedIn>--}%
            %{--<sec:ifNotGranted roles="ROLE_STUDY_CENTRE">--}%
                %{--<li><g:link controller="student" action="enrollmentAtIdol"><g:message--}%
                        %{--code="default.mainMenu2.enrollAtIdol"/></g:link></li>--}%
            %{--</sec:ifNotGranted>--}%
        %{--</sec:ifLoggedIn>--}%

    %{--<li><a href="#"><g:message code="default.mainMenu2.subMenu4"/></a></li>--}%
        <li><a class="statustopopup" href="#"><g:message code="default.mainMenu2.subMenu5"/></a></li>
    </ul>
</li>
<sec:ifLoggedIn>
    <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
        <li><a href="#"><g:message code="default.mainMenu3"/></a>
            <ul>
                <li>
                    <a href="#"><g:message code="default.mainMenu3.subMenu5"/></a>
                    <ul>
                        <li><a href="#"><g:message code="default.mainMenu6.subMenu16"/></a>
                            <ul>
                                <li><g:link controller="bank" action="createBank"><g:message
                                        code="default.mainMenu6.subMenu14"
                                        default="Add Bank"/></g:link></li>
                                <li><g:link controller="bank" action="bankList"><g:message
                                        code="default.mainMenu6.subMenu15"
                                        default="View Bank"/></g:link></li>
                            </ul>
                        </li>

                        <li><a href="#"><g:message code="default.mainMenu6.subMenu22"/></a>
                            <ul>
                                <li><g:link controller="district" action="createDistrict"><g:message
                                        code="default.mainMenu6.subMenu23" default="Add District"/></g:link></li>
                                <li><g:link controller="district" action="districtList"><g:message
                                        code="default.mainMenu6.subMenu24" default="View/Update"/></g:link></li>
                            </ul>
                        </li>
                        <li><a href="#"><g:message code="default.mainMenu6.subMenu17"/></a>
                            <ul>
                                <li><g:link controller="branch" action="createBranch"><g:message
                                        code="default.mainMenu6.subMenu18" default="Add Bank"/></g:link></li>
                                <li><g:link controller="branch" action="branchList"><g:message
                                        code="default.mainMenu6.subMenu19" default="View Bank"/></g:link></li>
                            </ul>
                        </li>
                        <li><a href="#"><g:message code="default.mainMenu3.subMenu11"/></a>
                            <ul>
                                <li><g:link controller="examinationCenter" action="createNewCity"><g:message
                                        code="default.mainMenu3.subMenu11.subMenu1"/></g:link></li>
                                <li><g:link controller="examinationCenter" action="listOfCity"><g:message
                                        code="default.mainMenu3.subMenu11.subMenu2"/></g:link></li>
                            </ul>
                        </li>
                        <li><a href="#"><g:message code="default.mainMenu3.subMenu12"/></a>
                            <ul>
                                <li><g:link controller="category" action="createNewCategory"><g:message
                                        code="default.mainMenu3.subMenu12.subMenu1"/></g:link></li>
                                <li><g:link controller="category" action="categoryList"><g:message
                                        code="default.mainMenu3.subMenu12.subMenu2"/></g:link></li>
                            </ul>
                        </li>
                    </ul>
                </li>
                %{--<li>--}%
                    %{--<a href="#"><g:message code="default.mainMenu2.subMenu2"/></a>--}%
                    %{--<ul>--}%
                        %{--<li><g:link controller="examinationCenter" action="createExamCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu11.subMenu1"/></g:link></li>--}%
                        %{--<li><g:link controller="examinationCenter" action="listOfExamCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu11.subMenu2"/></g:link></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
                %{--<li><a href="#"><g:message code="default.mainMenu3.subMenu3"/></a>--}%
                    %{--<ul>--}%
                        %{--<li><g:link controller="studyCenter" action="createNewStudyCenter"><g:message--}%
                                %{--code="default.mainMenu3.subMenu3.subMenu1"/></g:link></li>--}%
                        %{--<li><g:link controller="studyCenter" action="viewStudyCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu3.subMenu2"/></g:link></li>--}%
                        %{--<li><g:link controller="studyCenter" action="updateStudyCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu3.subMenu3"/></g:link></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%

                %{--<li><a href="#"><g:message code="default.mainMenu3.subMenu1"/></a>--}%
                    %{--<ul>--}%
                        %{--<li><g:link controller="examinationCenter" action="create"><g:message--}%
                                %{--code="default.mainMenu3.subMenu1.subMenu1"/></g:link></li>--}%
                        %{--<li><g:link controller="examinationCenter" action="viewExaminationCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu1.subMenu2"/></g:link></li>--}%
                        %{--<li><g:link controller="examinationCenter" action="updateExaminationCentre"><g:message--}%
                                %{--code="default.mainMenu3.subMenu1.subMenu3"/></g:link></li>--}%
                    %{--</ul>--}%
                %{--</li>--}%


                <li><a href="#"><g:message code="default.mainMenu3.subMenu6"/></a>
                    <ul>
                        <li><g:link controller="course" action="createNewCourse"><g:message
                                code="default.mainMenu3.subMenu6.subMenu1"/></g:link></li>
                        <li><g:link controller="course" action="listOfCourses"><g:message
                                code="default.mainMenu3.subMenu6.subMenu2"/></g:link></li>
                        <li><g:link controller="course" action="updateCourses"><g:message
                                code="default.mainMenu3.subMenu6.subMenu3"/></g:link></li>
                    </ul>
                </li>

                <li><a href="#"><g:message code="default.mainMenu3.subMenu2"/></a>
                    <ul>
                        <li><g:link controller="admitCard" action="SingleAdmitCardGenerate"><g:message
                                code="default.mainMenu3.subMenu2.submenu1.submemu1"/></g:link></li>
                        %{--<li><a href="#" class="SingleAdmitCardGenerate"><g:message--}%
                        %{--code="default.mainMenu3.subMenu2.submenu1.submemu1"/></a></li>--}%
                        <li><g:link controller="admitCard" action="bulkCreationOfAdmitCard"><g:message
                                code="default.mainMenu3.subMenu2.submenu1.submenu2"/></g:link></li>
                        <li><g:link controller="admitCard" action="bulkCreationOfAdmitCardForFormFill"><g:message
                                code="default.mainMenu3.subMenu2.submenu1.submenu3"/></g:link></li>
                    </ul>
                </li>

                <li><a href="#"><g:message code="default.mainMenu3.subMenu9"/></a>
                    <ul>

                        <li><g:link controller="admin" action="addCourses"><g:message
                                code="default.mainMenu3.subMenu11.subMenu1"/></g:link></li>

                        <li><g:link controller="admin" action="listOfCourses"><g:message
                                code="default.mainMenu3.subMenu11.subMenu2"/></g:link></li>
                    </ul>
                </li>
            </ul>
        </li>

    </sec:ifNotGranted>
</sec:ifLoggedIn>
<sec:ifLoggedIn>
    <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
        <li><a href="#"><g:message code="default.mainMenu4"/></a>
            %{--ADDED BY DIGVIJAY ON 3rd JUNE 2014--}%
            <ul>

                <li><a href="#"><g:message code="default.mainMenu4.subMenu9"/></a>
                    <ul>
                        <li>
                            <g:link controller="marksType" action="createMarksType"><g:message
                                    code="default.mainMenu4.subMenu9.submenu1"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="marksType" action="marksTypeList"><g:message
                                    code="default.mainMenu4.subMenu9.submenu2"/>
                            </g:link>
                        </li>
                    </ul>
                </li>
                <li>
                    <g:link controller="postExamination" action="createMarksFoil"><g:message
                            code="default.mainMenu4.subMenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="homeAssignmentExcelUpload"><g:message
                            code="default.mainMenu4.subMenu2.subMenu1"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="marksEntering"><g:message
                            code="default.mainMenu4.subMenu2"/>
                    </g:link>
                </li>

                <li>
                    <g:link controller="postExamination" action="markMismatchReport"><g:message
                            code="default.mainMenu4.subMenu3"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="absenteeProcessing"><g:message
                            code="default.mainMenu4.subMenu4"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="marksUpdation"><g:message
                            code="default.mainMenu4.subMenu5"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="resultProcessing"><g:message
                            code="default.mainMenu4.subMenu6"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="finalResult"><g:message
                            code="default.mainMenu4.subMenu7"/>
                    </g:link>
                </li>
                <li>
                    <g:link controller="postExamination" action="meritRegister"><g:message
                            code="default.mainMenu4.subMenu10"/>
                    </g:link>
                </li>


                <li><a href="#"><g:message code="default.mainMenu4.subMenu8"/></a>
                    <ul>
                        <li>
                            <g:link controller="postExamination" action="bulkMarksSheet"><g:message
                                    code="default.mainMenu4.subMenu8.submenu1"/>
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="postExamination" action="singleMarksSheet"><g:message
                                    code="default.mainMenu4.subMenu8.submenu2"/>
                            </g:link>
                        </li>
                    </ul>
                </li>

            </ul>
        </li>

    </sec:ifNotGranted>
</sec:ifLoggedIn>
<li><a href="#"><g:message code="default.mainMenu5"/></a>
    <ul>
        <li><g:link controller="student" action="downloadAdmitCard"><g:message
                code="default.mainMenu2.subMenu3"/></g:link></li>

    </ul>
</li>

</sec:ifNotGranted>
<sec:ifAnyGranted roles="ROLE_LIBRARY">
    <li><a href="#"><g:message code="default.mainMenu11"/></a>

        <ul>
            <li><g:link><g:message code="default.mainMenu11.subMenu2"/></g:link>
                <ul>
                    <g:link controller="admin" action="catalogType"><g:message
                            code="default.mainMenu11.subMenu1.submenu1"/></g:link>
                    <g:link controller="admin" action="catalogType" params="[view: 'view']"><g:message
                            code="default.mainMenu11.subMenu1.submenu3"/></g:link>
                </ul>
            </li>
            <li><g:link><g:message code="default.mainMenu11.subMenu3"/></g:link>
                <ul>

                    <g:link controller="admin" action="catalogCatagory"><g:message
                            code="default.mainMenu11.subMenu3.submenu1"/></g:link>
                    <g:link controller="admin" action="catalogCatagory" params="[view: 'view']"><g:message
                            code="default.mainMenu11.subMenu3.submenu2"/></g:link>
                </ul>
            </li>
            <li><a href="#"><g:message code="default.mainMenu11.subMenu1"/></a>
                <ul>
                    <g:link controller="admin" action="addCatalog"><g:message
                            code="default.mainMenu11.subMenu1.submenu1"/></g:link>
                    <g:link controller="admin" action="editCatalog"><g:message
                            code="default.mainMenu11.subMenu1.submenu2"/></g:link>
                    <g:link controller="admin" action="viewCatalog"><g:message
                            code="default.mainMenu11.subMenu1.submenu3"/></g:link>
                </ul>
            </li>

            <li><g:link controller="admin" action="bookIssue"><g:message
                    code="default.mainMenu11.subMenu5"/></g:link>

            <li><a href="#"><g:message code="default.mainMenu11.subMenu4"/></a>
                <ul>
                    <g:link controller="libraryReports" action="issuedBooks"><g:message
                            code="default.mainMenu11.subMenu4.submenu1"/></g:link>
                    <li><g:link controller="libraryReports" action="listOfCatalogs"><g:message
                            code="default.mainMenu11.subMenu4.submenu2"/></g:link>
                        <ul>
                            %{--<g:link controller="libraryReports" action="byType"><g:message--}%
                                    %{--code="default.mainMenu11.subMenu4.submenu2.submenu1"/></g:link>--}%
                            <g:link controller="libraryReports" action="byCategory"><g:message
                                    code="default.mainMenu11.subMenu4.submenu2.submenu2"/></g:link>
                            <g:link controller="libraryReports" action="byIsbn"><g:message
                                    code="default.mainMenu11.subMenu4.submenu2.submenu3"/></g:link>
                            <g:link controller="libraryReports" action="byTitle"><g:message
                                    code="default.mainMenu11.subMenu4.submenu2.submenu4"/></g:link>
                            <g:link controller="libraryReports" action="byAuthor"><g:message
                                    code="default.mainMenu11.subMenu4.submenu2.submenu5"/></g:link>
                        </ul></li>

                    <g:link controller="libraryReports" action="overdueBooks"><g:message
                            code="default.mainMenu11.subMenu4.submenu3"/></g:link>
                </ul>

            </li>
        </ul>
    </li>

</sec:ifAnyGranted>

<li><a href="#"><g:message code="default.mainMenu12"/></a>
    <ul>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <li>
                <g:link controller="admin" action="noticeBoard"><g:message
                        code="default.mainMenu3.subMenu7.subMenu3"/></g:link>
            </li>
            <li>
                <g:link controller="admin" action="noticeBoardDel"><g:message
                        code="default.mainMenu12.subMenu3"/></g:link>
            </li>

        </sec:ifAnyGranted>
        <li>
            <g:link controller="admin" action="noticeBoardView"><g:message
                    code="default.mainMenu12.subMenu2"/></g:link>
        </li>

    </ul>

</li>

<sec:ifNotGranted roles="ROLE_LIBRARY">
    <sec:ifLoggedIn>

        <li><a href="#"><g:message code="default.mainMenu6"/></a>
            <ul>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><a href="#"><g:message code="default.mainMenu6.subMenu11"/></a>
                        <ul>
                            <li><g:link controller="admin" action="assignAdmissionPeriod"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu8"/></g:link></li>
                            <li><a href="#"><g:message code="default.mainMenu3.subMenu7.subMenu7"/></a>
                                <ul>
                                    <li><g:link controller="admin" action="assignLateFeeDate"><g:message
                                            code="default.mainMenu3.subMenu7.subMenu7.subMenu1"/></g:link>
                                    </li>
                                    <li><g:link controller="admin" action="removeLateFeeDate"><g:message
                                            code="default.mainMenu3.subMenu7.subMenu7.subMenu2"/></g:link></li>
                                </ul>
                            </li>
                            <li><g:link controller="admin" action="assignExaminationDate"><g:message
                                    code="default.mainMenu6.subMenu4"/></g:link></li>
                            <li><g:link controller="admin" action="assignRollNoGenerationDate"><g:message
                                    code="default.mainMenu6.subMenu9"/></g:link></li>
                        </ul>
                    </li>
                </sec:ifNotGranted>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><g:link controller="admin" action="assignExaminationVenue"><g:message
                            code="default.mainMenu6.subMenu5"/></g:link></li>
                </sec:ifNotGranted>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><g:link controller="admin" action="downloadAttendanceSheet"><g:message
                            code="default.mainMenu6.subMenu6"/></g:link></li>
                </sec:ifNotGranted>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><g:link controller="admin" action="studyMaterial"><g:message
                            code="default.mainMenu6.subMenu10"/></g:link></li>
                </sec:ifNotGranted>
                <sec:ifLoggedIn>
                    <sec:ifNotGranted roles="ROLE_STUDY_CENTRE">
                        <li><g:link controller="student" action="generateIdentityCard"><g:message
                                code="default.mainMenu2.subMenu4"/></g:link></li>
                    </sec:ifNotGranted>
                </sec:ifLoggedIn>
                <li><a href="#"><g:message code="default.mainMenu6.subMenu7"/></a>
                    <ul>
                        <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_LIBRARY">
                            <li><g:link controller="admin" action="searchStudentName"><g:message
                                    code="default.mainMenu6.subMenu2"/></g:link></li>
                        </sec:ifNotGranted>
                        <sec:ifNotGranted roles="ROLE_STUDY_CENTRE">
                            <li><g:link controller="student" action="studentListView"><g:message
                                    code="default.mainMenu2.subMenu6"/></g:link></li>
                        </sec:ifNotGranted>
                        <sec:ifNotGranted roles="ROLE_STUDY_CENTRE">
                            <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                                    code="default.mainMenu2.subMenu12"/></g:link></li>
                        </sec:ifNotGranted>
                        <sec:ifAnyGranted roles="ROLE_STUDY_CENTRE">
                            <li><g:link controller="admin" action="individualStudentUpdate"><g:message
                                    code="default.mainMenu2.subMenu13"/></g:link></li>
                        </sec:ifAnyGranted>
                    </ul>
                </li>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><g:link controller="admin" action="viewListGenerateRollNumber"><g:message
                            code="default.mainMenu6.subMenu1"/></g:link></li>
                </sec:ifNotGranted>

                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE">
                    <li><g:link controller="admitCard" action="loadIdolSignatureInAdmit"><g:message
                            code="default.mainMenu6.subMenu3"/></g:link></li>
                    <li><g:link controller="homeAssignment" action="submitHomeAssignment"><g:message
                            code="default.mainMenu6.subMenu20"/></g:link></li>
                    <li><a href="#"><g:message code="default.mainMenu6.subMenu21"/></a>
                        <ul>
                            <li><g:link controller="homeAssignment" action="studentAddressSingle"><g:message
                                    code="default.mainMenu3.subMenu2.submenu1.submemu1"/></g:link></li>
                            <li><g:link controller="homeAssignment" action="studentAddress"><g:message
                                    code="default.mainMenu3.subMenu2.submenu4"/></g:link></li>
                        </ul>
                    </li>
                </sec:ifNotGranted>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <li><g:link controller="photoUpload" action="photoUpload"><g:message
                            code="default.mainMenu3.subMenu2.submenu5"/></g:link></li>
                    <li><g:link controller="feeDetails" action="printAChallan"><g:message
                            code="default.mainMenu3.subMenu2.submenu3"/></g:link></li>
                </sec:ifAnyGranted>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <li><a href="#"><g:message code="default.mainMenu10"/></a>
                        <ul>
                            <li><g:link controller="employe" action="createEmployee"><g:message
                                    code="default.mainMenu10.subMenu1"/></g:link></li>
                            <li><g:link controller="employe" action="listOfEmployee"><g:message
                                    code="default.mainMenu10.subMenu2"/></g:link></li>
                        </ul>
                    </li>
                    <li><a href="#"><g:message code="default.mainMenu13"/></a>
                        <ul>
                            <li><g:link controller="equipment" action="createEquipment"><g:message
                                    code="default.mainMenu13.subMenu1"/></g:link></li>
                            <li><g:link controller="equipment" action="listOfEquipment"><g:message
                                    code="default.mainMenu13.subMenu2"/></g:link></li>
                        </ul>
                    </li>
                </sec:ifAnyGranted>
            </ul>
        </li>
    </sec:ifLoggedIn>
    <sec:ifLoggedIn>
        <li><a href="#"><g:message code="default.mainMenu7"/></a>
            <ul>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><a href="#"><g:message code="default.mainMenu8.subMenu5"/></a>
                        <ul>
                            <li><g:link controller="programFee" action="addFeeType"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu4"/></g:link></li>
                            <li><g:link controller="programFee" action="viewExistingFeeType"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu5"/></g:link></li>
                        </ul>
                    </li>
                    <li><a href="#"><g:message code="default.mainMenu8.subMenu6"/></a>
                        <ul>
                            <li><g:link controller="programFee" action="createAdmissionFee"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu11"/></g:link></li>
                            <li><g:link controller="programFee" action="listOfAdmissionFee"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu12"/></g:link></li>
                            <li><g:link controller="programFee" action="createNewFeeType"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu1"/></g:link></li>
                            <li><g:link controller="programFee" action="listOfFeeType"><g:message
                                    code="default.mainMenu3.subMenu7.subMenu2"/></g:link></li>
                        </ul>
                    </li>
                </sec:ifNotGranted>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE">
                    <li><a href="#"><g:message code="default.mainMenu7.subMenu4.chandan"/></a>
                        <ul>
                            <li><g:link controller="feeDetails" action="postAdmissionFeeAtIdol"><g:message
                                    code="default.mainMenu8.subMenu4.subMenu3"/></g:link></li>
                            <li><g:link controller="admin" action="feeVoucher"><g:message
                                    code="default.mainMenu7.subMenu1"/></g:link></li>
                            <li><g:link controller="admin" action="feeVoucher"><g:message
                                    code="default.mainMenu7.subMenu1.subMenu1"/></g:link></li>
                            <li><g:link controller="admin" action="generateCustomChallan"><g:message
                                    code="default.mainMenu6.subMenu12"/></g:link></li>
                            <li><g:link controller="feeDetails" action="searchCustomChallan"><g:message
                                    code="default.mainMenu6.subMenu8"/></g:link></li>
                        </ul>
                    </li>
                </sec:ifNotGranted>
                %{--<sec:ifNotGranted roles="ROLE_IDOL_USER">--}%
                    %{--<li><a href="#"><g:message code="default.mainMenu8.subMenu3"/></a>--}%
                        %{--<ul>--}%
                            %{--<li><g:link controller="feeDetails" action="generateChallanSCAdmissionFee"><g:message--}%
                                    %{--code="default.mainMenu8.subMenu3.subMenu1"/></g:link></li>--}%
                        %{--</ul>--}%
                    %{--</li>--}%
                %{--</sec:ifNotGranted>--}%
                <sec:ifNotGranted roles="ROLE_IDOL_USER">
                    <li><a href="#"><g:message code="default.mainMenu8.subMenu4"/></a>
                        <ul>
                            <li>
                                <g:link controller="feeDetails" action="payAdmissionFee"><g:message
                                        code="default.mainMenu8.subMenu3.subMenu3"/></g:link>
                            </li>
                            <li>
                                <g:link controller="feeDetails" action="payMiscellaneousFee"><g:message
                                        code="default.mainMenu8.subMenu4.subMenu2"/></g:link></li>

                        </ul>
                    </li>
                </sec:ifNotGranted>
                <sec:ifNotGranted roles="ROLE_STUDY_CENTRE,ROLE_IDOL_USER">
                    <li><g:link controller="admin"
                                action="approvePayInSlip"><g:message
                                code="default.mainMenu7.subMenu2"/></g:link></li>
                </sec:ifNotGranted>
                <li><g:link controller="feeDetails"
                            action="feeStatusForRollNumber"><g:message
                            code="default.mainMenu7.subMenu3"/></g:link></li>
                <li><g:link controller="feeDetails"
                            action="challanNumberStatus"><g:message
                            code="default.mainMenu7.subMenu4"/></g:link></li>
            </ul>
        </li>
    </sec:ifLoggedIn>

    <sec:ifLoggedIn>
        <li><g:link controller="report" action="reportIndex"><g:message code="default.mainMenu9"/></g:link></li>
    </sec:ifLoggedIn></sec:ifNotGranted>
</div>

<div class="scroller"><!-- this is for emulating position fixed of the nav -->
    <div class="scroller-inner">
        <g:render template="/layouts/statusPopup"/>
    </div>
</div>
</div>