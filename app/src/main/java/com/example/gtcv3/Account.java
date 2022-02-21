package com.example.gtcv3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {
TextView verifyTxt;
FirebaseAuth fAuth;
Button verifyBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
verifyTxt = findViewById(R.id.verifytxt);
        verifyBtn = findViewById(R.id.verifybtn);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

if (!user.isEmailVerified())

{
    verifyTxt.setVisibility(View.VISIBLE);
    verifyBtn.setVisibility(View.VISIBLE);

verifyBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Account.this, "A verifcation email has been sent",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Email not sent to user"+e.getMessage());
            }
        });
    }
});
}
        if (user.isEmailVerified())
        {
            verifyTxt.setVisibility(View.GONE);
            verifyBtn.setVisibility(View.GONE);
        }
    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }






}