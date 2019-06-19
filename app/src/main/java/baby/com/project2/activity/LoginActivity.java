package baby.com.project2.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import baby.com.project2.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CardView BtnLogin,BtnFace;
    TextInputEditText UserId,PassId;
    CheckBox CbRemember;

    String user="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initInstances();

    }

    private void initInstances() {

        UserId = (TextInputEditText) findViewById(R.id.userId);
        PassId = (TextInputEditText) findViewById(R.id.passId);
        BtnLogin = (CardView) findViewById(R.id.btnlogin);
        BtnFace = (CardView) findViewById(R.id.btnface);
        CbRemember = (CheckBox) findViewById(R.id.cbRemember);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == BtnLogin){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
