package baby.com.project2.activity;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import baby.com.project2.R;

public class AddChildActivity extends AppCompatActivity {

    private ImageButton ImageBtnAddProfileAddChild;
    private ImageView CloseImgbtnAddChild,ImageAlertNameAddChild,ImageAlertBirthdayAaddChild,ImageViewCalendarAddChild;
    private TextView TextViewAddChildBirthday;
    private EditText EditTextAddChildName,EditTextAddChildWeight,EditTextAddChildHeight;
    private Spinner SpinnerBloodTypeAddChild;
    private RadioGroup Gender;
    private RadioButton RdGenderBoy,RdGenderGirl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        initInstances();
    }

    private void initInstances() {

        toolbar                     = (Toolbar)findViewById(R.id.toolbar);
        ImageBtnAddProfileAddChild  = (ImageButton)findViewById(R.id.imagebtn_addprofile_addchild);
        CloseImgbtnAddChild         = (ImageView)findViewById(R.id.close_imgbtn_addchild);
        ImageAlertNameAddChild      = (ImageView)findViewById(R.id.image_alert_name_addchild);
        ImageAlertBirthdayAaddChild = (ImageView)findViewById(R.id.image_alert_birthday_addchild);
        ImageViewCalendarAddChild   = (ImageView)findViewById(R.id.imageview_calendar_add_child);
        TextViewAddChildBirthday    = (TextView)findViewById(R.id.textview_add_child_birthday);
        EditTextAddChildName        = (EditText)findViewById(R.id.edittext_add_child_name);
        EditTextAddChildHeight      = (EditText)findViewById(R.id.edittext_add_child_height);
        EditTextAddChildWeight      = (EditText)findViewById(R.id.edittext_add_child_weight);
        SpinnerBloodTypeAddChild    = (Spinner)findViewById(R.id.spinner_bloodtype_add_child);
        Gender                      = (RadioGroup)findViewById(R.id.gender_addchild);
        RdGenderBoy                 = (RadioButton)findViewById(R.id.rd_gender_boy_addchild);
        RdGenderGirl                = (RadioButton)findViewById(R.id.rd_gender_girl_addchild);


        CloseImgbtnAddChild.setVisibility(View.INVISIBLE);
        ImageAlertNameAddChild.setVisibility(View.INVISIBLE);
        ImageAlertBirthdayAaddChild.setVisibility(View.INVISIBLE);

        setToolBar();

    }

    private void setToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
