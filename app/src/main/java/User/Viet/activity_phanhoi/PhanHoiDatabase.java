package User.Viet.activity_phanhoi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PhanHoiDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DoAn.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CREATE = "CREATE TABLE PhanHoi (maPhanHoi INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, thoiGianPhanHoi DATETIME DEFAULT CURRENT_TIMESTAMP, NoiDung TEXT, media1 VARCHAR(255), media2 VARCHAR(255), media3 VARCHAR(255), soDienThoai VARCHAR(15), tenKH VARCHAR(255), maDonHang INTEGER)";

    public PhanHoiDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PhanHoiDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PhanHoiDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PhanHoi");
        onCreate(db);
    }

    // Phương thức để lưu ảnh dưới dạng chuỗi Base64
    public String saveImageToDatabase(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT); // Chuyển đổi ảnh sang chuỗi Base64
    }

    // Phương thức để thêm phản hồi vào cơ sở dữ liệu
    public long insertFeedback(String hoTen, String soDienThoai, String phanHoi, Bitmap[] images) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKH", hoTen);
        values.put("soDienThoai", soDienThoai);
        values.put("NoiDung", phanHoi);
        values.put("user_id", 1);
        values.put("maDonHang", 1);

        // Thêm ảnh vào cơ sở dữ liệu
        for (int i = 0; i < images.length; i++) {
            String mediaKey = "media" + (i + 1);
            if (i < 3) { // Giới hạn lưu trữ chỉ 3 ảnh
                values.put(mediaKey, saveImageToDatabase(images[i]));
            }
        }

        return db.insert("PhanHoi", null, values);
    }
    // Phương thức để xóa hết dữ liệu trong bảng PhanHoi
    public void deleteAllFeedback() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("PhanHoi", null, null); // Xóa hết các hàng trong bảng
        db.close();
    }

    public Cursor rawQuery(String sql){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor cs = sqLiteDatabase.rawQuery(sql,null);
        return cs;
    }
}

