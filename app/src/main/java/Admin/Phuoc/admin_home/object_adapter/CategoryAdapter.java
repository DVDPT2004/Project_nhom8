package Admin.Phuoc.admin_home.object_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.util.ArrayList;

import Admin.Phuoc.admin_home.activity_food.AdminAddItemActivity;
import Admin.Phuoc.admin_home.activity_food.AdminEditItemActivity;
import Admin.Phuoc.admin_home.object.Category;
import Admin.Phuoc.admin_home.object_database.CategoryDatabase;
import Database.MainData.MainData;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    int layout;
    ArrayList<Category> categorylist;
    private CategoryDatabase categoryDatabase;
    private MainData db;

    public CategoryAdapter(int layout, ArrayList<Category> categorylist, Context context) {
        this.layout = layout;
        this.categorylist = categorylist;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categorylist.get(position);
        holder.textViewCategory.setText(category.getNameCategory());
        holder.buttonDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy vị trí chính xác tại thời điểm nhấp
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == RecyclerView.NO_POSITION) return;

                Category category = categorylist.get(currentPosition);
                String categoryname = category.getNameCategory();

                db = new MainData(context,"mainData.sqlite",null,1);
                categoryDatabase = new CategoryDatabase(db);
                if (categoryDatabase.deleteCategory(categoryname)) {
                    categorylist.remove(currentPosition);  // Xóa mục khỏi danh sách
                    notifyItemRemoved(currentPosition);    // Thông báo RecyclerView cập nhật
                    // Cập nhật danh sách danh mục trong Spinner
                    if (context instanceof AdminAddItemActivity) {
                        ((AdminAddItemActivity) context).setupCategorySpinner();
                    }
                    if (context instanceof AdminEditItemActivity) {
                        ((AdminEditItemActivity) context).setupCategorySpinner();
                    }
                    Toast.makeText(context, "Danh mục đã được xóa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Danh mục chưa được xóa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button buttonDeleteCategory;
        TextView textViewCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonDeleteCategory = itemView.findViewById(R.id.admin_button_delete_category);
            textViewCategory = itemView.findViewById(R.id.admin_textView_category);
        }
    }
}
