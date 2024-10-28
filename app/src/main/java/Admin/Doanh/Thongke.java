package Admin.Doanh;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.project_nhom8.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import Admin.Phuoc.admin_order.object_database.OrderDatabase;
import Database.MainData.MainData;

public class Thongke extends AppCompatActivity {
    private OrderDatabase orderDatabase;
    private MainData db;
    BarData data;
    Integer demthanhcong = 0, demthatbai =  0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new MainData(Thongke.this,"mainData.sqlite",null,1);
        orderDatabase = new OrderDatabase(db);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_thongke);

        // Khởi tạo BarChart
        BarChart chart = findViewById(R.id.barchart);

        // Tạo danh sách BarEntry cho đơn hàng thành công và bị hủy theo từng tháng
        ArrayList<BarEntry> successfulOrders = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            int successfulOrderCount = orderDatabase.DemSoDonTheoThang_ThanhCong(String.valueOf(month + 1)) ;
            successfulOrders.add(new BarEntry(month, successfulOrderCount)); // gắn month theo nhãn dán getXAxis().setValueFormatter
            demthanhcong += successfulOrderCount;
        }

        ArrayList<BarEntry> canceledOrders = new ArrayList<>();

        for (int month = 0; month < 12; month++) {
            int canceledOrderCount = orderDatabase.DemSoDonTheoThang_Thatbai(String.valueOf(month + 1)) ;
            canceledOrders.add(new BarEntry(month, canceledOrderCount)); // gắn month theo nhãn dán getXAxis().setValueFormatter
            demthatbai+=canceledOrderCount;
        }
        // Tạo BarDataSet cho đơn hàng thành công và bị hủy
        BarDataSet successfulDataSet = new BarDataSet(successfulOrders, "Đơn hàng thành công");
        successfulDataSet.setColor(0xFF4CAF50); // Màu xanh lá cây

        BarDataSet canceledDataSet = new BarDataSet(canceledOrders, "Đơn hàng bị hủy");
        canceledDataSet.setColor(0xFFFF0000); // Màu đỏ0xFFFF0000

        // Tạo BarData từ các BarDataSet
            if(demthatbai > demthanhcong){
                data = new BarData(canceledDataSet,successfulDataSet);
            }else {
                data = new BarData(successfulDataSet, canceledDataSet);
            }



// Chia hai cot
//        data.setBarWidth(0.3f); // Độ rộng của mỗi cột
//
//        // Nhóm các cột theo tháng
//        float groupSpace = 0.4f; // Khoảng cách giữa các nhóm (tháng)
//        float barSpace = 0.05f;  // Khoảng cách giữa các cột trong một nhóm
//        chart.setData(data);
//        chart.groupBars(0, groupSpace, barSpace); // Nhóm cột theo tháng

        // Thiết lập dữ liệu cho biểu đồ
        data.setValueTextSize(12f); // Thiết lập kích thước chữ cho giá trị trên cột
        chart.setData(data);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new String[]{"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12",})); // Định dạng trục x

        chart.animateY(1000); // Animation cho trục y

        // Tùy chỉnh giao diện biểu đồ
        chart.getLegend().setEnabled(true);  // Hiển thị chú thích
        chart.setDrawValueAboveBar(true);     // Hiển thị giá trị trên đầu mỗi cột
        chart.setPinchZoom(false);             // Vô hiệu hóa zoom
        chart.getAxisLeft().setDrawGridLines(false); // Ẩn đường lưới bên trái
        chart.getAxisRight().setDrawGridLines(false); // Ẩn đường lưới bên phải
        chart.getXAxis().setDrawGridLines(false); // Ẩn đường lưới bên dưới
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt vị trí nhãn trục x ở phía dưới



        Button btnDoanhThu = findViewById(R.id.but_doanhthu);

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang StatisticsActivity khi nhấn vào nút
              finish();
            }
        });
    }
}
