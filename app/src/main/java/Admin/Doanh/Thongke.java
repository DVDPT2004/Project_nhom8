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

public class Thongke extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_thongke);

        // Khởi tạo BarChart
        BarChart chart = findViewById(R.id.barchart);

        // Tạo danh sách BarEntry cho đơn hàng thành công và bị hủy theo từng tháng
        ArrayList<BarEntry> successfulOrders = new ArrayList<>();
        successfulOrders.add(new BarEntry(0, 70f));  // Đơn hàng thành công tháng 1
        successfulOrders.add(new BarEntry(1, 80f));  // Đơn hàng thành công tháng 2
        successfulOrders.add(new BarEntry(2, 90f));  // Đơn hàng thành công tháng 3
        successfulOrders.add(new BarEntry(3, 100f)); // Đơn hàng thành công tháng 4
        successfulOrders.add(new BarEntry(4, 110f)); // Đơn hàng thành công tháng 5
        successfulOrders.add(new BarEntry(5, 120f)); // Đơn hàng thành công tháng 6
        successfulOrders.add(new BarEntry(6, 130f)); // Đơn hàng thành công tháng 7
        successfulOrders.add(new BarEntry(7, 140f)); // Đơn hàng thành công tháng 8
        successfulOrders.add(new BarEntry(8, 150f)); // Đơn hàng thành công tháng 9
        successfulOrders.add(new BarEntry(9, 160f)); // Đơn hàng thành công tháng 10
        successfulOrders.add(new BarEntry(10, 170f)); // Đơn hàng thành công tháng 11
        successfulOrders.add(new BarEntry(11, 180f)); // Đơn hàng thành công tháng 12

        ArrayList<BarEntry> canceledOrders = new ArrayList<>();
        canceledOrders.add(new BarEntry(0, 5f));   // Đơn hàng bị hủy tháng 1
        canceledOrders.add(new BarEntry(1, 6f));   // Đơn hàng bị hủy tháng 2
        canceledOrders.add(new BarEntry(2, 7f));   // Đơn hàng bị hủy tháng 3
        canceledOrders.add(new BarEntry(3, 8f));   // Đơn hàng bị hủy tháng 4
        canceledOrders.add(new BarEntry(4, 9f));   // Đơn hàng bị hủy tháng 5
        canceledOrders.add(new BarEntry(5, 10f));  // Đơn hàng bị hủy tháng 6
        canceledOrders.add(new BarEntry(6, 11f));  // Đơn hàng bị hủy tháng 7
        canceledOrders.add(new BarEntry(7, 12f));  // Đơn hàng bị hủy tháng 8
        canceledOrders.add(new BarEntry(8, 13f));  // Đơn hàng bị hủy tháng 9
        canceledOrders.add(new BarEntry(9, 14f));  // Đơn hàng bị hủy tháng 10
        canceledOrders.add(new BarEntry(10, 15f)); // Đơn hàng bị hủy tháng 11
        canceledOrders.add(new BarEntry(11, 16f)); // Đơn hàng bị hủy tháng 12

        // Tạo BarDataSet cho đơn hàng thành công và bị hủy
        BarDataSet successfulDataSet = new BarDataSet(successfulOrders, "Đơn hàng thành công");
        successfulDataSet.setColor(0xFF4CAF50); // Màu xanh lá cây

        BarDataSet canceledDataSet = new BarDataSet(canceledOrders, "Đơn hàng bị hủy");
        canceledDataSet.setColor(0xFFFF0000); // Màu đỏ

        // Tạo BarData từ các BarDataSet
        BarData data = new BarData(successfulDataSet, canceledDataSet);
        data.setValueTextSize(12f); // Thiết lập kích thước chữ cho giá trị trên cột

        // Thiết lập dữ liệu cho biểu đồ
        chart.setData(data);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new String[]{"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"})); // Định dạng trục x

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
                Intent intent = new Intent(Thongke.this, DoanhThu.class);
                startActivity(intent);
            }
        });
    }
}
