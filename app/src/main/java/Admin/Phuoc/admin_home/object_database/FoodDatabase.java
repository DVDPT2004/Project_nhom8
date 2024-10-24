package Admin.Phuoc.admin_home.object_database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import Admin.Phuoc.admin_home.object.Food;
import Database.MainData.MainData;

public class FoodDatabase {

    private MainData databaseHelper;
    public FoodDatabase(MainData databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public boolean insertFood(Food food){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues() ;
        values.put("tenSanPham",food.getNameFood());
        values.put("tendanhmuc",food.getCategory());
        values.put("gia",food.getPrice());
        values.put("giadagiam",food.getPricecurrent());
        values.put("tinhTrang",food.getStatus());
        values.put("discount",food.getDiscount());
        values.put("anhSanPham",food.getImageMain().toString());
        ArrayList<Uri> anhMota = food.getImageSubs();
        for(int i = 0 ; i< anhMota.size(); i++){
            values.put("anhMota" + (i+1) , anhMota.get(i).toString());
        }
        values.put("moTaSanPham",food.getDescription());

        // kiểm tra ten san pham co tôn tai khong
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM SanPham WHERE tenSanPham = ?",new String[]{food.getNameFood()});
            if (cursor.getCount() > 0) {
                return false; // Email đã tồn tại
            }
            long result = db.insert("SanPham", null, values);
            return result != -1; // Trả về true nếu đăng ký thành công
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public ArrayList<Food> selectFood(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Food> foodList = new ArrayList<>();
        Cursor cursor = null;
        try {
            // Truy vấn tất cả các dòng từ bảng SanPham
            cursor = db.rawQuery("SELECT * FROM SanPham", null);
            // Kiểm tra xem có dữ liệu không
            if (cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ từng cột của bảng SanPham
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("maSanPham"));
                    String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow("tenSanPham"));
                    String madanhmuc = cursor.getString(cursor.getColumnIndexOrThrow("tendanhmuc"));
                    long gia = cursor.getLong(cursor.getColumnIndexOrThrow("gia"));
                    long giadagiam = cursor.getLong(cursor.getColumnIndexOrThrow("giadagiam"));
                    String tinhTrang = cursor.getString(cursor.getColumnIndexOrThrow("tinhTrang"));
                    int discount = cursor.getInt(cursor.getColumnIndexOrThrow("discount"));
                    String anhSanPham = cursor.getString(cursor.getColumnIndexOrThrow("anhSanPham"));
                    String moTaSanPham = cursor.getString(cursor.getColumnIndexOrThrow("moTaSanPham"));
                    ArrayList<Uri> anhMota = new ArrayList<>();
                    for (int i = 1; i <= 3; i++) { // Giả sử có tối đa 3 ảnh mô tả
                        int columnIndex = cursor.getColumnIndex("anhMota" + i);
                        if (columnIndex != -1) {
                            String anhMotaUri = cursor.getString(columnIndex);
                            if (anhMotaUri != null) {
                                anhMota.add(Uri.parse(anhMotaUri));
                            }
                        }
                    }
                    Food food1 = new Food(id,madanhmuc, tinhTrang, gia, giadagiam, tenSanPham, Uri.parse(anhSanPham), moTaSanPham, discount, anhMota);
                    foodList.add(food1);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return foodList;
    }

    public boolean deleteFood(int foodId){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();  // ghi vao dâatabase
        String whereClause = "maSanPham = ?";
        String[] whereArgs = { String.valueOf(foodId) };
        try {
            int deletedRows = db.delete("SanPham", whereClause, whereArgs); // Thực hiện câu lệnh xóa
            return deletedRows > 0; // Trả về true nếu xóa thành công
        } finally {
            db.close(); // Đóng kết nối
        }
    }

    public boolean updateFood(int foodId, Food updatedFood) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("tenSanPham", updatedFood.getNameFood());
        values.put("tendanhmuc", updatedFood.getCategory());
        values.put("gia", updatedFood.getPrice());
        values.put("giadagiam", updatedFood.getPricecurrent());
        values.put("tinhTrang", updatedFood.getStatus());
        values.put("discount", updatedFood.getDiscount());
        values.put("anhSanPham", updatedFood.getImageMain().toString());
        values.put("moTaSanPham", updatedFood.getDescription());

        ArrayList<Uri> anhMota = updatedFood.getImageSubs();
        Log.d("anhMota.size(): ",""+anhMota.size());

        // Kiểm tra danh sách ảnh mô tả
        if (anhMota.size() != 0) {
            for (int i = 0; i < anhMota.size(); i++) {
                values.put("anhMota" + (i + 1), anhMota.get(i).toString());  // Cập nhật ảnh mô tả không rỗng
            }
            // Nếu danh sách ảnh mô tả ít hơn 4, các cột dư thừa sẽ được đặt thành null
            for (int i = anhMota.size(); i < 4; i++) {
                values.putNull("anhMota" + (i + 1));
            }
        } else {
            // Đặt giá trị null cho tất cả các cột anhMota nếu danh sách rỗng
            values.putNull("anhMota1");
            values.putNull("anhMota2");
            values.putNull("anhMota3");
            values.putNull("anhMota4");
        }
        try {
            int rowsAffected = db.update("SanPham", values, "maSanPham = ?", new String[]{String.valueOf(foodId)});
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } finally {
            db.close(); // Đóng kết nối
        }
    }
}
