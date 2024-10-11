package User.Viet.activity_chitietmonan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project_nhom8.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int[] images; // Mảng chứa ID của các hình ảnh

    public ImageAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // Nếu chưa có View, tạo mới
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(500, 500)); // Điều chỉnh kích thước nếu cần
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8); // Padding giữa các hình
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(images[position]); // Đặt hình ảnh
        return imageView;
    }
}

