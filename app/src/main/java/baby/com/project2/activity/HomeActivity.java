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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import baby.com.project2.fragment.ListintroFragment;
import baby.com.project2.fragment.MenuFragment;
import baby.com.project2.fragment.ReportFragment;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.KidModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private ImageView ImageViewKidsAdd;

    ArrayList<KidModelClass> items;
    KidListItemsAdapter adapter;
    private RecyclerView recyclerView;

    String DateAge;
    SelectChildDto dto;

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
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        setShowDataChild();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initInstances() {

        toolbar              = (Toolbar)findViewById(R.id.toolbar);
        ImageViewKidsAdd     = (ImageView)findViewById(R.id.image_view_kids_add);
        recyclerView         = (RecyclerView)findViewById(R.id.recycler_view_kids);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        ImageViewKidsAdd.setOnClickListener(this);

        setToolBar();
    }

    private void setShowDataChild() {
        DecimalFormat formatter = new DecimalFormat("00");
        String uid = SharedPrefUser.getInstance(HomeActivity.this).getUid();
        reqselectchild(uid);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        fragment = new HomeFragment();
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

    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new KidListItemsAdapter(HomeActivity.this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getResult().size();


        for (int i = 0; i < size; i++) {

            String name = dto.getResult().get(i).getC_name();
            String birthday = dto.getResult().get(i).getC_birthday();
            setDate(birthday);

            try {
                items.add(new KidModelClass(dto.getResult().get(i).getC_id(),"kkk",name,DateAge,dto.getResult().get(i).getC_gender()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == ImageViewKidsAdd){
            Intent intent = new Intent(HomeActivity.this, AddChildActivity.class);
            startActivity(intent);
        }
    }

    public void reqselectchild(String uid) {

        final Context mcontext = HomeActivity.this;
        String reqBody = "{\"id\":\""+uid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectChildDto> call = HttpManager.getInstance().getService().loadAPISelect(requestBody);
        call.enqueue(new Callback<SelectChildDto>() {

            @Override
            public void onResponse(Call<SelectChildDto> call, Response<SelectChildDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    SelectChildManager.getInstance().setItemsDto(dto);

                    SharedPrefUser.getInstance(Contextor.getInstance().getmContext())
                            .saveChidId(dto.getResult().get(0).getC_id(),dto.getResult().get(0).getC_gender(),dto.getResult().get(0).getC_birthday());

                    setNavigation();
                    setRecyclerView();

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

    private void setDate(String dateAge) {

        int DayBrith,MonthBrith,YearBrith,DayTo,MonthTo,YearTo,Day,Month,Year,Sum;

        Date datetoday = new Date();
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(datetoday);

        DayTo = calendartoday.get(Calendar.DAY_OF_MONTH);
        MonthTo = calendartoday.get(Calendar.MONTH)+1;
        YearTo = calendartoday.get(Calendar.YEAR);

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = sdf.parse(dateAge);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DayBrith = calendar.get(Calendar.DAY_OF_MONTH);
        MonthBrith = calendar.get(Calendar.MONTH)+1;
        YearBrith = calendar.get(Calendar.YEAR);

        if(DayTo>=DayBrith){
            Day = DayTo-DayBrith;
            if(MonthTo>=MonthBrith){
                Month = MonthTo-MonthBrith;
                Year = YearTo-YearBrith;
                Sum = Month+Year*12;
            }else {
                Month = (MonthTo+12)-MonthBrith;
                Year = ((YearTo-1)-YearBrith);
                Sum = Month+Year*12;
            }
        }else {
            Day = (DayTo+30)-DayBrith;
            Month = MonthTo-1;
            if(Month>=MonthBrith){
                Month = Month-MonthBrith;
                Year = (YearTo-YearBrith);
                Sum = Month+Year*12;
            }else {
                Month = (Month+12)-MonthBrith;
                Year = ((YearTo-1)-YearBrith);
                Sum = Month+Year*12;
            }
        }

        if(Sum>24){
            Sum = 24;
        }

        DateAge = Sum+" เดือน "+Day+" วัน";

    }
}
