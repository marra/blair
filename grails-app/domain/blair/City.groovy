package blair

class City {

	String name
	State state
	String isLeaf
	String latitude
	String longitude
	String woeid
	String isTouristicPlace
	City parentCity
	String mapType
	
	static hasMany = [cityCoordinates : CityCoordinate]
	
    static constraints = {
		isLeaf(nullable: true)
		latitude(nullable: true)
		longitude(nullable: true)
		parentCity(nullable: true)
		isTouristicPlace(nullable: true)
		woeid(nullable: true)
		mapType(nullable: true)
    }
	
	def String toString(){
		return name
	}
}
