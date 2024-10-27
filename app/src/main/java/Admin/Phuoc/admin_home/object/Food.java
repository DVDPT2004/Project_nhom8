package Admin.Phuoc.admin_home.object;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Food implements Parcelable {
    private Uri imageMain;
    private String nameFood;
    private long price;
    private String category;
    private String status;
    private String description;
    private int discount;
    private long pricecurrent;
    private ArrayList<Uri> imageSubs;
    private int idFood;

    public Food() {
    }

    public Food(Integer idFood ,String category, String status, long price, long pricecurrent, String nameFood, Uri imageMain, String description, int discount, ArrayList<Uri> imageSubs) {
        this.category = category;
        this.status = status;
        this.price = price;
        this.nameFood = nameFood;
        this.imageMain = imageMain;
        this.description = description;
        this.discount = discount;
        this.imageSubs = imageSubs;
        this.pricecurrent = pricecurrent;
        this.idFood = idFood;
    }
    public Food(String category, String status, long price, long pricecurrent, String nameFood, Uri imageMain, String description, int discount, ArrayList<Uri> imageSubs) {
        this.category = category;
        this.status = status;
        this.price = price;
        this.nameFood = nameFood;
        this.imageMain = imageMain;
        this.description = description;
        this.discount = discount;
        this.imageSubs = imageSubs;
        this.pricecurrent = pricecurrent;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getImageMain() {
        return imageMain;
    }

    public void setImageMain(Uri imageMain) {
        this.imageMain = imageMain;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getPricecurrent() {
        pricecurrent = (long) Math.ceil(price * (100 - discount) / 100.0);
        return pricecurrent;
    }

    public void setPricecurrent(long pricecurrent) {
        this.pricecurrent = pricecurrent;
    }

    public ArrayList<Uri> getImageSubs() {
        return imageSubs;
    }

    public void setImageSubs(ArrayList<Uri> imageSubs) {
        this.imageSubs = imageSubs;
    }

    // Thực hiện Parcelable
//    Việc triển khai Parcelable cho phép đối tượng Food được truyền giữa các Activity/Fragment một cách hiệu quả.
    protected Food(Parcel in) {
        idFood = in.readInt();

        imageMain = in.readParcelable(Uri.class.getClassLoader());
        nameFood = in.readString();
        price = in.readLong();
        category = in.readString();
        status = in.readString();
        description = in.readString();
        discount = in.readInt();
        pricecurrent = in.readLong();
        if (in.readInt() == 1) {
            imageSubs = in.createTypedArrayList(Uri.CREATOR);
        } else {
            imageSubs = new ArrayList<>();
        }
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idFood);

        dest.writeParcelable(imageMain, flags);
        dest.writeString(nameFood);
        dest.writeLong(price);
        dest.writeString(category);
        dest.writeString(status);
        dest.writeString(description);
        dest.writeInt(discount);
        dest.writeLong(pricecurrent);
        if (imageSubs == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
            dest.writeTypedList(imageSubs);
        }
    }
}
