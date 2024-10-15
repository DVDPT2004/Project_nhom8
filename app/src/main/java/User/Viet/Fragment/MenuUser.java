package User.Viet.Fragment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_nhom8.R;
import com.example.project_nhom8.databinding.ActivityLayoutUserBinding;


//import com.example.appdatdoan.

public class MenuUser extends AppCompatActivity {
    ActivityLayoutUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout_user);

        binding = ActivityLayoutUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new GiaoDienDauFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.action_home) {
                replaceFragment(new GiaoDienDauFragment());
            } else if (item.getItemId() == R.id.action_cart) {
                replaceFragment(new CartFragment());
            } else if (item.getItemId() == R.id.action_feedback) {
                replaceFragment(new FeedbackFragment());
            } else if (item.getItemId() == R.id.action_contact) {
                replaceFragment(new LienHeFragment());
            } else if (item.getItemId() == R.id.action_user) {
                replaceFragment(new UserFragment());
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
