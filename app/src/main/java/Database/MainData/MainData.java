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
private static final String CREATE_TABLE_Urer = "CREATE TABLE User (user_id INTEGER PRIMARY KEY AUTOINCREMENT , full_name TEXT , email TEXT UNIQUE , password TEXT , role  TEXT)";

private static final String CREATE_TABLE_SanPham = "CREATE TABLE SanPham (maSanPham INTEGER PRIMARY KEY AUTOINCREMENT, tenSanPham NVARCHAR(100), madanhmuc INT, gia INT, giadagiam INT, tinhTrang NVARCHAR(20), discount INT, maPhanHoi INT, anhSanPham TEXT, anhMota1 TEXT, anhMota2 TEXT, anhMota3 TEXT, anhMota4 TEXT, moTaSanPham TEXT)";
private static final String CREATE_TABLE_Danhmuc ="CREATE TABLE Danhmuc (maDanhMuc INTEGER PRIMARY KEY AUTOINCREMENT, tenDanhMuc NVARCHAR(50))";

private static final String CREATE_TABLE_DonHang ="CREATE TABLE DonHang (maDonHang INTEGER PRIMARY KEY AUTOINCREMENT, ngayGioDatHang DATETIME, ThanhTien INT, user_id INT, noiGiaoHang TEXT, soDienThoai NVARCHAR(15), phuongThucThanhToan NVARCHAR(20), tenKH NVARCHAR(50))";

private static final String CREATE_TABLE_PhanHoi = "CREATE TABLE PhanHoi (maPhanHoi INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, thoiGianPhanHoi DATETIME DEFAULT CURRENT_TIMESTAMP, NoiDung TEXT, media1 VARCHAR(255), media2 VARCHAR(255), media3 VARCHAR(255), soDienThoai VARCHAR(15), tenKH VARCHAR(255), maDonHang INTEGER)";

private static final String CREATE_TABLE_chitietDonHang="CREATE TABLE chiTietDonHang (maDonHang INT, maSanPham INT, soLuong INT, PRIMARY KEY (maDonHang, maSanPham))";
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
            sqLiteDatabase.execSQL(CREATE_TABLE_Urer);
            sqLiteDatabase.execSQL(CREATE_TABLE_SanPham);
            sqLiteDatabase.execSQL(CREATE_TABLE_Danhmuc);
            sqLiteDatabase.execSQL(CREATE_TABLE_DonHang);
            sqLiteDatabase.execSQL(CREATE_TABLE_chitietDonHang);
            sqLiteDatabase.execSQL(CREATE_TABLE_PhanHoi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
