package User.Viet.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.project_nhom8.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import Database.MainData.MainData;
import User.Viet.Modal.Feedback;
import User.Viet.activity_chitietmonan.ChiTietMonAn;
import User.Viet.Modal.Photo;
import User.Viet.activity_trangchu.PhotoAdapter;
import User.Viet.Modal.ThucDon;
import User.Viet.activity_trangchu.ThucDonAdapter;
import me.relex.circleindicator.CircleIndicator;

public class GiaoDienDauFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter adapter;
    private ThucDonAdapter thucDonAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;
    private GridView gridView;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private List<String> danhMucList; // Danh sách danh mục
    private MainData sanphamhienthi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trangchu, container, false);

        viewPager = view.findViewById(R.id.viewpaper);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        gridView = view.findViewById(R.id.grThucDon);
        autoCompleteTextView = view.findViewById(R.id.txtdanhmuc);

        sanphamhienthi = new MainData(getContext(), "mainData.sqlite", null, 1);
        danhMucList = new ArrayList<>(); // Khởi tạo danh sách danh mục

        // Lấy danh sách danh mục từ cơ sở dữ liệu
        getDanhMuc();

        // Thiết lập adapter cho AutoCompleteTextView
        adapterItems = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, danhMucList.toArray(new String[0]));
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDanhMuc = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Đã chọn danh mục: " + selectedDanhMuc, Toast.LENGTH_SHORT).show();
                // Hiển thị sản phẩm theo tendanhmuc
                initMainDishData(selectedDanhMuc);
            }
        });

        mListPhoto = getListPhoto();
        adapter = new PhotoAdapter(getContext(), mListPhoto);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        circleIndicator.setBackgroundResource(R.drawable.indicator_drawable);
        AutoSlideImages();

        initMainDishData(""); // Hiển thị dữ liệu mặc định

        return view;
    }

    private void getDanhMuc() {
        // Truy vấn để lấy danh mục từ bảng SanPham
        Cursor cursor = sanphamhienthi.SelectData("SELECT DISTINCT tendanhmuc FROM SanPham");
        Set<String> danhMucSet = new HashSet<>(); // Sử dụng HashSet để loại bỏ trùng lặp
        while (cursor.moveToNext()) {
            danhMucSet.add(cursor.getString(0)); // Giả sử tendanhmuc là cột đầu tiên
        }
        danhMucList.addAll(danhMucSet); // Chuyển đổi Set về List
        cursor.close(); // Đóng con trỏ
    }

    private void initMainDishData(String tendanhmuc) {
        List<ThucDon> listmonan = new ArrayList<>();
        thucDonAdapter = new ThucDonAdapter(getContext(), R.layout.activity_item_thucdon, listmonan);
        gridView.setAdapter(thucDonAdapter);

        // Truy vấn để lấy sản phẩm theo tendanhmuc
        Cursor cursor;
        if (tendanhmuc.isEmpty()) {
            cursor = sanphamhienthi.SelectData("SELECT * FROM SanPham");
        } else {
            cursor = sanphamhienthi.SelectData("SELECT * FROM SanPham WHERE tendanhmuc = '" + tendanhmuc + "'");
        }

        while (cursor.moveToNext()) {
            listmonan.add(new ThucDon(
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getFloat(4),
                    cursor.getString(1),
                    cursor.getInt(6),
                    cursor.getString(13),
                    cursor.getString(3)
            ));
        }
        cursor.close(); // Đóng con trỏ
        thucDonAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietMonAn.class);
                ThucDon selectedMonAn = (ThucDon) thucDonAdapter.getItem(i);

                intent.putExtra("tenmonan", selectedMonAn.getTenmonan());
                intent.putExtra("motamonan", selectedMonAn.getMotamonan());
                intent.putExtra("hinhanh", selectedMonAn.getAvatar());
                intent.putExtra("hinhanh1", selectedMonAn.getAnhmota1());
                intent.putExtra("hinhanh2", selectedMonAn.getAnhmota2());
                intent.putExtra("hinhanh3", selectedMonAn.getAnhmota3());
                intent.putExtra("hinhanh4", selectedMonAn.getAnhmota4());
                intent.putExtra("giaGiam", selectedMonAn.giaGiam());
                startActivity(intent);
            }
        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.doan1));
        list.add(new Photo(R.drawable.doan2));
        list.add(new Photo(R.drawable.doan3));
        list.add(new Photo(R.drawable.doan4));
        return list;
    }

    private void AutoSlideImages() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (getActivity() == null) {
                        return; // Bảo vệ nếu fragment không còn gắn với activity
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        int currentItem = viewPager.getCurrentItem();
                        if (currentItem < mListPhoto.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    });
                }
            }, 3000, 3000); // Tự động slide mỗi 3 giây
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null; // Dừng timer khi fragment bị hủy
        }
    }
}
