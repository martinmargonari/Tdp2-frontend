package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ImageAdapter;
import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.ForumCategory;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.services.ForumCategoriesServices;
import com.example.margonari.tdp2_frontend.services.ForumPostServices;
import com.example.margonari.tdp2_frontend.services.ForumThreadsServices;
import com.example.margonari.tdp2_frontend.services.ListCoursesByCategoriesServices;
import com.example.margonari.tdp2_frontend.services.ListMyCoursesServices;
import com.example.margonari.tdp2_frontend.services.LoginServices;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 0;

    private GridView grillaCategorias;
    private ImageAdapter adapterCategorias;
    private String  api_token;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private FirebaseAuth auth;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        auth = FirebaseAuth.getInstance();
        if ( auth.getCurrentUser()!=null){
            Log.d("AUTH", "User LOGGED IN");
            userEmail=auth.getCurrentUser().getEmail();
            firstName=auth.getCurrentUser().getDisplayName();
            Log.d("hola",auth.getCurrentUser().getProviders().toString());
            auth.getCurrentUser().getProviderData();
            profilePicture= auth.getCurrentUser().getPhotoUrl().toString();
            InitApiTokenFromServer(userEmail);
        }else{
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)

                            .setProviders(
                    AuthUI.FACEBOOK_PROVIDER,
                    AuthUI.GOOGLE_PROVIDER,
                    AuthUI.EMAIL_PROVIDER
            ).build(),RC_SIGN_IN);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView userNameText = (TextView) headerView.findViewById(R.id.user_name);
        TextView emailText = (TextView) headerView.findViewById(R.id.user_email);
        ImageView imageProfile = (ImageView) headerView.findViewById(R.id.profile_picture);
        userNameText.setText(firstName + " " + lastName);
        emailText.setText(userEmail);

        Glide.with(this).load(profilePicture).into(imageProfile);

        grillaCategorias = (GridView) findViewById(R.id.grilla_categorias);
        adapterCategorias = new ImageAdapter(this);
        grillaCategorias.setAdapter(adapterCategorias);
        grillaCategorias.setOnItemClickListener(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if( requestCode == RC_SIGN_IN){
                if ( resultCode== RESULT_OK){
                    Log.d("AUTH",auth.getCurrentUser().getEmail());
                    Log.d("hola",auth.getCurrentUser().getProviders().toString());

                    InitApiTokenFromServer(auth.getCurrentUser().getEmail());
                    userEmail=auth.getCurrentUser().getEmail();
                    firstName=auth.getCurrentUser().getDisplayName();
                    profilePicture= auth.getCurrentUser().getPhotoUrl().toString();
                }
        }else{Log.d("AUTH","User not autenticated");}
    }

    private void InitApiTokenFromServer(String userEmail) {
        HttpRequestTaskLogin httpRequestTask = new HttpRequestTaskLogin();

        httpRequestTask.execute(userEmail, FirebaseInstanceId.getInstance().getToken());
        try {
            Login login = (Login) httpRequestTask.get();
            api_token=login.getApi_token();
            CourselandApp.setApi_token(api_token);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mis_cursos) {
            HttpRequestTaskMyCourses httpRequestTaskMyCourses= new HttpRequestTaskMyCourses();
            httpRequestTaskMyCourses.execute();
            ArrayList<Course> listCourses= new ArrayList<>();
            try {
                 listCourses= httpRequestTaskMyCourses.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this,MyCoursesActivity.class);
            intent.putExtra("LIST_CATEGORIES", listCourses);
            intent.putExtra("API_TOKEN", api_token);
            startActivity(intent);
        } else if (id == R.id.cursos_destacados) {
            //////////////////////////Categorias en curso 6 ( el de python):::: Burcar en el log "CategoriaDescription" //////////////////////////
            HttpRequestTaskForumCategories httpRequestTaskForumCategories= new HttpRequestTaskForumCategories();
            httpRequestTaskForumCategories.execute("6");//CUrso de python
            ArrayList<ForumCategory> listCateories= new ArrayList<>();
            try {
                listCateories= httpRequestTaskForumCategories.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("CategoriaDescription", listCateories.get(0).getDescription());

            //////////////////////////////////////////Threads en la categoria 1 del curso de python :::: Burcar en el log "ThreadTitle" //////////////////////////////

            HttpRequestTaskForumThreads httpRequestTaskForumThreads= new HttpRequestTaskForumThreads();
            httpRequestTaskForumThreads.execute("1");//Categoria 1 del curso de python
            ArrayList<ForumThread> listThreads= new ArrayList<>();
            try {
                listThreads= httpRequestTaskForumThreads.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("ThreadTitle", listThreads.get(0).getTitle());

            //////////////////////////////////////////Threads en la categoria 1 del curso de python :::: Burcar en el log "PostContent" //////////////////////////////

            HttpRequestTaskForumPost httpRequestTaskForumPost= new HttpRequestTaskForumPost();
            httpRequestTaskForumPost.execute("1");//Categoria 1 del curso de python
            ArrayList<ForumPost> listPost= new ArrayList<>();
            try {
                listPost= httpRequestTaskForumPost.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("PostContent", listPost.get(0).getContent());

            ///////////////////////////////////////////////////////////////////////////////////////////////////////
        } else if (id == R.id.todos_los_cursos) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.notificaciones) {

        } else if (id == R.id.ajustes) {

        } else if (id == R.id.cerrar_sesion) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("AUTH", "User LOGGED OUT");
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER,
                            AuthUI.EMAIL_PROVIDER
                    ).build(),RC_SIGN_IN);

                                    }

            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                 Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.margonari.tdp2_frontend/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.margonari.tdp2_frontend/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
            ArrayList<Course> courses= (ArrayList<Course>) httpRequestTask.get();
            Intent intent = new Intent(this, CoursesActivity.class);
            intent.putExtra("API_TOKEN", api_token);
            intent.putExtra("LIST_COURSES", courses);
            intent.putExtra("CATEGORY_NAME",category_name);
            startActivity( intent);

        }catch (Exception e){
            e.printStackTrace();
        }
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

    class HttpRequestTaskMyCourses extends AsyncTask<String, Void, ArrayList<Course>> {

        ArrayList<Course> listaCursos;
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {

                ListMyCoursesServices listMyCoursesServices= new ListMyCoursesServices();
                listMyCoursesServices.setApi_security(api_token);
                listaCursos= (ArrayList<Course>) listMyCoursesServices.getListCoursesBy();


            } catch (Exception e) {
                Log.e("ListCoursesByCategories", e.getMessage(), e);
            }

            return listaCursos;
        }

    }


    private class HttpRequestTaskLogin extends AsyncTask<String, Void, Login> {
        @Override
        protected Login doInBackground(String... params) {
            try {
                String user = params[0];
                String token= params[1];
                Log.d("tokenRequestLogin", token);

                Login login=  new LoginServices().getLoginBy(user, token);
                return login;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }



    private class HttpRequestTaskForumCategories extends AsyncTask<String, Void, ArrayList<ForumCategory>> {

        ArrayList<ForumCategory> listaCategorias;
        @Override
        protected ArrayList<ForumCategory> doInBackground(String... params) {
            try {
                String course_id = params[0];

                ForumCategoriesServices forumCategoriesServices= new ForumCategoriesServices();
                forumCategoriesServices.setApi_security(api_token);
                listaCategorias= (ArrayList<ForumCategory>) forumCategoriesServices.getListCategoriesBy(course_id);
                return listaCategorias;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }

    private class HttpRequestTaskForumThreads extends AsyncTask<String, Void, ArrayList<ForumThread>> {

        ArrayList<ForumThread> listaThreads;
        @Override
        protected ArrayList<ForumThread> doInBackground(String... params) {
            try {
                String category_id = params[0];

                ForumThreadsServices forumCategoriesServices= new ForumThreadsServices();
                forumCategoriesServices.setApi_security(api_token);
                listaThreads = (ArrayList<ForumThread>) forumCategoriesServices.getListThreadsBy(category_id);
                return listaThreads;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }


    private class HttpRequestTaskForumPost extends AsyncTask<String, Void, ArrayList<ForumPost>> {

        ArrayList<ForumPost> listaPost;
        @Override
        protected ArrayList<ForumPost> doInBackground(String... params) {
            try {
                String thread_id = params[0];

                ForumPostServices forumPostServices= new ForumPostServices();
                forumPostServices.setApi_security(api_token);
                listaPost = (ArrayList<ForumPost>) forumPostServices.getListPostBy(thread_id);
               Log.d("EL POST :",listaPost.get(0).toString()) ;
                return listaPost;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
