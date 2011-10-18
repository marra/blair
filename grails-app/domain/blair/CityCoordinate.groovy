package blair

class CityCoordinate {

	String latitude
	String longitude
	
	static belongsTo = [city:City]
	
    static constraints = {
    }
}
