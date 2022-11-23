package com.example.demologin3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText reg_email, reg_password;
    private Button btn_reg;
    String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        reg_email = findViewById(R.id.Log_email);
        reg_password = findViewById(R.id.Log_password);
        btn_reg = findViewById(R.id.Register_btn);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


    }

    private void Register() {
        String user = reg_email.getText().toString().trim();
        String pass = reg_password.getText().toString().trim();
        if (user.isEmpty()) {
            reg_email.setError("Email is Required");
        }
        if (pass.isEmpty()) {
            reg_password.setError("Password is Required");
        }
        if (!user.matches(emailPattern)){
            reg_email.setError("Enter Correct Email");
        }
        else if(pass.isEmpty() || pass.length()<6){
            reg_password.setError("Enter proper password");
        }
        else {

            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity2.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity2.this, MainActivity.class));
                    } else {
                        Toast.makeText(MainActivity2.this, "Registration Unccessfull!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}