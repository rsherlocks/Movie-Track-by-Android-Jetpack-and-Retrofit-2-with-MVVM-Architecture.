package com.example.androidshaper.movietrack.MyAdapterView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidshaper.movietrack.UiMain.MovieDetails;
import com.example.androidshaper.movietrack.R;
import com.example.androidshaper.movietrack.RetrofitModal.ResultObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//    public List<ResultObjectView> list;
   public List<ResultObject> list;
    public Context context;

    public MyAdapter(List<ResultObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


                final ResultObject resultObject=list.get(position);
//                Toast.makeText(context,String.valueOf(list.size()),Toast.LENGTH_SHORT).show();
//                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
//            Toast.makeText(context,String.valueOf(resultObjectView.getTitle()),Toast.LENGTH_SHORT).show();

           holder.textViewMovie.setText(String.valueOf(resultObject.getTitle()));
            String imageUrl="https://image.tmdb.org/t/p/w500"+resultObject.getPoster_path();
            Picasso.get().load(imageUrl).into(holder.imageViewMovie);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(context, MovieDetails.class);

                    intent.putExtra("MovieTitle", resultObject.getTitle());
                    intent.putExtra("MovieImage", "https://image.tmdb.org/t/p/w500"+resultObject.getBackdrop_path());
                    intent.putExtra("MovieDescription",resultObject.getOverview());
                    intent.putExtra("MovieDate",resultObject.getRelease_date());
                    intent.putExtra("MovieLanguage",resultObject.getOriginal_language());
                    intent.putExtra("MovieAdult",String.valueOf(resultObject.getAdult()));
                    intent.putExtra("MovieVideo",String.valueOf(resultObject.getVideo()));
                    intent.putExtra("MovieVote",String.valueOf(resultObject.getVote_count()));
                    intent.putExtra("MovieRating",String.valueOf(resultObject.getVote_average()));

                    context.startActivity(intent);

                }
            });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
