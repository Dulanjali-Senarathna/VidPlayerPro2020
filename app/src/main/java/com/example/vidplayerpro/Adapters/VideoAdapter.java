package com.example.vidplayerpro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vidplayerpro.Models.VideoModel;
import com.example.vidplayerpro.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder>
{
    Context context;
    ArrayList<VideoModel> arrayListVideos;
    public VideoAdapter(Context context, ArrayList<VideoModel> arrayListVideos)
    {
        this.context = context;
        this.arrayListVideos = arrayListVideos;
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos,parent,false);
        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder holder, int position) {
        Glide.with(context).load(arrayListVideos.get(position).getVideo_thumb()).into(holder.image);

        holder.name.setText(arrayListVideos.get(position).getVideo_name() + "."+arrayListVideos.get(position).getVideo_extension());

        holder.duration.setText(arrayListVideos.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return arrayListVideos.size();
    }

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView name,duration;

        public VideoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.vid_image);
            name = (TextView)itemView.findViewById(R.id.vid_name);
            duration = (TextView)itemView.findViewById(R.id.vid_duration);
        }
    }
}
