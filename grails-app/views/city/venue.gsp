<div id="venueData" style="float: left; width:60%;" >

	<div style="float:left;">
		<h1>${fsVenue.name}</h1> 	
	</div>
	<div style="float:left; margin-top:10px; margin-left:5px;">
		(${fsVenue.stats.checkinsCount} checkins)
	</div>	
	<div style="clear:both;">
		${fsVenue.categories?.name[0]} (${fsVenue.location?.address}) <a href="">Ver en mapa</a>
	</div>
	
	<!-- Fotos: {fsVenue.photos.count} <br> -->	
	<div class="venuePlace">
		<ul>
		<g:each in="${fsVenue.photos.groups[1].items}" var="photo">
			<li style="display: block; float: left;"><img width="300" height="300" src="${photo.sizes.items[1].url}"></li>
		</g:each>			
		</ul>
	</div> 

	<div style="height: 400px; overflow: auto; position: relative;">
		Comentarios (${fsVenue.tips.count}) <br>
		<table>
		<g:each in="${fsVenue.tips.groups?.items[0]}" var="tip">
			<tr>
				<td><img width="48" height="48" src="${tip.user.photo}"></td>
				<td> <div class="tweet-screen-name">${tip.user.firstName} ${tip.user.lastName}</div>
					${tip.text}
				</td>
		<!-- <td>{tip.user.firstName} {tip.user.lastName}</td>  -->		
			</tr>
		
		</g:each>
		</table>
	</div>
	
	<div>Mayor: ${fsVenue.mayor.user.firstName} ${fsVenue.mayor.user.lastName}
	</div>	
	<div><img width="48" height="48" src="${fsVenue.mayor.user.photo}">
	</div>	

</div>