package baby.com.project2.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.ChildGrowActivity;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.develorment.DevelopmentManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelorHomeFragment extends Fragment implements View.OnClickListener {

    private Button ButtonAddData;
    private TextView TextViewStatusDev;

    SelectDataDevDto dtodev;
    SelectDevDto dto;

    ArrayList<String> stringArrayList;

    public DevelorHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_develor_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        ButtonAddData        = (Button)rootView.findViewById(R.id.button_add_data);
        TextViewStatusDev      = (TextView)rootView.findViewById(R.id.textview_status_dev);
        String cid = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();

        Dev();
        reqDataDevelorment(cid);
        ButtonAddData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), DevelopMentActivity.class);
        getContext().startActivity(intent);
    }


    public void reqDataDevelorment(String cid) {

        final Context mcontext = getContext();
        String reqBody = "{\"C_id\":\""+cid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDataDevDto> call = HttpManager.getInstance().getService().loadAPIDataDevDtoCall(requestBody);
        call.enqueue(new Callback<SelectDataDevDto>() {

            @Override
            public void onResponse(Call<SelectDataDevDto> call, Response<SelectDataDevDto> response) {
                if(response.isSuccessful()){
                    dtodev = response.body();
                    DataDevManager.getInstance().setItemsDto(dtodev);
                    reqDav("");

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDataDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reqDav(String id) {

        final Context mcontext = getContext();
        String reqBody = "{\"id_agedev\":\""+id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDevDto> call = HttpManager.getInstance().getService().loadAPIDevDtoCall(requestBody);
        call.enqueue(new Callback<SelectDevDto>() {

            @Override
            public void onResponse(Call<SelectDevDto> call, Response<SelectDevDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    DevelopmentManager.getInstance().setItemsDto(dto);

                    try {
                        Showdata(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint());
                    }catch (Exception e){

                    }

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Showdata(int sum) {
        int all = DevAll(sum);
        int succ = 0;

        int size = dtodev.getDatadev().size();
        for (int i = 0; i < size; i++) {
            if(dtodev.getDatadev().get(i).getFKcd_status()==1){
                succ++;
            }
        }
        TextViewStatusDev.setText(succ+" จาก "+all);
    }

    private int DevAll(int sum) {
        int size = dto.getDev().size();
        int s = 0;
        if (sum >= 19) {
            for (int i = 0; i < size; i++) {
                s++;
            }
        } else {
            int sizeType = stringArrayList.size();
            for (int i = 0; i < sizeType; i++) {
                for (int j = 0; j < size; j++) {
                    if (dto.getDev().get(j).getId_agedev().equals(stringArrayList.get(i))) {
                        s++;
                    }
                }
            }
        }

        return s;
    }

    private void Dev() {
        stringArrayList = new ArrayList<>();
        int sum = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint();

        if (sum < 2) {
            stringArrayList.add("01");
        } else if (sum < 3) {
            stringArrayList.add("01");
            stringArrayList.add("02");
        } else if (sum < 5) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
        } else if (sum < 7) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
        } else if (sum < 9) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
        } else if (sum < 10) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
        } else if (sum < 13) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
        } else if (sum < 16) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
        } else if (sum < 18) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
            stringArrayList.add("09");
        } else if (sum < 19) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
            stringArrayList.add("09");
            stringArrayList.add("10");
        }
    }
}
