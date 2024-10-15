package User.Viet.activity_trangchu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.project_nhom8.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ThucDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ThucDon> thucDonList;

    public ThucDonAdapter(Context context, int layout, List<ThucDon> thucDonList) {
        this.context = context;
        this.layout = layout;
        this.thucDonList = thucDonList;
    }

    @Override
    public int getCount() {
        return thucDonList.size();
    }

    @Override
    public Object getItem(int position) {
        return thucDonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageAvatar);
        TextView txtname = convertView.findViewById(R.id.txttenmonan);
        TextView txtgiachinh = convertView.findViewById(R.id.txtgiachinh);
        TextView txtphantram = convertView.findViewById(R.id.txtphantram);
        TextView txtgiagiam = convertView.findViewById(R.id.txtgiagiam);

        ThucDon thucDon = thucDonList.get(position);
        imageView.setImageResource(thucDon.getAvatar());
        txtname.setText(thucDon.getTenmonan());

        // Định dạng và gán giá
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        txtgiachinh.setText(formatter.format(thucDon.getGiachinh()) + " VNĐ");
        txtgiagiam.setText(formatter.format(thucDon.giaGiam()) + " VNĐ");
        txtphantram.setText("Giảm " + thucDon.getPhantram() + "%"); // Hiển thị phần trăm

        // Kiểm tra tình trạng sản phẩm
        if ("Hết".equals(thucDon.getTinhtrang())) {
            // Làm mờ sản phẩm
            convertView.setAlpha(0.5f);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.black)); // Thay đổi thành màu sắc bạn muốn

            // Thiết lập sự kiện nhấn
            convertView.setOnClickListener(v -> {
                // Hiện thông báo
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo")
                        .setMessage("Món này đã hết. Vui lòng chọn món khác.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false); // Không cho phép đóng bằng cách nhấn ra ngoài
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        } else {
            // Đặt lại độ mờ và màu nền
            convertView.setAlpha(1f);
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent)); // Màu nền trong suốt
        }

        return convertView;
    }
}

