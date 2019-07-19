package baby.com.project2.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.AddChildActivity;
import baby.com.project2.activity.EditChildActivity;
import baby.com.project2.activity.HomeActivity;
import baby.com.project2.adapter.KidListItemsAdapter;
import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.util.SharedPrefDayMonthYear;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.KidModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMainFragment extends Fragment implements View.OnClickListener {

    private ImageView ImageViewKidsAdd;

    ArrayList<KidModelClass> items;
    KidListItemsAdapter adapter;
    private RecyclerView recyclerView;

    FragmentTransaction f1;

    String DateAge;
    SelectChildDto dto = SelectChildManager.getInstance().getItemsDto();

    public HomeMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setShowDataChild();
        setFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setShowDataChild() {
        DecimalFormat formatter = new DecimalFormat("00");
        String uid = SharedPrefUser.getInstance(getContext()).getUid();
        reqselectchild(uid);
    }

    private void setFragment() {
        f1 = getChildFragmentManager().beginTransaction();
        f1.replace(R.id.fragment1, new HomeFragment());
        f1.commit();
    }

    private void initInstances(View rootView) {

        ImageViewKidsAdd     = (ImageView)rootView.findViewById(R.id.image_view_kids_add);
        recyclerView         = (RecyclerView)rootView.findViewById(R.id.recycler_view_kids);
        ImageViewKidsAdd.setOnClickListener(this);
    }


    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new KidListItemsAdapter(getContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getResult().size();

        for (int i = 0; i < size; i++) {

            String name = dto.getResult().get(i).getC_name();
            String birthday = dto.getResult().get(i).getC_birthday();
            String ima=dto.getResult().get(i).getC_image();

            if(ima == null){
                ima = "";
            }

            setDate(birthday);

            try {
                items.add(new KidModelClass(dto.getResult().get(i).getC_id(),ima,name,DateAge,dto.getResult().get(i).getC_gender()
                        ,dto.getResult().get(i).getC_birthday()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
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
        SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).saveChidIdDate(Sum);

        Year = Sum/12;
        Month = Sum%12;

        String dmy = "";
        if(Year>0){
            dmy = dmy + Year +" ปี ";
        }
        if(Month>0){
            dmy = dmy + Month + " เดือน ";
        }
        if(Day>0){
            dmy = dmy + Day + " วัน";
        }

        DateAge = dmy;

        SharedPrefDayMonthYear.getInstance(Contextor.getInstance().getmContext()).saveage(Day,Month,Year,dmy);

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

    @Override
    public void onClick(View v) {
        if(v == ImageViewKidsAdd){
            Intent intent = new Intent(getContext(), AddChildActivity.class);
            getContext().startActivity(intent);
        }
    }
}
