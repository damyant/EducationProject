<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 7/1/14
  Time: 12:55 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <g:javascript src='admitCard.js'/>
    <g:javascript src='registerPage.js'/>
</head>
<body>
<div id="main">
    <fieldset class="form">
<g:uploadForm controller="admitCard" action="submitSignatureImage" method='post' enctype="multipart/form-data"  id="signatureImage" name="signatureImage">
       <h3>Admit Card Signature Upload</h3>
        <table class="inner">
            <tr>
                <td class="university-size-1-3">Examination Venue</td>
                <td class="university-size-2-3">
                    <g:select name="examVenue" id="examVenue" optionKey="id" class="university-size-1-2"
                              optionValue="name" from="${examinationproject.ExaminationVenue.findAllByCity(examinationproject.City.findByCityName('Guwahati'))}" noSelection="['': ' Select Examination Venue']"/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-3">
                    Upload Signature
                </td>
                <td class="university-size-2-3">
                    <div id="profile-image">
                        %{--<g:if test="${admitInst!=null}">--}%
                            %{--<img src="${createLink(controller: 'admitCard', action: 'showSignature', id: admitInst?.id--}%
                                %{--, mime: 'image/jpeg')}" class="university-signature" id="signature"/>\--}%
                        %{--</g:if>--}%
                        %{--<g:else>--}%
                            <img src="" class="university-signature" id="signature"/>
                        %{--</g:else>--}%
                    </div>
                    <input type='file' id="profileImage" onchange="readURL(this, 'signature');" class="university-button" name="signature"/>
                    <input type="text" id="imageValidate" name="imageValidate" style="width: 1px;height: 1px;border: 0px;"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <input type="submit" class="ui-button" value="Upload" onclick="validateProgramFee()"/>
                </td>
            </tr>
        </table>
</g:uploadForm>
    </fieldset>
</div>
</body>
</html>