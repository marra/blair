package blair

import grails.plugin.springcache.SpringcacheService
import grails.plugin.springcache.annotations.Cacheable
import groovyx.net.http.HTTPBuilder
import net.sf.ehcache.Element

class CityService {

	SpringcacheService springcacheService	
	
    def getTwitterSearch = { query ->
		
		def searchResult = []
		def element 
		
		try {
			element = springcacheService.getSpringcacheCacheManager().getEhcache("twitterCache").get(query)
			
			if (element == null){

				def http = new HTTPBuilder('http://search.twitter.com')
 
				http.get( path : '/search.json', query : [q:"${query}"] ){ resp, json ->
			
			
					json.results.each {
						TwitterSearchResult tweet = new TwitterSearchResult()
						tweet.from_user_id_str = it.from_user_id_str
						tweet.profile_image_url = it.profile_image_url
						tweet.created_at = it.created_at
						tweet.from_user = it.from_user
						tweet.id_str = it.id_str
						tweet.metadata = it.metadata
						tweet.to_user_id = it.to_user_id
						tweet.text = it.text
						tweet.id = it.id
						tweet.from_user_id = it.from_user_id
						tweet.geo = it.geo
						tweet.iso_language_code = it.iso_language_code
						tweet.to_user_id_str = it.to_user_id_str
						tweet.source = it.source
	
						searchResult.add(tweet)
					}
				}
			
				springcacheService.getSpringcacheCacheManager().getEhcache("twitterCache").put(new Element(query,searchResult))
			}else{
				searchResult = element.getObjectValue()
			}
		}catch(Exception e){}
			return searchResult
	}
	

	def getFoursquareSearch = { latitude,longitude ->
		
		def result = [:]
		def element
		def categ
		try {
			element = springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").get(latitude+longitude)

			if (element == null){				
				def http = new HTTPBuilder('https://api.foursquare.com')
				http.setHeaders(['Accept-Language' : 'es'])
				http.get( path : '/v2/venues/search', query : [ll:"${latitude},${longitude}",
														   client_id:"RNSVQL1KTKUVSNSOICRRJWV2NZZJOQ4QTC2HN4KQAMYSJ5EL", 
														   client_secret:"P2HHPL5Q4U2GEEWZLEOVUFFFSBP5NXHRSMNDSHUR5I24KIVK",
														   v:"20110705"] ){ resp, json ->
			

				  json.response.venues.each {
				
						FoursquareSearchResult fsquare = new FoursquareSearchResult()
						fsquare.id2 = it.id
						fsquare.name = it.name
						fsquare.contact = it.contact
						fsquare.location.address = it.location.address
						fsquare.location.crossStreet = it.location.crossStreet
						fsquare.location.city = it.location.cityString 
						fsquare.location.state = it.location.state
						fsquare.location.lat = it.location.lat
						fsquare.location.lng = it.location.lng
						fsquare.location.distance = it.location.distance
						fsquare.categories = it.categories
						fsquare.verified = it.verified
						fsquare.stats.checkinsCount = it.stats.checkinsCount
						fsquare.stats.usersCount = it.stats.usersCount
						fsquare.hereNow.count = it.hereNow.count
				
						if (fsquare.categories.size()>0){
							if(fsquare.categories.parents[0]){
								categ = fsquare.categories.parents[0][0]
							}else{
								categ = "Otros"
							}
							
							categ = translateCateg(categ)
						
							if(result.get(categ) == null){
								result.put(categ,[fsquare])
							}else{
								result.get(categ).add(fsquare)
							}
						}		
					}
				}
				
				springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").put(new Element(latitude+longitude,result))
			}else{
				result = element.getObjectValue()
			}
		
		}
		catch(Exception e){
			println e
			}
		
		result.each{
			def a = it.value
			it.value = a.sort{it.stats.checkinsCount}.reverse()
		}
		
		return result.sort{it.key}
	}
	
