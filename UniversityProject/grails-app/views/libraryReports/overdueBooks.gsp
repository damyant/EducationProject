<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 10/6/14
  Time: 1:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">


        <g:uploadForm>


            <table class="university-size-full-1-1 inner spinner">



                <tr>

                    <td class="university-size-1-3">Student ID<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="studentId" class="university-size-1-2" ></td>
                </tr>




                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Submit"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>


</body>
</html>