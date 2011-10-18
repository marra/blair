		<div class="header-right">
			<div class="header-right-weather">				
				<div class="heather-right-weather-text">
					<ul>
						<li style="text-decoration:underline;">Ahora:</li>
						<li>${weather?.conditionTemp}°${weather?.temparatureUnit}</li>
					</ul> 
				</div>
				<div class="heather-right-weather-image">
					<img src="http://l.yimg.com/a/i/us/we/52/${weather?.conditionCode}.gif">
				</div>
				<div class="heather-right-weather-text">
					<ul>
						<li style="text-decoration:underline;">Hoy:</li>
						<li>Min: ${weather?.forecastLow1}°${weather?.temparatureUnit} </li>
						<li>Max: ${weather?.forecastHigh1}°${weather?.temparatureUnit} </li>
					</ul> 
				</div>								
				<div class="heather-right-weather-image">
					<img src="http://l.yimg.com/a/i/us/we/52/${weather?.forecastCode1}.gif">
				</div>
				<div class="heather-right-weather-text">
					<ul>
						<li style="text-decoration:underline;">Mañana:</li>
						<li>Min: ${weather?.forecastLow2}°${weather?.temparatureUnit} </li>
						<li>Max: ${weather?.forecastHigh2}°${weather?.temparatureUnit} </li>
					</ul> 
				</div>								
				<div style="float:left;">
					<img src="http://l.yimg.com/a/i/us/we/52/${weather?.forecastCode2}.gif">
				</div>
			</div>
		</div>