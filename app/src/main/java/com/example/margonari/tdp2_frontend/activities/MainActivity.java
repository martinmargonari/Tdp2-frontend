package com.example.margonari.tdp2_frontend.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.example.margonari.tdp2_frontend.services.LoginServices;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 0;
    private ImageView imageProfile;
    private GridView grillaCategorias;
    private ImageAdapter adapterCategorias;
    private String api_token;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private FirebaseAuth auth;
    private GoogleApiClient client;
    private Context context;
    private TextView userNameText;
    private TextView emailText;
    private Uri photo_storage_url;
    private String has_notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context= this;
        this.setTitle("Encontrar Cursos");
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


        initMenu();

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

         userNameText = (TextView) headerView.findViewById(R.id.user_name);
         emailText = (TextView) headerView.findViewById(R.id.user_email);
        imageProfile = (ImageView) headerView.findViewById(R.id.profile_picture);


        imageProfile.setImageDrawable(getDrawable(R.drawable.com_facebook_profile_picture_blank_portrait));



        grillaCategorias = (GridView) findViewById(R.id.grilla_categorias);
        adapterCategorias = new ImageAdapter(this);
        grillaCategorias.setAdapter(adapterCategorias);
        grillaCategorias.setOnItemClickListener(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.d("RESULT", "On activity result");
                Log.d("Nombre usuario", auth.getCurrentUser().getDisplayName().toString());
                initMenu();

            } else {

                Log.d("AUTHS", "Problema con smart lock");
            }

        } else {
            Log.d("AUTH", "User not autenticated");
        }
    }

    private void initMenu() {
        auth = FirebaseAuth.getInstance();

        if(imageProfile!=null &auth!=null & auth.getCurrentUser()!=null){
            userEmail = auth.getCurrentUser().getEmail();
            firstName = auth.getCurrentUser().getDisplayName().toString();

            if(auth.getCurrentUser().getPhotoUrl()!=null){
                profilePicture = auth.getCurrentUser().getPhotoUrl().toString();
                if (profilePicture != null) {
                    try {

                        Picasso.Builder builder = new Picasso.Builder(this);
                        builder.listener(new Picasso.Listener()
                        {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                            {
                                exception.printStackTrace();
                            }
                        });
                        builder.build().load(profilePicture).into(
                                new Target() {
                                    @Override
                                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){

                                        imageProfile.setImageBitmap(bitmap);
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] dataBAOS = baos.toByteArray();

                                        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://courseland-143207.appspot.com");
                                        StorageReference imagesRef = storageRef.child(userEmail);

                                        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                                 photo_storage_url = taskSnapshot.getDownloadUrl();
                                                Log.d("PhotoUrl", String.valueOf(photo_storage_url));
                                                InitApiTokenFromServer(userEmail,String.valueOf(photo_storage_url));

                                            }
                                        });
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {}

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                        InitApiTokenFromServer(userEmail,String.valueOf(photo_storage_url));

                                    }
                                }
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }




    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("imageUrl").child(auth.getCurrentUser().getDisplayName()+1);
        ref.setValue(imageEncoded);
    }

    private void InitApiTokenFromServer(String userEmail,String photo) {
        if(auth.getCurrentUser()!=null) {
            HttpRequestTaskLogin httpRequestTask = new HttpRequestTaskLogin();

            httpRequestTask.execute(userEmail, FirebaseInstanceId.getInstance().getToken(),
                    photo, auth.getCurrentUser().getDisplayName());
            //Log.d("PhotoUrl: ", photo_storage_url.toString());
            try {
                Login login = (Login) httpRequestTask.get();
                has_notifications=login.getHas_notifications();
                Log.d("HAS_NOTIF",has_notifications);
                api_token = login.getApi_token();
                CourselandApp.setApi_token(api_token);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
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

            Intent intent = new Intent(MainActivity.this, MyCoursesActivity.class);
            intent.putExtra(MyCoursesActivity.API_TOKEN, api_token);
            intent.putExtra(MyCoursesActivity.USER_EMAIL, userEmail);
            intent.putExtra(MyCoursesActivity.USER_FULLNAME, firstName);
            intent.putExtra(MyCoursesActivity.USER_PICTURE, profilePicture);
            startActivity(intent);
        } else if (id == R.id.todos_los_cursos) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.ajustes) {
            Log.d("HAS_NOTIF AJUS",has_notifications);

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("HAS_NOTIFICATIONS", has_notifications);
            startActivity(intent);
        } else {
            if (id == R.id.cerrar_sesion) {
                if (auth.getCurrentUser() != null) {
                    AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH", "User LOGGED OUT");
                            FirebaseAuth.getInstance().signOut();

                        }
                    });
                }
                finish();
                startActivity(getIntent());
            }
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
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Log.d("AUTH", "User LOGGED IN");
            //userEmail = auth.getCurrentUser().getEmail();
            //firstName = auth.getCurrentUser().getDisplayName();
            //InitApiTokenFromServer(userEmail);
            initMenu();
        } else {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setLogo(R.drawable.ic_launcher)
                            .setIsSmartLockEnabled(false)
                            .setProviders(
                                    AuthUI.FACEBOOK_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER
                            ).build(), RC_SIGN_IN);

        }
        userNameText.setText(firstName);
        emailText.setText(userEmail);
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
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra("API_TOKEN", api_token);
        }
        super.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Categoria item = (Categoria) parent.getItemAtPosition(position);
        int item_id = Categoria.getCategoryByIdView(item.getId());
        String category_name = item.getName();


        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute(String.valueOf(item_id));
        try {
            ArrayList<Course> courses = (ArrayList<Course>) httpRequestTask.get();
            Intent intent = new Intent(this, CoursesActivity.class);
            intent.putExtra("API_TOKEN", api_token);
            intent.putExtra("LIST_COURSES", courses);
            intent.putExtra("CATEGORY_NAME", category_name);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class HttpRequestTask extends AsyncTask<String, Void, ArrayList<Course>> {
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {
                String id_categorie = params[0];
                ListCoursesByCategoriesServices listCoursesByCategoriesServices = new ListCoursesByCategoriesServices();
                listCoursesByCategoriesServices.setApi_security(api_token);
                ArrayList<Course> listCourses = (ArrayList<Course>) listCoursesByCategoriesServices.getListCoursesBy(id_categorie);

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
                String token = params[1];
                String image_url = params[2];
                String name = params[3];

                Log.d("tokenRequestLogin", token);

                Login login = new LoginServices().getLoginBy(user, token, image_url, name);
                return login;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }

    @Override
    protected void onDestroy() {
       super.onDestroy();
    }
}
