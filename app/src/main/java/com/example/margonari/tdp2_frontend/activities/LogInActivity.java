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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

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

    //Facebook Login
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mStatusTextView = (TextView) findViewById(R.id.status);
        mUserView = (EditText) findViewById(R.id.username_input);

        mpasswordView = (EditText) findViewById(R.id.password_input);
        mpasswordView.getText().toString();
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //Faceboook Login setup Button
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mStatusTextView.setText(R.string.facebook_login_succes);
                goToMainActivity();
            }

            @Override
            public void onCancel() {
                mStatusTextView.setText(R.string.facebook_login_cancel);
            }

            @Override
            public void onError(FacebookException exception) {
                mStatusTextView.setText(R.string.facebook_login_fail);
            }
        });

        googleLoginSetup();
        butonsRestTestSetup();

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
            mStatusTextView.setText("FAILED SIGN IN");
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
        Intent mainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
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
            mStatusTextView.setText("SIGNED IN");
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }


    private void butonsRestTestSetup() {
        Button btn_rest_login = (Button) findViewById(R.id.button_rest_login);
        btn_rest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestLoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btn_rest_categories = (Button) findViewById(R.id.button_rest_categories);
        btn_rest_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestListCategoriesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        Button button_rest_signIn = (Button) findViewById(R.id.button_rest_signIn);
        button_rest_signIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                HttpRequestTask httpRequestTask =new HttpRequestTask();
                mUserView.setText("admin@admin.com");
                mpasswordView.setText("85d8b4ccd607dde1753fa9293d694c03");
                httpRequestTask.execute(mUserView.getText().toString(), mpasswordView.getText().toString());
                try {
                    Login login= (Login)httpRequestTask.get();
                    Toast.makeText(v.getContext(), (String)login.toString(),
                            Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(v.getContext(), MainActivity.class);
                    //startActivityForResult(intent, 0);
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private class HttpRequestTask extends AsyncTask<String, Void, Login> {
        @Override
        protected Login doInBackground(String... params) {
            try {
                String user= params[0];
                String password= params[1];
                String loginQuery = getLoginQueryBy(user, password);
                LoginDTO loginDTO = (LoginDTO) geDataOftDTO(loginQuery, LoginDTO.class);
                return loginDTO.getData();
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

        @NonNull
        private String getLoginQueryBy(String user, String password) {
            String url = getResources().getString(R.string.login_services_address_first);
            StringBuffer urlStringBuffer=new StringBuffer(url);
            urlStringBuffer.append("?");
            urlStringBuffer.append("api_security=");
            urlStringBuffer.append(password);
            urlStringBuffer.append("&email=");
            urlStringBuffer.append(user);

            return  urlStringBuffer.toString();
        }


        private Object geDataOftDTO(String url, Class object) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, object);
        }

        @Override
        protected void onPostExecute(Login greeting) {

        }

    }




}
