package User.Viet.activity_phanhoi;

// FeedbackAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project_nhom8.R;

import java.util.List;

import User.Viet.Modal.ItemFeedback;


public class ItemFeedbackAdapter extends BaseAdapter {
    private Context context;
    private List<ItemFeedback> feedbackList;

    public ItemFeedbackAdapter(Context context, List<ItemFeedback> feedbackList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.items_dsphanhoi, parent, false);
        }

        // Lấy thông tin từ feedbackList
        ItemFeedback feedback = feedbackList.get(position);

        // Gán dữ liệu vào các view
        TextView userNameTextView = convertView.findViewById(R.id.txtUserName);
        TextView feedbackTimeTextView = convertView.findViewById(R.id.txttime);
        TextView feedbackContentTextView = convertView.findViewById(R.id.txtFeedbackContent);

        userNameTextView.setText(feedback.getUserName());
        feedbackTimeTextView.setText(feedback.getFeedbackTime());
        feedbackContentTextView.setText(feedback.getFeedbackContent());

        return convertView;
    }
}

