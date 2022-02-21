package com.example.gtcv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText lEmail, lPassword;
    Button lLoginBtn;
    TextView ltocreate, forgotLink;
    ProgressBar pBar;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
///test
        lEmail = findViewById(R.id.email);
        lPassword = findViewById(R.id.paswword);
        pBar = findViewById(R.id.progressBar);
        lLoginBtn = findViewById(R.id.loginbtn);
        ltocreate = findViewById(R.id.toRegister);
        fAuth = FirebaseAuth.getInstance();
forgotLink = findViewById(R.id.forgetpass);



        lLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    lEmail.setError("Email address is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    lEmail.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    lPassword.setError("The password must be longer than 6 characters");
                    return;
                }
                pBar.setVisibility(View.VISIBLE);
                //actual user authentification
fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
   if(task.isSuccessful())
   {
       Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
       startActivity(new Intent(getApplicationContext(), Account.class));

   }else {pBar.setVisibility(View.GONE);
       Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show(); }
    }
});

            }

        });


        ltocreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

forgotLink.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        EditText resetEmail = new EditText(view.getContext());
        AlertDialog.Builder passwordreesetdiag = new AlertDialog.Builder(view.getContext());
        passwordreesetdiag.setTitle("Password Reset");
        passwordreesetdiag.setMessage(" Enter your email address. ");
        passwordreesetdiag.setView(resetEmail);
        passwordreesetdiag.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //take the email and send reset link
                String Email = resetEmail.getText().toString();
                fAuth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(Login.this,"The password reset link has been sent to your email address. ",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,"An error has occured!" + e.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        passwordreesetdiag.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
passwordreesetdiag.create().show();
    }
});






    }
}