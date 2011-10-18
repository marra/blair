package blair

class FoursquareSearchResult {

	def String id2
	def String name
	def String contact
	def location = [:]
	def categories = [:]
	def String verified
	def stats = [:]
	def hereNow = [:]

	def String toString(){
		return name
	}
}
