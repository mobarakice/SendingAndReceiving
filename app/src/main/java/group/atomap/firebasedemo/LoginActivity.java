package group.atomap.firebasedemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import group.atomap.firebasedemo.firebasechat.ConversationActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText etUserId, etPassword;
    private ProgressDialog mDialog;
    private Firebase refFirebase;
    public static FirebaseAuth mAuth;
    private AuthResult mAuthResult;
    private AuthData mAuthData;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpView();
    }

    private void setUpView() {
        etUserId = (EditText) findViewById(R.id.user_id);
        etPassword = (EditText) findViewById(R.id.user_password);
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Login");
        mDialog.setMessage("Please wait a minute......!");
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        refFirebase = new Firebase("https://fir-demo-88ca7.firebaseio.com");
        mAuth = FirebaseAuth.getInstance();
        //mFirebaseUser = mAuth.getCurrentUser().getUid();

    }

//    private void createUser(final String email, final String password) {
//        if (email == null || password == null) {
//            return;
//        }
//        refFirebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
//            @Override
//            public void onSuccess(Map<String, Object> stringObjectMap) {
//                Log.d("Successfully created", "seelog: " + "ID: " + stringObjectMap.get(email) + "\t Pass: " + stringObjectMap.get(password));
//            }
//
//            @Override
//            public void onError(FirebaseError firebaseError) {
//                Toast.makeText(getApplicationContext(), "Account creation failed.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void login(String email, String password) {
        showDialog();
        if (email == null || password == null) {
            hideDialog();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("seelog", "signInWithEmail:onComplete:" + task.isSuccessful());
                    hideDialog();
                    startActivity(new Intent(LoginActivity.this, ConversationActivity.class));
                    finish();
                } else {
                    Log.w("seelog", "signInWithEmail:failed", task.getException());
                    Toast.makeText(getApplicationContext(), " Sign in failed",
                            Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }
        });
    }


    public void onClickLogin(View view) {
        String email = etUserId.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etUserId.setError("Required");
            return;
        } else {
            etUserId.setError(null);
        }
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required");
            return;
        } else {
            etPassword.setError(null);
        }
        login(email, password);
        etUserId.setText("");
        etPassword.setText("");

    }

    public void onClickRegister(View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        final View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.registration, null);
        final EditText email = (EditText) dialogView.findViewById(R.id.id);
        final EditText pass = (EditText) dialogView.findViewById(R.id.pass);
        alertDialog.setView(dialogView);
        alertDialog.setTitle("Registration From")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = email.getText().toString();
                        String password = pass.getText().toString();
                        createNewUser(id, password);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        alertDialog.show();

    }

    private void showDialog() {
        if (!mDialog.isShowing() && mDialog != null) {
            mDialog.show();
        }
    }

    private void hideDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void createNewUser(final String email, String pass) {
        showDialog();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("Successfully created", "seelog: " + "ID: " + task.getResult().getUser().getUid() + "\t Pass: " + task.getResult().getUser().getEmail());
                    Toast.makeText(LoginActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
//                    User user = new User();
//                    user.setUserId(task.getResult().getUser().getUid());
//                    user.setUserEmail(email);
//                    Util.mUser.add(user);
                    hideDialog();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        });

    }

    public static ArrayList<String> getAllUId(FirebaseAuth firebaseAuth) {
        firebaseAuth.getCurrentUser().getUid();

        return null;
    }

}
