package Database.MainData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MainData extends SQLiteOpenHelper {

//    CREATE MAINDATA
private static final String DATABASE_NAME = "mainData.sqlite";
private static final int DATABASE_VERSION = 1;


// create table
//  vd :
//private static final String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS table1 (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//        "name TEXT NOT NULL);";


    public MainData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public  void ExecuteSQL(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
    }
    public Cursor SelectData(String sql){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c  = sqLiteDatabase.rawQuery(sql,null);
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
          // Thực hiện câu lệnh tạo bảng
          //  sqLiteDatabase.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
