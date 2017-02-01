package com.example.kolibreath.onit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by kolibreath on 2017/2/1.
 */

public class OnitMainActivity extends AppCompatActivity {

    private void initWiget(){
        ImageButton gototheUserCenter = (ImageButton) findViewById(R.id.userCenter);
        FloatingActionButton firstFAB = (FloatingActionButton) findViewById(R.id.firstFAB);
        final FloatingActionButton secondFAB = (FloatingActionButton) findViewById(R.id.secondFAB);
        final FloatingActionButton thirdFAb = (FloatingActionButton) findViewById(R.id.thirdFAB);
        final TextView assign_onit = (TextView) findViewById(R.id.assign_onit);
        final TextView search_for_bff  = (TextView) findViewById(R.id.search_potential_bff);

        gototheUserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(this,);
            }
        });
        firstFAB.setOnClickListener(new View.OnClickListener() {
            int clickState = 1;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.firstFAB:
                    if(clickState==1){
                        secondFAB.setVisibility(View.VISIBLE);
                        thirdFAb.setVisibility(View.VISIBLE);
                        assign_onit.setVisibility(View.VISIBLE);
                        search_for_bff.setVisibility(View.VISIBLE);
                        clickState = 0;
                        break;
                    }
                    if(clickState==0){
                        secondFAB.setVisibility(View.GONE);
                        thirdFAb.setVisibility(View.GONE);
                        assign_onit.setVisibility(View.GONE);
                        search_for_bff.setVisibility(View.GONE);
                        clickState=1;
                        break;
                    }
                        break;

                }
            }
        });
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onitdongtai);

        initWiget();
    }
}
