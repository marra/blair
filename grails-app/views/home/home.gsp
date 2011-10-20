<html>
<!--[if lt IE 7 ]> <html lang="es" class="no-js ie6"> <![endif]--> 
<!--[if IE 7 ]>    <html lang="es" class="no-js ie7"> <![endif]--><!--[if IE 8 ]>    <html lang="es" class="no-js ie8"> <![endif]--> 
<!--[if IE 9 ]>    <html lang="es" class="no-js ie9"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> <html lang="es" class="no-js"> <!--<![endif]-->
<head>
	<link rel="stylesheet" type="text/css" href="<g:createLinkTo dir='css' file='chico-min-0.7.0.css' />">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<title>Argentina IN</title>
</head>
<body>
	
	<div class="menu-top">
		<div style="float:right;"><a href="">Regístrate</a> | <a href="">Ingresar</a></div>
	</div>
	
	<div class="home-header" style="clear:both;">
		<div style="float:left;width:20%;"> 
			<img src="http://static.mlstatic.com/org-img/chico/img/logo-mercadolibre.png"> 
		</div>
		
		<div style="float:left;width:45%;"> 
			<input type="text" size="60"> <input type="button" value="Buscar">
		</div>
		
		<div style="float:left;width:35%;">
			<div>
				<input type="text" value="Email"> <input type="text" value="Contraseña"> <input type="button" value="Entrar">
			</div>
			<div>
				<input type="checkbox"> No cerrar sesión <a href>¿Olvidaste tu contraseña?</a> 
			</div>
		</div>		
	</div>
	
	<div class="home-main" style="padding:20;">

		<!--<g:render template="/layouts/mostCheckinVenues" model="[topVenues:topVenues, thisPlace:thisCountry, where:where]" />-->
		<div style="float:left;width:60%;"> 
			<div style="font-size: 60px;">Descubre tu ciudad</div>
			<div style="font-size: 20px;">Cosas sorprendentes están sucediendo cerca tuyo. Encuentralas!</div>
			<div>
				<div style="float:left;">Now in Argentina</div>
				<div style="float:left;">Lo que más gusta</div>
			</div>
		</div>   			    		
		
		<div style="width:40%; float: left;">
			<div style="margin-left:70;">
				<h2><span class="typo">Regístrate</span></h2>
				<br>
    			<ul>
					<li> Nombre: <input type="text"> </li>
					<li> Email: <input type="text"> </li>
					<li> Password: <input type="password"> </li>
					<li> Sexo: <select><option>Selecciona el sexo:</option><option>Mujer</option><option>Hombre</option></select></li>
					<li> Fecha de nacimiento:
						<select name="birthday_day" id="birthday_day">
							<option value="-1">Día:</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
						</select> 
						<select name="birthday_month" id="birthday_month"> 
							<option value="-1">Mes:</option>
							<option value="1">enero</option>
							<option value="2">febrero</option>
							<option value="3">marzo</option>
							<option value="4">abril</option>
							<option value="5">mayo</option>
							<option value="6">junio</option>
							<option value="7">julio</option>
							<option value="8">agosto</option>
							<option value="9">septiembre</option>
							<option value="10">octubre</option>
							<option value="11">noviembre</option>
							<option value="12">diciembre</option>
						</select> 
						<select name="birthday_year" id="birthday_year"> 
							<option value="-1">Año:</option>
							<option value="2011">2011</option>
							<option value="2010">2010</option>
							<option value="2009">2009</option>
						</select>
					
					</li>
					<li>
						<input type="button" value="Regístrate">
					</li>
	    		</ul>
    		</div>	   
		</div>
		
	</div>
	
	<g:render template="/layouts/footer" />	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
	<script src="<g:createLinkTo dir='js' file='chico-min-0.7.0.js' />"></script>	
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script src="<g:createLinkTo dir='js' file='markerclusterer_compiled.js' />"></script>
	<script src="<g:createLinkTo dir='js' file='main.js' />"></script>
	
			
</body>
</html>
