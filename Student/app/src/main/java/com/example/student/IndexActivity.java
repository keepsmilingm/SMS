package com.example.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        (findViewById(R.id.go_class)).setOnClickListener(this);
        (findViewById(R.id.go_student)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go_class:
                Intent intent1=new Intent("com.example.smis.class");
                startActivity(intent1);
                break;
            case R.id.go_student:

                Intent intent2=new Intent("com.example.smis.student");
                startActivity(intent2);
                break;
        }
    }
}