	def getFoursquareVenuesByCateg = { city ->
		
		def result = [:]
		def categ
		def foursquareResults = getFoursquareVenues(city)
		
		foursquareResults.each {
			
			if (it.categories.size()>0){
				if(it.categories.parents[0]){
					categ = it.categories.parents[0][0]
				}else{
					categ = "Otros"
				}
				
				categ = translateCateg(categ)
			
				if(result.get(categ) == null){
					result.put(categ,[it])
				}else{
					result.get(categ).add(it)
				}
			}
		}
		
		result.each{
			def a = it.value
			it.value = a.sort{it.stats.checkinsCount}.reverse()
		}
		
		return result.sort{it.key}
		 
	}
	
	def getFoursquareVenues = { city ->

		def element
		def foursquareResults
		
		element = springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").get(city.id)
		
		if (element == null){
			foursquareResults = getFoursquareSearchV2(city.latitude, city.longitude)
			city.cityCoordinates?.each{
				def aux = getFoursquareSearchV2(it.latitude, it.longitude)
				foursquareResults.addAll(foursquareResults.size(), aux)
			}
			foursquareResults = foursquareResults.unique { a, b -> a.id2.equals(b.id2)?0:1}
			springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").put(
												new Element(city.id,foursquareResults))
		}else{
			foursquareResults = element.getObjectValue()
		}
					
