package com.example.kolibreath.onit;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kolibreath on 2017/2/8.
 */

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button delete, back;
    private TextView s_textview;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onitdongtai_specific);

        delete = (Button)findViewById(R.id.delete);
        back = (Button)findViewById(R.id.back);
        s_textview = (TextView) findViewById(R.id.s_textView);
        notesDB =  new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        s_textview.setText(getIntent().getStringExtra(NotesDB.CONTENT));
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.delete:
                deleteDataBase();
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }

    }
    public void deleteDataBase(){
        dbWriter.delete(NotesDB.TABLE_NAME,"_id="+ getIntent().getIntExtra(NotesDB.ID,0),null);
    }

}
