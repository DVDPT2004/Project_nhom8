package Admin.Phuoc.admin_home.activity_food;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Admin.Phuoc.admin_home.object.Food;
import Admin.Phuoc.admin_home.object_adapter.FoodAdapter;
import Admin.Phuoc.admin_home.object_database.FoodDatabase;
import Database.MainData.MainData;

public class AdminFoodFragment extends Fragment {
    private ArrayList<Food> foodList;
    private FoodAdapter foodAdapter;    // Adapter để hiển thị danh sách món ăn
    private static final int REQUEST_CODE_ADD_PRODUCT = 1;   // Mã yêu cầu cho việc thêm món ăn
    private RecyclerView recyclerView;
    private FoodDatabase foodDatabase;
    private MainData db;
    private View rootView;
    public AdminFoodFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MainData(getContext(),"mainData.sqlite",null,1);
        foodDatabase = new FoodDatabase(db);
        foodList = new ArrayList<>();
        foodList = foodDatabase.selectFood();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho fragment
        rootView = inflater.inflate(R.layout.activity_admin_do_an, container, false);

        setupRecyclerView(rootView);    // Thiết lập RecyclerView

        setupAddItemButton(rootView);   // Thiết lập nút thêm món ăn

        setupSearchItem(rootView);      // Thiết lập ô tìm kiếm món ăn
        return rootView;
    }

    public void onResume() {     // Thực hiện cập nhật dữ liệu hoặc bất kỳ hành động nào khi Activity quay lại
        super.onResume();
        db = new MainData(getContext(),"mainData.sqlite",null,1);
        foodDatabase = new FoodDatabase(db);
        foodList = new ArrayList<>();
        foodList = foodDatabase.selectFood();
        setupRecyclerView(rootView);
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_item);
        // Sử dụng LinearLayoutManager để hiển thị theo chiều dọc
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Khởi tạo adapter và gán cho RecyclerView
        foodAdapter = new FoodAdapter(getActivity(), R.layout.activity_admin_item_do_an, foodList);
        recyclerView.setAdapter(foodAdapter);
    }

    private void setupAddItemButton(View view) {
        ImageView addItemButton = view.findViewById(R.id.admin_add_item_button);
        addItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminAddItemActivity.class);
            intent.putParcelableArrayListExtra("foodList", foodList);
            startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);   // Bắt đầu Activity với mã yêu cầu
        });
    }

    private void filterFoodList(String query) {
        List<Food> filteredList = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getNameFood().toLowerCase().contains(query.toLowerCase()) || food.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(food);
            }
        }
        foodAdapter.updateList(filteredList);   // Cập nhật adapter với danh sách đã lọc
    }

    private void setupSearchItem(View view) {
        EditText searchItemEditText = view.findViewById(R.id.admin_text_search_item);
        searchItemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFoodList(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Kiểm tra mã yêu cầu và kết quả từ Activity thêm món
        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == Activity.RESULT_OK) {
            handleUpdatedFoodList(data);
        }
    }

    // Xử lý danh sách món ăn được cập nhật từ Activity thêm món
    private void handleUpdatedFoodList(Intent data) {
        // Nhận danh sách món ăn đã được cập nhật
        ArrayList<Food> updatedFoodList = foodDatabase.selectFood();
        if (updatedFoodList != null) {
            foodList.clear();
            foodList.addAll(updatedFoodList);
            foodAdapter.notifyDataSetChanged();  // nhac recyclerView cap nhat lai danh sach cac mon an
        }
    }


}
