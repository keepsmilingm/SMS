package com.example.student;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.AddInfo;

public class AddClassActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinner;
    String stu_sex="";
    String stu_name="";
    String stu_native_place="";
    String stu_class="";
    RadioGroup sex_group;
    RadioButton nan_btn,nv_btn;
    SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        findViewById(R.id.btn_add_class).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_class:
                String class_no=((TextView)findViewById(R.id.class_no)).getText().toString();
                String class_name=((TextView)findViewById(R.id.class_name)).getText().toString();
                ContentValues class_value = new ContentValues();
                if(!class_name.equals("") && !class_name.equals("")){
                    class_value.put("C_name",class_no);
                    class_value.put("C_desc",class_name);
                    if(AddInfo("tb_class",class_value)){
                        Toast.makeText(this,"插入成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"插入失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }else{
                    Toast.makeText(this,"数据不能为空",Toast.LENGTH_SHORT).show();
                }


        }
    }
}