		return foursquareResults
	}
	
	def getHereNow = { city ->
		def foursquareResults = getFoursquareVenues(city)		
		def hereNow = []
		
		foursquareResults.each{
			if(it.hereNow.count > 0){
				hereNow.push(it)
			}			
		}
		
		return hereNow
	}
	
	def getFoursquareSearchV2 = { latitude,longitude ->
		
		def result = []
		def element
		def categ
		try {
			//element = springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").get(latitude+longitude)

			//if (element == null){
				def http = new HTTPBuilder('https://api.foursquare.com')
				http.setHeaders(['Accept-Language' : 'es'])
				http.get( path : '/v2/venues/search', query : [ll:"${latitude},${longitude}",
														   client_id:"RNSVQL1KTKUVSNSOICRRJWV2NZZJOQ4QTC2HN4KQAMYSJ5EL",
														   client_secret:"P2HHPL5Q4U2GEEWZLEOVUFFFSBP5NXHRSMNDSHUR5I24KIVK",
														   v:"20110705"] ){ resp, json ->
			

				  json.response.venues.each {
				
						FoursquareSearchResult fsquare = new FoursquareSearchResult()
						fsquare.id2 = it.id
						fsquare.name = it.name
						fsquare.contact = it.contact
						fsquare.location.address = it.location.address
						fsquare.location.crossStreet = it.location.crossStreet
						fsquare.location.city = it.location.cityString
						fsquare.location.state = it.location.state
						fsquare.location.lat = it.location.lat
						fsquare.location.lng = it.location.lng
						fsquare.location.distance = it.location.distance
						fsquare.categories = it.categories
						fsquare.verified = it.verified
						fsquare.stats.checkinsCount = it.stats.checkinsCount
						fsquare.stats.usersCount = it.stats.usersCount
						fsquare.hereNow.count = it.hereNow.count
				
						if (fsquare.categories.size()>0){
							result.add(fsquare)
						}
					}
				}
				
				//springcacheService.getSpringcacheCacheManager().getEhcache("foursquareCache").put(new Element(latitude+longitude,result))
			//}else{
				//result = element.getObjectValue()
			//}
		
		}
		catch(Exception e){
			println e
			}
		
		
		return result
	}
	
	
	
	def translateCateg = { categ ->
		if("Arts & Entertainment".equals(categ))
			return "Arte y Entretenimientos"
		else if("Colleges & Universities".equals(categ))
			return "Educación"
		else if("Food".equals(categ))
			return "Restaurantes y Comidas" 
		else if("Great Outdoors".equals(categ))
			return "Deportes y Aire Libre"
		else if("Home, Work, Others".equals(categ))
			return "Oficinas, Casas y Otros"
		else if("Nightlife Spots".equals(categ))
			return "Bares y Boliches"
		else if("Shops & Services".equals(categ))
			return "Comercios y Servicios"
		else if("Travel Spots".equals(categ))
			return "Transportes y Turismo"
		else 
			return "Oficinas y Otros"
	}
	
	def getFoursquareVenue = { venueId ->
		
		
		FoursquareVenueResult fsVenue = new FoursquareVenueResult()
		def element
		try {
			
			element = springcacheService.getSpringcacheCacheManager().getEhcache("foursquareVenueCache").get(venueId)
			
			if (element == null){
				
				def http = new HTTPBuilder('https://api.foursquare.com')
				
				http.setHeaders(['Accept-Language' : 'es'])
				
				http.get( path : "/v2/venues/${venueId}", query : [client_id:"RNSVQL1KTKUVSNSOICRRJWV2NZZJOQ4QTC2HN4KQAMYSJ5EL",
														   client_secret:"P2HHPL5Q4U2GEEWZLEOVUFFFSBP5NXHRSMNDSHUR5I24KIVK",
														   v:"20110705"] ){ resp, json ->
				
				

					fsVenue.name = json.response.venue.name
					fsVenue.contact = json.response.venue.contact
					fsVenue.location = json.response.venue.location
					fsVenue.categories = json.response.venue.categories
					fsVenue.verified = json.response.venue.verified
					fsVenue.stats = json.response.venue.stats
					fsVenue.url = json.response.venue.url
					fsVenue.hereNow = json.response.venue.hereNow
					fsVenue.mayor = json.response.venue.mayor
					fsVenue.tips = json.response.venue.tips
					fsVenue.tags = json.response.venue.tags
					fsVenue.specials = json.response.venue.specials
					fsVenue.specialsNearby = json.response.venue.specialsNearby
					fsVenue.shortUrl = json.response.venue.shortUrl
					fsVenue.timeZone = json.response.venue.timeZone
					fsVenue.photos = json.response.venue.photos
					fsVenue.description = json.response.venue.description
				
				}
				
				springcacheService.getSpringcacheCacheManager().getEhcache("foursquareVenueCache").put(new Element(venueId,fsVenue))
			}else{
				fsVenue = element.getObjectValue()
			}
		
		}
		catch(Exception e){
			println e
			}
		
		return fsVenue
	}
	
	
	def getGoogleImages = { query ->
		
		def searchResult = []
		
		try {
			
			def http = new HTTPBuilder('https://ajax.googleapis.com')
 
			http.get( path : '/ajax/services/search/images', query : [v:"1.0",q:"${query}", rsz:"8"] ){ resp, json ->
			

			json.responseData.results.each {
				
				GoogleImageResult gImg = new GoogleImageResult()
				gImg.GsearchResultClass = it.GsearchResultClass
				gImg.width = it.width
				gImg.height = it.height
				gImg.imageId = it.imageId
				gImg.tbWidth = it.tbWidth
				gImg.tbHeight = it.tbHeight
				gImg.unescapedUrl = it.unescapedUrl
				gImg.url = it.url
				gImg.visibleUrl = it.visibleUrl
				gImg.title = it.title
				gImg.titleNoFormatting = it.titleNoFormatting
				gImg.originalContextUrl = it.originalContextUrl
				gImg.content = it.content
				gImg.contentNoFormatting = it.contentNoFormatting
				gImg.tbUrl = it.tbUrl

				searchResult.add(gImg)
				}
			}
		}
		catch(Exception e){
			}
		
		return searchResult
	}
	
	
	def getYouTubeVideos = { query ->
		
		def searchResult = []
		
		try {
			
			def http = new HTTPBuilder('http://gdata.youtube.com')
 
			http.get( path : '/feeds/api/videos', query : [vq:"${query}", alt:"json"] ){ resp, json ->
			
			json.feed.entry.each {
				
				YouTubeVideoResult ytVideo = new YouTubeVideoResult()
				ytVideo.published = it.published
				ytVideo.updated = it.updated
				ytVideo.category = it.category
				ytVideo.title = it.title
				ytVideo.content = it.content
				ytVideo.link = it.link
				ytVideo.author = it.author
				ytVideo.gdComments = it.gd$comments
				ytVideo.mediaGroup = it.media$group
				ytVideo.gdRating = it.gd$rating
				ytVideo.ytStatistics = it.yt$statistics

				searchResult.add(ytVideo)
				}
			}
		}
		catch(Exception e){
			}
		
		return searchResult
	}
	
	
	def getYahooGeoCode = { query ->
	
		YahooGeoCodeResult geoCode = new YahooGeoCodeResult()
		
		try {
			
			def http = new HTTPBuilder('http://where.yahooapis.com')
 
			http.get( path : '/geocode',  query:[location:"${query}"] ){ resp, xml ->
			
				geoCode.latitude = xml.Result.latitude
				geoCode.longitude = xml.Result.longitude
				geoCode.woeid = xml.Result.woeid
				
			}
			
		} catch(Exception e){
			println e
		}
		return geoCode
    }
	
	def getYahooWeather = { woeid ->
		
		YahooWeatherResult weather = new YahooWeatherResult()
		def element
		try {
			element = springcacheService.getSpringcacheCacheManager().getEhcache("weatherCache").get(woeid)
			if (element == null){
				def http = new HTTPBuilder('http://weather.yahooapis.com')
 
				http.get( path : '/forecastrss',  query:[w:"${woeid}", u:"c"] ){ resp, xml ->
							

					weather.lastBuildDate = xml.channel.lastBuildDate
					weather.temparatureUnit = xml.channel.units.@temperature
					weather.distanceUnit = xml.channel.units.@distance
					weather.pressureUnit = xml.channel.units.@pressure
					weather.speedUnit = xml.channel.units.@speed			
					weather.windChill = xml.channel.wind.@chill
					weather.windDirection = xml.channel.wind.@direction
					weather.windSpeed = xml.channel.wind.@speed			
					weather.atmosphereHumidity = xml.channel.atmosphere.@humidity
					weather.atmosphereVisibility = xml.channel.atmosphere.@visibility
					weather.atmospherePressure = xml.channel.atmosphere.@pressure
					weather.atmosphereRising = xml.channel.atmosphere.@rising			
					weather.astronomySunrise = xml.channel.astronomy.@sunrise
					weather.astronomySunset = xml.channel.astronomy.@sunset			
					weather.latitude = xml.channel.item.lat
					weather.conditionText = xml.channel.item.condition.@text
					weather.conditionCode = xml.channel.item.condition.@code
					weather.conditionTemp = xml.channel.item.condition.@temp
					weather.forecastDay1 = xml.channel.item.forecast[0].@day			
					weather.forecastDay2 = xml.channel.item.forecast[1].@day
					weather.forecastDate1 = xml.channel.item.forecast[0].@date
					weather.forecastDate2 = xml.channel.item.forecast[1].@date
					weather.forecastLow1 = xml.channel.item.forecast[0].@low
					weather.forecastLow2 = xml.channel.item.forecast[1].@low
					weather.forecastHigh1 = xml.channel.item.forecast[0].@high
					weather.forecastHigh2 = xml.channel.item.forecast[1].@high
					weather.forecastText1 = xml.channel.item.forecast[0].@text
					weather.forecastText2 = xml.channel.item.forecast[1].@text
					weather.forecastCode1 = xml.channel.item.forecast[0].@code
					weather.forecastCode2 = xml.channel.item.forecast[1].@code
			
				}
				springcacheService.getSpringcacheCacheManager().getEhcache("weatherCache").put(new Element(woeid,weather))
			}else{
				weather = element.getObjectValue()
			}
			
		}
		catch(Exception e){
			println e
			}
		
		return weather
	}
	
	
	
	def getWikipediaData = { wikiPlaceName, wikiPath ->
		
		String searchResult = new String()
		String wPath = wikiPath != null? wikiPath : wikiPlaceName 
		try {
			def http = new HTTPBuilder('http://es.wikipedia.org')
			http.get( path : '/wiki/'+wPath, query : [a:"a"], 
				headers : ['User-Agent':'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)'] ){ resp, xml ->

				xml.BODY.DIV[2].DIV[2].P.each {
					searchResult = searchResult + it.text()
					searchResult = searchResult + "<br>"

				}
			}
		}
		catch(Exception e){
			println e
			}
		
		return searchResult
	}
	
	
	def getGoogleGeoCode = { query ->
		
			GoogleGeoCodeResult geoCode = new GoogleGeoCodeResult()
			
			try {
				
				def http = new HTTPBuilder('http://maps.google.com/')
			
				http.get( path : '/maps/api/geocode/json',  query:[address:"${query}",sensor:"false",region:"ar"] ){ resp, json ->
				
					geoCode.address_components = json.results.address_components[0]
					geoCode.formatted_address = json.results.formatted_address
					geoCode.geometry = json.results.geometry
					geoCode.partial_match = json.results.partial_match
					geoCode.types = json.results.types
					
				}
				
			} catch(Exception e){
				println e.getStackTrace() 
			}
			return geoCode
		}
	
	def getTopVenues = { when, object ->
		
		def topVenues
		
		if(Constants.NOW.equals(when)){
			if(object instanceof Country){
				topVenues = VenueData.findAll("from VenueData as v where v.hereNow > 0 and v.country = ?",[object]).sort{it.hereNow}.reverse()
			}else if(object instanceof State){
				topVenues = VenueData.findAll("from VenueData as v where v.hereNow > 0 and v.state = ?",[object]).sort{it.hereNow}.reverse()			
			}else if(object instanceof City){
				topVenues = VenueData.findAll("from VenueData as v where v.hereNow > 0 and v.city = ?",[object]).sort{it.hereNow}.reverse()
			}			
		}else if ("TODAY".equals(when)){
			if(object instanceof Country){
				topVenues = VenueStatsDay.findAll("from VenueStatsDay as vsd where vsd.hereNow > 0 and vsd.country = ? and vsd.day = day(sysdate) and vsd.month = month(sysdate) and vsd.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}else if(object instanceof State){
				topVenues = VenueStatsDay.findAll("from VenueStatsDay as vsd where vsd.hereNow > 0 and vsd.state = ? and vsd.day = day(sysdate) and vsd.month = month(sysdate) and vsd.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}else if(object instanceof City){
				topVenues = VenueStatsDay.findAll("from VenueStatsDay as vsd where vsd.hereNow > 0 and vsd.city = ? and vsd.day = day(sysdate) and vsd.month = month(sysdate) and vsd.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}
		}else if ("THIS_MONTH".equals(when)){
			if(object instanceof Country){
				topVenues = VenueStatsDay.findAll("from VenueStatsMonth as vsm where vsm.hereNow > 0 and vsm.country = ? and vsm.month = month(sysdate) and vsm.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}else if(object instanceof State){
				topVenues = VenueStatsDay.findAll("from VenueStatsMonth as vsm where vsm.hereNow > 0 and vsm.state = ? and vsm.month = month(sysdate) and vsm.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}else if(object instanceof City){
				topVenues = VenueStatsDay.findAll("from VenueStatsMonth as vsm where vsm.hereNow > 0 and vsm.city = ? and vsm.month = month(sysdate) and vsm.year = year(sysdate)",[object]).sort{it.hereNow}.reverse()
			}
		}else if ("ANY_TIME".equals(when)){
			if(object instanceof Country){
				topVenues = VenueData.findAllByCountry(object).sort{it.checkinsCount}.reverse()
			}else if(object instanceof State){
				topVenues = VenueData.findAllByState(object).sort{it.checkinsCount}.reverse()
			}else if(object instanceof City){
				topVenues = VenueData.findAllByCity(object).sort{it.checkinsCount}.reverse()
			}
		}
		
		if(topVenues?.size() > 10){
			topVenues = topVenues.getAt(0..9)
		}
		
	}
}
