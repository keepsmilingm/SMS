package com.example.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.deleteClassById;
import static com.example.student.SQliteCURD.deleteStuByClass;
import static com.example.student.SQliteCURD.queryClassById;

public class ClassListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Cursor listItemCursor = null;
    private Button btn_add;
    private Button btn_searh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        btn_add=findViewById(R.id.btn_add_class);
        btn_searh=findViewById(R.id.btn_search_class);
        btn_add.setOnClickListener(this);
        btn_searh.setOnClickListener(this);
        SQliteCURD db=new SQliteCURD();
        db.getDb();
        System.out.println();
        setadapter("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search_class:
                TextView data=findViewById(R.id.search_data);
                String datas=data.getText().toString();
                if(!datas.equals("")){
                    setadapter(datas);
                }else{
                    setadapter("");
                }
                break;
            case R.id.btn_add_class:
                Intent addintent=new Intent("com.example.smis.add_class");
                startActivity(addintent);



//



        }
    }
    private void setadapter(String data){
        if(data.equals("")){
            listItemCursor = SQliteCURD.queryAll("tb_class");
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(ClassListActivity.this,
                    R.layout.class_adapter, listItemCursor, new String[] {"_id","C_name","C_desc"},
                    new int[] { R.id.c_id, R.id.c_name,R.id.c_desc},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            ListView listView=findViewById(R.id.listClass);
            listView.setOnItemClickListener(this);
            listView.setAdapter(adapter);
            initListNoteListener();
        }else{
            listItemCursor = SQliteCURD.queryClassByName("tb_class",data);
            if(listItemCursor!=null){
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(ClassListActivity.this,
                        R.layout.class_adapter, listItemCursor, new String[] {"_id","C_name","C_desc"},
                        new int[] { R.id.c_id, R.id.c_name,R.id.c_desc},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                ((ListView) this.findViewById(R.id.listClass)).setAdapter(adapter);
                initListNoteListener();
            }else{
                Toast.makeText(this,"无数据",Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ListView listView = (ListView) parent;
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);
        String personid=cursor.getString(cursor.getColumnIndex("_id"));
        Toast.makeText(getApplicationContext(), personid, Toast.LENGTH_LONG).show();

    }

    /**
     * 初始化笔记列表的长按和点击事件
     */
    private void initListNoteListener() {
        // 长按删除
        ((ListView) this.findViewById(R.id.listClass))
                .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, final long id) {
                        new AlertDialog.Builder(ClassListActivity.this)
                                .setTitle("提示框")
                                .setMessage("确认删除该记录？？")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0,int arg1) {

                                                //删除后刷新列表
                                                boolean bool2=false;
                                                Cursor cursor =queryClassById((int)id);
                                                int c=cursor.getCount();
                                                System.out.println(c);
                                                if (cursor.moveToFirst()) {
                                                    // 将内容提取出来
                                                    String class_name=cursor.getString(1);
                                                    System.out.println(class_name);
                                                    bool2=deleteStuByClass(class_name);
                                                    System.out.println(bool2);
                                                }
                                                System.out.println(bool2);

                                                boolean bool1=deleteClassById((int)id);


                                                if(bool1 && bool2){

                                                    Toast.makeText(ClassListActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(ClassListActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                                                }
                                                setadapter("");

//                                                if( deleteClassById((int) id)){
//                                                    Toast.makeText(
//                                                            ClassListActivity.this,
//                                                            "删除成功！！",
//                                                            Toast.LENGTH_LONG)
//                                                            .show();
//                                                    setadapter("");
//                                                }else{
//                                                    Toast.makeText(
//                                                            ClassListActivity.this,
//                                                            "删除失败！！",
//                                                            Toast.LENGTH_LONG)
//                                                            .show();
//                                                    setadapter("");
//                                                }

                                                ClassListActivity.this.onResume();
                                            }
                                        }).setNegativeButton("取消", null).show();
                        return true;
                    }
                });

        //点击进行修改操作
        ((ListView) this.findViewById(R.id.listClass))
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent("com.example.smis.class_info");
                        //将id数据放置到Intent
                        in.putExtra("id", id);
                        System.out.println(id);
                        startActivity(in);
                    }
                });

    }


}
