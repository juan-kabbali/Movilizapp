package uao.movilizapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;


public class logInActivity extends Activity {


    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



    }


    public void signIn(View view){
       Intent datosPertonalesIntent = new Intent(this, datosPersonalesActivity.class);
        startActivity(datosPertonalesIntent);
    }









}
