package com.example.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.AddInfo;
import static com.example.student.SQliteCURD.queryAll;

public class AddStuActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {
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
        setContentView(R.layout.activity_add_stu);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        findViewById(R.id.btn_add_stu).setOnClickListener(this);
        spinner=findViewById(R.id.stu_class);
        sex_group=findViewById(R.id.sex_group);
        nan_btn=findViewById(R.id.btn_nan);
        nv_btn=findViewById(R.id.btn_nv);
        sex_group.setOnCheckedChangeListener(this);
        Cursor listItemCursor=queryAll("tb_class");
        String [] arr;
        adapter = new SimpleCursorAdapter(AddStuActivity.this,
                R.layout.spinner, listItemCursor, new String[] {"C_name"},
                new int[] { R.id.for_spinner},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ((Spinner) this.findViewById(R.id.stu_class)).setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) spinner.getItemAtPosition(position);
                stu_class=cursor.getString(cursor.getColumnIndex("C_name"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_stu:
                stu_name=((TextView)findViewById(R.id.stu_name)).getText().toString();
                stu_native_place=((TextView)findViewById(R.id.stu_native_place)).getText().toString();
                System.out.println(stu_native_place);
                ContentValues student_value = new ContentValues();
                if(!stu_name.equals("") && !stu_sex.equals("") && !stu_class.equals(""))
                {
                    student_value.put("S_name",stu_name);
                    student_value.put("S_class",stu_class);
                    student_value.put("S_gender",stu_sex);
                    student_value.put("S_native_place",stu_native_place);

                    if(AddInfo("tb_student",student_value)){
                        Toast.makeText(this,"插入成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"插入失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }else
                    Toast.makeText(this,"数据不能为空",Toast.LENGTH_SHORT).show();

        }
    }
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        stu_sex=checkedId==R.id.btn_nan?"男":"女";
        System.out.println(stu_sex);

    }
}
