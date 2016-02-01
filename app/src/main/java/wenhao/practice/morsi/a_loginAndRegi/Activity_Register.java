package wenhao.practice.morsi.a_loginAndRegi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;
import java.util.Random;

import wenhao.practice.morsi.Constant_ApplicationConstant;
import wenhao.practice.morsi.b_UserAndGroup.Object_User;
import wenhao.practice.morsi.R;
import wenhao.practice.morsi.b_UserAndGroup.View_ug_activity_main;

public class Activity_Register extends AppCompatActivity {

    private EditText TV_emial;
    private EditText TV_password;
    private EditText TV_password2;
    private EditText TV_user;
    private Button btn_regi;

    private AsyncTask regiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_register);

        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TV_emial=(EditText)findViewById(R.id.regi_email);
        TV_password = (EditText)findViewById(R.id.regi_password);
        TV_password2 = (EditText)findViewById(R.id.regi_password2);
        TV_user = (EditText)findViewById(R.id.regi_userName);

        btn_regi = (Button)findViewById(R.id.regi_btn);
        btn_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegi();
            }
        });

    }


    private void attemptRegi() {
        // Reset errors.
        TV_emial.setError(null);
        TV_password.setError(null);
        TV_password.setError(null);
        TV_user.setError(null);

        // Store values at the time of the login attempt.
        String email = TV_emial.getText().toString();
        String password = TV_password.getText().toString();
        String password2 = TV_password2.getText().toString();
        final String userName = TV_user.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)){
            TV_emial.setError(getString(R.string.error_field_required));
            focusView = TV_emial;
            cancel = true;
        }
        else if (!isEmailValid(email)){
            TV_emial.setError(getString(R.string.error_invalid_email));
            focusView = TV_emial;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)|| !isPasswordValid(password)){
            TV_password.setError(getString(R.string.error_invalid_password));
            focusView = TV_password;
            cancel = true;
        }

        if (TextUtils.isEmpty(password2) || !isPassword2Valid(password, password2)){
            TV_password2.setError(getString(R.string.error_invalid_password2));
            focusView = TV_password2;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)){
            TV_user.setError(getString(R.string.error_field_required));
            focusView = TV_user;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        }
        else {
            final Firebase ref = new Firebase(Constant_ApplicationConstant.FirebaseURL);
            ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Toast.makeText(getBaseContext(),"User Created!",Toast.LENGTH_SHORT).show();
                    Firebase userRef = ref.child("users").child(stringObjectMap.get("uid").toString());
                    final Random rand = new Random();
                    int idx = rand.nextInt(9)+1;
                    Object_User regiUsr = new Object_User(userName, String.valueOf(idx));
                    userRef.setValue(regiUsr);
                    goToUserList(stringObjectMap.get("uid").toString());
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(getBaseContext(),firebaseError.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("UserCreateErr", firebaseError.toString());
                }
            });
        }


    }

    private void goToUserList(String uid) {
        Intent intent = new Intent();
        intent.putExtra(View_ug_activity_main.TAG_UID, uid);
        intent.setClass(getBaseContext(),View_ug_activity_main.class);
        startActivity(intent);
    }

    private boolean isPassword2Valid(String password, String password2) {
        return password.equals(password2);
    }

    private boolean isPasswordValid(String password) {
        return password.length()>7;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }


}
