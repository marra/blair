class UrlMappings {

	static excludes = ['/css/*','/images/*', '/js/*', '/favicon.ico']
	
	static mappings = {

		/*"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}*/
		
		name principal: "/" {
			controller="city"
			action="home"
		}
		
		
		name natural: "/$state?/$county?/$city?" {
			controller= "city"
			action="showCity"
		}
		
		name state: "/$state?" {
			controller= "state"
			action="show"
		}
		
		name city: "/$state?/$city?" {
			controller= "city"
			action="showCity"
		}
		
		name YGeoCode: "/YGeoCode/" {
			controller= "city"
			action="yGeoCode"
		}

		name GGeoCode: "/GGeoCode/" {
			controller= "city"
			action="gGeoCode"
		}

		name GGeoCode: "/GGeoCodeStates/" {
			controller= "city"
			action="gGeoCodeStates"
		}
		
		name wiki: "/WikiContent/" {
			controller= "city"
			action="wikiContent"
		}
		
		name DbUtil: "/DbUtil/" {
			controller= "dbUtil"
			action="data"
		}

		name DbUtil: "/DbUtil/sql" {
			controller= "dbUtil"
			action="sql"
		}

		name test: "/cacheStatus/" {
			controller= "city"
			action="cacheStatus"
		}

		name updateVenues: "/updateVenues/" {
			controller= "city"
			action="updateVenues"
		}
		
		name venue: "/venue/$venue_id" {
			controller= "city"
			action="venue"
		}
		
		name topVenues: "/topVenues/$where/$object_id/$when" {
			controller= "city"
			action="topVenues"
		}
		
		name test: "/test/" {
			controller= "city"
			action="test"
		}
		
		"500"(view:'/error')
	}
}
