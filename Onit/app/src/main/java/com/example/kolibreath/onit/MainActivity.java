package com.example.kolibreath.onit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.31.169:3000/statuses/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ServiceInterface si = retrofit.create(ServiceInterface.class);

    private EditText usersName;

    private void initWidget(){
        EditText userPassword = (EditText)findViewById(R.id.users_password);
        usersName = (EditText) findViewById(R.id.users_name);
        TextView clicktoregister = (TextView) findViewById(R.id.clicktoregister);
        userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        clicktoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void matchUserNameAndPassWord() {
        //先设置为正确 测试登录之后的界面
        final boolean matches = true;
        Button readytologin = (Button) findViewById(R.id.login_button);
        readytologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (matches) {
                    getApp().getUserText(usersName.getText().toString());
                    Intent intent = new Intent(MainActivity.this, OnitMainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        matchUserNameAndPassWord();





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
