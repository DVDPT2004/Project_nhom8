package User.Duy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.text.SimpleDateFormat;
import java.util.List;

import User.Duy.Modal.DonHang;
import User.Duy.Views.OrderDetailActivity;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    private List<DonHang> donHangList;
    private Context context;

    // Constructor
    public DonHangAdapter(List<DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_don_hang_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.textMaDonHang.setText("Mã Đơn Hàng: " + donHang.getMaDonHang());
        holder.textNgayGioDatHang.setText("Ngày Giờ Đặt: " + sdf.format(donHang.getNgayGioDatHang()));
        holder.textThanhTien.setText("Thành Tiền: " + donHang.getThanhTien() + " VNĐ");
        holder.textTinhTrangDonHang.setText("Tình Trạng: " + getTinhTrang(donHang.getTinhTrangDonHang()));


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("DON_HANG", donHang); // Truyền thông tin đơn hàng
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMaDonHang, textNgayGioDatHang, textThanhTien, textTenKH, textTinhTrangDonHang;
        TextView textNoiGiaoHang, textSoDienThoai, textPhuongThucThanhToan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMaDonHang = itemView.findViewById(R.id.textMaDonHang);
            textNgayGioDatHang = itemView.findViewById(R.id.textNgayGioDatHang);
            textThanhTien = itemView.findViewById(R.id.textThanhTien);
            textTinhTrangDonHang = itemView.findViewById(R.id.textTinhTrangDonHang);

        }
    }
    private String getTinhTrang(int trangthai_int){
        String trangthai_string = "";
        if(trangthai_int == 0){
            trangthai_string = "chưa chuẩn bị";
        }
        else if(trangthai_int == 1){
            trangthai_string = "đang chuẩn bị";
        }
        else if(trangthai_int == 2){
            trangthai_string = "giao hàng thành công";
        }
        else if(trangthai_int == 3){
            trangthai_string = "hủy đơn hàng";
        }
        return trangthai_string;
    }
}
