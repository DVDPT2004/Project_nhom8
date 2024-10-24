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
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

        // Sử dụng Uri để lấy ảnh từ đường dẫn
        String imageUriString = thucDonList.get(position).getAvatar();
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(imageUri); // Sử dụng context
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.doan1); // Hình ảnh mặc định nếu không tìm thấy
            }
        } else {
            imageView.setImageResource(R.drawable.doan1); // Hình ảnh mặc định
        }


        txtname.setText(thucDon.getTenmonan());

        // Định dạng và gán giá
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        txtgiachinh.setText(formatter.format(thucDon.getGiachinh()) + " đ");
        txtgiagiam.setText(formatter.format(thucDon.giaGiam()) + " đ");

        txtphantram.setText(thucDon.getPhantram() + "%");

        return convertView;
    }
}
