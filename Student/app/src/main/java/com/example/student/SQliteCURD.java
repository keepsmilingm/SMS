package com.example.student;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQliteCURD {

    private static SQLiteDatabase db=null;  //定义数据库
    private static boolean flag=false;
    static{
        db=SQLiteDatabase.openOrCreateDatabase("data/data/com.example.student/student",null);
        String sql_class="create table tb_class( _id integer primary key autoincrement,C_name varchar(100) unique ,C_desc varchar(100))";
        String sql_admin="create table tb_admin( _id integer primary key autoincrement,A_name varchar(100),A_password varchar(100))" ;
        String sql_student="create table tb_student( _id integer primary key autoincrement,\n" +
                "  S_class varchar(100) NOT NULL,\n" +
                "  S_name varchar(50) NOT NULL,\n" +
                "  S_gender varchar(4) NOT NULL,\n" +
                "  S_native_place varchar(50) ,\n" +
                "  S_birthday date ,FOREIGN KEY (S_class) REFERENCES tb_class (C_name) ON DELETE CASCADE ON UPDATE CASCADE)";

        try{
            db.rawQuery("select count(1) from tb_class",null);
            System.out.println("数据库已存在");
        }catch(Exception e){

           db.execSQL(sql_class);
           db.execSQL(sql_admin);
           db.execSQL(sql_student);
           db.execSQL("INSERT INTO tb_admin VALUES (1, 'admin', '0000');");

           db.execSQL("INSERT INTO tb_class VALUES (1,'16A','计算机科学与技术');");
           db.execSQL("INSERT INTO tb_class VALUES (2,'16B','软件工程');");

           db.execSQL("INSERT INTO tb_student VALUES (1001, '16A', '张三', '女', '云南昭通', '1997-8-1');");

           db.execSQL("INSERT INTO tb_student VALUES (1005, '16C', '李四', '女', '云南昆明', '1998-6-4');");

            System.out.println("创建数据库完毕");

        }
    }

    public static SQLiteDatabase getDb(){
        return db;
    }

    public static Cursor queryAll(String table){
        return db.query(table,null,null,null,null,null,null);
    }

    public static Cursor queryStuByS_class(String table ,String S_class){
        Cursor cur=null;
        try{
            cur=db.query(table,null,"S_class=?",new String[]{S_class},null,null,null);
            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }
    public static Cursor queryClassByName(String table ,String C_name){
        Cursor cur=null;
        try{

            cur=db.query(table,null,"C_name=?",new String[]{C_name},null,null,null);

            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }
    public static Cursor queryClassById(Integer id){
        Cursor cur=null;
        try{
            cur=db.query("tb_class",null,"_id=?",new String[]{id.toString()},null,null,null);
            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }
    public static Cursor queryStuByName(String table ,String S_name){
        Cursor cur=null;
        try{

            cur=db.query(table,null,"S_name=?",new String[]{S_name},null,null,null);

            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }

    public static Cursor queryStuById(String table ,String S_id){
        Cursor cur=null;
        try{
            cur=db.query(table,null,"_id=?",new String[]{S_id},null,null,null);
            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }
    public static Cursor queryStuById(Integer S_id){
        Cursor cur=null;
        try{
            cur=db.query("tb_student",null,"_id=?",new String[]{S_id.toString()},null,null,null);
            return cur;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cur;
    }

    public static boolean deleteById(String table,String id){
        if(id==null)
            return false;
        return db.delete(table,"_id=?",new String[]{id.toString()})>0;
    }
    public static boolean deleteStuById(Integer id){

        return db.delete("tb_student","_id=?",new String[]{id.toString()})>0;
    }
    public static boolean deleteClassById(Integer id){

       return(db.delete("tb_class","_id=?",new String[]{id.toString()})>0 &&
               db.delete("tb_student","S_class=?",new String[]{id.toString()})>0) ;
    }
    public static boolean deleteStuByClass(String name){

        return(db.delete("tb_student","S_class=?",new String[]{name})>0) ;
    }

    public static boolean UpdateStuById(String table, String id, ContentValues values){
        if(id==null)
            return false;
        return db.update(table,values,"_id=?",new String[]{id.toString()})>0;

    }
    public static boolean UpdateClassById(String table, String id, ContentValues values){
        if(id==null)
            return false;
        return db.update(table,values,"_id=?",new String[]{id.toString()})>0;

    }

    public static boolean AddInfo(String table, ContentValues values){
        return db.insert(table,null,values)>0;
    }

    public static boolean queryLogin(String user,String psw){
        Cursor cur=null;
        try{
            cur=db.query("tb_admin",null,"A_name=? AND A_password=?",new String[]{user,psw},null,null,null);

            return cur.getCount()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
