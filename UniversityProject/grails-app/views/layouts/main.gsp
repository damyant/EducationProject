<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Examination Project</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		%{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">--}%
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'validationEngine.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'header.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}" type="text/css">
        <link rel='stylesheet' href="${resource(dir: 'css', file: 'style_popup.css')}" type='text/css'>
        <link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'>
        <g:javascript library='jquery' />
        <g:javascript library="application"/>
        <r:layoutResources />

        <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'validate.js')}"></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'viewResult.js')}'></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'admitCard.js')}'></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'viewAdmitCard.js')}'></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'editAdmitCard.js')}'></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'deleteAdmitCard.js')}'></script>
        <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'base.js')}'></script>

    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js/jquery', file: 'jquery.js')}'></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'validation-en.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'validation-engine.js')}"></script>

		<g:layoutHead/>


	</head>
	<body style="margin: 0 auto">
		%{--<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>--}%
		%{--<g:layoutBody/>--}%
		%{--<div class="footer" role="contentinfo"></div>--}%
		%{--<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>--}%
		<r:layoutResources />
        %{--HOME BAR ENDS--}%
    <div class="divHeader">
        <g:render template="/layouts/header"/>
    </div>
    %{--TODO HEADER DONE--}%

    <div style="min-height: 100%;">
        <g:layoutBody/>
    </div>

    %{--TODO: INTEGRATING FOOTER--}%
<div>
    %{--<g:render template="/layouts/footer"/>--}%
    </div>


	</body>
</html>
