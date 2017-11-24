package hu.bme.aut.skywatcher.network;

import java.util.Map;


import hu.bme.aut.skywatcher.model.SearchedPictures;
import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.QueryMap;


public interface ImageSearcherApi {

    @GET("/search")
    Call<SearchedPictures> getPicture(@QueryMap Map<String, String> options);
}
