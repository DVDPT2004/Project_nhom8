package Admin.Phuoc.admin_order.object_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Admin.Phuoc.admin_order.object.Order;
import Admin.Phuoc.admin_order.object_database.OrderDatabase;
import Database.MainData.MainData;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private Context context;
    private int layout;
    private List<Order> orderList;
    private OrderDatabase orderDatabase;
    private MainData db;
//    private String
    private static int index;

    public OrderAdapter(int index, Context context, int layout, List<Order> orderList) {
        this.index = index;
        this.context = context;
        this.layout = layout;
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.maDonHang.setText(String.valueOf(order.getMaDonHang()));
        holder.tenKhachHang.setText(order.getTenKhachHang());
        holder.soDienThoai.setText(order.getSoDienThoai());
        holder.diaChiNhan.setText(order.getDiaChiNhan());
        holder.thucDon.setText(order.getThucDon());
        holder.ngayDatHang.setText(order.getNgayDatHang());
        holder.tinhTrang.setText(order.getTinhTrang());
        holder.thanhTien.setText(formatter.format(order.getThanhTien()) + " VNĐ");
        holder.phuongThucThanhToan.setText(order.getPhuongThucThanhToan());

        if(order.getTinhTrang().equals("Chưa chuẩn bị")){
            holder.updateStatus.setText("Xác nhận đơn");
            holder.updateStatus.setBackgroundColor(Color.parseColor("#FFBD4A"));
            holder.orderCencel.setVisibility(View.GONE);
        } else if (order.getTinhTrang().equals("Đang giao hàng")) {
            holder.updateStatus.setText("Giao hàng thành công");
            holder.updateStatus.setBackgroundColor(Color.parseColor("#8692f7"));
            holder.orderCencel.setVisibility(View.VISIBLE);
            holder.orderCencel.setBackgroundColor(Color.parseColor("#959595"));

        } else if (order.getTinhTrang().equals("Giao hàng thành công")) {
            holder.updateStatus.setText("Đã hoàn thành");
            holder.updateStatus.setBackgroundColor(Color.parseColor("#FF3515"));
            holder.orderCencel.setVisibility(View.GONE);
            holder.updateStatus.setClickable(false);
        } else if (order.getTinhTrang().equals("Giao hàng thất bại")) {
            holder.updateStatus.setClickable(false);
            holder.updateStatus.setText("Đơn hủy");
            holder.updateStatus.setBackgroundColor(Color.parseColor("#959595"));
            holder.orderCencel.setVisibility(View.GONE);
        }

        if(holder.updateStatus.getText().toString().trim().equals("Xác nhận đơn")){
            if(index == 3){
                holder.updateStatus.setOnClickListener(v -> {
                    Order order1 = orderList.get(position);
                    db = new MainData(context,"mainData.sqlite",null,1);
                    orderDatabase = new OrderDatabase(db);
                    if(orderDatabase.updateOrder(order1.getMaDonHang(),1)){
                        orderList = new ArrayList<>();
                        orderList = orderDatabase.selectOrder("SELECT * FROM DonHang ORDER by ngayGioDatHang DESC");
                        notifyDataSetChanged();
                    }
                });
            }else{
                holder.updateStatus.setOnClickListener(v -> {
                    Order order1 = orderList.get(position);
                    db = new MainData(context,"mainData.sqlite",null,1);
                    orderDatabase = new OrderDatabase(db);
                    if(orderDatabase.updateOrder(order1.getMaDonHang(),1)){
                        orderList = new ArrayList<>();
                        orderList = orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 0 ORDER by ngayGioDatHang DESC");
                        notifyDataSetChanged();
                    }
                });
            }
        }else if(holder.updateStatus.getText().toString().trim().equals("Giao hàng thành công")){
            if(index == 3){
                holder.updateStatus.setOnClickListener(v -> {
                    Order order1 = orderList.get(position);
                    db = new MainData(context,"mainData.sqlite",null,1);
                    orderDatabase = new OrderDatabase(db);
                    if(orderDatabase.updateOrder(order1.getMaDonHang(),2)){
                        orderList = new ArrayList<>();
                        orderList = orderDatabase.selectOrder("SELECT * FROM DonHang ORDER by ngayGioDatHang DESC");
                        notifyDataSetChanged();
                    }
                });
            }else{
                holder.updateStatus.setOnClickListener(v -> {
                    Order order1 = orderList.get(position);
                    db = new MainData(context,"mainData.sqlite",null,1);
                    orderDatabase = new OrderDatabase(db);
                    if(orderDatabase.updateOrder(order1.getMaDonHang(),2)){
                        orderList = new ArrayList<>();
                        orderList = orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 1 ORDER by ngayGioDatHang DESC");
                        notifyDataSetChanged();
                    }
                });
            }
        }
        if(index == 3){
            holder.orderCencel.setOnClickListener(v -> {
                Order order1 = orderList.get(position);
                db = new MainData(context,"mainData.sqlite",null,1);
                orderDatabase = new OrderDatabase(db);
                if(orderDatabase.updateOrder(order1.getMaDonHang(),3)){
                    orderList = new ArrayList<>();
                    orderList = orderDatabase.selectOrder("SELECT * FROM DonHang ORDER by ngayGioDatHang DESC");
                    notifyDataSetChanged();
                }
            });
        }else{
            holder.orderCencel.setOnClickListener(v -> {
                Order order1 = orderList.get(position);
                db = new MainData(context,"mainData.sqlite",null,1);
                orderDatabase = new OrderDatabase(db);
                if(orderDatabase.updateOrder(order1.getMaDonHang(),3)){
                    orderList = new ArrayList<>();
                    orderList = orderDatabase.selectOrder("SELECT * FROM DonHang where tinhTrangDonHang = 1 ORDER by ngayGioDatHang DESC");
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView phuongThucThanhToan,thanhTien, maDonHang, tenKhachHang, soDienThoai,diaChiNhan, thucDon, ngayDatHang, tinhTrang;
        private LinearLayout linearLayoutItem;
        Button updateStatus,orderCencel;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutItem = itemView.findViewById(R.id.admin_order_item);
            phuongThucThanhToan = itemView.findViewById(R.id.admin_order_phuongthucthanhtoan);
            thanhTien = itemView.findViewById(R.id.admin_order_thanhtien);
            maDonHang = itemView.findViewById(R.id.admin_order_madonhang);
            tenKhachHang = itemView.findViewById(R.id.admin_order_tenkhachhang);
            soDienThoai = itemView.findViewById(R.id.admin_order_sodienthoai);
            diaChiNhan = itemView.findViewById(R.id.admin_order_diachinhan);
            thucDon = itemView.findViewById(R.id.admin_order_thucdon);
            ngayDatHang = itemView.findViewById(R.id.admin_order_ngaydathang);
            tinhTrang = itemView.findViewById(R.id.admin_order_tinhtrangdonhang);
            updateStatus = itemView.findViewById(R.id.admin_order_button_update_status);
            orderCencel = itemView.findViewById(R.id.admin_order_button_order_cencel);

        }
    }
}
