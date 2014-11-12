

<div class="toPopup" id="statusToPopup">

    <div class="close"></div>
    <span class="ecs_tooltip">Press Esc to close <span class="arrow"></span></span>
    <div class="popup_content" id="statusPopupContent"> <!--your content start-->
        <g:form method="post" name="statusOfApplication" id="statusOfApplication" >
            <p><bold>Please fill the information to check the Status Of Application</bold></p>
            <div class="input">
                <g:textField name="referenceNumber" id="referenceNumber"  onkeypress="return isNumber(event)" value="" placeholder="Enter Reference Number"/>
            </div>
            <input type="button" name="submit" value="Submit" onclick="showStatus();" />
        </g:form>
        <div id="statusofApp" style="display: none">

        </div>

    </div> <!--your content end-->
</div> <!--toPopup end-->
%{--<html>--}%
%{--<head>--}%
    %{--<meta name="layout" content="main"/>--}%
%{--</head>--}%
%{--<body>--}%

%{--<div class="toPopup" id="admitCardPopup">--}%

    %{--<div class="closeAdmitCard"></div>--}%
    %{--<span class="ecs_tooltip1">Press Esc to close <span class="arrow"></span></span>--}%
    %{--<div class="popup_content" id="admitCardPopupContent"> <!--your content start-->--}%
%{--<g:form name="abc1" id="abc1" controller="admitCard" action="printAdmitCard">--}%
            %{--<p><bold>Please fill the information to download the Admit Card</bold></p>--}%
            %{--<div class="input">--}%
                %{--<input type="text" name="rollNumber" id="rollNo"  placeholder="Roll Number"/><label id="rollMsg" class="error"></label>--}%
            %{--</div>--}%
            %{--<div class="input">--}%
                %{--<input type="text" name="dob" id="dob" placeholder="Date of Birth" /><label id="dobMsg"></label>--}%
            %{--</div>--}%
            %{--<input type="button" name="submit" value="Submit" onclick="downloadAdmitCard();" />--}%

        %{--<div id="statusofApp" style="display: none">--}%

        %{--</div>--}%
%{--</g:form>--}%
    %{--</div> <!--your content end-->--}%

%{--</div> <!--toPopup end-->--}%


<div class="loader"></div>
<div class="backgroundPopup" id="statusBackgroundPopup"></div>

%{--<div class="loader"></div>--}%
%{--<div class="backgroundPopup1" id="statusBackgroundPopup1"></div>--}%
%{--</body>--}%
%{--</html>--}%
