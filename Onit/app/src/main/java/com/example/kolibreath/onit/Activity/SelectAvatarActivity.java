package com.example.kolibreath.onit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kolibreath.onit.R;

/**
 * Created by kolibreath on 2017/2/8.
 */

public class SelectAvatarActivity extends AppCompatActivity implements View.OnClickListener{

    private Button select,next;
    private ImageView imageView;
    private int clickStatus;
    private Toolbar toolbar;
    private int imageIndex;
    private Intent imageIntent;
    private int imageIndexRest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_avatar);
        imageIndex = 1;

        clickStatus =0;
        initWidget();
    }
    private void initWidget(){
        select = (Button) findViewById(R.id.selectTheOne);
        next = (Button) findViewById(R.id.selectNextne);

        toolbar = (Toolbar) findViewById(R.id.toolbar_for_selectAvatar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView  = (ImageView) findViewById(R.id.displayAvatar);


        next.setOnClickListener(this);
        select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "onClick: ");
        switch (v.getId()){
            case R.id.selectTheOne:
                Log.d("selectthisone", "onClick: ");
                Intent imageIntent = new Intent(SelectAvatarActivity.this,UserMainActivity.class);
              if(imageIndex<5||imageIndex==5) {
                    switch (imageIndex) {
                        case 2:
                            imageIntent.putExtra("key", R.drawable.java);
                            break;
                        case 3:
                            imageIntent.putExtra("key", R.drawable.cplus);
                            break;
                        case 4:
                            imageIntent.putExtra("key", R.drawable.css);
                            break;
                        case 5:
                            imageIntent.putExtra("key", R.drawable.python);
                            break;
                    }
                }
                if (imageIndex > 5 ) {
                    imageIndexRest =imageIndex%5;
                    switch (imageIndexRest) {
                        case 1:
                            imageIntent.putExtra("key", R.drawable.php);
                            break;
                        case 2:
                            imageIntent.putExtra("key", R.drawable.java);
                            break;
                        case 3:
                            imageIntent.putExtra("key", R.drawable.cplus);
                            break;
                        case 4:
                            imageIntent.putExtra("key", R.drawable.css);
                            break;
                        case 0:
                            imageIntent.putExtra("key",R.drawable.python);
                            break;
                    }
                }
                startActivity(imageIntent);
                        break;
                        case R.id.selectNextne:
                            Log.d("selectNextone", "onClick: ");
                            switch (clickStatus) {
                                case 0:
                                    imageView.setImageResource(R.drawable.java);
                                    clickStatus++;
                                    imageIndex++;
                                    break;
                                case 1:
                                    imageView.setImageResource(R.drawable.cplus);
                                    clickStatus++;
                                    imageIndex++;
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.css);
                                    clickStatus++;
                                    imageIndex++;
                                    break;
                                case 3:
                                    imageView.setImageResource(R.drawable.python);
                                    clickStatus++;
                                    imageIndex++;
                                    break;
                                case 4:
                                    imageView.setImageResource(R.drawable.php);
                                    clickStatus = 0;
                                    imageIndex++;
                                    break;
                            }
                            break;
                    }
                }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_avatar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

