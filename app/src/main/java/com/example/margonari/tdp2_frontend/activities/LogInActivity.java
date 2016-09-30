package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.services.LoginServices;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LogInActivity";

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private LoginButton fb_LoginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in );

        fb_LoginButton = (LoginButton) findViewById( R.id.login_button );
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);


        findViewById( R.id.sign_in_button ).setOnClickListener( this );



        //Faceboook Login
        callbackManager = CallbackManager.Factory.create();
        fb_LoginButton.setReadPermissions( Arrays.asList( "email" ) );

        fb_LoginButton.registerCallback( callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken( loginResult.getAccessToken() );
            }

            @Override
            public void onCancel() {
                Toast.makeText( getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText( getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT ).show();
            }
        } );

        //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .enableAutoManage( this /* FragmentActivity */, this /* OnConnectionFailedListener */ )
                .addApi( Auth.GOOGLE_SIGN_IN_API, gso )
                .build();

        signInButton.setScopes(gso.getScopeArray());


        //Firebase Login
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goToMainActivity();
                }
            }
        };

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        //progressBar.setVisibility(View.VISIBLE);
        // fb_LoginButton.setVisibility( View.GONE );

        AuthCredential credential = FacebookAuthProvider.getCredential( accessToken.getToken() );
        firebaseAuth.signInWithCredential( credential ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG ).show();
                    Log.d( TAG, "Firebase: login success" );
                } else {
                    Log.d( TAG, "Firebase: login unsuccess" );
                }
                //progressBar.setVisibility(View.GONE);
                // fb_LoginButton.setVisibility( View.VISIBLE );
            }
        } );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (FacebookSdk.isFacebookRequestCode(requestCode)){
            callbackManager.onActivityResult( requestCode, resultCode, data );
        }

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                Log.d( TAG, "Google SignIn:  Unsuccess" );

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener( firebaseAuthListener );
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener( firebaseAuthListener );
    }




    private void goToMainActivity() {

        HttpRequestTask httpRequestTask = new HttpRequestTask();

        httpRequestTask.execute( userEmail );
        try {
            Login login = (Login) httpRequestTask.get();
            if (login != null) {
                Intent mainActivityIntent = new Intent( LogInActivity.this, MainActivity.class );
                mainActivityIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                mainActivityIntent.putExtra( "API_TOKEN", login.getApi_token() );
                startActivity( mainActivityIntent );
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        signIn();

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN );
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LogInActivity.this, "Login Successful.",
                                    Toast.LENGTH_SHORT).show();
                                goToMainActivity();

                        }



                        // ...
                    }
                });

    }
    private class HttpRequestTask extends AsyncTask<String, Void, Login> {
        @Override
        protected Login doInBackground(String... params) {
            try {
                String user = params[0];
                Login login = new LoginServices().getLoginBy( user );
                return login;
            } catch (Exception e) {
                Log.e( "LoginActivity", e.getMessage(), e );
            }

            return null;
        }

    }
}




