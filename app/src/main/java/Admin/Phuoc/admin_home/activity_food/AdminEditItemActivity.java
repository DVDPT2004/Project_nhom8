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

import com.bumptech.glide.Glide;
import com.example.project_nhom8.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Admin.Phuoc.admin_home.object.Category;
import Admin.Phuoc.admin_home.object.Food;
import Admin.Phuoc.admin_home.object_adapter.CategoryAdapter;
import Admin.Phuoc.admin_home.object_database.CategoryDatabase;
import Admin.Phuoc.admin_home.object_database.FoodDatabase;
import Database.MainData.MainData;

public class AdminEditItemActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private ActivityResultLauncher<Intent> imageMainLauncher;
    private ActivityResultLauncher<Intent> imageSubsidiaryLauncher;
    private ArrayList<Uri> selectedImageUris = new ArrayList<>();
    private EditText nameEditText, descriptionEditText, priceEditText, discountEditText;
    private Spinner categorySpinner, statusSpinner;
    private ImageView editImageViewMain, backButton;
    private Button editButton,buttonChooseImage, imageSubsidiaryButton;
    private Button adminAddCategoryButton;
    List<String> category,status;
    private Uri imageMain; // URI của hình ảnh chính
    ArrayList<Category> categorylist;
    ArrayList<String> categorylistStr = new ArrayList<>();
    private CategoryDatabase categoryDatabase;
    private FoodDatabase foodDatabase;
    private MainData db;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Food> foodList;
    private int foodId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sua_do_an);

        initializeViews();

        setupBackButton();  // quay trở lai trang truoc do

        setupCategorySpinner();

        setupStatusSpinner();

//        checkNameExists();

        Intent intentEdit = getIntent();
        foodId = intentEdit.getIntExtra("food_id",0);
        String foodName = intentEdit.getStringExtra("food_name");
        String foodCategory = intentEdit.getStringExtra("food_category");
        String foodDescription = intentEdit.getStringExtra("food_description");
        long foodPrice = intentEdit.getLongExtra("food_price", 0);
        int foodDiscount = intentEdit.getIntExtra("food_discount", 0);
        String foodStatus = intentEdit.getStringExtra("food_status");
        Uri foodImageMain = Uri.parse(intentEdit.getStringExtra("food_imageMain"));
        imageMain = foodImageMain;
        ArrayList<Uri> imageSubsList = intentEdit.getParcelableArrayListExtra("food_imageSubs");
        selectedImageUris = imageSubsList;
