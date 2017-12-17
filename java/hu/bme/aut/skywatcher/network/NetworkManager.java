package hu.bme.aut.skywatcher.network;


import hu.bme.aut.skywatcher.model.PictureoftheDay;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class NetworkManager {

    private static final String ENDPOINT_ADDRESS = "https://api.nasa.gov";
    private static final String APP_ID = "SZB0PcRIyi4mgKBHKIkXCBsfXyH0EX9nfblt2Qjt";

    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private Retrofit retrofit;
    private PoDApi pictureapi;

    private NetworkManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pictureapi = retrofit.create(PoDApi.class);
    }

    public Call<PictureoftheDay> getPicture(String date) {
        return pictureapi.getPicture(APP_ID, date);
    }

}