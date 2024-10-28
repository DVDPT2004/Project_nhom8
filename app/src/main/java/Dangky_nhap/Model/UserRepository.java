package Dangky_nhap.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Database.MainData.MainData;

public class UserRepository {
    private MainData databaseHelper;
    private SharedPreferences sharedPreferences;
    public UserRepository(MainData databaseHelper, Context context) {
        this.databaseHelper = databaseHelper;
        sharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

    }
    // Hàm mã hóa mật khẩu bằng SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Kiểm tra xem email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM User WHERE email = ?", new String[]{email});
            return cursor.getCount() > 0; // Trả về true nếu email đã tồn tại
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // Thêm người dùng vào cơ sở dữ liệu
    public boolean addUser(Userr user) {
        if (isEmailExists(user.getEmail())) {
            return false; // Email đã tồn tại, không thêm người dùng mới
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("full_name", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", hashPassword(user.getPassword()));
        values.put("role", user.getRole());

        try {
            long result = db.insert("User", null, values);
            return result != -1; // Trả về true nếu thêm thành công
        } finally {
            db.close();
        }
    }


    // Đăng nhập người dùng
    public Userr loginUser(String email, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String hashedPassword = hashPassword(password);
            cursor = db.rawQuery("SELECT * FROM User WHERE email = ? AND password = ?", new String[]{email,hashedPassword});
            if (cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id")); // Lấy user_id
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
                return new Userr(fullName, email, hashedPassword, role); // Trả về người dùng
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
        values.put("password",hashPassword(newPassword));
        //cap nhat
        int rows =db.update("User",values,"email = ?",new String[]{email});
        db.close();;

        return rows >0;
    }
    // Lưu trạng thái đăng nhập vào SharedPreferences
    public void saveLoginInfo(String email,String role)  {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userRole", role);
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
        editor.putBoolean("isLoggedIn", false);
        editor.clear();
        editor.apply();
    }

    // Lấy email của người dùng từ SharedPreferences
    public String getLoggedInUserEmail() {
        return sharedPreferences.getString("userEmail", null);
    }

    // Lấy user_id theo email
    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int userId = -1; // Giá trị mặc định nếu không tìm thấy

        try {
            cursor = db.rawQuery("SELECT user_id FROM User WHERE email = ?", new String[]{email});
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id")); // Lấy user_id
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return userId; // Trả về user_id
    }
    // Lấy role của người dùng từ SharedPreferences
    public String getLoggedInUserRole() {
        return sharedPreferences.getString("userRole", null);
    }
}
