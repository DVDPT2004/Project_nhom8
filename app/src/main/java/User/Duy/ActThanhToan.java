package User.Duy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom8.MainActivity;
import com.example.project_nhom8.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Database.MainData.MainData;
import User.Viet.activity_trangchu.Trangchu;

public class ActThanhToan extends AppCompatActivity {
    Spinner spinner;
    ListView lv_donhang;
    EditText et_hoten;
    EditText et_sdt;
    EditText et_diachi;
    Button btn_huy;
    Button btn_dathang;
    List<GioHang> list;
    ArrayAdapter<String> itemApapter;
    TextView txt_tongtienthanhtoan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_thanh_toan_user);
        // findview by id
        txt_tongtienthanhtoan = findViewById(R.id.txt_tongTienThanhToan);
        lv_donhang = (ListView) findViewById(R.id.lv_donhang);
        et_hoten = findViewById(R.id.et_hoten);
        et_sdt = findViewById(R.id.et_sdt);
        et_diachi = findViewById(R.id.et_diachi);
        btn_huy = findViewById(R.id.btn_huy);
        btn_dathang = findViewById(R.id.btn_dathang);
        spinner = findViewById(R.id.spinner); // Lấy đối tượng Spinner từ layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, // Sử dụng 'this' làm context
                R.array.pttt, // Tài nguyên mảng
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        initData();
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = et_hoten.getText().toString().trim();
                String sdt = et_sdt.getText().toString().trim();
                String diachi = et_diachi.getText().toString().trim();
                String pttt = spinner.getSelectedItem().toString().trim();
                if(hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty() ){
                    Toast.makeText(ActThanhToan.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (sdt.length() != 10) {
                    Toast.makeText(ActThanhToan.this, "Bạn phải nhập số điện thoại hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else{
                    execDatHang(hoten,sdt,diachi,list,1, pttt);
                    Intent intent = new Intent(ActThanhToan.this, Trangchu.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData(){
        GioHang giohang = new GioHang(this);
        list = giohang.getAll();
        List<String> listStringGioHang = new ArrayList<>();
        for(GioHang tmp : list){
            String price = formatCurrency(tmp.getGia());
            String msg = tmp.getTensp() + "- (" +price +")" + "- Số lượng " + tmp.getSoLuong();
            listStringGioHang.add(msg);
        }
        itemApapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStringGioHang);
        lv_donhang.setAdapter(itemApapter);
        txt_tongtienthanhtoan.setText(formatCurrency(tongTien(list)));
    }
    private void execDatHang(String hoten, String sdt, String diachi, List<GioHang> list, int user_id, String pttt){
        int tongtien = tongTien(list);
        String sql = "insert into DonHang (ngayGioDatHang,ThanhTien , user_id , noiGiaoHang ,phuongThucThanhToan , tenKH ,tinhTrangDonHang, soDienThoai ) values";
        sql += "(datetime('now'), '" +tongtien+ "', '"+user_id+"', '"+diachi+"', '"+pttt+"', '"+hoten + "', 'cho xac nhan', '"+sdt+"' )";
        MainData mainData = new MainData(this);
        mainData.ExecuteSQL(sql);
        Cursor c = mainData.SelectData("SELECT last_insert_rowid()");
        if(c != null){
            while(c.moveToNext()){
                int madh = Integer.parseInt(c.getString(0));
                for (GioHang item : list){
                    String sql_gh = "insert into chiTietDonHang values ('"+madh+"', '"+item.getIdSanPham()+"', '"+item.getSoLuong()+"')";
                    mainData.ExecuteSQL(sql_gh);
                }
            }
            Toast.makeText(ActThanhToan.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            DBGioHangManager dbGioHangManager = new DBGioHangManager(this);
            dbGioHangManager.execSQL("delete from giohang");
        }
    }
    public int tongTien(List<GioHang> list){
        int tien = 0;
        for(int i = 0; i < list.size();i++){
            tien += list.get(i).getGia() * list.get(i).getSoLuong();
        }
        return tien;
    }
    public String formatCurrency(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount) + " VNĐ";
    }
}
