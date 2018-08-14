package com.example.root.g_track;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private CardView Register;
    private MaterialEditText Email;
    private MaterialEditText Password;
    private TextView Logintrigger;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //handle login
        if(firebaseAuth.getCurrentUser() != null){
            ProgressDialog nDialog;
            nDialog = new ProgressDialog(Register.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Get Data");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();

            Query query = FirebaseDatabase.getInstance().getReference("user")
                    .orderByKey()
                    .equalTo(user.getUid());
            query.addValueEventListener(valueEventListener);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //set progress dialog
        progressDialog = new ProgressDialog(this);
        //set fauth

        // get element
        Register = findViewById(R.id.register);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Logintrigger = findViewById(R.id.Logintrigger);

        Logintrigger.setOnClickListener(this);
        Register.setOnClickListener(this);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                finish();
                startActivity(new Intent(getApplicationContext(), Main_activity.class));
                Log.e("Masuk","USER ADA!!!!!!!! OUT");
            }else{
                finish();
                Intent i = new Intent(getApplicationContext(), Profileinput.class);
                i.putExtra("sumber", "register");
                startActivity(i);
                Log.e("Masuk","USER GA ADA!!! ");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {
        if (v == Register){
            registerUser();
            //finish();
        }
        if (v== Logintrigger){
            Intent i = new Intent(Register.this,Login.class);
            startActivity(i);
            finish();
        }
    }

    private void registerUser() {
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

        progressDialog.setMessage("Mendaftarkan User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User berhasil dibuat", Toast.LENGTH_SHORT).show();
                            //strat new activity
                            Intent i = new Intent(Register.this,Profileinput.class);
                            i.putExtra("sumber", "register");
                            startActivity(i);
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "User gagal dibuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
