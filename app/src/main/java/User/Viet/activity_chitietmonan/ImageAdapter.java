package User.Viet.activity_chitietmonan;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project_nhom8.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private String[] imageUrls;

    public ImageAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return imageUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.griditem_chitietmonan, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView); // Đảm bảo ID đúng với layout
        String imageUrl = imageUrls[position];

        // Thiết lập hình ảnh cho ImageView
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImageURI(Uri.parse(imageUrl)); // Nếu hình ảnh từ URI
        } else {
            imageView.setImageDrawable(null); // Hình ảnh mặc định
        }

        return convertView;
    }

}
