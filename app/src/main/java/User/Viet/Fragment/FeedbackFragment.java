package User.Viet.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.List;

import User.Viet.Modal.ItemFeedback;
import User.Viet.activity_phanhoi.ItemFeedbackAdapter;

public class FeedbackFragment extends Fragment {
    private ListView listViewContacts;
    private ItemFeedbackAdapter adapter;
    private List<ItemFeedback> feedbackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_layout_listphanhoi, container, false);

        // Khởi tạo ListView và Adapter
        listViewContacts = view.findViewById(R.id.listViewContacts);
        feedbackList = new ArrayList<>();

        // Thêm dữ liệu mẫu
        feedbackList.add(new ItemFeedback("lavistudio.vn", "Hôm qua", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("user2", "10:25 20/10/2024", "Cảm ơn bạn đã phản hồi với chúng tôi"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));
        feedbackList.add(new ItemFeedback("lavistudio.vn", "10:23 20/10/2024", "Món ăn bạn làm không được ngon lắm"));

        // Khởi tạo adapter
        adapter = new ItemFeedbackAdapter(getContext(), feedbackList);
        listViewContacts.setAdapter(adapter);

        return view;
    }
}
