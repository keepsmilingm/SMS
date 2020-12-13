package com.example.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.UpdateStuById;
import static com.example.student.SQliteCURD.queryStuById;

public class StuInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinner;
    TextView nameText;
    TextView jiguanText;
    TextView sexText;
    TextView classText;
    RadioGroup sex_group;
    RadioButton nan_btn,nv_btn;
    String stu_sex="";
    String stu_name="";
    String stu_native_place="";
    String stu_class="";
    SimpleCursorAdapter adapter;
    Button btn_update;
    private String StuId = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_info);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        sex_group=findViewById(R.id.sex_group);
        nan_btn=findViewById(R.id.btn_nan);
        nv_btn=findViewById(R.id.btn_nv);
        nameText=(TextView) findViewById(R.id.stu_name);
        jiguanText=findViewById(R.id.stu_native_place);
        sexText=findViewById(R.id.stu_sex);
        classText=findViewById(R.id.stu_class);
        btn_update=findViewById(R.id.btn_update_stu);
        btn_update.setOnClickListener(this);

        initNoteEditValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update_stu:
                stu_name=((TextView)findViewById(R.id.stu_name)).getText().toString();
                stu_native_place=((TextView)findViewById(R.id.stu_native_place)).getText().toString();
                stu_sex=((TextView)findViewById(R.id.stu_sex)).getText().toString();
                stu_class=((TextView)findViewById(R.id.stu_class)).getText().toString();
                System.out.println(stu_native_place);
                ContentValues student_value = new ContentValues();
                if(!stu_name.equals("") && !stu_sex.equals("") && !stu_class.equals(""))
                {
                    student_value.put("S_name",stu_name);
                    student_value.put("S_class",stu_class);
                    student_value.put("S_gender",stu_sex);
                    student_value.put("S_native_place",stu_native_place);

                    if(UpdateStuById("tb_student",StuId,student_value)){
                        Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"更新失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }else
                    Toast.makeText(this,"数据不能为空",Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 初始化编辑页面的值（如果进入该页面时存在一个id的话），比如标题，内容。
     */
    private void initNoteEditValue() {
        // 从Intent中获取id的值
        long id = this.getIntent().getLongExtra("id", -1L);
        // 如果有传入id那么id！=-1
        if (id != -1L) {
            // 使用noteId保存id
            StuId = String.valueOf(id);
            // 查询该id的笔记
            Cursor cursor = queryStuById((int) id);
            if (cursor.moveToFirst()) {
                // 将内容提取出来
                nameText.setText(cursor.getString(2));
                jiguanText.setText(cursor.getString(4));
                sexText.setText(cursor.getString(3));
                classText.setText(cursor.getString(1));

//                setSpinnerItemSelectedByValue(spinner,cursor.getString(1));
            }
        }
    }
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        stu_sex=checkedId==R.id.btn_nan?"男":"女";
//        System.out.println(stu_sex);
//
//    }
//
//    public  void setSpinnerItemSelectedByValue(Spinner spinner,String value){
//        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
//        int k= apsAdapter.getCount();
//        for(int i=0;i<k;i++){
//            if(value.equals(apsAdapter.getItem(i).toString())){
////                spinner.setSelection(i,true);// 默认选中项
//                spinner.setSelection(i);// 默认选中项
//
//                break;
//            }
//        }
//    }

}
