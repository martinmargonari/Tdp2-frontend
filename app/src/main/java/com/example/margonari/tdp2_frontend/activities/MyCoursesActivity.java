package com.example.margonari.tdp2_frontend.activities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.services.ListMyCoursesFinishedServices;
import com.example.margonari.tdp2_frontend.services.ListMyCoursesServices;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCoursesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String API_TOKEN = "API_TOKEN";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_FULLNAME = "USER_FULLNAME";
    public static final String USER_PICTURE = "USER_PICTURE";

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Intent intent;
    private static String LOG_TAG = "MyCoursesActivity";
    private ArrayList<Course> coursesList;
    private String api_token;
    private String userEmail;
    private String userFullName;
    private String userPicture;
    private DownloadManager downloadManager;
    public String filenameManager;
    private long q;
    private String has_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        intent = getIntent();
        api_token = intent.getStringExtra(API_TOKEN);
        userEmail = intent.getStringExtra(USER_EMAIL);
        userFullName = intent.getStringExtra(USER_FULLNAME);
        userPicture = intent.getStringExtra(USER_PICTURE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container_pages);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_courses);
        tabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_my_courses);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView userNameText = (TextView) headerView.findViewById(R.id.user_name);
        TextView emailText = (TextView) headerView.findViewById(R.id.user_email);
        ImageView imageProfile = (ImageView) headerView.findViewById(R.id.profile_picture);

        userNameText.setText(userFullName);
        emailText.setText(userEmail);
        imageProfile.setImageDrawable(getDrawable(R.drawable.com_facebook_profile_picture_blank_portrait));

        if (userPicture != null){
            try{
                Picasso.with(this).load(userPicture).into(imageProfile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mis_cursos) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.todos_los_cursos) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("API_TOKEN", api_token);
            startActivity(intent);
        } else if (id == R.id.ajustes) {

            Log.d("HAS_NOTIF AJUS",CourselandApp.getHas_notifications());

            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("HAS_NOTIFICATIONS", CourselandApp.getHas_notifications());
            startActivity(intent);

        } else if (id == R.id.cerrar_sesion) {
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH", "User LOGGED OUT");
                        FirebaseAuth.getInstance().signOut();

                    }
                });

            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) {
                HttpRequestTaskMyCoursesCurrent httpRequestTaskMyCourses = new HttpRequestTaskMyCoursesCurrent();
                httpRequestTaskMyCourses.execute();
                ArrayList<Course> coursesList = new ArrayList<>();
                try {
                    coursesList = httpRequestTaskMyCourses.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return MyCoursesCurrentFragment.newInstance(api_token, coursesList);

            } else if (position == 1) {
                HttpRequestTaskMyCoursesFinished httpRequestTaskMyCourses = new HttpRequestTaskMyCoursesFinished();
                httpRequestTaskMyCourses.execute();
                ArrayList<Course> coursesList = new ArrayList<>();
                try {
                    coursesList = httpRequestTaskMyCourses.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                    return MyCoursesFinishedFragment.newInstance(api_token, coursesList);
                }
            return null;

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CURSOS ACTUALES";
                case 1:
                    return "CURSOS FINALIZADOS";
            }
            return null;
        }
    }




    private class HttpRequestTaskMyCoursesCurrent extends AsyncTask<String, Void, ArrayList<Course>> {

        ArrayList<Course> listaCursos;
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {

                ListMyCoursesServices listMyCoursesServices= new ListMyCoursesServices();
                listMyCoursesServices.setApi_security(api_token);
                listaCursos = (ArrayList<Course>) listMyCoursesServices.getListCoursesBy();


            } catch (Exception e) {
                Log.e("ListCoursesCurrent", e.getMessage(), e);
            }

            return listaCursos;
        }
    }

    private class HttpRequestTaskMyCoursesFinished extends AsyncTask<String, Void, ArrayList<Course>> {

        ArrayList<Course> listaCursos;
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {

                ListMyCoursesFinishedServices listMyCoursesFinishedServices = new ListMyCoursesFinishedServices();
                listMyCoursesFinishedServices.setApi_security(api_token);
                listaCursos = (ArrayList<Course>) listMyCoursesFinishedServices.getListCoursesBy();


            } catch (Exception e) {
                Log.e("ListCoursesFinished", e.getMessage(), e);
            }

            return listaCursos;
        }
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

}
