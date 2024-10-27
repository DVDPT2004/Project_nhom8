package Admin.Phuoc.admin_feedback.activity_feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project_nhom8.R;

import Admin.Phuoc.admin_feedback.object.Feedback;
import Admin.Phuoc.admin_feedback.object_database.FeedbackDatabase;
import Database.MainData.MainData;

public class AdminFeedbackDetailActivity extends AppCompatActivity {
    private TextView nameUserFeedback,timeFeedback,contentUserFeedback;
    private ImageView media1,media2,media3,buttonBack;
    private EditText contentAdminFeedback;
    private Button buttonResponse;
    private MainData db;
    private FeedbackDatabase feedbackDatabase;
    private int intentIdFeedback;
    private String intentContentUserFeedback;
    private Uri intentMedia1,intentMedia2,intentMedia3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chi_tiet_phan_hoi);

        initializeViews();

        getAndSetViews();

        setupbuttonBack();

        setupButtonResponse();

    }

    private void initializeViews() {
        nameUserFeedback = findViewById(R.id.admin_feedback_detail_nameUser);
        timeFeedback = findViewById(R.id.admin_feedback_detail_timeFeedback);
        contentUserFeedback = findViewById(R.id.admin_feedback_detail_contentUserFeedback);
        media1 = findViewById(R.id.admin_feedback_detail_media1);
        media2 = findViewById(R.id.admin_feedback_detail_media2);
        media3 = findViewById(R.id.admin_feedback_detail_media3);
        contentAdminFeedback = findViewById(R.id.admin_feedback_detail_contentAdminFeedback);
        buttonResponse = findViewById(R.id.admin_feedback_detail_buttonResponse);
        buttonBack = findViewById(R.id.admin_feedback_detail_back);

        contentAdminFeedback.requestFocus();

    }

    private void getAndSetViews(){
        //getViews
        Intent intentFeedback = getIntent();
        intentIdFeedback = intentFeedback.getIntExtra("feedback_idFeedback",0);
        String intentNameUserFeedback = intentFeedback.getStringExtra("feedback_nameUserFeedback");
        String intentTimeFeedback = intentFeedback.getStringExtra("feedback_timeFeedback");
        intentContentUserFeedback = intentFeedback.getStringExtra("feedback_contentUserFeedback");
        intentMedia1 = intentFeedback.getStringExtra("feedback_media1") != null ? Uri.parse(intentFeedback.getStringExtra("feedback_media1")) : null;
        intentMedia2 = intentFeedback.getStringExtra("feedback_media2") != null ? Uri.parse(intentFeedback.getStringExtra("feedback_media2")) : null;
        intentMedia3 = intentFeedback.getStringExtra("feedback_media3") != null ? Uri.parse(intentFeedback.getStringExtra("feedback_media3")) : null;
        int intentStatusFeedback = intentFeedback.getIntExtra("feedback_statusFeedback",0);
        String intentContentAdminFeedback = intentFeedback.getStringExtra("feedback_contentAdminFeedback");

        //setViews
        nameUserFeedback.setText(intentNameUserFeedback);
        timeFeedback.setText("Thời gian phản hồi: " + intentTimeFeedback);
        contentUserFeedback.setText(intentContentUserFeedback);
        if (intentMedia1 != null) {
            Glide.with(AdminFeedbackDetailActivity.this)
                    .load(intentMedia1)
                    .into(media1);
        }
        if (intentMedia2 != null) {
            Glide.with(AdminFeedbackDetailActivity.this)
                    .load(intentMedia2)
                    .into(media2);
        }
        if (intentMedia3 != null) {
            Glide.with(AdminFeedbackDetailActivity.this)
                    .load(intentMedia3)
                    .into(media3);
        }
    }

    private void setupbuttonBack(){
        buttonBack.setOnClickListener(v -> finish());
    }

    private void setupButtonResponse(){
        buttonResponse.setOnClickListener(v->{
            if(!contentAdminFeedback.getText().toString().trim().isEmpty()){
                db = new MainData(this,"mainData.sqlite",null,1);
                feedbackDatabase = new FeedbackDatabase(db);
                Feedback updateResponse = new Feedback(contentAdminFeedback.getText().toString().trim(),1,"DATETIME(CURRENT_TIMESTAMP, '+7 hours')",nameUserFeedback.getText().toString().trim(),intentIdFeedback,intentContentUserFeedback,null,null,null);
                if(feedbackDatabase.updateResponse(intentIdFeedback,updateResponse)){
                    Toast.makeText(AdminFeedbackDetailActivity.this, "Phản hồi thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminFeedbackDetailActivity.this, "Phản hồi thất bại!", Toast.LENGTH_SHORT).show();
                    contentAdminFeedback.requestFocus();
                }
            }
        });
    }
}
