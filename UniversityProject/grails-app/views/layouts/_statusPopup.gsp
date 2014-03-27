

<div class="toPopup" id="statusToPopup">

    <div class="close"></div>
    <span class="ecs_tooltip">Press Esc to close <span class="arrow"></span></span>
    <div class="popup_content" id="statusPopupContent"> <!--your content start-->
        <g:form action="showResults" method="post" controller="home" >
            <p><bold>Please fill the information to check the Status Of Application</bold></p>
            <div class="input">
                <g:textField name="rollNumber" value="" placeholder="Enter Reference Number"/>
            </div>
            <input type="SUBMIT" name="submit" value="Submit" />


        </g:form>

    </div> <!--your content end-->

</div> <!--toPopup end-->

<div class="loader"></div>
<div class="backgroundPopup" id="statusBackgroundPopup"></div>

