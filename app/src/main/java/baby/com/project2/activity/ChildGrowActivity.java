package baby.com.project2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import baby.com.project2.R;

public class ChildGrowActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageButton ImageBtnGrow;
    private ImageView ImageDeletePhoto,ImageAlertWidth,ImageAlertHeight;
    private Button ButtonSave;
    private TextView TextViewAddChildBirthday;
    private EditText EdittextAddChildWeight,EdittextAddChildHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_grow);
        initInstances();
    }

    private void initInstances(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageBtnGrow       = (ImageButton)findViewById(R.id.imagebtn_grow);
        ImageDeletePhoto   = (ImageView)findViewById(R.id.image_delete_photo);
        ImageAlertWidth    = (ImageView)findViewById(R.id.image_alert_width);
        ImageAlertHeight   = (ImageView)findViewById(R.id.image_alert_height);

        ButtonSave         = (Button)findViewById(R.id.button_save);

        TextViewAddChildBirthday = (TextView)findViewById(R.id.textview_add_child_birthday);

        EdittextAddChildWeight = (EditText)findViewById(R.id.edittext_add_child_weight);
        EdittextAddChildHeight = (EditText)findViewById(R.id.edittext_add_child_height);

        ImageDeletePhoto.setVisibility(View.INVISIBLE);
        ImageAlertWidth.setVisibility(View.INVISIBLE);
        ImageAlertHeight.setVisibility(View.INVISIBLE);

        setDate();
    }

    private void setDate() {

        ImageBtnGrow.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v == ImageBtnGrow){
            Intent intent = new Intent();
            startActivity(intent);
        }
    }
}
