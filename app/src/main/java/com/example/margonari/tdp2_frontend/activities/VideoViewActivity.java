package com.example.margonari.tdp2_frontend.activities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.margonari.tdp2_frontend.R;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class VideoViewActivity extends Activity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    private DownloadManager downloadManager;
    public String filenameManager;

    // Insert your Video URL
    String VideoURL;
    //Language
    String Language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_video_view);

        Intent intent = getIntent();

        VideoURL = intent.getStringExtra("VIDEO_URL");
        Language = intent.getStringExtra("IDIOMA");

        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(VideoViewActivity.this);
        // Set progressbar title
        pDialog.setTitle(intent.getStringExtra("VIDEO_NAME"));
        // Set progressbar message
        pDialog.setMessage("Cargando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoViewActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
            //videoview.addSubtitleSource(getResources().openRawResource(R.raw.sample),
            //        MediaFormat.createSubtitleFormat("text/vtt", Locale.ENGLISH.getLanguage()));

            String sdcard = Environment.getExternalStorageDirectory().getPath();
            String url = "";
            if (Objects.equals(Language, "0")){
                url = sdcard + "/download/test_esp.vtt";
            } else if (Language.equals("1")) {
                url = sdcard + "/download/test_eng.vtt";
            }

            System.out.println("La ruta es: " + url);

            File f_path = new File(url);
            InputStream  fis = null;
            try {
                fis = new BufferedInputStream(new FileInputStream(f_path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (!Language.equals("2")) {
                videoview.addSubtitleSource(fis, MediaFormat.createSubtitleFormat("text/vtt", Locale.ENGLISH.getLanguage()));
            }


        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

    }

    public void downloadFile(String urlFile, final String filename){

        Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();
        filenameManager= String.valueOf(filename);
        Log.d("FILENAME", filenameManager);

        this.downloadManager = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        String url = urlFile ;
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle(filename )
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        filename)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Log.i("Download1", String.valueOf(request));
        final long q= this.downloadManager.enqueue(request);


        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(q);
                    downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
                                    filenameManager);
                            Log.d("FILENAME", filenameManager);

                            Uri path = Uri.fromFile(file);
                            Log.i("Fragment2", String.valueOf(file.getAbsolutePath()));
                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            String ext1 = FilenameUtils.getExtension(filenameManager); // returns "txt"

                            pdfOpenintent.setDataAndType(path, "application/"+ext1);
                            try {
                                context.startActivity(pdfOpenintent);
                            } catch (ActivityNotFoundException e) {

                            }
                        }
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    @Override
    public void onBackPressed()
    {
        videoview.stopPlayback();
        finish();
    }


}
