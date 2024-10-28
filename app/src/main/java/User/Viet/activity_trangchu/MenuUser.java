package User.Viet.activity_trangchu;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_nhom8.R;
import com.example.project_nhom8.databinding.ActivityLayoutUserBinding;

import Dangky_nhap.Views.Profile_User;
import User.Duy.ActGioHang;
import User.Viet.Fragment.CartFragment;
import User.Viet.Fragment.FeedbackFragment;
import User.Viet.Fragment.GiaoDienDauFragment;
import User.Viet.Fragment.LienHeFragment;
import User.Viet.Fragment.UserFragment;

public class MenuUser extends AppCompatActivity {
    ActivityLayoutUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLayoutUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Kiểm tra Intent để lấy selected_item
        int selectedItemId = getIntent().getIntExtra("selected_item", R.id.action_home);
        int selectedItemId1 = getIntent().getIntExtra("selected_item1", R.id.action_home);

        // Thiết lập BottomNavigationView để chọn mục đúng


        // Thay thế Fragment mặc định dựa trên selectedItemId1 bằng if-else
        if (selectedItemId1 == R.id.action_feedback) {
            replaceFragment(new FeedbackFragment());
        } else if (selectedItemId == R.id.action_cart) {
            replaceFragment(new ActGioHang());
        } else {
            replaceFragment(new GiaoDienDauFragment());
        }
        binding.bottomNavigationView.setSelectedItemId(selectedItemId);
        binding.bottomNavigationView.setSelectedItemId(selectedItemId1);

        // Lắng nghe sự kiện chọn item
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                replaceFragment(new GiaoDienDauFragment());
            } else if (item.getItemId() == R.id.action_cart) {
                replaceFragment(new ActGioHang());
            } else if (item.getItemId() == R.id.action_feedback) {
                replaceFragment(new FeedbackFragment());
            } else if (item.getItemId() == R.id.action_contact) {
                replaceFragment(new LienHeFragment());
            } else if (item.getItemId() == R.id.action_user) {
                replaceFragment(new Profile_User());
            } else {
                return false; // Không có item nào được chọn
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
