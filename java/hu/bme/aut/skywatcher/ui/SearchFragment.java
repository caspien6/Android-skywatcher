package hu.bme.aut.skywatcher.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    OnSearchButtonClickedListener listenerActivity;
    EditText editText;
    Button searchTextBtn;
    SearchedPictures searchedPictures;

    public interface OnSearchButtonClickedListener {
        public void onSearchConditionSelected(SearchedPictures item);
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listenerActivity = (OnSearchButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        editText = (EditText) v.findViewById(R.id.image_search_text);
        searchTextBtn = (Button) v.findViewById(R.id.searchTextBtn);
        searchTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().matches("")){
                    load_picture_data();
                }
                else{
                    Toast.makeText(SearchFragment.this.getActivity(),
                            R.string.no_word,Toast.LENGTH_SHORT).show();
                }

            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (i)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            if (i == KeyEvent.KEYCODE_ENTER){
                                if(!editText.getText().toString().matches("")){
                                    load_picture_data();
                                }
                                else{
                                    Toast.makeText(SearchFragment.this.getActivity(),
                                            R.string.no_word,Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.telescope, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.telescopebtn) {

        }
        return super.onOptionsItemSelected(item);
    }

    private void load_picture_data(){
        NetworkManager2.getInstance().getImageByName(editText.getText().toString()).enqueue(new Callback<SearchedPictures>() {
            @Override
            public void onResponse(Call<SearchedPictures> call,
                                   Response<SearchedPictures> response) {
                if (response.isSuccessful()) {
                    if(response.body().getCollection().getMetadata().getTotalHits() != 0){
                        displayPictureData(response.body());
                    }
                    else{
                        Toast.makeText(SearchFragment.this.getActivity(),
                                R.string.no_result,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchFragment.this.getActivity(),
                            "Response unsuccesful",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchedPictures> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SearchFragment.this.getActivity(),
                        "Error in network request.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void displayPictureData(SearchedPictures body) {
        searchedPictures = body;
        searchedPictures.setSearchedName(editText.getText().toString());
        listenerActivity.onSearchConditionSelected(searchedPictures);
        Bundle bundle = new Bundle();
        bundle.putSerializable(this.getActivity().getApplicationContext().getString(R.string.serialized_images), searchedPictures);
        Intent intent = new Intent(getActivity(), PictureResultsActivity.class);
        intent.putExtras(bundle);


        startActivity(intent);
        //searchedPictures = body;
    }


}
