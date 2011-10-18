package blair

class State {

	String name
	Country country
	Region region
	String latitude
	String longitude
	String mapZoom
	
	static constraints = {
		mapZoom(nullable: true)
	}
	
	def String toString(){
		return name
	}
}
