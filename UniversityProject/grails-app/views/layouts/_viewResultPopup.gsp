

<div class="toPopup" id="viewResultToPopup">

    	        <div class="close"></div>
    	        <span class="ecs_tooltip">Press Esc to close <span class="arrow"></span></span>
            <div class="popup_content" id="viewResultPopupContent"> <!--your content start-->
                <g:form action="showResults" method="post" controller="home" >
                    <p><bold>Please fill the information to view Result</bold></p>
                    <div class="input">
                        <g:textField name="rollNumber" value="" placeholder="Roll Number"/>
                    </div>
                    <div class="input">
                        <input type="text" name="Date of birth" placeholder="Date of Birth" />
                    </div>
                    <input type="SUBMIT" name="submit" value="Submit" />


                </g:form>

            </div> <!--your content end-->

    </div> <!--toPopup end-->

    <div class="loader"></div>
    	    <div class="backgroundPopup" id="viewResultBackgroundPopup"></div>

