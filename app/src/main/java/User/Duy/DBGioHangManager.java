package User.Duy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBGioHangManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbGioHang.sqlite";
    private static final int VERSION = 1;
    public DBGioHangManager(@Nullable Context context) {
        super(context,DB_NAME,null,VERSION);
    }
    public DBGioHangManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_create_table = "create table giohang (id_sp integer primary key , soluong int)";
        sqLiteDatabase.execSQL(sql_create_table);
//        String drop = "drop table tblBook";
//        sqLiteDatabase.execSQL(drop);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void execSQL(String sql) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public Cursor selectData(String sql){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
}
