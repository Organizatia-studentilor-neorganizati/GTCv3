package com.example.gtcv3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText rFullName, rEmail, rPassword, rPhone;
    TextView rtocreate;
    Button rCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rFullName = findViewById(R.id.fullname);
        rEmail = findViewById(R.id.email);
        rPassword = findViewById(R.id.paswword);
        rPhone = findViewById(R.id.phone);
        rCreateBtn = findViewById(R.id.createbtn);

        rtocreate = findViewById(R.id.tologin);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null)
        //if the user is already logged in he is skipped this registration step

        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        rCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                String fullName = rFullName.getText().toString();
                String phonenumber = rPhone.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Email address is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    rPassword.setError("The password must be longer than 6 characters");
                    return;
                }


                // user actually registration to firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Full name", rFullName);
                                    user.put("Email adress", rEmail);
                                    user.put("Phone number", rPhone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: Account is created for " + userID);
                                        }
                                    });
                                    Log.println(Log.DEBUG,"debug", "Your message to print");


                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else
                                {
                                    Toast.makeText(SignUp.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                );


            }
        });


     /*rtocreate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           startActivity(new Intent(getApplicationContext(),Login.class));
         }
     });*/
        rtocreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}