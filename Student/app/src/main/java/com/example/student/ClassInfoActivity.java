package com.example.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.UpdateClassById;
import static com.example.student.SQliteCURD.queryClassById;

public class ClassInfoActivity extends AppCompatActivity implements View.OnClickListener{
   
    TextView nameText;
    TextView descText;
    String class_name="";
    String class_desc="";
    Button btn_update_class;
    private String ClassId = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

     
        nameText=(TextView) findViewById(R.id.class_name);
        descText=findViewById(R.id.class_desc);
        btn_update_class=findViewById(R.id.btn_update_class);
        btn_update_class.setOnClickListener(this);
        initNoteEditValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update_class:
                class_name=((TextView)findViewById(R.id.class_name)).getText().toString();
                class_desc=((TextView)findViewById(R.id.class_desc)).getText().toString();
                System.out.println(class_desc);
                ContentValues class_value = new ContentValues();
                if(!class_name.equals("") && !class_desc.equals("") )
                {
                    class_value.put("C_name",class_name);
                    class_value.put("C_desc",class_desc);

                    if(UpdateClassById("tb_class",ClassId,class_value)){
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
            ClassId = String.valueOf(id);
            // 查询该id的笔记
            Cursor cursor = queryClassById((int) id);
            if (cursor.moveToFirst()) {
                // 将内容提取出来
                nameText.setText(cursor.getString(1));
                descText.setText(cursor.getString(2));
            }
        }
    }


}
