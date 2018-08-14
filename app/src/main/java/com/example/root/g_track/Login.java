package com.example.root.g_track;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private MaterialEditText Email;
    private MaterialEditText Password;
    private CardView Login;
    private TextView register;


    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //handle user login
        firebaseAuth = firebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Garasi.class));
        }

        //set progress dialog
        progressDialog = new ProgressDialog(this);
        //get element
        register = findViewById(R.id.register_trig);
        Email = findViewById(R.id.editText);
        Password = findViewById(R.id.editText2);
        Login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(v.getContext(),Register.class);
                startActivity(i);
            }
        });
        Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==Login){
            userlogin();
        }
    }

    private void userlogin() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Masukan email anda", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Masukan password anda", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Masuk ke aplikasi...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Intent i = new Intent(Login.this,Main_activity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Login gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
