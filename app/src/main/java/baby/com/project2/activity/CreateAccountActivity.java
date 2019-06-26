package baby.com.project2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.CheckBox;
import android.widget.EditText;

import baby.com.project2.R;

public class CreateAccountActivity extends AppCompatActivity {

    EditText EditTextName,EditTextEmail,EditTextPassword,EditTextConfirmPassword;
    CheckBox Cbrobod;
    CardView BtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initInstances();
    }

    private void initInstances() {

        EditTextName            = (EditText)findViewById(R.id.edittext_name);
        EditTextEmail           = (EditText)findViewById(R.id.edittext_email);
        EditTextPassword        = (EditText)findViewById(R.id.edittext_password);
        EditTextConfirmPassword = (EditText)findViewById(R.id.edittext_confirmpassword);
        Cbrobod                 = (CheckBox) findViewById(R.id.cbrobod);
        BtnSignup               = (CardView)findViewById(R.id.btnsignup);

    }
}
