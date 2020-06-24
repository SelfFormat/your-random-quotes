package com.example.android.weatherlistwidget;

/**
 * A dummy class that we are going to use internally to store weather data.  Generally, this data
 * will be stored in an external and persistent location (ie. File, Database, SharedPreferences) so
 * that the data can persist if the process is ever killed.  For simplicity, in this sample the
 * data will only be stored in memory.
 */
class WeatherDataPoint {
    String day;
    int degrees;

    WeatherDataPoint(String d, int deg) {
        day = d;
        degrees = deg;
    }
}
