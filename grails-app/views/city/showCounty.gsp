<html>
<head>
	<link rel="stylesheet" type="text/css" href="<g:createLinkTo dir='css' file='chico-min-0.7.0.css' />">
</head>
<body>
	<div class="menu-top">
		<ul>
			<li>
           		<strong> <link:principal>Argentina</link:principal></strong> &gt;
           		<strong> <link:state state="${city.state.name.replace(' ','-')}">${city.state.name}</link:state></strong> &gt;
           		<strong> <link:city city="${city.name.replace(' ','-')}"
           						state="${city.state.name.replace(' ','-')}">${city.name}</link:city></strong>
           	</li>
		</ul>
	</div>

	<h2><span class="typo">Localidades</span></h2>
	<ul>
	<g:each in="${allPlaces}" var="place"> 	
	   	<li><link:natural state="${place.state.name.replace(' ','-')}" 
	   		city="${city.name.replace(' ','-')}" place="${place.name.replace(' ','-')}">${place.name}</link:natural></li>
	</g:each>
	</ul>
</body>
</html>
