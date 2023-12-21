package org.example;

import java.util.List;

public class weatherAPI {

    private List<Weather> Weather;

    public List<weatherAPI.Weather> getWeather() {
        return Weather;
    }

    public void setWeather(List<weatherAPI.Weather> weather) {
        Weather = weather;
    }

    public static class Weather{
        String main;
        String description;

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
    }
}
