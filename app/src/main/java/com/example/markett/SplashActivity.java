package com.example.markett;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    Thread wait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Tools.context=getApplicationContext();
        firebaseAuth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        splashThread();

        //firebaseAuth.signOut(); //signup yapabiliyor muyuz görmek için yaptık.
        if(firebaseAuth.getCurrentUser()!=null){
            Tools.showMessage("You are already login. Redirect to main page.");
            wait.start();
        }else{
            Tools.showMessage("Login or Register please.");
        }
    }
    public void splashThread(){
        wait= new Thread(){
            public void run() {
                try {
                    sleep(2000);
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashActivity.this,navActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void loginClick(View view) {
        Intent i =new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void signupClick(View view) {
        Intent i =new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}