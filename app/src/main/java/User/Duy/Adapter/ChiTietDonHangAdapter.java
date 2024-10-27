package User.Duy.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.List;

import User.Duy.Modal.ChiTietDonHang;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {
    private List<ChiTietDonHang> chiTietDonHangList;

    // Constructor
    public ChiTietDonHangAdapter(List<ChiTietDonHang> chiTietDonHangList) {
        this.chiTietDonHangList = chiTietDonHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDonHang chiTiet = chiTietDonHangList.get(position);
        holder.textMaDonHang.setText("Mã Đơn Hàng: " + chiTiet.getMaDonHang());
        holder.textMaSanPham.setText("Mã Sản Phẩm: " + chiTiet.getMaSanPham());
        holder.textSoLuong.setText("Số Lượng: " + chiTiet.getSoLuong());





    }

    @Override
    public int getItemCount() {
        return chiTietDonHangList.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textMaDonHang;
        TextView textMaSanPham;
        TextView textSoLuong;
        Button phanHoi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMaDonHang = itemView.findViewById(R.id.textMaDonHang);
            textMaSanPham = itemView.findViewById(R.id.textMaSanPham);
            textSoLuong = itemView.findViewById(R.id.textSoLuong);
            phanHoi = itemView.findViewById(R.id.user_phan_hoi);
        }
    }

}
