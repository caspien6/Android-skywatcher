package hu.bme.aut.skywatcher.network;


import hu.bme.aut.skywatcher.model.PictureoftheDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PoDApi {

    @GET("/planetary/apod")
    Call<PictureoftheDay> getPicture(@Query("api_key") String appId);

}
