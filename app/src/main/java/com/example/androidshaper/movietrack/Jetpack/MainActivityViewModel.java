package com.example.androidshaper.movietrack.Jetpack;

import android.app.Application;

import com.example.androidshaper.movietrack.RetrofitModal.ResultObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private ViewRepositories viewRepositories;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        viewRepositories=new ViewRepositories(application);
    }
    public LiveData<List<ResultObject>> getAllMovies()
    {
        return viewRepositories.getListMutableLiveData();
    }
    public MutableLiveData<List<ResultObject>> getPopularMovies()
    {
        return viewRepositories.getListMutableLiveData();
    }
}
