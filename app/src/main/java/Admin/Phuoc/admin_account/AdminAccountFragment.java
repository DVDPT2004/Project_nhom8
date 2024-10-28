package Admin.Phuoc.admin_account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project_nhom8.R;

import Dangky_nhap.Model.UserRepository;
import Dangky_nhap.Views.Login;
import Dangky_nhap.Views.Profile_admin;
import Database.MainData.MainData;

public class AdminAccountFragment extends Fragment {
    private View rootView;
    private UserRepository userRepository;
    private MainData db;
    private TextView logout,email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MainData(getContext(), "mainData.sqlite", null, 1);
        userRepository = new UserRepository(db, getContext()); // Khởi tạo UserRepository
//        setContentView(R.layout.activity_profile_admin);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_profile_admin, container, false);
        logout = rootView.findViewById(R.id.logout);
        email = rootView.findViewById(R.id.email);
        String Email= userRepository.getLoggedInUserEmail();
        email.setText(Email);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.logout();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
}