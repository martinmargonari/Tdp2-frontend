package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.rest_dto.LoginDTO;
import com.example.margonari.tdp2_frontend.services.LoginServices;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class LogInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "LogInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private TextView mStatusTextView;
    private EditText mUserView;
    private EditText mpasswordView;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String profilePicture;

    //Facebook Login
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //Faceboook Login setup Button
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    userEmail = me.optString("email");
                                    firstName = me.optString("first_name");
                                    lastName = me.optString("last_name");
                                    profilePicture = ImageRequest.getProfilePictureUri(me.optString("id"), 500, 500).toString();
                                }
                            }
                        }).executeAsync();
                goToMainActivity();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        googleLoginSetup();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(boolean signedIn) {
        if (!signedIn) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }


    private void goToMainActivity() {

        HttpRequestTask httpRequestTask = new HttpRequestTask();

        httpRequestTask.execute(userEmail);
        try {
            Login login = (Login) httpRequestTask.get();
            if (login != null) {
                Intent mainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
                mainActivityIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK) ;
                mainActivityIntent.putExtra("API_TOKEN", login.getApi_token());
                mainActivityIntent.putExtra("EMAIL",userEmail);
                mainActivityIntent.putExtra("FIRST_NAME",firstName);
                mainActivityIntent.putExtra("LAST_NAME",lastName);
                mainActivityIntent.putExtra("PROFILE_PICTURE",profilePicture);
                startActivity(mainActivityIntent);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void googleLoginSetup() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(true);
            goToMainActivity();
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Login> {
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




