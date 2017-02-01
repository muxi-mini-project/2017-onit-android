package com.example.kolibreath.onit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by kolibreath on 2017/2/1.
 */

public class RegisterActivity extends AppCompatActivity {

    private void initWiget(){
        EditText registerUserPasswWord = (EditText) findViewById(R.id.registerUsersPassword);
        TextView clicktoLogin = (TextView) findViewById(R.id.clicktoLogin);

        clicktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        registerUserPasswWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWiget();

    }
}
