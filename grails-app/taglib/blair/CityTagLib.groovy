package blair

class CityTagLib {
	static namespace = "place"
	
	def placeName = { attrs -> 
		String name
		
		if (attrs.placeName.length() > 22)
			name = attrs.placeName.substring(0,22)+"..."
		else
			name = attrs.placeName
		
		out << name
		
	}
	
	def checkIns = { attrs ->
		
		String checkIns
		if(attrs.count < 2)
			checkIns = "check-in"
		else
			checkIns = "check-ins"
		
		out << checkIns 
	}
	
	def counterCheckins = { attrs ->
		String counterCheckins
		
		if(Constants.ANY_TIME.equals(attrs.when) || attrs.when == null){
			counterCheckins = attrs.venue.checkinsCount
		}else{ 
			counterCheckins = attrs.venue.hereNow		
		}
		
		out << 	counterCheckins
	}
	
	def whenText = { attrs -> 
		String whenText
		
		if(Constants.NOW.equals(attrs.when)){
			if(attrs.whenParam.equals(attrs.when)){
				whenText = "<strong>Ahora</strong>"
			}else{
				whenText = "Ahora"
			}
		}else if(Constants.TODAY.equals(attrs.when)){
			if(attrs.whenParam.equals(attrs.when)){
				whenText = "<strong>Hoy</strong>"
			}else{
				whenText = "Hoy"
			}
		}else if(Constants.THIS_MONTH.equals(attrs.when)){
			if(attrs.whenParam.equals(attrs.when)){
				whenText = "<strong>Este mes</strong>"
			}else{
				whenText = "Este mes"
			}
		}else if(Constants.ANY_TIME.equals(attrs.when)){
			if(attrs.whenParam.equals(attrs.when) || attrs.whenParam == null){
				whenText = "<strong>En todo momento</strong>"
			}else{
				whenText = "En todo momento"
			}
		}
		
		out << whenText
	}
		
}
