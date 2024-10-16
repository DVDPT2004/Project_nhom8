package User.Viet.activity_chitietmonan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_nhom8.R;

import java.util.List;

import User.Viet.Modal.Feedback;

public class FeedbackAdapter extends BaseAdapter {
    private Context context;
    private List<Feedback> feedbackList;

    public FeedbackAdapter(Context context, List<Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @Override
    public int getCount() {
        return feedbackList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedbackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_feedback, parent, false);
        }

        Feedback feedback = feedbackList.get(position);

        TextView txtUserName = convertView.findViewById(R.id.txtUserName);
        TextView txtFeedbackDate = convertView.findViewById(R.id.txtFeedbackDate);
        TextView txtFeedbackContent = convertView.findViewById(R.id.txtFeedbackContent);
        ImageView imgMedia1 = convertView.findViewById(R.id.idmedia1);
        ImageView imgMedia2 = convertView.findViewById(R.id.idmedia2);
        ImageView imgMedia3 = convertView.findViewById(R.id.idmedia3);

        txtUserName.setText(feedback.getUserName());
        txtFeedbackDate.setText(feedback.getFeedbackDate());
        txtFeedbackContent.setText(feedback.getFeedbackContent());

        // Xử lý hình ảnh
        byte[] hinhanh1 = feedback.getImage1(); // Giả định phương thức getImage1() trả về byte[] cho imgMedia1
        byte[] hinhanh2 = feedback.getImage2(); // Giả định phương thức getImage2() trả về byte[] cho imgMedia2
        byte[] hinhanh3 = feedback.getImage3(); // Giả định phương thức getImage3() trả về byte[] cho imgMedia3

        // Hiển thị hình ảnh cho imgMedia1
        if (hinhanh1 != null && hinhanh1.length > 0) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(hinhanh1, 0, hinhanh1.length);
            imgMedia1.setImageBitmap(bitmap1);
        } else {
            imgMedia1.setVisibility(View.GONE); // Hình ảnh mặc định
        }

        // Hiển thị hình ảnh cho imgMedia2
        if (hinhanh2 != null && hinhanh2.length > 0) {
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(hinhanh2, 0, hinhanh2.length);
            imgMedia2.setImageBitmap(bitmap2);
        } else {
            imgMedia2.setVisibility(View.GONE); // Hình ảnh mặc định
        }

        // Hiển thị hình ảnh cho imgMedia3
        if (hinhanh3 != null && hinhanh3.length > 0) {
            Bitmap bitmap3 = BitmapFactory.decodeByteArray(hinhanh3, 0, hinhanh3.length);
            imgMedia3.setImageBitmap(bitmap3);
        } else {
            imgMedia3.setVisibility(View.GONE); // Hình ảnh mặc định
        }

        return convertView;
    }

}


