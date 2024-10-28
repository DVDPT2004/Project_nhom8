package Admin.Doanh;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.project_nhom8.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Admin.Phuoc.admin_order.object_database.OrderDatabase;
import Dangky_nhap.Views.Profile_admin;
import Database.MainData.MainData;

public class DoanhThu extends AppCompatActivity implements OnChartValueSelectedListener {
    private  List<String> xLabel = new ArrayList<>(); //ox
    private  ArrayList<Entry> entries = new ArrayList<>(); // điểm tren do thi
    private CombinedChart mChart;
    private  EditText edtDateStart, edtDateEnd;
    private   TextView text_donhang,text_doanhthu;
    private    int totalDonhang=0 ;
    private    int totalDoanhthu=0;
    private    int totalRevenue = 0 ;
    private ArrayList<Integer> dsTongtien = new ArrayList<>();
    private OrderDatabase orderDatabase;
    private MainData db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_doanh_thu);



//        Xử lý button

//        Ngày start
        edtDateStart = findViewById(R.id.edtDateStart);
        edtDateStart.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(DoanhThu.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Lưu ý: Month trả về từ 0-11 nên cần +1
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        edtDateStart.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

//      Ngày end
        edtDateEnd = findViewById(R.id.edtDateEnd);
        edtDateEnd.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(DoanhThu.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Lưu ý: Month trả về từ 0-11 nên cần +1
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        edtDateEnd.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });



        Button btnThongKe = findViewById(R.id.but_thongke);

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang StatisticsActivity khi nhấn vào nút
                Intent intent = new Intent(DoanhThu.this, Thongke.class);
                startActivity(intent);
            }
        });

        ImageButton btn_adminAc1 =  findViewById(R.id.btn_adminAc);
        btn_adminAc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoanhThu.this, Profile_admin.class);
                startActivity(intent);

            }
        });

        Button btnHienThi = findViewById(R.id.but_hienthi);
        btnHienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setDateRangeToXLabel();
            }
        });


//        Hết xử lý button


//        Xử lý textview
        text_doanhthu = findViewById(R.id.text_doanhthu);
        text_donhang = findViewById(R.id.text_donhang);
        text_doanhthu.setText(String.valueOf(totalDoanhthu) + " VND");
        text_donhang.setText(String.valueOf(totalDonhang));

//        Hết xử lý texview


        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);



    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private void setDateRangeToXLabel() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        try {
            // Lấy giá trị ngày bắt đầu và kết thúc từ EditText
            Date startDate = dateFormat.parse(edtDateStart.getText().toString());
            Date endDate = dateFormat.parse(edtDateEnd.getText().toString());

            // Kiểm tra xem ngày kết thúc có sau ngày bắt đầu không
            if (startDate != null && endDate != null && !startDate.after(endDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                xLabel.clear();
                dsTongtien.clear();
                // Thêm các ngày dạng chuỗi vào mảng xLabel cho đến khi đạt đến ngày kết thúc
                while (!calendar.getTime().after(endDate)) {
//                    Kết nối db order
                    db = new MainData(DoanhThu.this,"mainData.sqlite",null,1);
                    orderDatabase = new OrderDatabase(db);
                    String dateString = dateFormat.format(calendar.getTime()); // Định dạng thành chuỗi
                    totalRevenue = orderDatabase.selectTongTien(dateString); // Tính tổng tiền
                    Log.d("Tongtien", "setDateRangeToXLabel: "+totalRevenue);
                    dsTongtien.add(totalRevenue); // thiết lập danh sach them truc oy
                    // tong text doanh thu
                    totalDoanhthu +=totalRevenue;
// tong text đơn
                    totalDonhang += orderDatabase.DemSoDon(dateString);
                    xLabel.add(dateString); // Thêm chuỗi vào xLabel

                    calendar.add(Calendar.DAY_OF_MONTH, 1); // Tăng ngày lên 1
                }

                //        Xử lý textview
                NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

                text_doanhthu.setText(formatter.format(totalDoanhthu) + " VND");
                text_donhang.setText(String.valueOf(totalDonhang));
                totalDoanhthu = 0;
                totalDonhang=0;
//        Hết xử lý texview

            } else {
                Toast.makeText(this, "Vui lòng chọn khoảng ngày hợp lệ", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Chọn ngày hợp lệ", Toast.LENGTH_SHORT).show();

        }

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mChart.setData(data);
        mChart.invalidate();
    }

    private  DataSet dataChart() {
            entries.clear();

        ArrayList<Integer> data = dsTongtien;

        Log.d("daata", "dataChart: "+xLabel.size() +" "+ data.size());
        //   Các điểm trên đồ thị
        for (int index = 0; index < xLabel.size(); index++) {
//   test mac dinh         if (index < data.size()) {
//                // Nếu index không vượt quá chiều dài của mảng data, lấy giá trị từ data
//                entries.add(new Entry(index, data.get(index)));
//                Log.d("daata11", "dataChart: "+index);
//            } else {
//                // Nếu index vượt quá chiều dài của mảng data, thêm giá trị 0
//                entries.add(new Entry(index, 0));
//                Log.d("daata12", "dataChart: "+index);
//            }
//            Log.d("daata13", "dataChart: "+index);



            entries.add(new Entry(index, data.get(index)));

        }


        LineDataSet set = new LineDataSet(entries, "Doanh thu theo ngay"); //  Là danh sách các Entry mà bạn đã tạo ra trước đó, đại diện cho dữ liệu bạn muốn vẽ.
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //Chọn chế độ hiển thị đường (Cubic Bezier, trong trường hợp này, tạo ra các đường cong mượt mà).
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);
        set.setAxisDependency(YAxis.AxisDependency.LEFT); //Xác định rằng dữ liệu này sẽ được vẽ trên trục Y bên trái.




        return set;
    }

}