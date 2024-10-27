package User.Duy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_nhom8.R;

import java.text.DecimalFormat;
import java.util.List;

import Dangky_nhap.Model.UserRepository;
import Database.MainData.MainData;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<GioHang> list;
    public GioHangAdapter() {
    }

    public GioHangAdapter(List<GioHang> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private OnDataChangedListener onDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    // Gọi listener mỗi khi có sự thay đổi
    private void notifyDataChanged() {
        if (onDataChangedListener != null) {
            onDataChangedListener.onDataChanged();
        }
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);


        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        TextView txt_tensp= convertView.findViewById(R.id.txt_tensp);
        TextView txt_gia = convertView.findViewById(R.id.txt_gia);
        EditText et_soluong = convertView.findViewById(R.id.et_soluong);
        Button btn_giam = convertView.findViewById(R.id.btn_giam);
        Button btn_tang = convertView.findViewById(R.id.btn_tang);
        Button btn_xoa  = convertView.findViewById(R.id.btn_xoa);
        TextView txt_idsp = convertView.findViewById(R.id.txt_idsp);

        View finalConvertView = convertView;
        MainData db = new MainData(layoutInflater.getContext(), "mainData.sqlite",null , 1);
        db.getReadableDatabase();
        UserRepository userRepository = new UserRepository(db, layoutInflater.getContext());
        String email = userRepository.getLoggedInUserEmail();
        int user_id = userRepository.getUserIdByEmail(email);
        db.close();
        et_soluong.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                // khi nguoi dung bam enter thi se chay vao ham nay
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String sl = et_soluong.getText().toString();
                    db.getReadableDatabase();
                    String sql = "update GIOHANG set soLuong = " + sl + " where maSanPham = " + txt_idsp.getText().toString() + "and id_user = " + user_id;
                    db.ExecuteSQL(sql);
                    db.close();
                    return true;
                }
                return false;
            }
        });
        et_soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sl_string = charSequence.toString().trim();
                int sl = 0;
                if(!sl_string.isEmpty()){
                    sl = Integer.parseInt(sl_string);
                }
                if(sl > 50){
                    sl = 50;
                    et_soluong.setText(String.valueOf(sl));
                    et_soluong.setSelection(et_soluong.getText().length());
                    Toast.makeText(context, "Số lượng tối đa là 100", Toast.LENGTH_SHORT).show();
                }
                else if(sl == 0){
                    sl = 1;
                    et_soluong.setText(String.valueOf(sl));
                    et_soluong.setSelection(et_soluong.getText().length());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String sl = editable.toString().trim();
//                String sql;
//                Toast.makeText(context, txt_idsp.getText().toString(), Toast.LENGTH_SHORT).show();
//                if(sl.isEmpty()){
//                    sql = "update giohang set soluong = 1 " + " where id_sp = " + txt_idsp.getText().toString();
//                }
//                else{
//                    sql = "update giohang set soluong = " + sl + " where id_sp = " + txt_idsp.getText().toString();
//                }
//                execSQLGioHang(sql);
//                return;
            }

        });
        btn_giam.setOnClickListener(view -> {
            int sl = Integer.parseInt(et_soluong.getText().toString());
            if(sl <= 1){
                Toast.makeText(finalConvertView.getContext(), "Bạn không thể giảm số lượng, vui lòng nhấn nút xóa nếu bạn muốn xóa!", Toast.LENGTH_LONG).show();
            }
            else{
                sl--;
                et_soluong.setText(String.valueOf(sl));
                list.get(pos).setSoLuong(sl);
                db.getReadableDatabase();
                String sql = "update GIOHANG set soLuong = " + sl + " where maSanPham = " + txt_idsp.getText().toString() + " and id_user = " + user_id;
                db.ExecuteSQL(sql);
                db.close();
                notifyDataSetChanged();
                notifyDataChanged();
            }

        });
        btn_tang.setOnClickListener(view -> {
            int sl = Integer.parseInt(et_soluong.getText().toString());
            if(sl > 50){
                Toast.makeText(finalConvertView.getContext(), "Bạn không thể đặt cùng 1 loại mặt hàng số lượng quá 50!", Toast.LENGTH_LONG).show();
            }
            else{
                sl++;
                et_soluong.setText(String.valueOf(sl));
                list.get(pos).setSoLuong(sl);
                db.getReadableDatabase();
                String sql = "update GIOHANG set soLuong = " + sl + " where maSanPham = " + txt_idsp.getText().toString() + " and id_user = " + user_id;
                db.ExecuteSQL(sql);
                db.close();
                Toast.makeText(finalConvertView.getContext(), sql, Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
                notifyDataChanged();
            }

        });
        btn_xoa.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này hay không");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.getReadableDatabase();
                    String sql = "delete from GIOHANG where maSanPham = " + txt_idsp.getText().toString() + " and id_user = " + user_id;
                    db.ExecuteSQL(sql);
                    db.close();
                    list.remove(pos);
                    Toast.makeText(finalConvertView.getContext(), sql, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    notifyDataChanged();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        });

        GioHang giohang = list.get(pos);
//        if (giohang.getAnhsp() != null) {
//            imgSanPham.setImageURI(giohang.getAnhsp());
//        } else {
//            imgSanPham.setImageResource(R.drawable.banhmi); // Ảnh mặc định nếu URI trống
//        }
//        if (giohang.getAnhsp() != null) {
//            Glide.with(context)
//                    .load(giohang.getAnhsp()) // URL hoặc URI của ảnh
//                    .into(imgSanPham);
//        } else {
//            imgSanPham.setImageResource(R.drawable.banhmi); // Ảnh mặc định nếu URI trống
//        }
        txt_tensp.setText(giohang.getTensp().toString());
        et_soluong.setText(String.valueOf(giohang.getSoLuong()));
        txt_gia.setText(formatCurrency(giohang.getGia()));
        txt_idsp.setText(String.valueOf(giohang.getIdSanPham()));
        return convertView;
    }
    public String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " VNĐ";
    }
}



