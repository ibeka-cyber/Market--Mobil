package com.example.markett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markett.models.UserModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,password;
    Button signup;
    TextView login;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.editTextTextPersonName);
        email=(EditText) findViewById(R.id.editTextTextEmailAddress);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        signup=(Button) findViewById(R.id.signupR);
        login=(TextView) findViewById(R.id.loginR);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar3);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupClick();
            }
        });
    }
    public void signupClick(){
        String userName= name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        if(userName.isEmpty()){
            Tools.showMessage("User Name can't be empty!");
        }
        if(userEmail.isEmpty()){
            Tools.showMessage("User Email can't be empty!");
        }
        if(password.equals("") || password.length()<6){
            Tools.showMessage("invalid password");
        }


        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).
                addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                   UserModels user = new UserModels(userName,userEmail,userPassword);
                   String uid= Objects.requireNonNull(task.getResult().getUser()).getUid();//databasede isim kaydedilmiyor. Database içinde
                   //isim adı altında dallanmasını sağlamak için uid çektik.
                    Tools.showMessage("Registration successful");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myReference = database.getReference("users").child(uid); // users rootunun içinde uid diye bir alan daha aç demek.
                    myReference.setValue(user); // veri tabanımıza referans olan değişkenimize mail name ve passwordu almasını ve kaydetmesini söyledik.
                    myReference.setValue("Hello, world!");
                    startActivity(new Intent(RegisterActivity.this,SplashActivity.class));
                }else{
                    Tools.showMessage("Failed");
                }
            }
        });
    }

    public void loginClick(View view) {
        Intent i =new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
}