package com.example.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.student.SQliteCURD.queryLogin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;
    String user,psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);
        login=findViewById(R.id.btn_login);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                user=((TextView)findViewById(R.id.user)).getText().toString();
                psw=((TextView)findViewById(R.id.psw)).getText().toString();
                findViewById(R.id.psw);
                if(user.equals("") || psw.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入完整信息！",Toast.LENGTH_LONG).show();
                }else{
                    if(queryLogin(user,psw)){
                        Intent intent=new Intent("com.example.smis.index");
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"用户名或密码错误，请重试！",Toast.LENGTH_LONG).show();
                    }
                }



        }
    }
}
