package Admin.Phuoc.admin_order.object_database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Admin.Phuoc.admin_order.object.Order;
import Database.MainData.MainData;

public class OrderDatabase {

    private MainData databaseHelper;
    public OrderDatabase(MainData databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public ArrayList<Order> selectOrder(String sql){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Order> orderList = new ArrayList<>();
        int maDonHang,userId,tinhTrangDonHang;
        long thanhTien;
        String ngayGioDatHang,thucDon = "",tenKhachHang,noiGiaoHang,soDienThoai,phuongThucThanhToan,displayTime;

        Cursor cursor = null, cursor1 = null;
        try {
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                maDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));
                thanhTien = cursor.getLong(cursor.getColumnIndexOrThrow("ThanhTien"));
                ngayGioDatHang = cursor.getString(cursor.getColumnIndexOrThrow("ngayGioDatHang"));
                tenKhachHang = cursor.getString(cursor.getColumnIndexOrThrow("tenKH"));
                userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                noiGiaoHang = cursor.getString(cursor.getColumnIndexOrThrow("noiGiaoHang"));
                soDienThoai = cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai"));
                phuongThucThanhToan = cursor.getString(cursor.getColumnIndexOrThrow("phuongThucThanhToan"));
                tinhTrangDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("tinhTrangDonHang"));
                displayTime = formatFeedbackTime(ngayGioDatHang);
                thucDon = "";
                try{
                    cursor1 = db.rawQuery("SELECT SanPham.tenSanPham , chiTietDonHang.soLuong FROM SanPham JOIN chiTietDonHang on SanPham.maSanPham = chiTietDonHang.maSanPham where maDonHang = "+ maDonHang,null);
                    while (cursor1.moveToNext()){
                        String tenMon = cursor1.getString(cursor1.getColumnIndexOrThrow("tenSanPham"));
                        int soLuong = cursor1.getInt(cursor1.getColumnIndexOrThrow("soLuong"));
                        thucDon = thucDon +  tenMon + ": " + soLuong +  "\n";
                    }
                }finally {
                    if (cursor1 != null) {
                        cursor1.close();
                    }
                }
                String tinhTrang = "";
                if(tinhTrangDonHang == 0){
                    tinhTrang = "Chưa chuẩn bị";
                } else if (tinhTrangDonHang == 1) {
                    tinhTrang = "Đang giao hàng";
                }else if (tinhTrangDonHang == 2) {
                    tinhTrang = "Giao hàng thành công";
                }else if (tinhTrangDonHang == 3) {
                    tinhTrang = "Giao hàng thất bại";
                }
                Order order1 = new Order(phuongThucThanhToan,thanhTien,noiGiaoHang,tinhTrang,thucDon,tenKhachHang,soDienThoai,displayTime,maDonHang);
                orderList.add(order1);
            }
        }catch (Exception e) {
            Log.e("Error", "Error while fetching feedback", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return orderList;
    }

    public boolean updateOrder(int maDonHang, int updatedOrderStatus) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            String sql = "UPDATE DonHang SET " + "tinhTrangDonHang = ? " + " WHERE maDonHang = ?";
            db.execSQL(sql, new Object[]{
                    updatedOrderStatus,
                    maDonHang
            });
            return true;
        } finally {
            db.close();
        }
    }

    private String formatFeedbackTime(String ngayGioDatHang) {
        // Định dạng ban đầu
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        // Định dạng đầu ra mong muốn
        SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        try {
            // Chuyển đổi từ chuỗi sang Date
            Date date = originalFormat.parse(ngayGioDatHang);
            // Định dạng lại Date thành chuỗi theo định dạng mong muốn
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ngayGioDatHang; // Trả về mặc định nếu không xác định được
    }
}
