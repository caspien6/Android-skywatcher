package hu.bme.aut.skywatcher.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import hu.bme.aut.skywatcher.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flavienlaurent.discrollview.lib.DiscrollView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.PictureoftheDay;
import hu.bme.aut.skywatcher.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    DiscrollView discrollView;
    TextView title;
    TextView description;
    PictureoftheDay pic_of_day;
    ImageView picture;

    boolean isImageFitToScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_main, container, false);


        discrollView = (DiscrollView) v.findViewById(R.id.discroll_view);
        title = (TextView) v.findViewById(R.id.pod_title);
        title.setFocusableInTouchMode(true);
        description = (TextView) v.findViewById(R.id.pod_description);
        picture = (ImageView) v.findViewById(R.id.picture_of_the_day);

        //picture.setFocusableInTouchMode(true);



        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.HOUR,-6);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        pic_of_day = new Gson().fromJson(this.getActivity().getPreferences(Context.MODE_PRIVATE).getString(sf.format(currentTime.getTime()), null), PictureoftheDay.class);

        if (pic_of_day == null) {
            load_picture_data();
            Toast.makeText(MainFragment.this.getActivity(),
                    "Nem talalta a mait",
                    Toast.LENGTH_SHORT).show();
        }
        else displayPictureData(pic_of_day);


        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void load_picture_data(){
        NetworkManager.getInstance().getPicture().enqueue(new Callback<PictureoftheDay>() {
            @Override
            public void onResponse(Call<PictureoftheDay> call,
                                   Response<PictureoftheDay> response) {
                Toast.makeText(MainFragment.this.getActivity(),
                        "Erseewffweefeweror: "+response.message(),
                        Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    displayPictureData(response.body());
                } else {
                    Toast.makeText(MainFragment.this.getActivity(),
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PictureoftheDay> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainFragment.this.getActivity(),
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPictureData(PictureoftheDay body) {
        pic_of_day = body;

        Glide.with(MainFragment.this.getActivity()).asBitmap()
                .load(body.getUrl())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        picture.setImageBitmap(resource);
                    }
                });
        //discrollView.addView(picture);
        title.setText(pic_of_day.getTitle());
        description.setText(pic_of_day.getExplanation());

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = this.getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(pic_of_day.getDate(), new Gson().toJson(pic_of_day));
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (pic_of_day == null){
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.HOUR,-6);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            pic_of_day = new Gson().fromJson(this.getActivity().getPreferences(Context.MODE_PRIVATE).getString(sf.format(currentTime.getTime()), null), PictureoftheDay.class);
            if (pic_of_day != null)displayPictureData(pic_of_day);

        }
    }

}
