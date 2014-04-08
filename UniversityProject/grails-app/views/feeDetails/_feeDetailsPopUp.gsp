

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

<div class="loader"></div>
<div class="backgroundPopup" id="statusBackgroundPopup"></div>

