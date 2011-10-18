package blair

class VenueData {

	Country country
	State state
    City city
	String venueId
	String name
	String category
	String categId
	String parentCateg
	int checkinsCount
	int usersCount
	int hereNow
		
	def String toString(){
		return name
	}
	
	static constraints = {
		parentCateg(nullable: true)
	}
}
