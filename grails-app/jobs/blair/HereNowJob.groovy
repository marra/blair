package blair


class HereNowJob {
	static triggers = {
		cron name: 'myTrigger', cronExpression: "0 58 * * * ?"
	}

    def execute() {
        println "Inicio"
		
		def controller = new CityController()
		controller.updateVenues()
		println "Fin"
    }
}