//        Toast.makeText(this, "id = " + foodId, Toast.LENGTH_SHORT).show();

        // Thiết lập giá trị cho các EditText
        nameEditText.setText(foodName);
        int foodCategoryPosition = adapter.getPosition(foodCategory);
        categorySpinner.setSelection(foodCategoryPosition);
        descriptionEditText.setText(foodDescription);
        priceEditText.setText(String.valueOf(foodPrice)); // Chuyển đổi int thành String
        discountEditText.setText(String.valueOf(foodDiscount)); // Chuyển đổi int thành String
        int foodStatusPosition = adapter2.getPosition(foodStatus);
        statusSpinner.setSelection(foodStatusPosition);


        // Thiết lập hình ảnh chính
        if (foodImageMain != null) {
//            editImageViewMain.setImageURI(foodImageMain);
            Glide.with(AdminEditItemActivity.this)
                    .load(foodImageMain)  // Uri của ảnh
                    .into(editImageViewMain);
        }
        gridLayout.removeAllViews(); // Xóa tất cả các view hiện có
        for (Uri uri : imageSubsList) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(200, 180)));
            Glide.with(AdminEditItemActivity.this)
                    .load(uri)  // Uri của ảnh
                    .into(imageView);
            imageView.setOnClickListener(v -> {
                selectedImageUris.remove(uri);

                updateImageViews(); // Cập nhật lại danh sách ảnh
            });
            gridLayout.addView(imageView); // Thêm ImageView vào GridLayout
        }

        // Nút thêm danh mục
        adminAddCategoryButton.setOnClickListener(v ->
                showAddCategoryPopup()
        );

        // Thiết lập sự kiện cho nút chọn ảnh chính
        setupImageMainButton();

        // Thiết lập sự kiện cho nút chọn ảnh phụ
        setupImageSubsidiaryButton();

        // Lắng nghe sự kiện khi nhấn nút "cap nhat"
        setupButtonUpdate();

    }
    private void setupStatusSpinner() {
        // Thiết lập danh sách tranạng thái món ăn
        status = new ArrayList<>();
        status.add("Còn");
        status.add("Hết");

        // Tạo adapter cho spinner
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter2);
    }

    private void setupBackButton(){
        backButton.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        // Tham chiếu các view cần kiểm tra
        nameEditText = findViewById(R.id.admin_name_edit_item);
        categorySpinner = findViewById(R.id.admin_category_edit_item);
        descriptionEditText = findViewById(R.id.admin_description_edit_item);
        priceEditText = findViewById(R.id.admin_price_edit_item);
        discountEditText = findViewById(R.id.admin_discount_edit_item);
        statusSpinner = findViewById(R.id.admin_status_edit_item);
        editImageViewMain = findViewById(R.id.edit_imageView_main);
        editButton = findViewById(R.id.admin_edit_item);
        gridLayout = findViewById(R.id.edit_listView_image_sub);
        adminAddCategoryButton = findViewById(R.id.admin_edit_category);
        buttonChooseImage = findViewById(R.id.edit_imageMain);
        imageSubsidiaryButton = findViewById(R.id.edit_imageSubsidiary);
        backButton = findViewById(R.id.edit_back);
    }

    private void checkNameExists(){
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                db = new MainData(AdminEditItemActivity.this,"mainData.sqlite",null,1);
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorylistStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }
    // nut cap nhat
    private void setupButtonUpdate(){
        editButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Thực hiện các thao tác thêm món ăn vào cơ sở dữ liệu hoặc các hành động khác.
                db = new MainData(this,"mainData.sqlite",null,1);

                foodDatabase = new FoodDatabase(db);
                int discount = 0;
                if(!discountEditText.getText().toString().trim().isEmpty()){
                    discount = Integer.parseInt(discountEditText.getText().toString());
                }
                Food updateFood = new Food(foodId,categorySpinner.getSelectedItem().toString().trim(),
                        statusSpinner.getSelectedItem().toString().trim(),
                        Long.parseLong(priceEditText.getText().toString()),
                        (long) Math.ceil(Long.parseLong(priceEditText.getText().toString()) * (100 - Integer.parseInt(discountEditText.getText().toString())) / 100.0),
                        nameEditText.getText().toString(),
                        imageMain,
                        descriptionEditText.getText().toString(),
                        discount,
                        selectedImageUris);

                if(foodDatabase.updateFood(getIntent().getIntExtra("food_id",0),updateFood)){
                    Toast.makeText(AdminEditItemActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminEditItemActivity.this, "ten mon an da ton tai!", Toast.LENGTH_SHORT).show();
                    nameEditText.requestFocus();
                }

            }
        });
    }

    // anh chinh
    private void setupImageMainButton(){
        imageMainLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            getContentResolver().takePersistableUriPermission(imageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                editImageViewMain.setImageBitmap(bitmap);
                                imageMain = imageUri;
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
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageMainLauncher.launch(Intent.createChooser(intent, "Chọn ảnh chính"));
        });
    }

    // anh phu
    private void setupImageSubsidiaryButton() {
        imageSubsidiaryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        if (clipData != null) {
                            for (int i = 0; i < clipData.getItemCount() && selectedImageUris.size() < 3; i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                if (!selectedImageUris.contains(imageUri)) { // Kiểm tra nếu uri đã tồn tại
                                    selectedImageUris.add(imageUri);

                                    // Lưu quyền truy cập lâu dài
                                    getContentResolver().takePersistableUriPermission(imageUri,
                                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                }
                            }
                        } else {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null && selectedImageUris.size() < 3 && !selectedImageUris.contains(imageUri)) {
                                selectedImageUris.add(imageUri);

                                // Lưu quyền truy cập lâu dài
                                getContentResolver().takePersistableUriPermission(imageUri,
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            }
                        }
                        updateImageViews(); // Cập nhật ảnh hiển thị sau khi chọn
                    } else {
                        // Xử lý trường hợp không có ảnh nào được chọn
                        Toast.makeText(this, "Bạn chưa chọn ảnh nào!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Cài đặt sự kiện cho nút chọn ảnh phụ
        imageSubsidiaryButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageSubsidiaryLauncher.launch(Intent.createChooser(intent, "Chọn tối đa 3 ảnh"));
        });
    }


    // kiem tra inputs
    private boolean validateInputs() {
//        db = new MainData(AdminEditItemActivity.this,"mainData.sqlite",null,1);
//        foodDatabase = new FoodDatabase(db);
//        foodList = new ArrayList<>();
//        foodList = foodDatabase.selectFood();
//        for (Food x: foodList) {
//            if(nameEditText.getText().toString().trim().equals(x.getNameFood())){
//                nameEditText.setError("Tên món ăn đã tồn tại!");
//                return false;
//            }
//        }

        // Kiểm tra tên món ăn
        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Tên món ăn không được để trống");
            nameEditText.requestFocus();
            return false;
        }

        // Kiểm tra danh mục
        if (categorySpinner.getSelectedItemPosition() == 0) { // Giả sử phần tử đầu tiên là chuỗi rỗng
            Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
            categorySpinner.requestFocus();
            return false;
        }

        // Kiểm tra mô tả món ăn
        if (descriptionEditText.getText().toString().trim().isEmpty()) {
            descriptionEditText.setError("Mô tả món ăn không được để trống");
            descriptionEditText.requestFocus();
            return false;
        }

        // Kiểm tra giá món ăn
        if (priceEditText.getText().toString().trim().isEmpty()) {
            priceEditText.setError("Giá món ăn không được để trống");
            priceEditText.requestFocus();
            return false;
        }

        // Kiểm tra khuyến mại
