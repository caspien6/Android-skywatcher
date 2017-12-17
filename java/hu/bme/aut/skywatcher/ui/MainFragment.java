package hu.bme.aut.skywatcher.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.Datum;
import hu.bme.aut.skywatcher.model.Item;
import hu.bme.aut.skywatcher.model.Link;
import hu.bme.aut.skywatcher.model.PictureoftheDay;
import hu.bme.aut.skywatcher.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment implements FullScreenInterface{

    public MainFragment() {
        // Required empty public constructor
    }


    TextView title;
    TextView description;
    PictureoftheDay pic_of_day;
    ImageView picture;
    AVLoadingIndicatorView progressbar;
    Animation fade_in, fade_out,rotation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);
        title = (TextView) v.findViewById(R.id.pod_title);
        title.setFocusableInTouchMode(true);
        description = (TextView) v.findViewById(R.id.pod_description);
        picture = (ImageView) v.findViewById(R.id.picture_of_the_day);
        progressbar = (AVLoadingIndicatorView) v.findViewById(R.id.loadingpicture);
        fade_in = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),R.anim.fade_in);
        fade_out= AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),R.anim.fade_out);
        rotation = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),R.anim.rotation);

        title.setAnimation(fade_in);
        description.setAnimation(fade_in);
        picture.setAnimation(fade_in);
        progressbar.setAnimation(fade_out);


        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.HOUR,-6);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        pic_of_day = new Gson().fromJson(this.getActivity().getPreferences(Context.MODE_PRIVATE).getString(sf.format(currentTime.getTime()), null), PictureoftheDay.class);

        if (pic_of_day == null) {
            startAnim();
            load_picture_data(sf.format(currentTime.getTime()));
        }
        else {
            startAnim();
            displayPictureData(pic_of_day);
        }
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pic_of_day != null){
                    showInFullscreen();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_window, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*rotation = AnimationUtils.loadAnimation(this.getActivity().getApplicationContext(),R.anim.rotation);
        item.getActionView().startAnimation(rotation);
*/
        if (id == R.id.deleteAllBtn) {

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void load_picture_data(String date){
        NetworkManager.getInstance().getPicture(date).enqueue(new Callback<PictureoftheDay>() {
            @Override
            public void onResponse(Call<PictureoftheDay> call,
                                   Response<PictureoftheDay> response) {
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
                        R.string.error_in_network,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAnim(){
        //progressbar.show();
        progressbar.smoothToShow();
        title.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
        picture.setVisibility(View.GONE);
    }

    private void stopAnim(){
        //progressbar.hide();
        progressbar.smoothToHide();
        title.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        picture.setVisibility(View.VISIBLE);
    }

    private void displayPictureData(PictureoftheDay body) {
        pic_of_day = body;
        if (!pic_of_day.getMediaType().equals("video")){
            Glide.with(MainFragment.this.getActivity()).asBitmap()
                    .load(body.getUrl())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            stopAnim();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            stopAnim();
                            return false;
                        }
                    })
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            picture.setImageBitmap(resource);
                        }
                    });
            title.setText(pic_of_day.getTitle());
            description.setText(pic_of_day.getExplanation());

        }
        else{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //Mentes, mivel az adatunk legyen meg, mert ha lefut ujra a create view akkor nem fog kelleni letölteni a mostanit...
            SharedPreferences.Editor editor = this.getActivity().getPreferences(Context.MODE_PRIVATE).edit();
            if (pic_of_day != null){
                editor.putString(pic_of_day.getDate(), new Gson().toJson(pic_of_day));
                editor.commit();
            }
            try {
                Date thisday = sf.parse(pic_of_day.getDate());
                Calendar c = Calendar.getInstance();
                c.setTime(thisday);
                c.add(Calendar.DATE, -1);
                thisday = c.getTime();
                pic_of_day = null;
                pic_of_day = new Gson().fromJson(this.getActivity().getPreferences(Context.MODE_PRIVATE).getString(sf.format(thisday.getTime()), null), PictureoftheDay.class);
                if (pic_of_day == null) {
                    load_picture_data(sf.format(thisday.getTime()));
                }
                else displayPictureData(pic_of_day);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = this.getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        if (pic_of_day != null){
            editor.putString(pic_of_day.getDate(), new Gson().toJson(pic_of_day));
            editor.commit();
        }

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

    @Override
    public void showInFullscreen() {
        Bundle bundle = new Bundle();
        Item it = new Item();
        List<Datum> data = new ArrayList<Datum>();
        Datum d = new Datum();
        d.setNasaId(pic_of_day.getTitle().replace(" ", ""));

        List<Link> links =new  ArrayList<Link>();
        Link l = new Link();l.setHref(pic_of_day.getHdurl());
        data.add(d);
        links.add(l);
        it.setData(data);
        it.setLinks(links);
        bundle.putSerializable(getString(R.string.serialize_item), it);

        Intent intent = new Intent(getActivity(), FullscreenActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
