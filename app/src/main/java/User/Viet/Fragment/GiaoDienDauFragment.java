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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
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
    private SearchView searchView;
    private List<ThucDon> fullFoodList;
    private List<ThucDon> filteredFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trangchu, container, false);

        viewPager = view.findViewById(R.id.viewpaper);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        gridView = view.findViewById(R.id.grThucDon);
        autoCompleteTextView = view.findViewById(R.id.txtdanhmuc);
        searchView = view.findViewById(R.id.search_view);

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

        fullFoodList = new ArrayList<>();
        filteredFoodList = new ArrayList<>();
        initMainDishData(""); // Hiển thị dữ liệu mặc định
        setupSearchView();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFoodList(newText); // Lọc danh sách món ăn khi người dùng nhập vào ô tìm kiếm
                return true;
            }
        });
    }

    // Phương thức lọc danh sách món ăn
    private void filterFoodList(String query) {
        filteredFoodList.clear();
        Set<String> addedFoods = new HashSet<>(); // Sử dụng HashSet để theo dõi món ăn đã thêm

        if (query.isEmpty()) {
            // Khi ô tìm kiếm trống, chỉ hiển thị món ăn theo danh mục đã chọn
            // Nếu không có danh mục nào được chọn, có thể hiển thị toàn bộ danh sách
            if (autoCompleteTextView.getText().toString().isEmpty()) {
                filteredFoodList.addAll(fullFoodList); // Hiển thị tất cả nếu không có danh mục nào
            } else {
                // Lấy danh mục đã chọn
                String selectedDanhMuc = autoCompleteTextView.getText().toString();
                for (ThucDon food : fullFoodList) {
                    if (food.getTenDanhMuc().equalsIgnoreCase(selectedDanhMuc) &&
                            !addedFoods.contains(food.getTenmonan())) {
                        filteredFoodList.add(food);
                        addedFoods.add(food.getTenmonan());
                    }
                }
            }
        } else {
            for (ThucDon food : fullFoodList) {
                // Lọc theo từ khóa tìm kiếm
                if (food.getTenmonan().toLowerCase().contains(query.toLowerCase()) &&
                        !addedFoods.contains(food.getTenmonan())) {
                    filteredFoodList.add(food);
                    addedFoods.add(food.getTenmonan());
                }
            }
        }

        thucDonAdapter.updateList(filteredFoodList); // Cập nhật adapter với danh sách đã lọc
    }


    public void updateList(List<ThucDon> newList) {
        this.fullFoodList.clear();
        this.fullFoodList.addAll(newList);
        thucDonAdapter.notifyDataSetChanged();
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
            ThucDon thucDon = new ThucDon(
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(5),
                    cursor.getFloat(3),
                    cursor.getString(1),
                    cursor.getInt(6),
                    cursor.getString(13),
                    cursor.getString(3),
                    cursor.getInt(0)
            );
            listmonan.add(thucDon);
            fullFoodList.add(thucDon); // Lưu trữ danh sách đầy đủ
        }
        cursor.close(); // Đóng con trỏ
        thucDonAdapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietMonAn.class);
                ThucDon selectedMonAn = (ThucDon) thucDonAdapter.getItem(i);
                if (selectedMonAn != null && !selectedMonAn.getTinhtrang().equals("Hết")) {
                    intent.putExtra("maSanPham", selectedMonAn.getMaSanPham());
                    intent.putExtra("tenmonan", selectedMonAn.getTenmonan());
                    intent.putExtra("motamonan", selectedMonAn.getMotamonan());
                    intent.putExtra("tinhTrang", selectedMonAn.getTinhtrang());
                    intent.putExtra("hinhanh", selectedMonAn.getAvatar());
                    intent.putExtra("hinhanh1", selectedMonAn.getAnhmota1());
                    intent.putExtra("hinhanh2", selectedMonAn.getAnhmota2());
                    intent.putExtra("hinhanh3", selectedMonAn.getAnhmota3());
                    intent.putExtra("hinhanh4", selectedMonAn.getAnhmota4());
                    intent.putExtra("giaGiam", selectedMonAn.giaGiam());
                    startActivity(intent);
                } else {
                    view.setAlpha(0.3f); // Độ mờ cho màu xám
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông báo")
                            .setMessage("Món ăn đã hết, vui lòng chọn món ăn khác.")
                            .setPositiveButton("OK", null)
                            .show();



                }
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
            }, 2000, 2000); // Chuyển hình mỗi 2 giây
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel(); // Hủy Timer khi fragment bị hủy
        }
    }
}
