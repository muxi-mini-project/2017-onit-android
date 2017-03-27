package com.example.kolibreath.onit.Activity;

import android.content.Context;
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
import android.widget.Toast;

import com.example.kolibreath.onit.Utils.App;
import com.example.kolibreath.onit.Beans.LoginUserBean;
import com.example.kolibreath.onit.Generics.ConnectionDetector;
import com.example.kolibreath.onit.Generics.LoginUser;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;

    private RelativeLayout relativeLayout;
    private EditText usersName,usersPassword;
    private RelativeLayout activity_main;

    private void initWidget(){
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
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

        final Button readytologin = (Button) findViewById(R.id.login_button);
        readytologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = usersName.getText().toString();
                String userPassword = usersPassword.getText().toString();
                ConnectionDetector.makeSnackBar(activity_main,getApplicationContext());
                if ((userName.length()>=4&&userName.length()<=20)){
                    if ((userPassword.length()>=3&&usersPassword.length()<=20)) {
                        App.storedUsername = usersName.getText().toString();
                        LoginUser user = new LoginUser(usersName.getText().toString(),
                                usersPassword.getText().toString());
                        Call<LoginUserBean> call = si.getUserToken(user);
                        call.enqueue(new Callback<LoginUserBean>() {
                            @Override
                            public void onResponse(Call<LoginUserBean> call, Response<LoginUserBean> response) {
                                LoginUserBean bean = response.body();
                                if (response.code()==200){
                                App.storedUserToken = bean.getToken();
                                Context con=MainActivity.this;
                                Toast.makeText(con , "登陆成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, OnitMainActivity.class);
                                    startActivity(intent);
                                }
                                if(response.code()==502){
                                    Snackbar.make(activity_main,"登录失败，您的账号和密码无法匹配",Snackbar.LENGTH_INDEFINITE)
                                            .setAction("前往修改", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    usersPassword.setText("");
                                                }
                                            }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginUserBean> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

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

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

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
