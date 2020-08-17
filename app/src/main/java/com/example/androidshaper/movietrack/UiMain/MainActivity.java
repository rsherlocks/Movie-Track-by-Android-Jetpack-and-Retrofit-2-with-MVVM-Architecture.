package com.example.androidshaper.movietrack.UiMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.androidshaper.movietrack.Jetpack.MainActivityViewModel;
import com.example.androidshaper.movietrack.MyAdapterView.MyAdapter;
import com.example.androidshaper.movietrack.R;
import com.example.androidshaper.movietrack.ResrofitService.OurRetrofitClient;
import com.example.androidshaper.movietrack.ResrofitService.RetrofitInstance;
import com.example.androidshaper.movietrack.RetrofitModal.MainObject;
import com.example.androidshaper.movietrack.RetrofitModal.ResultObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    OurRetrofitClient ourRetrofitClient;
    MyAdapter myAdapter;
    RecyclerView recyclerViewMovie;
    private ArrayList<ResultObject> movies=new ArrayList<>();
    MainActivityViewModel mainActivityViewModel;
    SwipeRefreshLayout swipeRefreshLayout;





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMovie=findViewById(R.id.movieRecyclerView);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        recyclerViewMovie.setHasFixedSize(true);
        ProgressDialogClass.setContext(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    loadDataByJetpack();



                swipeRefreshLayout.setRefreshing(false);
            }
        });

            loadDataByJetpack();






        //loadData();
    }

    private void loadDataByJetpack() {


        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

//        mainActivityViewModel.getAllMovies().observe(this, new Observer<List<ResultObject>>() {
//            @Override
//            public void onChanged(List<ResultObject> resultObjects) {
//                movies=(ArrayList<ResultObject>) resultObjects;
//                RecyclerViewConfig();
//            }
//        });
        mainActivityViewModel.getPopularMovies().observe(this, new Observer<List<ResultObject>>() {
            @Override
            public void onChanged(List<ResultObject> resultObjects) {
                movies=(ArrayList<ResultObject>) resultObjects;
                RecyclerViewConfig();
            }
        });

    }

    private void RecyclerViewConfig() {
        myAdapter=new MyAdapter(movies,this);

        if (this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),2);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerViewMovie.setLayoutManager(gridLayoutManager);

        }
        else {
            GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),4);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            recyclerViewMovie.setLayoutManager(gridLayoutManager);
        }
        recyclerViewMovie.setAdapter(myAdapter);



    }

    private void loadData() {
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/movie/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ourRetrofitClient=retrofit.create(OurRetrofitClient.class);
        ourRetrofitClient= RetrofitInstance.getService();
        Call<MainObject> call=ourRetrofitClient.getObject("38f781893cc431e2b2c21a872925a0ab");
        call.enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                if (response.isSuccessful())
                {
                    Log.d("Page", "onResponse: "+String.valueOf(response.body().getPage()));
                    Log.d("TotalPage", "onResponse: "+String.valueOf(response.body().getTotal_pages()));
                    Log.d("TotalResult", "onResponse: "+String.valueOf(response.body().getTotal_results()));

                    List<ResultObject> resultObjectList=response.body().getResults();



                    for (ResultObject resultObject : resultObjectList)
                    {

                        Log.d("Movie", "onResponse: "+resultObject.getTitle());
                        Log.d("Movie", "onResponse: "+String.valueOf(resultObject.getAdult()));
                        Log.d("Movie", "onResponse: "+String.valueOf(resultObject.getBackdrop_path()));



                    }



                        myAdapter=new MyAdapter(resultObjectList,MainActivity.this);
                        recyclerViewMovie.setAdapter(myAdapter);


                }


            }

            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {

                Log.d("Error Message", "onFailure: "+t.getMessage());

            }
        });
    }

}