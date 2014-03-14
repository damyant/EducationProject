<div class="toPopup" id="viewadmitCardToPopup">

    <div class="close"></div>
    <span class="ecs_tooltip">Press Esc to close <span class="arrow"></span></span>
    <div class="popup_content" id="viewadmitCardPopupContent"> <!--your content start-->
        <g:form action="createAdmintCard" method="post"  controller="admitCard" >
            <p><bold>Please fill the information to view Admit Card</bold></p>
            <div class="input">
                <g:textField name="Registration Number" value="" placeholder="Registration Number"/>
            </div>

            <input type="SUBMIT" name="submit" value="Submit" />
        %{--<g:actionSubmit value="Create" action="Update" />--}%


        </g:form>
    </div> <!--your content end-->

</div> <!--toPopup end-->

<div class="loader"></div>
<div class="backgroundPopup" id="viewadmitCardBackgroundPopup"></div>