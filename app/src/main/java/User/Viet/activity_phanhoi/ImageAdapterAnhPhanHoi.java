package User.Viet.activity_phanhoi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project_nhom8.R;

import java.util.ArrayList;

public class ImageAdapterAnhPhanHoi extends BaseAdapter {
    private ArrayList<Bitmap> imageList;
    private Context context;

    public ImageAdapterAnhPhanHoi(ArrayList<Bitmap> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_phanhoi, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        ImageView deleteButton = convertView.findViewById(R.id.deleteButton);

        // Gán hình ảnh
        imageView.setImageBitmap(imageList.get(position));

        // Xử lý sự kiện khi nhấn vào nút xóa
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa ảnh khỏi danh sách
                imageList.remove(position);
                // Cập nhật lại Adapter
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}

