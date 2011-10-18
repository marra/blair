<html>
<!--[if lt IE 7 ]> <html lang="es" class="no-js ie6"> <![endif]--> 
<!--[if IE 7 ]>    <html lang="es" class="no-js ie7"> <![endif]--><!--[if IE 8 ]>    <html lang="es" class="no-js ie8"> <![endif]--> 
<!--[if IE 9 ]>    <html lang="es" class="no-js ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> <html lang="es" class="no-js"> <!--<![endif]-->
<head>
	<link rel="stylesheet" type="text/css" href="<g:createLinkTo dir='css' file='chico-min-0.7.0.css' />">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />	
	<meta name="layout" content="main" /> 	
	<meta name=“Language“ CONTENT="Spanish">
	<meta http-equiv=”Content-Language” content=”es-ES”>	
	<META NAME=”description” CONTENT=””>	
	<title>${state.name} en Argentina IN</title>
</head>
<body>

	<div class="menu-top">
		<ul>
			<li>
           		<strong> <link:principal>${state.country.name}</link:principal></strong> &gt;
           		<strong> <link:state state="${state.name.replace(' ','-')}">${state.name}</link:state></strong>
           	</li>
		</ul>
	</div>
	
	<div class="header-main">    
    	<g:render template="/layouts/header_place" model="${[thisPlace:state.name]}" />
    	<g:render template="/layouts/header_weather" model="${[weather:null]}" />
	</div>

	<div class="main">
    	<g:render template="/layouts/titles" />
    	<div style="float:left; width:70%;">
    		<div style="float:left; width:40%;">
    			<ul>
				<g:each in="${allCities}" var="city"> 	
		   			<li><link:city state="${state.name.replace(' ','-')}" city="${city.name.replace(' ','-')}">${city.name}</link:city></li>
				</g:each>
				</ul>
    		</div>

			<g:render template="/layouts/mostCheckinVenues" model="${[topVenues:topVenues, thisPlace:state, where:where]}" />
		</div>
		<g:render template="/layouts/tweets" model="${[tweets:tweets]}" />
		
	</div>
	
			<g:render template="/layouts/footer" />

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script src="<g:createLinkTo dir='js' file='markerclusterer_compiled.js' />"></script>
	<script src="<g:createLinkTo dir='js' file='main.js' />"></script>
		
</body>
</html>
