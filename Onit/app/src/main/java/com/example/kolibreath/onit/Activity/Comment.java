package com.example.kolibreath.onit.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kolibreath.onit.Utils.App;
import com.example.kolibreath.onit.Generics.CommentClass;
import com.example.kolibreath.onit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kolibreath on 2017/2/11.
 */

public class Comment extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ListView commentListView;
    private List<CommentClass> commentList = new ArrayList<>();
    private CommentAdapter cAdapter;
    private ImageView sendMessage;
    private EditText sendComment;

    private void initWidget(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_for_comments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentListView = (ListView) findViewById(R.id.commentListView);
        sendMessage = (ImageView) findViewById(R.id.clicktoSendMessage);
        sendComment = (EditText) findViewById(R.id.sendComments);

        sendMessage.setOnClickListener(this);
    }

    private void initData(){
        CommentClass cs1 = new CommentClass("唐相儒","石泽远好帅怎么办？？？！看来我需要人搞定他，，",
                "2017/2/25",R.drawable.python);
        commentList.add(cs1);

        CommentClass cs2 = new CommentClass("肖遥","石泽远真是蠢得不要不要的，，，，",
                "2017/2/25",R.drawable.java);
        commentList.add(cs2);

        CommentClass cs3 = new CommentClass("蔡茹芸","你们说，我看就好",
                "2017/2/29",R.drawable.css);
        commentList.add(cs3);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initWidget();
        initData();

        cAdapter = new CommentAdapter(this,R.layout.comment_item,commentList);
        commentListView.setAdapter(cAdapter);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clicktoSendMessage:
                CommentClass cs = new CommentClass(getApp().storedUsername,sendComment.getText().toString(),
                        getDate(),R.drawable.avatar_default);
                commentList.add(cs);
                sendComment.setText("");
        }
    }

    class CommentAdapter extends ArrayAdapter<CommentClass> {

        private int resourceId;
        public CommentAdapter(Context context, int resourceId, List<CommentClass> objects){
            super(context,resourceId,objects);
            this.resourceId = resourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentClass cs  = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            CircleImageView userAvatar = (CircleImageView) view.findViewById(R.id.commentUserAvatar);
            TextView commentUserCommentTime = (TextView) view.findViewById(R.id.commentUserCommentTime);
            TextView commentUserName = (TextView) view.findViewById(R.id.commentUserName);
            TextView commentUserContent = (TextView) view.findViewById(R.id.commentUserContent);

            userAvatar.setImageResource(cs.getCommentuserAvatar());
            commentUserCommentTime.setText(cs.getCommentTime());
            commentUserName.setText(cs.getCommentUserName());
            commentUserContent.setText(cs.getCommentContents());

            return view;

        }
    }
    private String getDate() {
        String datestr = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        datestr = format.format(date);
        return datestr;
    }

    private App getApp(){
        return ((App)getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
}

