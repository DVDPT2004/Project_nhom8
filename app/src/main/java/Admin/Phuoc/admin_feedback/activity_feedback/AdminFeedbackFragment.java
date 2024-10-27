package Admin.Phuoc.admin_feedback.activity_feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.ArrayList;

import Admin.Phuoc.admin_feedback.object.Feedback;
import Admin.Phuoc.admin_feedback.object_adapter.FeedbackAdapter;
import Admin.Phuoc.admin_feedback.object_database.FeedbackDatabase;
import Database.MainData.MainData;

public class AdminFeedbackFragment extends Fragment {
    private View rootView;
    private FeedbackDatabase feedbackDatabase;
    private MainData db;
    private RecyclerView recyclerView;
    private ArrayList<Feedback> feedbackArrayList;
    private FeedbackAdapter feedbackAdapter;
    private Spinner feedbackSpinner;
    ArrayList<String> feedback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MainData(getContext(),"mainData.sqlite",null,1);
        feedbackDatabase = new FeedbackDatabase(db);
        feedbackArrayList = new ArrayList<>();
        feedbackArrayList = feedbackDatabase.selectFeedbackResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_admin_phan_hoi, container, false);

        feedbackSpinner = rootView.findViewById(R.id.admin_feedback_status);
        feedback = new ArrayList<>();
        feedback.add("Chưa phản hồi");
        feedback.add("Đã phản hồi");
        feedback.add("Tất cả");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, feedback);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackSpinner.setAdapter(adapter);
        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFeedback = feedback.get(i);
                if ("Chưa phản hồi".equals(selectedFeedback)) {
                    feedbackArrayList.clear();
                    feedbackArrayList.addAll(feedbackDatabase.selectFeedbackNoResponse());
                } else if ("Đã phản hồi".equals(selectedFeedback)) {
                    feedbackArrayList.clear();
                    feedbackArrayList.addAll(feedbackDatabase.selectFeedbackResponse());
                } else if ("Tất cả".equals(selectedFeedback)) {
                    feedbackArrayList.clear();
                    feedbackArrayList.addAll(feedbackDatabase.selectFeedback());
                }
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setupRecyclerView(rootView);
        return rootView;
    }

    public void onResume() {     // Thực hiện cập nhật dữ liệu hoặc bất kỳ hành động nào khi Activity quay lại
        super.onResume();
        feedbackArrayList.clear();
        String selectedFeedback = feedbackSpinner.getSelectedItem().toString();
        if ("Chưa phản hồi".equals(selectedFeedback)) {
            feedbackArrayList.addAll(feedbackDatabase.selectFeedbackNoResponse());
        } else if ("Tất cả".equals(selectedFeedback)) {
            feedbackArrayList.addAll(feedbackDatabase.selectFeedbackResponse());
        }
        feedbackAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_feedback_item);
        // Sử dụng LinearLayoutManager để hiển thị theo chiều dọc
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Khởi tạo adapter và gán cho RecyclerView
        feedbackAdapter = new FeedbackAdapter(getActivity(), R.layout.activity_admin_item_phan_hoi, feedbackArrayList);
        recyclerView.setAdapter(feedbackAdapter);
    }
}