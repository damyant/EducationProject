<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/15/2014
  Time: 5:27 PM
--%>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Identity Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    <script type="text/javascript">
    </script>
    <style type="text/css">
    @page {
        size: 125mm 92mm;
        margin: 10px;
    }
    div.break {
        page-break-after: always;
    }
    </style>
</head>

<body >
<g:each in="${0..studentInstance.size()-1}" var="i">
   <div style="width: 430px;height:310px;">
       <div style="width: 200px;height:305px;float: left; padding: 3px;border: 1px solid #000000; line-height: 37px; vertical-align: bottom;">
         <div><label style="">Name :</label> <label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentName[i]}</label></div>
         <div><label style="">Programme :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentProgram[i]}</label></div>
         <div><label style="">Roll Number :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentRoll[i]}</label></div>
         <div><label style="">Date of Birth :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentDOB[i]}</label></div>
         <div><label style="">Address :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentAddress[i]}</label></div>
         <div><label style="">Pin Code :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentPin[i]}</label></div>
         <div><label style="">Mobile Number :</label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;"> ${studentMobNo[i]}</label></div>
       </div>
       <div style="width: 200px;height:305px;float: right;text-align: center; padding: 3px;border: 1px solid #000000; line-height: 20px; vertical-align: bottom;">
           <div id="profile-image" style="text-align: center; margin-top: 5px;margin-bottom: 5px;">
               <g:if test="${studentInstance[i]?.studentImage}">
                   <rendering:inlineJpeg bytes="${studentInstance[i]?.getStudentImage()}" style="margin:auto; width: 100px;"/>
               </g:if>
               <g:else>
                   <div style="margin:auto; height: 120px;width:100px;text-align: center ; vertical-align:middle;border: 1px solid;">
                       <div style="margin: 20px 0px;">
                           Affix Passport Size Photo
                       </div>
                   </div>
               </g:else>
               <div><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;">Holders Signature</label></div>
               <div style="margin-top:30px;"><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;">.........................................</label></div>
               <div style="margin-top:25px;font-size: 13px;font-weight: bold;"><label>Valid Upto : </label><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: bold;">....................................</label></div>
               <div style="margin-top:35px;"><label style="text-transform:capitalize;font-family: Big Caslon, Book Antiqua, Palatino Linotype, Georgia, serif;font-weight: normal;font-style: italic;font-size: 10px;">Signature of the Issuing Authority</label></div>

           </div>
       </div>
   </div>
   <g:if test="${studentInstance.size()-1!=i}">
    <div class="break"/>
   </g:if>
</g:each>
</body>
</html>