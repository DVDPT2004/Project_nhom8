package User.Viet.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.UserRepository;
import Dangky_nhap.Views.Login;
import Dangky_nhap.Views.Update_password;
import Database.MainData.MainData;
import User.Duy.Views.OrderHistoryActivity;

public class UserFragment extends Fragment {
    private MainData db;
    private UserRepository userRepository;
    private TextView history_order, change_password, logout, email;
    private String getEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_user, container, false);

        // Thiết lập insets cho fragment
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các đối tượng và thiết lập giao diện
        db = new MainData(getContext(), "mainData.sqlite", null, 1);
        userRepository = new UserRepository(db, getContext());

        email = view.findViewById(R.id.email);
        history_order = view.findViewById(R.id.history_order);
        change_password = view.findViewById(R.id.change_password);
        logout = view.findViewById(R.id.logout);

        getEmail = userRepository.getLoggedInUserEmail();
        email.setText(getEmail);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Update_password.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRepository.logout();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

        history_order.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), OrderHistoryActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
