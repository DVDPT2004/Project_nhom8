package Admin.Phuoc.admin_feedback.object_database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Admin.Phuoc.admin_feedback.object.Feedback;
import Database.MainData.MainData;

public class FeedbackDatabase {

    private MainData databaseHelper;
    public FeedbackDatabase(MainData databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    public ArrayList<Feedback> selectFeedbackNoResponse(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM PhanHoi where trangThai = 0 order by thoiGianPhanHoi DESC", null);
            while (cursor.moveToNext()) {
                String noiDungAdminPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungAdminPhanHoi"));
                int trangthai = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));
                String thoigianphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("thoiGianPhanHoi"));
                String tenkhachphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("tenKH"));
                int maphanhoi = cursor.getInt(cursor.getColumnIndexOrThrow("maPhanHoi"));
                int maDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));
                String noiDungKhachPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungKhachPhanHoi"));
//                byte[] media1 = cursor.getBlob(cursor.getColumnIndexOrThrow("media1"));
//                byte[] media2 = cursor.getBlob(cursor.getColumnIndexOrThrow("media2"));
//                byte[] media3 = cursor.getBlob(cursor.getColumnIndexOrThrow("media3"));
                byte[] media1 = cursor.getBlob(5);
                byte[] media2 = cursor.getBlob(6);
                byte[] media3 = cursor.getBlob(7);
                String displayTime = formatFeedbackTime(thoigianphanhoi);

                Feedback feedback1 = new Feedback(maDonHang,noiDungAdminPhanHoi,trangthai,displayTime,tenkhachphanhoi,maphanhoi,noiDungKhachPhanHoi,media1,media2,media3);
                feedbackList.add(feedback1);
            }
        }catch (Exception e) {
            Log.e("Error", "Error while fetching feedback", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return feedbackList;
    }

    public ArrayList<Feedback> selectFeedbackResponse(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM PhanHoi where trangThai = 1 order by thoiGianPhanHoi DESC", null);
            while (cursor.moveToNext()) {
                String noiDungAdminPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungAdminPhanHoi"));
                int trangthai = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));
                String thoigianphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("thoiGianPhanHoi"));
                String tenkhachphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("tenKH"));
                int maphanhoi = cursor.getInt(cursor.getColumnIndexOrThrow("maPhanHoi"));
                String noiDungKhachPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungKhachPhanHoi"));
                byte[] media1 = cursor.getBlob(cursor.getColumnIndexOrThrow("media1"));
                byte[] media2 = cursor.getBlob(cursor.getColumnIndexOrThrow("media2"));
                byte[] media3 = cursor.getBlob(cursor.getColumnIndexOrThrow("media3"));
                int maDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));

                String displayTime = formatFeedbackTime(thoigianphanhoi);

                Feedback feedback1 = new Feedback(maDonHang,noiDungAdminPhanHoi,trangthai,displayTime,tenkhachphanhoi,maphanhoi,noiDungKhachPhanHoi,media1,media2,media3);
                feedbackList.add(feedback1);
            }
        }catch (Exception e) {
            Log.e("Error", "Error while fetching feedback", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return feedbackList;
    }

    public ArrayList<Feedback> selectFeedback(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM PhanHoi order by thoiGianPhanHoi DESC", null);
            while (cursor.moveToNext()) {
                String noiDungAdminPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungAdminPhanHoi"));
                int trangthai = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));
                String thoigianphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("thoiGianPhanHoi"));
                String tenkhachphanhoi = cursor.getString(cursor.getColumnIndexOrThrow("tenKH"));
                int maphanhoi = cursor.getInt(cursor.getColumnIndexOrThrow("maPhanHoi"));
                String noiDungKhachPhanHoi = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungKhachPhanHoi"));
                byte[] media1 = cursor.getBlob(cursor.getColumnIndexOrThrow("media1"));
                byte[] media2 = cursor.getBlob(cursor.getColumnIndexOrThrow("media2"));
                byte[] media3 = cursor.getBlob(cursor.getColumnIndexOrThrow("media3"));
                int maDonHang = cursor.getInt(cursor.getColumnIndexOrThrow("maDonHang"));

                String displayTime = formatFeedbackTime(thoigianphanhoi);

                Feedback feedback1 = new Feedback(maDonHang,noiDungAdminPhanHoi,trangthai,displayTime,tenkhachphanhoi,maphanhoi,noiDungKhachPhanHoi,media1,media2,media3);
                feedbackList.add(feedback1);
            }
        }catch (Exception e) {
            Log.e("Error", "Error while fetching feedback", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return feedbackList;
    }

    public boolean updateResponse(int idFeedback, Feedback updateFeedback){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            String sql = "UPDATE PhanHoi SET " + "trangThai = ?, " + "NoiDungAdminPhanHoi = ?, " + "thoiGianPhanHoi = DATETIME(CURRENT_TIMESTAMP, '+7 hours') " + "WHERE maPhanHoi = ?";
            db.execSQL(sql, new Object[]{
                    updateFeedback.getTrangThai(),
                    updateFeedback.getNoiDungAdminPhanHoi(),
                    idFeedback
            });
            return true;
        } finally {
            db.close();
        }
    }

    private String formatFeedbackTime(String thoigianphanhoi) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            // Chuyển đổi từ chuỗi sang Date
            Date date = originalFormat.parse(thoigianphanhoi);
            // Lấy thời gian hiện tại
            Calendar currentCalendar = Calendar.getInstance();
            Calendar feedbackCalendar = Calendar.getInstance();
            feedbackCalendar.setTime(date);

            // Tính số ngày chênh lệch
            long difference = currentCalendar.getTimeInMillis() - feedbackCalendar.getTimeInMillis();
            long daysDifference = difference / (1000 * 60 * 60 * 24); // Chuyển đổi từ milliseconds sang days

            // Kiểm tra xem phản hồi có trong ngày hiện tại không
            if (currentCalendar.get(Calendar.YEAR) == feedbackCalendar.get(Calendar.YEAR) &&
                    currentCalendar.get(Calendar.DAY_OF_YEAR) == feedbackCalendar.get(Calendar.DAY_OF_YEAR)) {
                // Nếu trong ngày hiện tại, hiển thị HH:mm
                return targetFormat.format(date);
            } else if (daysDifference == 1) {
                return "1 ngày trước";
            } else if (daysDifference == 2) {
                return "2 ngày trước";
            } else if (daysDifference >= 3) {
                return dateFormat.format(date); // Trả về định dạng DD/MM/YYYY
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return thoigianphanhoi; // Trả về mặc định nếu không xác định được
    }
}
