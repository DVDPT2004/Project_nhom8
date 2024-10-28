package Admin.Phuoc.admin_feedback.object_adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.List;

import Admin.Phuoc.admin_feedback.activity_feedback.AdminFeedbackDetailActivity;
import Admin.Phuoc.admin_feedback.object.Feedback;
import Admin.Phuoc.admin_feedback.object_database.FeedbackDatabase;
import Database.MainData.MainData;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{
    private Context context;
    private int layout;
    private List<Feedback> feedbackList;
    private FeedbackDatabase feedbackDatabase;

    public FeedbackAdapter(Context context, int layout, List<Feedback> feedbackList) {
        this.context = context;
        this.layout = layout;
        this.feedbackList = feedbackList;
    }


    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.nameUser.setText(feedback.getTenKhachPhanHoi());
        holder.contentFeedback.setText(feedback.getNoiDungKhachPhanHoi());
        holder.timeFeedback.setText(feedback.getThoiGianPhanHoi());

        holder.linearLayoutItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminFeedbackDetailActivity.class);
            Feedback feedbackItem = feedbackList.get(holder.getAdapterPosition());
            intent.putExtra("feedback_nameUserFeedback",feedbackItem.getTenKhachPhanHoi());
            intent.putExtra("feedback_timeFeedback",feedbackItem.getThoiGianPhanHoi());
            intent.putExtra("feedback_contentUserFeedback",feedbackItem.getNoiDungKhachPhanHoi());
            intent.putExtra("feedback_media1", feedbackItem.getMedia1() != null ? feedbackItem.getMedia1() : new byte[0]);
            intent.putExtra("feedback_media2", feedbackItem.getMedia2() != null ? feedbackItem.getMedia2() : new byte[0]);
            intent.putExtra("feedback_media3", feedbackItem.getMedia3() != null ? feedbackItem.getMedia3() : new byte[0]);
//            Log.d("feedback_media3", "feedback_media3: ");
            intent.putExtra("feedback_idFeedback",feedbackItem.getMaPhanhoi());
            intent.putExtra("feedback_statusFeedback",feedbackItem.getTrangThai());
            intent.putExtra("feedback_contentAdminFeedback",feedbackItem.getNoiDungAdminPhanHoi());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }


    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView nameUser,contentFeedback,timeFeedback;
        private LinearLayout linearLayoutItem;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutItem = itemView.findViewById(R.id.admin_feedback_item);
            nameUser = itemView.findViewById(R.id.admin_feedback_name_user);
            contentFeedback = itemView.findViewById(R.id.admin_feedback_content);
            timeFeedback = itemView.findViewById(R.id.admin_feedback_time_feedback);
        }
    }

}
