package com.durgasamples412.watchit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.durgasamples412.watchit.Adapters.ViewAllAdapter;
import com.durgasamples412.watchit.Networking.ApiClient;
import com.durgasamples412.watchit.Networking.ApiInterface;
import com.durgasamples412.watchit.POJO.Details;
import com.durgasamples412.watchit.POJO.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity {
    RecyclerView favRv;
    DBHelper helper;
    List<Result>movieidlist ;
    ViewAllAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favRv = findViewById(R.id.favrv);
        movieidlist = new ArrayList <>();
        helper = new DBHelper(this);
        movieidlist = helper.getmoviedetailsfromdb();
        adapter = new ViewAllAdapter(movieidlist,this);
        favRv.setLayoutManager(new GridLayoutManager(this,3));
        favRv.addItemDecoration(new SpacesItemDecoration(6));
        favRv.setAdapter(adapter);



    }
}
