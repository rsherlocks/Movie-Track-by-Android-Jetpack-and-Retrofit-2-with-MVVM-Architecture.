package com.example.androidshaper.movietrack.Jetpack;

import android.app.Application;
import android.app.ProgressDialog;
import android.util.Log;

import com.example.androidshaper.movietrack.UiMain.ProgressDialogClass;
import com.example.androidshaper.movietrack.ResrofitService.OurRetrofitClient;
import com.example.androidshaper.movietrack.ResrofitService.RetrofitInstance;
import com.example.androidshaper.movietrack.RetrofitModal.MainObject;
import com.example.androidshaper.movietrack.RetrofitModal.ResultObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRepositories {
    private ArrayList<ResultObject> arrayListMovies=new ArrayList<>();
    private MutableLiveData<List<ResultObject>> listMutableLiveData=new MutableLiveData<>();
    private Application application;
    OurRetrofitClient ourRetrofitClient;
    ProgressDialog progressDialog;

    public ViewRepositories(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<ResultObject>> getListMutableLiveData() {
              progressDialog=new ProgressDialog(ProgressDialogClass.getContext());
              progressDialog.setMessage("Loooding...");
              progressDialog.show();
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/movie/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ourRetrofitClient=retrofit.create(OurRetrofitClient.class);
        ourRetrofitClient= RetrofitInstance.getService();
        Call<MainObject> call=ourRetrofitClient.getObject("The Movie Db Api Key");
        call.enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                if (response.isSuccessful())
                {
                    Log.d("Page", "onResponse: "+String.valueOf(response.body().getPage()));
                    Log.d("TotalPage", "onResponse: "+String.valueOf(response.body().getTotal_pages()));
                    Log.d("TotalResult", "onResponse: "+String.valueOf(response.body().getTotal_results()));
                    MainObject mainObject=response.body();
                    if(mainObject!=null && mainObject.getResults()!=null)
                    {
                        arrayListMovies=(ArrayList<ResultObject>) mainObject.getResults();
                        listMutableLiveData.setValue(arrayListMovies);
                    }



                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {

                Log.d("Error Message", "onFailure: "+t.getMessage());
                progressDialog.dismiss();

            }
        });
        return listMutableLiveData;
    }
}
