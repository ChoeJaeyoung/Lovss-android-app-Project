package kr.co.ezenac.cjy.teamproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.model.Collect;

/**
 * Created by Administrator on 2018-02-22.
 */

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    public void deleteData(Integer id){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("delete from Collect where id = " + id);
    }

    public void insertData(Integer heart, Integer user_id, Integer img_id, String img_path, String img_content){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("insert into Collect Values (null," + heart + "," + user_id + "," + img_id + ",'" +
                img_path +"','" + img_content +"');");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Collect (id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",heart Integer, user_id INTEGER,img_id INTEGER, img_path Text, img_content Text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Collect> getCollectList(Integer user_id){
        ArrayList<Collect> list = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from Collect where user_id =" + user_id, null);
        while (cursor.moveToNext()){
            Integer id = cursor.getInt(0);
            Integer heart = cursor.getInt(1);
            Integer tmpUser_id = cursor.getInt(2);
            Integer img_id = cursor.getInt(3);
            String img_path = cursor.getString(4);
            String img_content = cursor.getString(5);

            Collect item = new Collect(id, heart, tmpUser_id, img_id, img_path, img_content);
            list.add(item);
        }
        return list;
    }
}
