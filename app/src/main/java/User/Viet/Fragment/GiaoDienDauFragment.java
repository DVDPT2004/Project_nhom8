package User.Viet.Fragment;

import android.content.Intent;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private String[] items = {"Món ăn chính", "Món tráng miệng", "Đồ uống"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trangchu, container, false);

        viewPager = view.findViewById(R.id.viewpaper);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        gridView = view.findViewById(R.id.grThucDon);
        autoCompleteTextView = view.findViewById(R.id.txtdanhmuc);

        adapterItems = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(), "Đây là danh sách: " + item, Toast.LENGTH_SHORT).show();
                if (i == 0) {
                    initMainDishData();
                } else if (i == 2) {
                    initDrinkData();
                }
            }
        });

        mListPhoto = getListPhoto();
        adapter = new PhotoAdapter(getContext(), mListPhoto);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        circleIndicator.setBackgroundResource(R.drawable.indicator_drawable);
        AutoSlideImages();

        initMainDishData(); // Hiển thị dữ liệu mặc định

        return view;
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.doan1));
        list.add(new Photo(R.drawable.doan2));
        list.add(new Photo(R.drawable.doan3));
        list.add(new Photo(R.drawable.doan4));
        return list;
    }

    private void initMainDishData() {
        List<ThucDon> list = new ArrayList<>();
        list.add(new ThucDon(R.drawable.doan1, R.drawable.doan1, R.drawable.doan1, R.drawable.doan1,
                R.drawable.doan1,"Còn", 200000, "Bò cuốn lá nốt", 5, "Bò cuốn lá nốt là món ăn đặc sản nổi tiếng của Việt Nam, được làm từ thịt bò tươi ngon cuộn trong lá nốt, mang đến hương vị đặc trưng thơm ngon và hấp dẫn. Món ăn này thường được ăn kèm với nước chấm đặc biệt, giúp tăng thêm hương vị đậm đà. Đặc biệt, bò cuốn lá nốt có thể được chế biến theo nhiều cách khác nhau, từ nướng cho đến hấp, làm cho món ăn này trở nên đa dạng và phong phú hơn. Hãy thử ngay để cảm nhận sự hòa quyện tuyệt vời giữa thịt bò và lá nốt!", 1));
        list.add(new ThucDon(R.drawable.doan2, R.drawable.doan2, R.drawable.doan2, R.drawable.doan2,
                R.drawable.doan2,"Còn", 300000, "Bò bóp mẻ", 30, "Bò bóp mẻ là món ăn truyền thống mang đậm hương vị miền Bắc, với thịt bò tươi ngon được trộn với mẻ, một loại gia vị đặc trưng của người dân nơi đây. Món ăn này không chỉ có vị chua chua, ngọt ngọt từ mẻ mà còn được điểm thêm những nguyên liệu tươi ngon như rau sống, dưa leo, và các loại gia vị khác. Được phục vụ cùng với nước chấm đậm đà, bò bóp mẻ không chỉ là món ăn ngon mà còn là trải nghiệm ẩm thực thú vị mà bạn không nên bỏ lỡ", 1));
        list.add(new ThucDon(R.drawable.doan3, R.drawable.doan3, R.drawable.doan3, R.drawable.doan3,
                R.drawable.doan3,"Còn", 10000, "Lẩu cá trình", 15, "Lẩu cá trình là một trong những món ăn nổi bật của ẩm thực miền Trung Việt Nam, được chế biến từ cá trình tươi ngon, loại cá đặc biệt được ưa chuộng bởi thịt ngọt và mềm. Món lẩu không chỉ hấp dẫn bởi vị ngon của cá mà còn bởi sự hòa quyện của nước dùng đậm đà, các loại rau sống tươi ngon và gia vị thơm phức. Lẩu cá trình thường được thưởng thức trong những dịp sum họp gia đình hoặc bạn bè, mang đến không khí ấm cúng và hương vị tuyệt vời khó quên.", 1));
        list.add(new ThucDon(R.drawable.doan4, R.drawable.doan4, R.drawable.doan4, R.drawable.doan4,
                R.drawable.doan4,"Hết", 300000, "Mực nướng & SET 10", 20, "Mực nướng là món ăn đặc sắc không thể thiếu trong thực đơn hải sản, với mực tươi ngon được tẩm ướp gia vị thơm ngon và nướng trên lửa than. Món ăn này mang đến hương vị đậm đà, giòn rụm và quyến rũ, làm say lòng thực khách ngay từ lần đầu thưởng thức. Đặc biệt, SET 10 bao gồm 10 món ăn kèm khác nhau, giúp bạn khám phá đa dạng hương vị, từ các loại hải sản đến món khai vị hấp dẫn. Đây thực sự là lựa chọn hoàn hảo cho những buổi tiệc tối cùng gia đình và bạn bè.", 1));

        thucDonAdapter = new ThucDonAdapter(getContext(), R.layout.activity_item_thucdon, list);
        gridView.setAdapter(thucDonAdapter);

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

    private void initDrinkData() {
        List<ThucDon> list = new ArrayList<>();
        list.add(new ThucDon(R.drawable.doan1, R.drawable.doan1, R.drawable.doan1, R.drawable.doan1,
                R.drawable.coca,"Còn", 20000, "CocaCola", 5, "Coca-Cola là thức uống có ga được yêu thích trên toàn thế giới. Với vị ngọt mát lạnh, sảng khoái, Coca-Cola không chỉ giúp giải khát mà còn là thức uống lý tưởng để thưởng thức cùng các món ăn. Đặc biệt, hương vị của Coca-Cola rất phù hợp với các món ăn như pizza, burger hay món chiên. Đây thực sự là lựa chọn không thể thiếu trong mỗi bữa tiệc hay buổi liên hoan.", 1));
        list.add(new ThucDon(R.drawable.doan2, R.drawable.doan2, R.drawable.doan2, R.drawable.doan2,
                R.drawable.nuoccam,"Còn", 15000, "Fanta", 30, "Fanta là một trong những thương hiệu nước giải khát nổi tiếng, với nhiều hương vị trái cây thơm ngon. Món nước này rất được yêu thích, đặc biệt trong những ngày hè oi ả. Fanta không chỉ giải khát mà còn mang đến sự mới mẻ, giúp bạn tận hưởng những khoảnh khắc vui vẻ bên bạn bè và gia đình. Hãy cùng Fanta tạo nên những kỷ niệm đáng nhớ trong mùa hè này!", 1));
        list.add(new ThucDon(R.drawable.doan3, R.drawable.doan3, R.drawable.doan3, R.drawable.doan3,
                R.drawable.nuocbohuc,"Còn", 10000, "Nước bò húc", 15, "Pepsi là một trong những loại nước ngọt được ưa chuộng nhất, với hương vị độc đáo và sảng khoái. Không chỉ thích hợp để uống một mình, Pepsi còn là lựa chọn tuyệt vời để kết hợp với các món ăn trong các bữa tiệc. Hương vị ngọt ngào của Pepsi sẽ mang đến cho bạn trải nghiệm thú vị và khó quên.", 1));
        list.add(new ThucDon(R.drawable.doan4, R.drawable.doan4, R.drawable.doan4, R.drawable.doan4,
                R.drawable.lavie,"Hết", 30000, "Nước lọc Lavie", 20, "Nước ép trái cây là một trong những thức uống bổ dưỡng, cung cấp vitamin và khoáng chất cần thiết cho cơ thể. Với hương vị thơm ngon, nước ép trái cây không chỉ giúp giải khát mà còn mang đến cảm giác sảng khoái, tươi mát. Đặc biệt, nước ép trái cây còn rất dễ chế biến, giúp bạn thưởng thức hương vị tự nhiên ngay tại nhà. Hãy bổ sung nước ép trái cây vào thực đơn hàng ngày để có sức khỏe tốt hơn nhé!", 1));

        thucDonAdapter = new ThucDonAdapter(getContext(), R.layout.activity_item_thucdon, list);
        gridView.setAdapter(thucDonAdapter);

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

