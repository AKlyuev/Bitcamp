package com.example.matt33n.bitcamp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    EditText email, password;
    Button signIn, createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.password);
        password = (EditText) findViewById(R.id.password);

        signIn = (Button) findViewById(R.id.signIn);
        createAccount = (Button) findViewById(R.id.createAccount);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("tag", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("tag", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
    private void loginUser() {

        String userEmail, userPassword;

        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();

        if( !TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword))
        {

            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {


                        //Intent moveToHome = new Intent(MainActivity.this, .class);
                        //moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(moveToHome);

                    }else
                    {

                        Toast.makeText(MainActivity.this, "Unable to login user", Toast.LENGTH_LONG).show();


                    }

                }
            });

        }else
        {

            Toast.makeText(MainActivity.this, "Please enter user email and password", Toast.LENGTH_LONG).show();


        }

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("sdasdsadsadsa");

                startActivity(new Intent(MainActivity.this, CreateAccount.class));

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
