package hu.bme.aut.skywatcher.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.HashMap;
import java.util.Map;

import hu.bme.aut.skywatcher.model.SearchedPictures;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager2 {

    private static final String ENDPOINT_ADDRESS = "https://images-api.nasa.gov/";

    private static NetworkManager2 instance;

    public static NetworkManager2 getInstance() {
        if (instance == null) {
            instance = new NetworkManager2();
        }
        return instance;
    }


    private Retrofit retrofit;
    private ImageSearcherApi imageSearcherApiApi;

    private NetworkManager2() {


        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        imageSearcherApiApi = retrofit.create(ImageSearcherApi.class);
    }

    public Call<SearchedPictures> getImageByName(String name) {
        Map<String, String> params = new HashMap<String,String>();
        params.put("media_type", "image");
        params.put("q", name);
        return imageSearcherApiApi.getPicture(params);
    }
}
