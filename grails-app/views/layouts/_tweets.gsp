<div style="float:left; width:30%;">    		
	<div class="twitter">			
		<table>
		<g:each in="${tweets}" var="tweet">
			<tr>
				<td>
					<img width="48" height="48" src="${tweet.profile_image_url}">
				</td>
			   	<td>
			   		<p><span class="tweet-screen-name">${tweet.from_user}</p></span>
			   		<p>${tweet.text}</p>
			   		<a title="${tweet.created_at}" href="#"><span>${tweet.created_at}</span></a>
			   	</td>
			</tr>
		</g:each>
		</table>
	</div>    		 	
</div>