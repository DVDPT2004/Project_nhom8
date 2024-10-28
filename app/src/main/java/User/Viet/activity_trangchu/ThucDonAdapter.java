package User.Viet.activity_trangchu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_nhom8.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import User.Viet.Modal.ThucDon;

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
        if (thucDon.getTinhtrang().equals("Hết")) {
            convertView.setAlpha(0.3f); // Đặt độ mờ 30%
        } else {
            convertView.setAlpha(1.0f); // Đặt độ mờ 100%
        }
        // Sử dụng Uri để lấy ảnh từ đường dẫn
        String imageUriString = thucDon.getAvatar(); // Lấy chuỗi Uri từ database

        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString); // Chuyển đổi chuỗi sang Uri
            try {
                InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            imageView.setImageResource(R.drawable.doan1); // Hình ảnh mặc định nếu không có
        }

        txtname.setText(thucDon.getTenmonan());

        // Định dạng và gán giá
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        txtgiachinh.setText(decimalFormat.format(thucDon.getGiachinh()) + " VND");
        txtgiagiam.setText(decimalFormat.format(thucDon.giaGiam()) + " VND");
        txtphantram.setText(thucDon.getPhantram() + "%");

        return convertView;
    }

    public void updateList(List<ThucDon> newList) {
        thucDonList.clear();
        thucDonList.addAll(newList);
        notifyDataSetChanged(); // Thông báo cho adapter để cập nhật UI
    }
}
