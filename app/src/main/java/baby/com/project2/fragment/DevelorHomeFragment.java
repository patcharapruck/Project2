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

import baby.com.project2.R;
import baby.com.project2.activity.ChildGrowActivity;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
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
                    SelectDataDevDto dtodev = response.body();
                    DataDevManager.getInstance().setItemsDto(dtodev);

                    TextViewStatusDev.setText(String.valueOf(dtodev.getDatadev().size()));

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
}
