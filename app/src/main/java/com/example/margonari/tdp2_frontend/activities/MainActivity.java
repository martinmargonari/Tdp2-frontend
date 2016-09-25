package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.margonari.tdp2_frontend.adapters.ImageAdapter;
import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.services.ListCoursesByCategoriesServices;
import com.example.margonari.tdp2_frontend.services.LoginServices;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private GridView grillaCategorias;
    private ImageAdapter adapterCategorias;
    private String  api_token;
    private String userEmail;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api_token = getIntent().getStringExtra("API_TOKEN");
        System.out.println("APITOKEN: " + api_token);

        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            goToLoginScreen();
        }
        else{

            InitUserEmailFromFacebook();
            if(api_token==null){
                InitApiTokenFromServer(userEmail);
            }


        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        grillaCategorias = (GridView) findViewById(R.id.grilla_categorias);
        adapterCategorias = new ImageAdapter(this);
        grillaCategorias.setAdapter(adapterCategorias);
        grillaCategorias.setOnItemClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void InitApiTokenFromServer(String userEmail) {
        HttpRequestTaskLogin httpRequestTask = new HttpRequestTaskLogin();

        httpRequestTask.execute(userEmail);
        try {
            Login login = (Login) httpRequestTask.get();
            api_token=login.getApi_token();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void InitUserEmailFromFacebook() {
        GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            userEmail = me.optString("email");


                        }
                    }
                }).executeAsync();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            onSearchRequested();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mis_cursos) {
            Intent intent = new Intent(this,MyCoursesActivity.class);
            intent.putExtra("API_TOKEN", api_token);
            startActivity(intent);
        } else if (id == R.id.cursos_destacados) {

        } else if (id == R.id.todos_los_cursos) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.notificaciones) {

        } else if (id == R.id.ajustes) {

        } else if (id == R.id.cerrar_sesion) {
            LoginManager.getInstance().logOut();
            goToLoginScreen();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.margonari.tdp2_frontend/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.margonari.tdp2_frontend/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this,LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }

    public void startActivity(Intent intent) {
        // check if search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra("API_TOKEN", api_token);
        }

        super.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria item = (Categoria) parent.getItemAtPosition(position);
            int item_id= Categoria.getCategoryByIdView(item.getId());
            String category_name = item.getName();


        HttpRequestTask  httpRequestTask= new HttpRequestTask();
        httpRequestTask.execute(String.valueOf(item_id));
        try {
            ArrayList<Course> categorias= (ArrayList<Course>) httpRequestTask.get();
            Intent intent = new Intent(this, CoursesActivity.class);
            intent.putExtra("API_TOKEN", api_token);
            intent.putExtra("LIST_CATEGORIES", categorias);
            intent.putExtra("CATEGORY_NAME",category_name);
            startActivity( intent);

        }catch (Exception e){
            e.printStackTrace();
        }
        /*
        intent.putExtra(ActividadDetalle.EXTRA_PARAM_ID, item.getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.imagen_coche),
                                    ActividadDetalle.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else*/
    }


    class HttpRequestTask extends AsyncTask<String, Void, ArrayList<Course>> {
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {
                String id_categorie = params[0];
                ListCoursesByCategoriesServices listCoursesByCategoriesServices= new ListCoursesByCategoriesServices();
                listCoursesByCategoriesServices.setApi_security(api_token);
                ArrayList<Course>  listCourses= (ArrayList<Course>) listCoursesByCategoriesServices.getListCoursesBy(id_categorie);

                return listCourses;
            } catch (Exception e) {
                Log.e("ListCoursesByCategories", e.getMessage(), e);
            }

            return null;
        }

    }


    private class HttpRequestTaskLogin extends AsyncTask<String, Void, Login> {
        @Override
        protected Login doInBackground(String... params) {
            try {
                String user = params[0];
                Login login=  new LoginServices().getLoginBy(user);
                return login;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }

}
