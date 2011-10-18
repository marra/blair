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
	<META NAME=”title” CONTENT=”${thisCity.name} en Argentina IN”>
	<META NAME=”description” CONTENT=””>
	<title>${thisCity.name} en Argentina IN</title>
</head>
<body>
     
     
    <div class="menu-top">
		<ul>
			<li>
           		<strong> <link:principal>${thisCity.state.country.name}</link:principal></strong> &gt;
           		<strong> <link:state state="${thisCity.state.name.replace(' ','-')}">${thisCity.state}</link:state></strong> &gt;
           		<g:if test='${thisCity.parentCity != null}'>
           			<strong> <link:city state="${thisCity.state.name.replace(' ','-')}" 
           						city="${thisCity.parentCity.name.replace(' ','-')}">${thisCity.parentCity.name}</link:city></strong> &gt;
           		</g:if>
           		<strong>${thisCity.name}</strong>
           	</li>
		</ul>
	</div>
	
	
    <div class="header-main">    
    	<g:render template="/layouts/header_place" model="${[thisPlace:thisCity.name]}" />
    	<g:render template="/layouts/header_weather" model="${[weather:weather]}" />
	</div>
	
	
	<div class="main">
	
    	<g:render template="/layouts/titles" />
    	
    	<div style="float: left; width:70%">
    		<g:render template="/layouts/mostCheckinVenues" model="${[topVenues:topVenues, thisPlace:thisCity, where:where]}" />
		
			<div id="venueData" style="float: left; width:60%">test
			</div>
		</div>  
		
		<g:render template="/layouts/tweets" model="${[tweets:tweets]}" />
		
		
		<div style="clear:both;">			
			<div class="foursquare" style="float:left; width:40%;"> 
				<ul class="places">
					<g:set var="counter" value="${0}" />
					<g:each in="${categoryPlacesMap}" var="categoryPlaces">
					<li> 
					 
						<span>${categoryPlaces.key}</span> 
						<ul>
							<g:each in="${categoryPlaces.value}" var="place">	
							<li style="overflow:auto;" title="${place.name.replace("'"," ")}">
								<a href="javascript:viewInMap(${counter}, '${place.id2}');"><place:placeName placeName="${place.name}" /> (${place.stats.checkinsCount} <place:checkIns count="${place.stats.checkinsCount}" />) </a>
								<g:set var="counter" value="${counter + 1}" />
							</li> 
							</g:each>
						</ul>
					</li> 
					</g:each>
 				</ul>
 			</div> 
 		 		
			<div id="map_canvas" style="width:55%; height:500">
			</div>

		</div>    
			    
	</div>
	
    <g:if test="${childrenCities != null}">
    <div style="clear:both;">
		<ul>
		<g:each in="${childrenCities}" var="city">
			<li><link:natural state="${city.state.name.replace(' ','-')}" 
           					  county="${city.parentCity.name.replace(' ','-')}"
           					  city="${city.name.replace(' ','-')}">${city.name}</link:natural></li>
		</g:each>
    	</ul>
    </div>
    </g:if>
    
    <div style="clear:both;">
		<ul>
		<g:each in="${hereNowList}" var="hereNow">
			<li>${hereNow.name} (${hereNow.hereNow.count})</li>
		</g:each>
    	</ul>
    </div>
    
	<g:render template="/layouts/footer" />	

	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
	<script src="<g:createLinkTo dir='js' file='chico-min-0.7.0.js' />"></script>
	<script type="text/javascript" src="http://www.panoramio.com/wapi/wapi.js?v=1&h1=es"></script>	
	<script src="<g:createLinkTo dir='js' file='main.js' />"></script>
	<script>

	$(".places").accordion({
	    selected: 1
	});

	
	</script>
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script>
	  var locations = []
	  var infowindow = new google.maps.InfoWindow();
	  var map;
	  
	  initMap();
	  
	  function initMap() {
	    var latlng = new google.maps.LatLng(${thisCity.latitude},${thisCity.longitude});
	    var myOptions = {
	      zoom: 15,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.<g:if test="${thisCity.mapType != null}">${thisCity.mapType}</g:if><g:else>ROADMAP</g:else>,
	      streetViewControl: false
	    };
	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	    locations = [
	         	    <g:each in="${categoryPlacesMap}" var="categoryPlaces"> 
	         	   		<g:each in="${categoryPlaces.value}" var="place">	
	         	   			['${categoryPlaces.key} ${place.name.replace("'"," ")} (${place.stats.checkinsCount} check-ins) ${place.location.address?.replace("'"," ")}',
	         	   			 ${place.location.lat},${place.location.lng}],
	         	    	</g:each>
	         	    </g:each>
	      []];

	    var marker, i;

	    for (i = 0; i < locations.length - 1; i++) {  
	      marker = new google.maps.Marker({
	        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
	        map: map
	      });

	      locations[i][3] = marker;

	      google.maps.event.addListener(marker, 'click', (function(marker, i) {
	        return function() {
	          infowindow.setContent(locations[i][0]);
	          infowindow.open(map, marker);
	        }
	      })(marker, i));
	    }
	      
	  }
	  
	  function viewInMap(id, venueId){
			var mapMarker = locations[id][3]
			google.maps.event.trigger(locations[id][3], "click");
			  $.get("/blair/venue/"+venueId, function(data){
				  infowindow.setContent(data);
			  });			
		  };

	function viewVenue(venueId){
		  $('#venueData').html('<center><img src="/blair/images/loading.gif"></center>');
		  $.get("/blair/venue/"+venueId, function(data){
			  $('#venueData').replaceWith(data);
			  var venuePlace = $(".venuePlace").carousel();
		  });				
		  
		  

		}
	
		  
	</script>
		
    </body>
</html>
