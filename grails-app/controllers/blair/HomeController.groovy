package blair

class HomeController {

    def index = { }
	def cityService
	
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
	
}
