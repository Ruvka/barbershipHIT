package com.example.barbershop_hit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mTextUsername = findViewById(R.id.edittext_username);
        mTextPassword = findViewById(R.id.edittext_password);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.button_register);
        mTextViewLogin = findViewById(R.id.textview_login);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if(user.isEmpty()){
                    mTextUsername.setError("Field can not be Empty ");
                }
                else if(pwd.isEmpty()){
                    mTextPassword.setError("Field can not be Empty ");
                }
                else if (pwd.length()<=7){
                    mTextPassword.setError("Password length Should be greater than 7 ");
                }
                else if(pwd.equals(cnf_pwd)){
                    mAuth.createUserWithEmailAndPassword(user, pwd)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this,"You have registered",Toast.LENGTH_SHORT).show();
                                        Intent moveToLogin = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(moveToLogin);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
