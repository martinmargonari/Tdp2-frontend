package com.example.margonari.tdp2_frontend.activities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.MaterialAdapter;
import com.example.margonari.tdp2_frontend.adapters.VideoAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Material;
import com.example.margonari.tdp2_frontend.domain.Question;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.domain.Video;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;

public class MyCourseUnitActivity extends AppCompatActivity {

    private TextView nameUnit;
    private TextView unitDescription;
    private ImageView imageUnit;

    private RecyclerView materialRecyclerView;
    private RecyclerView.LayoutManager materialLayoutManager;
    private RecyclerView.Adapter materialAdapter;
    private String api_token;
    private String session_id;
    private UnityInfo unityInfo;

    private ArrayList<Material> materialList;
    private RecyclerView videoRecyclerView;
    private RecyclerView.LayoutManager videoLayoutManager;
    private RecyclerView.Adapter videoAdapter;
    private ArrayList<Video> videosList;
    private static String LOG_TAG = "MyCourseUnitActivity";


    private DownloadManager downloadManager;
    public String filenameManager;
    private long q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_unit);
        Intent intent = getIntent();

        api_token = getIntent().getStringExtra("API_TOKEN");
        session_id = getIntent().getStringExtra("SESSION_ID");


        unityInfo = (UnityInfo)intent.getSerializableExtra("UNITY");

        this.setTitle(unityInfo.getUnity().getName());

        imageUnit = (ImageView) findViewById(R.id.image_unit);
        nameUnit = (TextView) findViewById(R.id.name_unit);
        unitDescription = (TextView) findViewById(R.id.unit_description);

        String urlImage = unityInfo.getUnity().getFull_image();

        Picasso.with(this).load(urlImage).into(imageUnit);
        nameUnit.setText(unityInfo.getUnity().getName());
        unitDescription.setText(unityInfo.getUnity().getDescription());

        materialRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_material);
        materialRecyclerView.setHasFixedSize(true);
        materialLayoutManager = new LinearLayoutManager(this);
        materialRecyclerView.setLayoutManager(materialLayoutManager);
        materialRecyclerView.setFocusable(false);
        materialAdapter = new MaterialAdapter(getDataSetMaterial(), this);
        materialRecyclerView.setAdapter(materialAdapter);
        materialList = getDataSetMaterial();


        videoRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_video);
        videoRecyclerView.setHasFixedSize(true);
        videoLayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(videoLayoutManager);
        videoRecyclerView.setFocusable(false);
        videoAdapter = new VideoAdapter(getDataSetVideos(), this);
        videoRecyclerView.setAdapter(videoAdapter);
        videosList = getDataSetVideos();



    }

    private ArrayList<Material> getDataSetMaterial() {
        //TODO
        ArrayList results = new ArrayList<Material>();
        for (int i = 0; i < unityInfo.getMaterials().length; i++) {
            results.add(unityInfo.getMaterials()[i]);
        }
        results.add(new Material("Examen de la Unidad",Material.EXAMEN));
        return results;
    }

    private ArrayList<Video> getDataSetVideos() {
        //TODO
        ArrayList results = new ArrayList<Video>();
        for (int i = 0; i < unityInfo.getVideos().length; i++) {
            results.add(unityInfo.getVideos()[i]);
        }

        return results;
    }


    @Override
    public void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {

        super.onResume();

        ((MaterialAdapter) materialAdapter).setOnItemClickListener(new MaterialAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                 Material material = materialList.get(position);
                int type = material.getType();

                if (type == Material.EXAMEN) {
                    if (unityInfo.getExam_score()==null ||unityInfo.getExam_score().equals("null")) {

                        ArrayList questions = new ArrayList<Question>();
                        for (int i = 0; i < unityInfo.getQuestions().length; i++) {
                            questions.add(unityInfo.getQuestions()[i]);
                        }
                        Intent intent = new Intent(MyCourseUnitActivity.this, EvaluationActivity.class);
                        intent.putExtra("API_TOKEN", api_token);
                        intent.putExtra("QUESTIONS", questions);
                        intent.putExtra("UNITY_ID", unityInfo.getUnity().getId());
                        intent.putExtra("SESSION_ID", session_id);

                        startActivityForResult(intent,1);
                    } else if(unityInfo.getExam_is_approved().equals("true")){
                        Toast.makeText( MyCourseUnitActivity.this,"Ya realizaste este examen, y lo aprobaste, no podes realizarlo nuevamente",Toast.LENGTH_SHORT).show();
                    }else {
                            Toast.makeText( MyCourseUnitActivity.this,"Ya realizaste este examen, no podes realizarlo nuevamente",Toast.LENGTH_SHORT).show();}
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(material.getFull_path()));
                    startActivity(browserIntent);
                }

                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });

        ((VideoAdapter) videoAdapter).setOnItemClickListener(new VideoAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                final Video video = videosList.get(position);
                CharSequence colors[] = new CharSequence[] {"Español", "Inglés", "Ninguno"};

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MyCourseUnitActivity.this);
                builder.setTitle("Elegí un idioma");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {

                        downloadFile("http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/course_materials/6/14.vtt","test_new.vtt");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(MyCourseUnitActivity.this, VideoViewActivity.class);
                                intent.putExtra("VIDEO_URL",video.getFull_path());
                                intent.putExtra("VIDEO_NAME",video.getName());
                                if (which == 0) {
                                    intent.putExtra("IDIOMA",String.valueOf(0));
                                } else if (which == 1) {
                                    intent.putExtra("IDIOMA",String.valueOf(1));
                                } else {
                                    intent.putExtra("IDIOMA",String.valueOf(2));
                                }

                                startActivity(intent);
                            }
                        }, 3000);

                    }
                });
                builder.show();


                Log.i(LOG_TAG, " Clicked on Video Item " + position);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                finish();
                startActivity(getIntent());
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//onActivityResult

}
