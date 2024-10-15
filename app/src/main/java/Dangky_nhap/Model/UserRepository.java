package Dangky_nhap.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Database.MainData.MainData;

public class UserRepository {
    private MainData databaseHelper;
    private SharedPreferences sharedPreferences;
    public UserRepository(MainData databaseHelper, Context context) {
        this.databaseHelper = databaseHelper;
        sharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
    }

    // Đăng ký người dùng
    public boolean registerUser(Userr user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        // Kiểm tra email có tồn tại không
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", new String[]{user.getEmail()});
            if (cursor.getCount() > 0) {
                return false; // Email đã tồn tại
            }
            long result = db.insert("User", null, values);
            return result != -1; // Trả về true nếu đăng ký thành công
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // Đăng nhập người dùng
    public Userr loginUser(String email, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?", new String[]{email, password});
            if (cursor.moveToFirst()) {
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                return new Userr(fullName, email, password, role); // Trả về người dùng
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }
    public boolean updatePassword(String email,String newPassword){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",newPassword);
        //cap nhat
        int rows =db.update("User",values,"email = ?",new String[]{email});
        db.close();;

        return rows >0;
    }


    // Lưu trạng thái đăng nhập vào SharedPreferences
    public void saveLoginInfo(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userEmail", email);
        editor.apply();
    }


    // Kiểm tra xem người dùng đã đăng nhập chưa
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Đăng xuất người dùng
    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    // Lấy email của người dùng từ SharedPreferences
    public String getLoggedInUserEmail() {
        return sharedPreferences.getString("userEmail", null);
    }

}
