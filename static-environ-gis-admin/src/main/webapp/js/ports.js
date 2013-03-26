var map;
var marker;
var geocoder;

function placeMarker(location) {
	 if (!marker) {
		  marker = new google.maps.Marker({
		      position: location,
		      map: map
		  });
	  } else {
		  marker.setPosition(location);
	  }
	  map.setCenter(location);
	  var field = document.getElementById("portCreate:coordinate");
	  field.value = "POINT (" + location.lat() + " " + location.lng() + ")";
}

function initialize() {
	var mapOptions = {
		center : new google.maps.LatLng(55.761123, 13.084717),
		zoom : 5,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(
			document.getElementById("map"), mapOptions);
	
	google.maps.event.addListener(map, 'click', function(event) {
		placeMarker(event.latLng);
	});
	
	setMarker(document.getElementById("portCreate:coordinate"));
	
	geocoder = new google.maps.Geocoder();
}

function setMarker(field) {
	if (field.value) {
		var regex = /POINT.+?([-0-9\.]+).+?([-0-9\.]+)/;
		var matches = regex.exec(field.value);
		placeMarker(new google.maps.LatLng(Number(matches[1]), Number(matches[2])));
	}
}

function codeAddress(address) {
    geocoder.geocode( { 'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        map.setCenter(results[0].geometry.location);
      } else {
        alert("Geocode was not successful for the following reason: " + status + "\nFor address " + address);
      }
    });
  }

function loadScript() {
	  var script = document.createElement("script");
	  script.type = "text/javascript";
	  script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyDW-fpm8XuSMsoOh4iByXEmQDVDTrJFkCY&sensor=false&callback=initialize";
	  document.body.appendChild(script);
}

window.onload = loadScript;
			