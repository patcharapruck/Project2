package baby.com.project2.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import baby.com.project2.R;
import baby.com.project2.fragment.ListVaccineFragment;


public class VaccineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    FragmentTransaction ListVac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        initInstances();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setFragment();
    }

    private void setFragment() {
        ListVac = getSupportFragmentManager().beginTransaction();
        ListVac.replace(R.id.fragment_listvac, new ListVaccineFragment());
        ListVac.commit();
    }

    private void initInstances(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
