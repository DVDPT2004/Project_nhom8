package Admin.Phuoc;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_nhom8.R;
import com.example.project_nhom8.databinding.ActivityMenuAdminBinding;

import Admin.Phuoc.admin_account.AdminAccountFragment;
import Admin.Phuoc.admin_feedback.activity_feedback.AdminFeedbackFragment;
import Admin.Phuoc.admin_home.activity_food.AdminFoodFragment;
import Admin.Phuoc.admin_order.activity_order.AdminOrderFragment;

public class MainActivityAdmin extends AppCompatActivity {
    ActivityMenuAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_admin);

        binding = ActivityMenuAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AdminFoodFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.admin_food) {
                replaceFragment(new AdminFoodFragment());
            } else if (itemId == R.id.admin_feedback) {
                replaceFragment(new AdminFeedbackFragment());
            } else if (itemId == R.id.admin_order) {
                replaceFragment(new AdminOrderFragment());
            } else if (itemId == R.id.admin_account) {
                replaceFragment(new AdminAccountFragment());
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