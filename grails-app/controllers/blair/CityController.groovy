package blair

import groovy.sql.Sql
import java.text.SimpleDateFormat

class CityController {
	
	def cityService
	def scaffold = true

	def showCity = {
		State state = State.findByName(params.state.replace("-"," "))
		City city
		
		if(params.county){
			City county = City.findByNameAndState(params.county?.replace("-"," "), state)
			city = City.find("from City as c where c.name = ? and c.state = ? and c.parentCity = ?", 
							 [params.city.replace("-"," "),state,county])
		}else{
			city = City.findByNameAndState(params.city.replace("-"," "), state,[cache:true])	
		}

		def childrenCities
		if("N".equals(city.isLeaf)){
			childrenCities = City.findAllByParentCity(city)
		}		
		def cityName = city.name.replace(" ","+")
		def twitterResults = cityService.getTwitterSearch(cityName)
		def foursquareResults = cityService.getFoursquareVenuesByCateg(city)
		def hereNowResults = cityService.getHereNow(city)
		def topVenues = cityService.getTopVenues(Constants.ANY_TIME,city)
		def yahooWeatherResults = cityService.getYahooWeather(city.woeid)

		def model = [tweets: twitterResults, categoryPlacesMap:foursquareResults,
					 weather:yahooWeatherResults, thisCity:city, childrenCities:childrenCities, 
					 hereNowList:hereNowResults,topVenues:topVenues,where:"city"]

		render (view:"city", model:model)
	}
		
	def showCounty = {
		def city = City.findByName(params.city.replace("-"," "),[cache:true])
		if("Y".equals(city.isLeaf)){
			forward(controller:"touristicPlace",action:"show",params:[place:params.city]) 	
		}else{
			def allPlaces = TouristicPlace.findAllByCity(city)
			def model = [city:city, allPlaces:allPlaces]

			render (view:"showCounty", model:model)
		}	
	}
		
	def home = {
		
		def country = Country.findByName("Argentina") //TODO obtener esto de la URL
		def allStates = State.getAll().sort{it.name}
		def topVenues = cityService.getTopVenues(Constants.NOW,country)
		def touristicPlaces = City.findAllByIsTouristicPlace("Y")
		Collections.shuffle(touristicPlaces)
		
		def model = [allStates:allStates, topVenues:topVenues, touristicPlaces:touristicPlaces.getAt(0..9), thisCountry:country,
					 where:"country"]

		render (view:"home", model:model)
	}
	
	
	def venue = {
		
		def fsVenue = cityService.getFoursquareVenue(params.venue_id)
		
		def model = [fsVenue:fsVenue]

		render (view:"venue", model:model)
		
	}
	
	def topVenues = {
		def place
		
		if("country".equals(params.where)){
			place = Country.findById(params.object_id)
		}else if("state".equals(params.where)){
			place = State.findById(params.object_id)
		}else if("city".equals(params.where)){
			place = City.findById(params.object_id)
		}
		
		
		def topVenues = cityService.getTopVenues(params.when, place)
		
		def model = [topVenues:topVenues, thisPlace:place, when:params.when, where:params.where]

		render (view:"/layouts/_mostCheckinVenues", model:model)
		
	}
	
	
	def yGeoCode = {
		
		def cities = City.getAll()
		
		cities.each{
			if(it.woeid == null){
				def yahooGeoCode = cityService.getYahooGeoCode(it.name.replace(" ","+"))
				it.setWoeid(yahooGeoCode.woeid)
				it.save(failOnError:true)
			}
		}
		render "DONE!"
	}
	
	def gGeoCode = {
	
		def cities = City.getAll()
		def cityName
		def parentCityName
		def stateName
		def countryName
		def googleGeoCode
		
		cities.each{
			if(it.latitude == null ){
				cityName = it.name.replace(" ","+")
				parentCityName = it.parentCity != null ? it.parentCity.name.replace(" ","+") : ""
				stateName = it.state.name.replace(" ","+")
				countryName = it.state.country.name
			
				googleGeoCode = cityService.getGoogleGeoCode(cityName + "+" + parentCityName + "+" +stateName + "+" + countryName)
				it.setLatitude(googleGeoCode.geometry.location.lat[0].toString())
				it.setLongitude(googleGeoCode.geometry.location.lng[0].toString())
				it.save(failOnError:true)
			}
		}
		render "DONE!"
	}
	
