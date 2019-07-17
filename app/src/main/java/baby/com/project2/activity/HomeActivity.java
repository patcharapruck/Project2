package baby.com.project2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import baby.com.project2.R;
import baby.com.project2.adapter.KidListItemsAdapter;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.fragment.HomeFragment;
import baby.com.project2.fragment.HomeMainFragment;
import baby.com.project2.fragment.ListintroFragment;
import baby.com.project2.fragment.MenuFragment;
import baby.com.project2.fragment.ReportFragment;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.util.SharedPrefUserFace;
import baby.com.project2.view.KidModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    SelectChildDto dto;
    Fragment fragment = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_logout) {
            SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).logout();
            SharedPrefUserFace.getInstance(Contextor.getInstance().getmContext()).logoutface();


            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            this.startActivity(intent);
            return true;
        }
        if (id == R.id.item_setting) {
            Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
        setShowDataChild();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFragment(fragment);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setShowDataChild() {
        DecimalFormat formatter = new DecimalFormat("00");

        String uid;
        if(SharedPrefUserFace.getInstance(Contextor.getInstance().getmContext()).getLoginFace()){
            uid  = SharedPrefUserFace.getInstance(HomeActivity.this).getKeyUid();
        }else {
             uid  = SharedPrefUser.getInstance(HomeActivity.this).getUid();
        }

        reqselectchild(uid);
    }

    private void initInstances() {

        toolbar              = (Toolbar)findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        setToolBar();
    }


    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        fragment = new HomeMainFragment();
                        break;
                    case R.id.item_menu:
                        fragment = new MenuFragment();
                        break;

                    case R.id.item_report:
                        fragment = new ReportFragment();
                        break;

                    case R.id.item_intro:
                        fragment = new ListintroFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.item_home);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void reqselectchild(String uid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"id\":\""+uid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectChildDto> call = HttpManager.getInstance().getService().loadAPISelect(requestBody);
        call.enqueue(new Callback<SelectChildDto>() {

            @Override
            public void onResponse(Call<SelectChildDto> call, Response<SelectChildDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    SelectChildManager.getInstance().setItemsDto(dto);

                 try {
                     SharedPrefUser.getInstance(Contextor.getInstance().getmContext())
                             .saveChidId(dto.getResult().get(0).getC_id(),dto.getResult().get(0).getC_gender(),dto.getResult().get(0).getC_birthday());
                 }catch (Exception e){
                     Intent intent = new Intent(HomeActivity.this, AddChildActivity.class);
                     startActivity(intent);
                 }

                    setNavigation();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectChildDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String formatDateTime = dateFormat.format(calendar.getTime());

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        DateDto dateDto = new DateDto();
        dateDto.setCalendar(calendar);
        dateDto.setDateToday(date);
        dateDto.setDateString(formatDateTime);
        dateDto.setDay(day);
        dateDto.setMonth(month);
        dateDto.setYear(year);
        DateManager.getInstance().setDateDto(dateDto);
    }
}
