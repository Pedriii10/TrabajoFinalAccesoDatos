document.getElementById("consultarTiempoBtn").addEventListener("click", function() {
    var fechaInicio = this.getAttribute("data-fechaInicio");
    var ubicacion = this.getAttribute("data-ubicacion");



    var apiKey = "8c72b0285d914027b0891804242702";
    var apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=8c72b0285d914027b0891804242702&q=Elche&dt=2024-02-28&lang=es";

    console.log("dsadsa");
   /* fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            var forecast = data.forecast.forecastday[0].day;

            document.getElementById("maxtemp_c").textContent = forecast.maxtemp_c + "°C";
            document.getElementById("mintemp_c").textContent = forecast.mintemp_c + "°C";
            document.getElementById("condition_text").textContent = forecast.condition.text;
            document.getElementById("condition_icon").src = "https:" + forecast.condition.icon;
        })
        .catch(error => {
            console.error('Error al consultar el tiempo:', error);
        });*/
});
