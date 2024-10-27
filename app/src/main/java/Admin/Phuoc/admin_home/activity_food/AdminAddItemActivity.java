package Admin.Phuoc.admin_home.activity_food;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom8.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Admin.Phuoc.admin_home.object.Category;
import Admin.Phuoc.admin_home.object.Food;
import Admin.Phuoc.admin_home.object_adapter.CategoryAdapter;
import Admin.Phuoc.admin_home.object_adapter.FoodAdapter;
import Admin.Phuoc.admin_home.object_database.CategoryDatabase;
import Admin.Phuoc.admin_home.object_database.FoodDatabase;
import Database.MainData.MainData;

public class AdminAddItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu chọn ảnh
    private GridLayout gridLayout;
    private CategoryAdapter categoryAdapter;
    private ActivityResultLauncher<Intent> imageMainLauncher; // Trình khởi tạo cho ảnh chính
    private ActivityResultLauncher<Intent> imageSubsidiaryLauncher; // Trình khởi tạo cho ảnh phụ
    private ArrayList<Uri> selectedImageUris = new ArrayList<>(); // Danh sách các URI ảnh phụ đã chọn
    private EditText nameEditText, descriptionEditText, priceEditText, discountEditText; // Các trường nhập liệu
    private Spinner categorySpinner, statusSpinner; // Spinner cho danh mục món ăn
    private ImageView imageViewMain; // Hình ảnh chính của món ăn
    private Button addButton; // Nút thêm món ăn
    private Button adminAddCategoryButton; // Nút thêm danh mục mới
    private ArrayList<Food> foodList;
    private Uri imageMain; // URI của hình ảnh chính
    ArrayList<String> status;
    ArrayList<Category> categorylist;
    ArrayList<String> categorylistStr = new ArrayList<>();

    private MainData db;
    private FoodDatabase foodDatabase;
    private CategoryDatabase categoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_them_do_an);

        // Tham chiếu các view cần kiểm tra
        initializeViews();

        // danh mục món ăn
        setupCategorySpinner();

        checkNameExists();

        // traạng thai món ăn
        setupStatusSpinner();

        //  sự kiện cho nút thêm danh mục
        setupAddCategoryButton();

        // sự kiện cho nút chọn ảnh chính
        setupImageMainButton();

        // sự kiện cho nút chọn ảnh phụ
        setupImageSubsidiaryButton();

        //  sự kiện cho nút quay lại
        setupBackButton();

        //  sự kiện cho nút thêm món ăn
        setupAddButton();
    }

    private void initializeViews() {
        // Khởi tạo các view
        nameEditText = findViewById(R.id.admin_name_add_item);
        categorySpinner = findViewById(R.id.admin_category_add_item);
        descriptionEditText = findViewById(R.id.admin_description_add_item);
        priceEditText = findViewById(R.id.admin_price_add_item);
        discountEditText = findViewById(R.id.admin_discount_add_item);
        imageViewMain = findViewById(R.id.imageView_main);
        addButton = findViewById(R.id.admin_add_item);
        gridLayout = findViewById(R.id.listView_image_sub);
        statusSpinner = findViewById(R.id.admin_status_add_item);
    }



    public void setupCategorySpinner() {
        // Thiết lập danh sách danh mục món ăn
        categorylistStr.clear();
        categorylist = new ArrayList<>();
        categorylistStr.add("");

        db = new MainData(this,"mainData.sqlite",null,1);
        categoryDatabase = new CategoryDatabase(db);

        ArrayList<Category> categoryListFromDb = categoryDatabase.selectCategory();
//        Log.d(""+categoryListFromDb.size(),"aaaaaaa");
        categorylist.addAll(categoryListFromDb);

        // Tạo adapter cho spinner
        for (Category x: categorylist) {
            categorylistStr.add(x.getNameCategory());
        }
        Collections.sort(categorylistStr);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorylistStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void setupStatusSpinner() {
        // Thiết lập danh sách tranạng thái món ăn
        status = new ArrayList<>();
        status.add("Còn");
        status.add("Hết");

        // Tạo adapter cho spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
    }

    private void checkNameExists(){
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                db = new MainData(AdminAddItemActivity.this,"mainData.sqlite",null,1);
                foodDatabase = new FoodDatabase(db);
                foodList = new ArrayList<>();
                foodList = foodDatabase.selectFood();
                for (Food x: foodList) {
                    if(nameEditText.getText().toString().trim().equals(x.getNameFood())){
                        nameEditText.setError("Tên món ăn đã tồn tại!");
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupAddCategoryButton() {
        // Nút thêm danh mục
        adminAddCategoryButton = findViewById(R.id.admin_add_category);
        adminAddCategoryButton.setOnClickListener(v -> showAddCategoryPopup());
    }

    private void setupImageMainButton() {
        // Chọn hình ảnh chính
        Button buttonChooseImage = findViewById(R.id.imageMain);
        imageMainLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                imageViewMain.setImageBitmap(bitmap);
                                imageMain = imageUri;

                                Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        buttonChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            imageMainLauncher.launch(Intent.createChooser(intent, "Chọn ảnh chính"));
        });
    }

    private void setupImageSubsidiaryButton() {
        // Chọn hình ảnh phụ
        imageSubsidiaryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {     // kiem tra xem co chon duoc anh khong
                        ClipData clipData = result.getData().getClipData();
                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount() && selectedImageUris.size() < 4; i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                selectedImageUris.add(imageUri);
                            }
                        } else if(selectedImageUris.size() < 4) {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null && selectedImageUris.size() < 4) {
                                selectedImageUris.add(imageUri);
                            }
                        }
                        updateImageViews();
                    }
                }
        );

        Button imageSubsidiaryButton = findViewById(R.id.imageSubsidiary);
        imageSubsidiaryButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imageSubsidiaryLauncher.launch(Intent.createChooser(intent, "Chọn tối đa 4 ảnh"));
        });
    }

    private void setupBackButton() {
        // Nút quay lại
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("foodList", getIntent().getParcelableArrayListExtra("foodList")); // Truyền lại danh sách đã cập nhật
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    private void setupAddButton() {
        // Lắng nghe sự kiện khi nhấn nút "Thêm"
        addButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // viet truy van add item vao daâtbase
                db = new MainData(this,"mainData.sqlite",null,1);
                foodDatabase = new FoodDatabase(db);
                int discount = 0;
                long currentPrice = Integer.parseInt(priceEditText.getText().toString());
                if(!discountEditText.getText().toString().trim().isEmpty()){
                    discount = Integer.parseInt(discountEditText.getText().toString());
                    currentPrice = (long) Math.ceil(Integer.parseInt(priceEditText.getText().toString()) * (100 - Integer.parseInt(discountEditText.getText().toString())) / 100.0);
                }
                Food newFood = new Food(categorySpinner.getSelectedItem().toString().trim(),
                        statusSpinner.getSelectedItem().toString().trim(),
                        Integer.parseInt(priceEditText.getText().toString()),
                        currentPrice,
                        nameEditText.getText().toString(),
                        imageMain,
                        descriptionEditText.getText().toString(),
                        discount,
                        selectedImageUris);

                foodDatabase.insertFood(newFood);

                clearInputs();
                Toast.makeText(AdminAddItemActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(AdminAddItemActivity.this, "Ten san pham da ton tai!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputs() {
        nameEditText.setText("");
        descriptionEditText.setText("");
        priceEditText.setText("");
        discountEditText.setText("");
        categorySpinner.setSelection(0);
        imageViewMain.setImageBitmap(null);
        statusSpinner.setSelection(0);
    }

    // Hàm hiển thị popup để thêm danh mục mới
    private void showAddCategoryPopup() {
        // Lấy kích thước màn hình
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        int popupHeight = screenHeight / 2;  // Chiều dài = 1/2 chiều dài màn hình
        int popupWidth = 4 * screenWidth / 5;

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_admin_them_danh_muc, null);

        // Tạo PopupWindow
        final PopupWindow popupWindow = new PopupWindow(popupView,
                popupWidth,
                popupHeight,
                true);

        // Hiển thị PopupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);

        // Làm mờ lớp background phía sau PopupWindow
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.7f;  // Điều chỉnh độ mờ
        getWindow().setAttributes(layoutParams);

        // Khôi phục độ trong suốt sau khi popup đóng
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams paramss = getWindow().getAttributes();
            paramss.alpha = 1.0f;
            getWindow().setAttributes(paramss);
        });

        popupWindow.showAtLocation(findViewById(R.id.admin_add_item), Gravity.CENTER, 0, 0);

        // Tham chiếu đến các view bên trong popup
        EditText categoryEditText = popupView.findViewById(R.id.new_category_name);
        Button saveCategoryButton = popupView.findViewById(R.id.add_category_button);
        RecyclerView recyclerView = popupView.findViewById(R.id.recyclerView_category);

        // Sử dụng LinearLayoutManager để hiển thị theo chiều dọc
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminAddItemActivity.this));

        Collections.sort(categorylist, new Comparator<Category>() {
            @Override
            public int compare(Category p1, Category p2) {
                return p1.getNameCategory().compareTo(p2.getNameCategory());  // Sắp xếp tăng dần theo tên
            }
        });
        // Khởi tạo adapter và gán cho RecyclerView
        categoryAdapter = new CategoryAdapter( R.layout.activity_admin_item_danh_muc,categorylist,AdminAddItemActivity.this);
        recyclerView.setAdapter(categoryAdapter);


        // Xử lý khi nhấn nút lưu danh mục
        saveCategoryButton.setOnClickListener(v -> {
            String newCategory = categoryEditText.getText().toString().trim();
            if (!newCategory.isEmpty()) {
                db = new MainData(this,"mainData.sqlite",null,1);
                categoryDatabase = new CategoryDatabase(db);

                Category newCategoryob = new Category(newCategory);
                if(categoryDatabase.insertCategory(newCategoryob)){
                    categorylist.add(newCategoryob);
                    Toast.makeText(AdminAddItemActivity.this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminAddItemActivity.this, "Danh mục: " + newCategory + " đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
                setupCategorySpinner();
                recyclerView.setAdapter(categoryAdapter);
                //popupWindow.dismiss();
            } else {
                Toast.makeText(AdminAddItemActivity.this, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Cập nhật hình ảnh phụ hiển thị trong GridLayout
    private void updateImageViews() {
        gridLayout.removeAllViews(); // Xóa tất cả các view hiện có

        for (int i = 0; i < selectedImageUris.size(); i++) {
            Uri imageUri = selectedImageUris.get(i);

            // Tạo một ImageView để hiển thị ảnh
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(200, 180))); // Kích thước ảnh
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageURI(imageUri);

            // Thêm sự kiện click để xóa ảnh khi người dùng nhấn vào
            final int index = i;
            imageView.setOnClickListener(v -> {
                // Xóa ảnh được chọn
                selectedImageUris.remove(index);
                updateImageViews(); // Cập nhật lại danh sách ảnh
            });

            // Thêm ImageView vào GridLayout
            gridLayout.addView(imageView);
        }
    }

    // Hàm kiểm tra đầu vào từ người dùng
    private boolean validateInputs() {
        db = new MainData(AdminAddItemActivity.this,"mainData.sqlite",null,1);
        foodDatabase = new FoodDatabase(db);
        foodList = new ArrayList<>();
        foodList = foodDatabase.selectFood();
        for (Food x: foodList) {
            if(nameEditText.getText().toString().trim().equals(x.getNameFood())){
                nameEditText.setError("Tên món ăn đã tồn tại!");
                return false;
            }
        }

        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Tên món ăn không được để trống");
            nameEditText.requestFocus();
            return false;
        }

        if (categorySpinner.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(this, "Danh mục không được để trống", Toast.LENGTH_SHORT).show();
            categorySpinner.requestFocus();
            return false;
        }

        if (descriptionEditText.getText().toString().trim().isEmpty()) {
            descriptionEditText.setError("Mô tả không được để trống");
            descriptionEditText.requestFocus();
            return false;
        }

        if (priceEditText.getText().toString().trim().isEmpty()) {
            priceEditText.setError("Giá không được để trống");
            priceEditText.requestFocus();
            return false;
        }

//        if (discountEditText.getText().toString().trim().isEmpty()) {
//            discountEditText.setError("Giảm giá không được để trống");
//            discountEditText.requestFocus();
//            return false;
//        }
        if(!discountEditText.getText().toString().trim().isEmpty()){
            if (Integer.parseInt(discountEditText.getText().toString().trim()) < 0 || Integer.parseInt(discountEditText.getText().toString().trim()) > 100) {
                discountEditText.setError("Giảm giá không hợp lệ");
                discountEditText.requestFocus();
                return false;
            }
        }

        if (imageViewMain.getDrawable() == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh chính", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Nếu tất cả các trường đều hợp lệ
        return true;
    }
}
