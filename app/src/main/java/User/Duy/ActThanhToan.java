package User.Duy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import com.example.project_nhom8.R;

public class ActThanhToan extends AppCompatActivity {
    Spinner spinner;
    ListView lv_donhang;
    EditText et_hoten;
    EditText et_sdt;
    EditText et_diachi;
    Button btn_huy;
    Button btn_dathang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_thanh_toan_user);
        // findview by id
        lv_donhang = findViewById(R.id.lv_donhang);
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

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}
