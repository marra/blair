package blair

class StateController {
	def scaffold = true
	def cityService
	
    def index = { }
	
	def show = {
		String stateName = params.state.replace("-"," ") 
		State state = State.findByName(stateName,[cache:true])
		def allCities = City.findAllByStateAndParentCity(state,null).sort{it.name}
		def twitterResults = cityService.getTwitterSearch(stateName)
		def topVenues = cityService.getTopVenues(Constants.NOW,state)
		//topVenues = topVenues.size()>10?topVenues.getAt(0..9):topVenues
		def model = [state:state, allCities:allCities, tweets:twitterResults, topVenues:topVenues, where:"state"]

		render (view:"state", model:model)	
	}
}
