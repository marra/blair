		function prettyLinks(){
			  var spans = document.getElementsByTagName("a");
			  for ( var i = 0; i < spans.length; i++ ){ 
				  if ( spans[i].title ) {
				      var date = prettyDate(spans[i].title);
				      
				      if ( date ){
				        spans[i].innerHTML = date;
				      }
				  }
			  }
			}
			
			prettyLinks();
			setInterval(prettyLinks, 1000);

			function prettyDate(time){
				var date = new Date((time || "")),
					diff = (((new Date()).getTime() - date.getTime()) / 1000),
					day_diff = Math.floor(diff / 86400);
				
				if ( isNaN(day_diff) || day_diff < 0 || day_diff >= 31 )
					return;
				
				return day_diff == 0 && (
						diff < 60 && "menos de 1 minuto" ||
						diff < 120 && "hace 1 minuto" ||
						diff < 3600 && "hace " + Math.floor( diff / 60 ) + " minutos" ||
						diff < 7200 && "hace 1 hora" ||
						diff < 86400 && "hace "+  Math.floor( diff / 3600 ) + " horas") ||
					day_diff == 1 && "ayer" ||
					day_diff < 7 && "hace " + day_diff + " días" ||
					day_diff < 31 && "hace " + Math.ceil( day_diff / 7 ) + " semanas";
			}

			
			function viewMostCheckins(where,id,when){
				  $('#listMostCheckins').html('<center><img src="/blair/images/loading.gif"></center>');
				  $.get("/blair/topVenues/"+where+"/"+id+"/"+when, function(data){
					  $('#mostCheckins').replaceWith(data);
				  });	
				  
				}
