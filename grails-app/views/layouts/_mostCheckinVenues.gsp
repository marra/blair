<div id="mostCheckins" style="width:40%; float: left;">
	<div>
		<g:if test="${where != 'city'}">
			<h2><span class="typo"><img height="15" width="15" title="Foursquare" src="http://imgs.sfgate.com/graphics/news/breakingalert/foursquare_icon.gif"> Lo más checkineado en ${thisPlace}</span></h2>
		</g:if>
		<p>
			<span>
				<a href="javascript:viewMostCheckins('${where}','${thisPlace.id}','ANY_TIME');"> <place:whenText whenParam="${when}" when="ANY_TIME"/></a> |			  
				<a href="javascript:viewMostCheckins('${where}','${thisPlace.id}','NOW');"> <place:whenText whenParam="${when}" when="NOW"/></a> |
				<a href="javascript:viewMostCheckins('${where}','${thisPlace.id}','TODAY');"> <place:whenText whenParam="${when}" when="TODAY"/></a> |
				<a href="javascript:viewMostCheckins('${where}','${thisPlace.id}','THIS_MONTH');"> <place:whenText whenParam="${when}" when="THIS_MONTH"/></a> 
			</span>
		</p>
		<br>
		
		<div id="listMostCheckins">
    		<ul>
			<g:each in="${topVenues}" var="venue"> 	
	   			<li>* <a href="javascript:viewVenue('${venue.venueId}');">${venue.name}</a> | 
	   			<g:if test="${where != 'city'}">
	   				<link:city state="${venue.state.name.replace(' ','-')}" city="${venue.city.name.replace(' ','-')}">${venue.city.name}</link:city> | 
	   			</g:if>	
	   			<place:counterCheckins when="${when}" venue="${venue}"/> </li>
	    	</g:each>
	    	</ul>
	    </div>
    </div>	    		
</div>	  
