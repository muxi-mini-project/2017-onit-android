package com.example.kolibreath.onit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kolibreath.onit.App;
import com.example.kolibreath.onit.Beans.RegisterBean;
import com.example.kolibreath.onit.Generics.RegisterUser;
import com.example.kolibreath.onit.InterfaceAdapter.ServiceInterface;
import com.example.kolibreath.onit.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.kolibreath.onit.R.id.registerXML;

/**
 * Created by kolibreath on 2017/2/1.
 */

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText registeruserName;
    private EditText confirmUserPassword;
    private EditText registerUserPasswWord;
    private TextView clicktoLogin;
    private Button registerforsure;
    private boolean textResult1 = false;


    private RelativeLayout register;

    private String userName;
    private String userPassword;
    private String confirmPassword;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    Retrofit retrofit;
    ServiceInterface si;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.12.214:5050/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        si = retrofit.create(ServiceInterface.class);

        registeruserName = (EditText) findViewById(R.id.registerUsername1);
        confirmUserPassword = (EditText) findViewById(R.id.registerConfirmPassword3);
        registerUserPasswWord = (EditText) findViewById(R.id.registerUsersPassword2);
        clicktoLogin = (TextView) findViewById(R.id.clicktoLogin);

        register = (RelativeLayout) findViewById(registerXML);
        registerforsure = (Button) findViewById(R.id.registerForSure);
        registerforsure.setOnClickListener(this);



        confirmUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        registerUserPasswWord.setTransformationMethod(PasswordTransformationMethod.getInstance());





    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerForSure:
        userName = registeruserName.getText().toString();
        userPassword = registerUserPasswWord.getText().toString();
        confirmPassword = confirmUserPassword.getText().toString();
                if (userName.length() >= 4 && userName.length() < 20) {
                if ((userPassword.length()>=8&&userPassword.length()<16)){
                    if (userPassword.equals(confirmPassword)){
                        RegisterUser user = new RegisterUser(registeruserName.getText().toString(),registerUserPasswWord.getText().toString());
                        Call<RegisterBean> call = si.getRegisterInfo(user);
                        call.enqueue(new Callback<RegisterBean>() {
                            @Override
                            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                                RegisterBean bean = response.body();
                                App.storedUsername = userName;
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<RegisterBean> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(register,"两次输入的密码不一致",Snackbar.LENGTH_INDEFINITE).
                                setAction("重新输入", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        confirmUserPassword.setText("");
                                    }
                                }).show();
                    }
                }else {
                    Snackbar.make(register,"密码长度不符合要求",Snackbar.LENGTH_INDEFINITE).
                            setAction("重新输入", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    registerUserPasswWord.setText("");
                                }
                            }).show();
                }
        }else{
                    Snackbar.make(register,"用户名长度不符合要求",Snackbar.LENGTH_INDEFINITE).
                            setAction("重新输入", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    registeruserName.setText("");
                                }
                            }).show();
                }

    }
    }
    private App getApp(){
        return  ((App)getApplicationContext());
    }
}

