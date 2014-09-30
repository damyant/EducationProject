<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv='cache-control' content='no-cache'>
    <meta http-equiv='expires' content='0'>
    <meta http-equiv='pragma' content='no-cache'>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Institute of Science and Technology</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'header.css')}" type="text/css">
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.datepicker.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'newjquery-ui.css')}" type='text/css'>
    <g:javascript library='jquery'/>
    <g:javascript library="application"/>
    <r:layoutResources/>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js/jquery', file: 'newjquery-1.9.1.js')}'></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js/jquery', file: 'newjquery-ui.js')}'></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'admitCard.js')}'></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'status.js')}'></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'base.js')}'></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'validation.js')}'></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'validate.js')}"></script>

    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.ui.datepicker.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js/jquery', file: 'jquery.blockUI.js')}'></script>

 <g:layoutHead/>


</head>

<body style="margin: 0 auto">

<r:layoutResources/>
<div class="divHeader">
    <g:render template="/layouts/header"/>
</div>
%{--TODO HEADER DONE--}%

<div>
    <g:layoutBody/>
</div>

%{--TODO: INTEGRATING FOOTER--}%
<div>
    <g:render template="/layouts/footer"/>
</div>

</body>
</html>
