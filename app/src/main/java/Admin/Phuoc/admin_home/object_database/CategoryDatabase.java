package Admin.Phuoc.admin_home.object_database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Admin.Phuoc.admin_home.object.Category;
import Database.MainData.MainData;

public class CategoryDatabase {
    private MainData databaseHelper;
    public CategoryDatabase(MainData databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public ArrayList<Category> selectCategory(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Category> categorylist = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Danhmuc", null);
            // Kiểm tra xem có dữ liệu không
            if (cursor.moveToFirst()) {
                do {
                    String tenDanhMuc = cursor.getString(cursor.getColumnIndexOrThrow("tenDanhMuc"));
                    Category category1 = new Category(tenDanhMuc);
                    categorylist.add(category1);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return categorylist;
    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tenDanhMuc",category.getNameCategory());
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM Danhmuc WHERE tenDanhMuc = ?",new String[]{category.getNameCategory()});
            if (cursor.getCount() > 0) {
                return false; // Email đã tồn tại
            }
            long result = db.insert("Danhmuc", null, values);
            return result != -1; // Trả về true nếu đăng ký thành công
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean deleteCategory(String categoryName){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();  // ghi vao dâatabase
        String whereClause = "tenDanhMuc = ?";
        String[] whereArgs = { categoryName };
        int deletedRows = db.delete("Danhmuc", whereClause, whereArgs); // thực hieện câu lệnh xóa
        db.close();
        return deletedRows > 0;
    }
}
