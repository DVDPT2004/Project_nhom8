package User.Duy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.List;
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


        View finalConvertView = convertView;
        btn_giam.setOnClickListener(view -> {
            int sl = Integer.parseInt(et_soluong.getText().toString());
            if(sl == 1){
                Toast.makeText(finalConvertView.getContext(), "Bạn không thể giảm số lượng, vui lòng nhấn nút xóa nếu bạn muốn xóa!", Toast.LENGTH_LONG).show();
            }
            else{
                sl--;
                et_soluong.setText(String.valueOf(sl));
                list.get(pos).setSoLuong(sl);
                notifyDataSetChanged();
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
                notifyDataSetChanged();
            }

        });
        btn_xoa.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này hay không");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    list.remove(pos);
                    notifyDataSetChanged();
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
        imgSanPham.setImageResource(giohang.getAnhsp());
        txt_tensp.setText(giohang.getTensp().toString());
        et_soluong.setText(String.valueOf(giohang.getSoLuong()));
        txt_gia.setText(String.valueOf(giohang.getGia()));

        return convertView;
    }

}



