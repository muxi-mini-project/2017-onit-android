package com.example.kolibreath.onit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private void initWidget(){
        EditText usersName = (EditText) findViewById(R.id.users_name);
        EditText userPassword = (EditText)findViewById(R.id.users_password);
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


        ActionBar actionBar = getSupportActionBar();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
