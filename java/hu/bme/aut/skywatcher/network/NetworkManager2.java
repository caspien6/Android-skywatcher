package hu.bme.aut.skywatcher.network;


import retrofit2.Retrofit;

public class NetworkManager2 {

    private static final String ENDPOINT_ADDRESS = "https://images-api.nasa.gov/";
    private static final String APP_ID = "SZB0PcRIyi4mgKBHKIkXCBsfXyH0EX9nfblt2Qjt";

    private static NetworkManager2 instance;

    public static NetworkManager2 getInstance() {
        if (instance == null) {
            instance = new NetworkManager2();
        }
        return instance;
    }
/*
    private Retrofit retrofit;
    private WeatherApi weatherApi;

    private NetworkManager2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);
    }

    public Call<WeatherData> getWeather(String city) {
        return weatherApi.getWeather(city, "metric", APP_ID);
    }*/
}
