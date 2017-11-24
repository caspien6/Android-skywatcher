package hu.bme.aut.skywatcher.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.SearchedPictures;
import hu.bme.aut.skywatcher.network.NetworkManager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    EditText editText;
    Button searchTextBtn;

    SearchedPictures searchedPictures;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_search, container, false);



        editText = (EditText) v.findViewById(R.id.image_search_text);
        searchTextBtn = (Button) v.findViewById(R.id.searchTextBtn);
        searchTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_picture_data();
            }
        });




        return v;
    }

    private void load_picture_data(){
        NetworkManager2.getInstance().getImageByName(editText.getText().toString()).enqueue(new Callback<SearchedPictures>() {
            @Override
            public void onResponse(Call<SearchedPictures> call,
                                   Response<SearchedPictures> response) {
                Toast.makeText(SearchFragment.this.getActivity(),
                        "Rakeresett: "+response.message(),
                        Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    displayPictureData(response.body());
                } else {
                    Toast.makeText(SearchFragment.this.getActivity(),
                            "Error: "+response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchedPictures> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SearchFragment.this.getActivity(),
                        "Error in network request, check LOG",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPictureData(SearchedPictures body) {
        searchedPictures = body;
        Bundle bundle = new Bundle();
        bundle.putSerializable("serialized_images", searchedPictures);
        Intent intent = new Intent(getActivity(), PictureResultsActivity.class);
        intent.putExtras(bundle);


        startActivity(intent);
        //searchedPictures = body;
    }


}
