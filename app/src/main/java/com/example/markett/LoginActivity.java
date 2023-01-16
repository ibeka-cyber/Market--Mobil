package com.example.markett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView signup;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.editTextTextEmailAddress);
        password=(EditText)findViewById(R.id.editTextTextPassword);
        signup=(TextView) findViewById(R.id.signupL);
        login=(Button)findViewById(R.id.loginL);
        firebaseAuth= FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword=password.getText().toString();
        if(userEmail.isEmpty()){
            Tools.showMessage("User Email can't be empty!");
        }
        if(password.equals("") || password.length()<6){
            Tools.showMessage("invalid password");
        }
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Tools.showMessage("Welcome");
                    progressBar.setVisibility(View.VISIBLE);
                    Intent i =new Intent(LoginActivity.this, navActivity.class);
                    startActivity(i);

                }else{
                    Tools.showMessage("Failed");
                }
            }
        });
    }

    public void signupClick(View view) {
        Intent i =new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }
}