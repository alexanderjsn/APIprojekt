package org.example;

import java.util.List;

public class weatherAPI {

    // skapar en lista för Väder - då i JSON filen är det en lista
    private List<Weather> weather;

    public weatherAPI(List<weatherAPI.Weather> weather) {
        weather = weather;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public static class Weather {
        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
// Getters och setters
    }
}