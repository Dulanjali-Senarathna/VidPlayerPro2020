package com.example.vidplayerpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.vidplayerpro.Activities.VideoPlayerActivity;
import com.example.vidplayerpro.Models.VideoModel;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    ArrayList<VideoModel> arrayListVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        arrayListVideos = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        
        displayAllVideos();
    }

    private void displayAllVideos() {
        Uri uri;
        Cursor cursor;
        int column_index_data,thum;

        String absolutePathThumb = null;

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String [] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Video.Media._ID,MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Video.Media.DEFAULT_SORT_ORDER;

        cursor = MainActivity.this.getContentResolver().query(uri,projection,null,null,orderBy);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext())
        {
            absolutePathThumb = cursor.getString(column_index_data);
            Uri thumbUri = Uri.fromFile(new File(absolutePathThumb));
            String cursorThumb = cursor.getString(thum);

            String fileName = FilenameUtils.getBaseName(absolutePathThumb);
            String extension = FilenameUtils.getExtension(absolutePathThumb);

            String duration = getDuration(absolutePathThumb);

            VideoModel videoModel = new VideoModel();
            videoModel.setDuration(duration);
            videoModel.setVideo_uri(thumbUri.toString());
            videoModel.setVideo_path(absolutePathThumb);
            videoModel.setVideo_name(fileName);
            videoModel.setVideo_thumb(cursor.getString(thum));
            if(extension!=null)
            {
                videoModel.setVideo_extension(extension);
            }
            else {
                videoModel.setVideo_extension("mp4");
            }

            if(duration!=null)
            {
                videoModel.setDuration(duration);
            }
            else {
                videoModel.setDuration("00:00");
            }

            arrayListVideos.add(videoModel);

        }

        //set adapter
    }

    private String getDuration(String absolutePathThumb)
    {
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this,Uri.fromFile(new File(absolutePathThumb)));
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInMilliSec = Long.parseLong(time);
            retriever.release();

            return (new SimpleDateFormat("mm:ss")).format(new Date(timeInMilliSec));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
        }

        public void openVideoPlayerActivity(VideoModel videoModel)
        {
            Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
            intent.putExtra("videoUri",videoModel.getVideo_uri());
            startActivity(intent);
        }
}
