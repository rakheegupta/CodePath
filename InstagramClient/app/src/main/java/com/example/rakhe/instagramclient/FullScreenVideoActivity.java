package com.example.rakhe.instagramclient;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.MediaController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class FullScreenVideoActivity extends AppCompatActivity {
    VideoView mVVVideo;
    String mVideoURL;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        mVVVideo=(VideoView)findViewById(R.id.vvVideo);
        mVideoURL=getIntent().getStringExtra("url");

        // Create a progressbar
        pDialog = new ProgressDialog(this);
        // Set progressbar title
        pDialog.setTitle("Playing Video");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(mVVVideo);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(mVideoURL);
            mVVVideo.setMediaController(mediacontroller);
            mVVVideo.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        mVVVideo.requestFocus();
        mVVVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                mVVVideo.start();
            }
        });
    }
}