	def gGeoCodeStates = {
		
		def state = State.getAll()
		def stateName
		def countryName
		def googleGeoCode
			
		state.each{
			stateName = it.name.replace(" ","+")
			countryName = it.country.name.replace(" ","+")
				
			googleGeoCode = cityService.getGoogleGeoCode("Provincia+de+"+stateName+"+"+countryName)
			it.setLatitude(googleGeoCode.geometry.location.lat[0].toString())
			it.setLongitude(googleGeoCode.geometry.location.lng[0].toString())
			it.save(failOnError:true)
		}
		render "DONE!"
	}
	
	
//	def wikiContent = {
//		
//		def places = TouristicPlace.getAll()
//		def wikiPlaceName
//		def wikipediaResults
//		
//		places.each{
//			wikiPlaceName = it.name.replace(" ","_")
//			wikipediaResults = touristicPlaceService.getWikipediaData(wikiPlaceName, it.wikiPath)
//		
//			if(wikipediaResults != null){
//				it.setWikiContent(wikipediaResults)
//				it.save(failOnError:true)
//			
//			}
//			render "DONE!"
//		}
//	}
	
	
	def cacheStatus = {
		
		springcacheService.getSpringcacheCacheManager().cacheNames.each{
			render springcacheService.getSpringcacheCacheManager().getEhcache(it).statistics
		}
	}
	
	def updateVenues = { 
		
		def foursquareResults
		def allCities = City.getAll()
		City city
		String category
		int aux
		
		def date = new Date()
		def formatYear = new SimpleDateFormat("yyyy")  
		def year = new Integer(formatYear.format(date))
		def formatMonth = new SimpleDateFormat("MM")
		def month = new Integer(formatMonth.format(date))
		def formatDay = new SimpleDateFormat("dd")
		def day = new Integer(formatDay.format(date))
		
		allCities.each{
			city = it
			foursquareResults = cityService.getFoursquareSearch(it.latitude, it.longitude)
			
			foursquareResults.each{
				category = it.key
				it.value.each{
					
					VenueData venueData = VenueData.findByVenueId(it.id2)
					if(venueData == null){
						venueData = new VenueData()
					}
					
					venueData.setCountry(city.state.country)
					venueData.setState(city.state)
					venueData.setCity(city)
					venueData.setVenueId(it.id2)
					venueData.setName(it.name)
					venueData.setCategory(category)
					venueData.setCategId(it.categories.id[0])
					venueData.setParentCateg(it.categories.parents[0][0])
					venueData.setCheckinsCount(it.stats.checkinsCount)
					venueData.setUsersCount(it.stats.usersCount)
					venueData.setHereNow(it.hereNow.count)
					venueData.save(failOnError:true)
					
					if(it.hereNow.count > 0){
						VenueStats venueStats = new VenueStats()
						venueStats.setCity(city)
						venueStats.setCountry(city.state.country)
						venueStats.setState(city.state)
						venueStats.setVenueId(it.id2)
						venueStats.setName(it.name)
						venueStats.setHereNow(it.hereNow.count)
						venueStats.setInsertDt(new Date())
						venueStats.save(failOnError:true)
						
						
						VenueStatsDay venueStatsDay = VenueStatsDay.find("from VenueStatsDay as vsd where vsd.venueId = ? and vsd.day = day(sysdate) and vsd.month = month(sysdate) and vsd.year = year(sysdate)",[it.id2])
						if(venueStatsDay == null){
							venueStatsDay = new VenueStatsDay()
							venueStatsDay.setCity(city)
							venueStatsDay.setCountry(city.state.country)
							venueStatsDay.setState(city.state)
							venueStatsDay.setVenueId(it.id2)
							venueStatsDay.setName(it.name)
							venueStatsDay.setHereNow(it.hereNow.count)
							venueStatsDay.setDay(day)
							venueStatsDay.setMonth(month)
							venueStatsDay.setYear(year)
						}else{
							aux = venueStatsDay.getHereNow()
							venueStatsDay.setHereNow(it.hereNow.count + aux)
						}
						
						venueStatsDay.save(failOnError:true)
						
						
						VenueStatsMonth venueStatsMonth = VenueStatsMonth.find("from VenueStatsMonth as vsd where vsd.venueId = ? and vsd.month = month(sysdate) and vsd.year = year(sysdate)",[it.id2])
						if(venueStatsMonth == null){
							venueStatsMonth = new VenueStatsMonth()
							venueStatsMonth.setCity(city)
							venueStatsMonth.setCountry(city.state.country)
							venueStatsMonth.setState(city.state)
							venueStatsMonth.setVenueId(it.id2)
							venueStatsMonth.setName(it.name)
							venueStatsMonth.setHereNow(it.hereNow.count)
							venueStatsMonth.setMonth(month)
							venueStatsMonth.setYear(year)
						}else{
							aux = venueStatsMonth.getHereNow()
							venueStatsMonth.setHereNow(it.hereNow.count + aux)
						}
						
						venueStatsMonth.save(failOnError:true)
						
						
						
					}
				}
			}
		}
		
		render "DONE!"
		
	}
	
	/*
	def test = {
		
		if (Country.findByName('Argentina')?){
			render "Existe!!"
		}else{
			  Country country = new Country()
			  country.setName('Argentina')
			  country.save()
			  render "DONE!"
		}

	 
	}*/
}
