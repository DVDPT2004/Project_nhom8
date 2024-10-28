package Admin.Phuoc.admin_home.object_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_nhom8.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Admin.Phuoc.admin_home.activity_food.AdminEditItemActivity;
import Admin.Phuoc.admin_home.object_database.FoodDatabase;
import Admin.Phuoc.admin_home.object.Food;
import Database.MainData.MainData;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private int layout;
    private List<Food> foodList;
    private FoodDatabase foodDatabase;
    private MainData db;
    private static final int REQUEST_CODE_ADD_PRODUCT = 1;

    public FoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent,
                false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

        Glide.with(context)
                .load(food.getImageMain())
                .into(holder.imageFood);

        holder.nameFood.setText(food.getNameFood());
        if (food.getDiscount() > 0) {
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // tạo gạch ngang cho gias gốc
            holder.price.setTextColor(context.getResources().getColor(R.color.darker_gray));
            holder.price.setTypeface(holder.price.getTypeface(), Typeface.NORMAL); // Đặt kiểu chữ là bình thường
            holder.priceCurrent.setText(formatter.format(food.getPricecurrent()) + " VND");
            holder.discount.setText("Giảm " + String.valueOf(food.getDiscount()) + "%");
            // Cài đặt màu sắc mặc định cho TextView
            holder.discount.setTextColor(Color.parseColor("#ff0000"));
            // Load animation từ file XML
            Animation blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.blink_animation);
            // Bắt đầu hiệu ứng nhấp nháy
            holder.discount.startAnimation(blinkAnimation);
        } else if(food.getDiscount() <= 0) {
            holder.price.setPaintFlags(holder.price.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)); // Hủy bỏ gạch ngang
            holder.priceCurrent.setText("");
            holder.price.setTextColor(context.getResources().getColor(R.color.holo_red_dark));
            holder.price.setTypeface(holder.price.getTypeface(), Typeface.BOLD); // Đặt kiểu chữ là đậm
            holder.discount.setText("");
        }
        holder.price.setText(formatter.format(food.getPrice()) + " VND");
        holder.category.setText(food.getCategory());
        holder.status.setText(food.getStatus());
        if(food.getStatus().equals("Hết")){
            holder.backgroundItem.setBackgroundColor(Color.parseColor("#C2C2C2"));
        }else {
            holder.backgroundItem.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.description.setText(food.getDescription());


        holder.buttonDeleteItem.setOnClickListener(v -> {
            confirmDeletePopup(holder.itemView, position);
        });

// nut sua
        holder.buttonEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminEditItemActivity.class);
                Food foodItem = foodList.get(holder.getAdapterPosition());

                intent.putExtra("food_id",foodItem.getIdFood());

                intent.putExtra("food_name", foodItem.getNameFood());
                intent.putExtra("food_category", foodItem.getCategory());
                intent.putExtra("food_description", foodItem.getDescription());
                intent.putExtra("food_price", foodItem.getPrice());
                intent.putExtra("food_discount", foodItem.getDiscount());
                intent.putExtra("food_status", foodItem.getStatus());
                intent.putExtra("food_imageMain", foodItem.getImageMain().toString());

                ArrayList<Uri> imageSubsList = new ArrayList<>(foodItem.getImageSubs());
                intent.putParcelableArrayListExtra("food_imageSubs", imageSubsList);

                // Sử dụng startActivityForResult thay vì ActivityResultLauncher
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);
            }

        });
    }

    //     Hàm hiển thị popup để xóa san phẩm
    private void confirmDeletePopup(View view, int position) {
        // Inflate layout popup
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_admin_xac_nhan_an, null);
        // Tạo PopupWindow
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        // Hiển thị PopupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Lấy tham chiếu đến Activity từ Context
        Activity activity = (Activity) context;

        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.alpha = 0.7f;  // Điều chỉnh độ mờ backgroud(0.0f - 1.0f)
        activity.getWindow().setAttributes(layoutParams);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = 1.0f;  // Khôi phục lại độ trong suốt
            activity.getWindow().setAttributes(params);
        });

        Button cancelButton = popupView.findViewById(R.id.cancel_button);
        Button confirmButton = popupView.findViewById(R.id.confirm_button);

        // Xử lý khi nhấn nút "Hủy"
        cancelButton.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        // Xử lý khi nhấn nút "Xóa"
        confirmButton.setOnClickListener(v -> {
            // Xóa item khỏi danh sách
            Food food = foodList.get(position);
            int foodId = food.getIdFood();
            db = new MainData(context,"mainData.sqlite",null,1);
            foodDatabase = new FoodDatabase(db);
            if(foodDatabase.updateStatusFood(foodId)){
                Toast.makeText(context, "Món ăn đã được ẩn", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Món ăn đang ở trạng thái còn.\n Không được xóa!", Toast.LENGTH_SHORT).show();
            }
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, foodList.size());
            foodList.clear();
            foodList = foodDatabase.selectFood();
            updateList(foodList);
            // Đóng popup
            popupWindow.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void updateList(List<Food> newList) {
        foodList = newList;
        notifyDataSetChanged();  // Thông báo cho adapter để làm mới RecyclerView
    }

    // ViewHolder giúp giữ các view cho mỗi item
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFood;
        TextView nameFood, price, category, status, description, discount, priceCurrent;
        Button buttonDeleteItem,buttonEditItem;
        LinearLayout backgroundItem;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.admin_image_item);
            nameFood = itemView.findViewById(R.id.admin_name_item);
            price = itemView.findViewById(R.id.admin_original_price_item);
            category = itemView.findViewById(R.id.admin_category_item);
            status = itemView.findViewById(R.id.admin_status_item);
            description = itemView.findViewById(R.id.admin_description_item);
            discount = itemView.findViewById(R.id.admin_discount_item);
            priceCurrent = itemView.findViewById(R.id.admin_current_price_item);
            buttonDeleteItem = itemView.findViewById(R.id.admin_button_delete_item);
            buttonEditItem = itemView.findViewById(R.id.admin_button_edit_item);
            backgroundItem = itemView.findViewById(R.id.background_item);

        }
    }

}