//        if (discountEditText.getText().toString().trim().isEmpty()) {
//            discountEditText.setError("Khuyến mại không được để trống");
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

        // Kiểm tra hình ảnh món ăn
        if (editImageViewMain.getDrawable() == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh chính cho món ăn", Toast.LENGTH_SHORT).show();
            editImageViewMain.requestFocus();
            return false;
        }

        return true; // Nếu tất cả đều hợp lệ
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

        popupWindow.showAtLocation(findViewById(R.id.admin_edit_item), Gravity.CENTER, 0, 0);

        // Tham chiếu đến các view bên trong popup
        EditText categoryEditText = popupView.findViewById(R.id.new_category_name);
        Button saveCategoryButton = popupView.findViewById(R.id.add_category_button);
        RecyclerView recyclerView = popupView.findViewById(R.id.recyclerView_category);

        // Sử dụng LinearLayoutManager để hiển thị theo chiều dọc
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminEditItemActivity.this));
        Collections.sort(categorylist, new Comparator<Category>() {
            @Override
            public int compare(Category p1, Category p2) {
                return p1.getNameCategory().compareTo(p2.getNameCategory());  // Sắp xếp tăng dần theo tên
            }
        });
        // Khởi tạo adapter và gán cho RecyclerView
        categoryAdapter = new CategoryAdapter( R.layout.activity_admin_item_danh_muc,categorylist,AdminEditItemActivity.this);
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
                    Toast.makeText(AdminEditItemActivity.this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminEditItemActivity.this, "Danh mục: " + newCategory + " đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
                setupCategorySpinner();
                recyclerView.setAdapter(categoryAdapter);
                //popupWindow.dismiss();
            } else {
                Toast.makeText(AdminEditItemActivity.this, "Tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Cập nhật các ảnh trong GridLayout và thêm chức năng xoá ảnh
    private void updateImageViews() {
        gridLayout.removeAllViews(); // Xóa tất cả các view hiện có
        for (Uri uri : selectedImageUris) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(200, 180)));
//            imageView.setImageURI(uri); // Thiết lập hình ảnh cho ImageView
            Glide.with(AdminEditItemActivity.this)
                    .load(uri)  // Uri của ảnh
                    .into(imageView);
            imageView.setOnClickListener(v -> {
                selectedImageUris.remove(uri);
                updateImageViews(); // Cập nhật lại danh sách ảnh
            });
            gridLayout.addView(imageView); // Thêm ImageView vào GridLayout
        }
    }

}
