package com.example.kolibreath.onit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.102:3000/statuses/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ServiceInterface si = retrofit.create(ServiceInterface.class);

    private EditText usersName,usersPassword;
    private RelativeLayout activity_main;

    private void initWidget(){
        usersPassword = (EditText)findViewById(R.id.users_password);
        usersName = (EditText) findViewById(R.id.users_name);
        TextView clicktoregister = (TextView) findViewById(R.id.clicktoregister);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        usersPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        clicktoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button readytologin = (Button) findViewById(R.id.login_button);
        readytologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = usersName.getText().toString();
                String userPassword = usersPassword.getText().toString();
                if ((userName.length()>=4&&userName.length()<=20)){
                    if ((userPassword.length()>=8&&usersPassword.length()<=20)) {
                    getApp().getUserText(usersName.getText().toString());
                    Intent intent = new Intent(MainActivity.this, OnitMainActivity.class);
                    startActivity(intent);
                }else{
                        Snackbar.make(activity_main,"密码长度不符合要求",Snackbar.LENGTH_INDEFINITE)
                                .setAction("前往修改", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        usersPassword.setText("");
                                    }
                                }).show();
                    }
                }else{
                    Snackbar.make(activity_main,"用户名长度不符合要求",Snackbar.LENGTH_INDEFINITE)
                            .setAction("前往修改", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    usersName.setText("");
                                }
                            }).show();
                }

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private App getApp(){
        return ((App)getApplicationContext());
    }
}
