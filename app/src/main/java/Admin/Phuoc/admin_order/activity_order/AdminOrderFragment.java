package Admin.Phuoc.admin_order.activity_order;

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

import Admin.Phuoc.admin_order.object.Order;
import Admin.Phuoc.admin_order.object_adapter.OrderAdapter;
import Admin.Phuoc.admin_order.object_database.OrderDatabase;
import Database.MainData.MainData;

public class AdminOrderFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private Spinner orderSpinner;
    ArrayList<String> order;
    private MainData db;
    private ArrayList<Order> orderArrayList;
    private OrderAdapter orderAdapter;
    private OrderDatabase orderDatabase;
    private int position = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MainData(getContext(),"mainData.sqlite",null,1);
        orderDatabase = new OrderDatabase(db);
        orderArrayList = new ArrayList<>();
        orderArrayList = orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 0 ORDER by ngayGioDatHang DESC");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_admin_don_hang, container, false);
        orderSpinner = rootView.findViewById(R.id.admin_order_status);

        order = new ArrayList<>();
        order.add("Chưa chuẩn bị");
        order.add("Đang giao hàng");
        order.add("Giao thành công");
        order.add("Giao thất bại");
        order.add("Tất cả");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, order);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFeedback = order.get(i);
                if ("Chưa chuẩn bị".equals(selectedFeedback)) {
                    orderArrayList.clear();
                    orderArrayList.addAll(orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 0 ORDER by ngayGioDatHang DESC"));
                    setupRecyclerView(rootView);
                } else if ("Đang giao hàng".equals(selectedFeedback)) {
                    orderArrayList.clear();
                    orderArrayList.addAll(orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 1 ORDER by ngayGioDatHang DESC"));
                    setupRecyclerView(rootView);
                } else if ("Giao thành công".equals(selectedFeedback)) {
                    orderArrayList.clear();
                    orderArrayList.addAll(orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 2 ORDER by ngayGioDatHang DESC"));
                    setupRecyclerView(rootView);
                } else if ("Giao thất bại".equals(selectedFeedback)) {
                    orderArrayList.clear();
                    orderArrayList.addAll(orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 3 ORDER by ngayGioDatHang DESC"));
                    setupRecyclerView(rootView);
                } else if ("Tất cả".equals(selectedFeedback)) {
                    position = 3;
                    orderArrayList.clear();
                    orderArrayList.addAll(orderDatabase.selectOrder("SELECT * FROM DonHang ORDER by ngayGioDatHang DESC"));
                    setupRecyclerView(rootView);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setupRecyclerView(rootView);
        return rootView;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_order);
        // Sử dụng LinearLayoutManager để hiển thị theo chiều dọc
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Khởi tạo adapter và gán cho RecyclerView
        orderAdapter = new OrderAdapter(position,getActivity(), R.layout.activity_admin_item_don_hang, orderArrayList);
        recyclerView.setAdapter(orderAdapter);
    }
}