<div>
    <div class="logo">
        <div class="header-parts"><img src="${resource(dir: 'images', file: 'logo.png')}" class="logo-image"></div>
        <sec:ifLoggedIn>
            <div class="header-parts" name="logout">
                <div class="university-session-management">
                    <sec:username/> || <g:link controller="logout">sign out</g:link> ||
                    <g:link controller="user" action="index">Manage User</g:link>
                </div>
            </div>
        </sec:ifLoggedIn>
    </div>

    <div id="header-Menu">
        %{--<div id="header-Menu">--}%
        <ul id="menu">
            <li><a href="#"><g:message code="default.mainMenu1"/></a></li>
            <li><a href="#"><g:message code="default.mainMenu2"/></a>
                <ul>
                    <li><g:link controller="student" action="registration"><g:message
                            code="default.mainMenu2.subMenu1"/></g:link></li>
                    <li><a class="viewResulttopopup"><g:message code="default.mainMenu2.subMenu2"/></a></li>
                    <li><a href="#"><g:message code="default.mainMenu2.subMenu3"/></a></li>
                    <li><a href="#"><g:message code="default.mainMenu2.subMenu4"/></a></li>
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
                    <li><a href="#"><g:message code="default.mainMenu3.subMenu2"/></a>
                        <ul>
                            <li><a href="#"><g:message code="default.mainMenu3.subMenu2.submenu1"/></a>
                                <ul>
                                    <li><a href="#" class="newAdmitCardPopup"><g:message
                                            code="default.mainMenu3.subMenu2.submenu1.submemu1"/></a></li>
                                    <li><a href="#"><g:message
                                            code="default.mainMenu3.subMenu2.submenu1.submenu2"/></a>
                                    </li>

                                </ul>
                            </li>
                            <li><a href="#" class="viewAdmitCardPopup"><g:message
                                    code="default.mainMenu3.subMenu2.submenu2"/></a></li>
                            <li><a href="#" class="editAdmitCardPopup"><g:message
                                    code="default.mainMenu3.subMenu2.submenu3"/></a></li>
                            <li><a href="#" class="deleteAdmitCardPopup"><g:message
                                    code="default.mainMenu3.subMenu2.submenu4"/></a></li>
                            <li><a href="#"><g:message code="default.mainMenu3.subMenu2.submenu5"/></a>
                                <ul>
                                    <li><a onclick="showNewAdmitCardPopup()"><g:message
                                            code="default.mainMenu3.subMenu2.submenu1.submemu1"/></a></li>
                                    <li><a href="#"><g:message
                                            code="default.mainMenu3.subMenu2.submenu1.submenu2"/></a>
                                    </li>
                                </ul>

                            </li>
                        </ul>
                    </li>
                    <li><a href="#"><g:message code="default.mainMenu3.subMenu6"/></a>
                        <ul>
                            <li><g:link controller="course" action="createNewCourse"><g:message
                                    code="default.mainMenu3.subMenu6.subMenu1"/></g:link></li>
                            <li><g:link controller="course"
                                        action="viewCourses"><g:message
                                        code="default.mainMenu3.subMenu6.subMenu2"/></g:link></li>
                            <li><g:link controller="course"
                                        action="updateCourses"><g:message
                                        code="default.mainMenu3.subMenu6.subMenu3"/></g:link></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li><a href="#"><g:message code="default.mainMenu4"/></a></li>
            <li><a href="#"><g:message code="default.mainMenu5"/></a></li>
            <li><a href="#"><g:message code="default.mainMenu6"/></a></li>
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
        </div><!-- /scroller-inner -->
    </div><!-- /scroller -->

</div>