package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.EditMilkActivity;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.vaccine.InsertVaccineManager;
import baby.com.project2.manager.singleton.vaccine.VaccineManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.VaccineModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccineListItemsAdapter extends RecyclerView.Adapter<VaccineListItemsAdapter.CustomViewVaccineList>{

    private Context context;
    private ArrayList<VaccineModelClass> items;
    private int statuss = 0;
    private String V_id;
    private String datetoday;
    private String place;


    public VaccineListItemsAdapter(Context context, ArrayList<VaccineModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewVaccineList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewVaccineList(LayoutInflater.from(context).inflate(R.layout.customview_list_vaccine, viewGroup, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CustomViewVaccineList customViewVaccineList, final int i) {

        int size = 0;
        datetoday = customViewVaccineList.dateDto.getDateString();
        DecimalFormat formatter = new DecimalFormat("00");
        V_id = items.get(i).getV_id();


        try {
            size = customViewVaccineList.dataVaccineDto.getDatavaccine().size();
        }catch (Exception e){
            size = 0;
        }

        customViewVaccineList.StatusVac.setVisibility(View.INVISIBLE);
        customViewVaccineList.Vaccine.setText(items.get(i).getVaccine());
        customViewVaccineList.Type.setText(items.get(i).getType());
        customViewVaccineList.TextViewDateVac.setText(datetoday);


        for(int j=0;j<size;j++){

            String id = formatter.format(customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getV_id());
            int ck = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_status();

            place = "";

            try {
                place = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_plase();
            }catch (Exception e){
                place = "";
            }

            String date = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_date();

            if(id.equals(V_id)){

                customViewVaccineList.TextViewDateVac.setText(date);

                switch (ck){
                    case 0:
                        customViewVaccineList.StatusVac.setVisibility(View.VISIBLE);
                        customViewVaccineList.StatusVac.setCardBackgroundColor(Color.parseColor("#FF0000"));
                        customViewVaccineList.NoVac.setChecked(true);
                        break;
                    case 1:
                        customViewVaccineList.StatusVac.setVisibility(View.VISIBLE);
                        customViewVaccineList.StatusVac.setCardBackgroundColor(Color.parseColor("#1CC50B"));
                        customViewVaccineList.YesVac.setChecked(true);
                        break;
                }

                customViewVaccineList.EditTextPlace.setText(place);
            }
        }

        customViewVaccineList.Mycontent.collapse();
        customViewVaccineList.CardViewVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewVaccineList.Mycontent.toggle();
            }
        });

        customViewVaccineList.BtnSaveVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                V_id = items.get(i).getV_id();
                switch (customViewVaccineList.CheckVac.getCheckedRadioButtonId()){
                    case R.id.no_vac:
                        statuss = 0;
                        break;
                    case R.id.yes_vac:
                        statuss = 1;
                        break;
                }
                customViewVaccineList.reqinsert(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild(),V_id,datetoday,statuss,place);
            }
        });
    }

    @Override
    public int getItemCount() {
        SelectVaccineDto vaccineDto = VaccineManager.getInstance().getItemsDto();
        return vaccineDto.getVaccine().size();
    }

    public class CustomViewVaccineList extends RecyclerView.ViewHolder {

        TextView Vaccine,Type,TextViewDateVac;
        CardView CardViewVaccine,StatusVac;
        ExpandableRelativeLayout Mycontent;
        RadioGroup CheckVac;
        RadioButton YesVac,NoVac;
        EditText EditTextPlace;
        Button BtnSaveVaccine;

        SelectDataVaccineDto dataVaccineDto;
        DateDto dateDto;

        public CustomViewVaccineList(@NonNull View itemView) {
            super(itemView);

            Vaccine             = (TextView)itemView.findViewById(R.id.vaccine);
            Type                = (TextView)itemView.findViewById(R.id.type);
            TextViewDateVac     = (TextView)itemView.findViewById(R.id.textview_date_vac);
            CardViewVaccine     = (CardView)itemView.findViewById(R.id.cardview_vaccine);
            StatusVac           = (CardView) itemView.findViewById(R.id.status_vac);
            CheckVac            = (RadioGroup)itemView.findViewById(R.id.check_vac);
            YesVac              = (RadioButton)itemView.findViewById(R.id.yes_vac);
            NoVac               = (RadioButton)itemView.findViewById(R.id.no_vac);
            Mycontent           = (ExpandableRelativeLayout) itemView.findViewById(R.id.mycontent);
            EditTextPlace       = (EditText) itemView.findViewById(R.id.edittext_place);
            BtnSaveVaccine      = (Button) itemView.findViewById(R.id.btn_savevaccine);

            dateDto = DateManager.getInstance().getDateDto();
            dataVaccineDto = DataVaccineManager.getInstance().getItemsDto();
        }

        public void reqinsert(String cid,String vid,String date,int status,String place) {

            final Context mcontext = Contextor.getInstance().getmContext();
            String reqBody = "{\"C_id\": \""+cid+"\",\"V_id\":\""+vid+"\",\"FKcv_date\":\""+date+"\",\"FKcv_status\":"+status+","+
                    "\"FKcv_plase\":\""+place+"\"}";
            final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
            Call<InsertVaccineDto> call = HttpManager.getInstance().getService().loadAPIVaccineinsert(requestBody);
            call.enqueue(new Callback<InsertVaccineDto>() {

                @Override
                public void onResponse(Call<InsertVaccineDto> call, Response<InsertVaccineDto> response) {
                    if(response.isSuccessful()){
                        InsertVaccineDto dto = response.body();
                        InsertVaccineManager.getInstance().setItemsDto(dto);
                        if(response.body().getSuccess()){

                            ShowAlertDialog(response.body().getSuccess());
                        }
                        else{
                            ShowAlertDialog(response.body().getSuccess());
                        //Toast.makeText(mcontext,dto.getSuccess(),Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<InsertVaccineDto> call, Throwable t) {
                    Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }

        private void ShowAlertDialog(boolean success) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Contextor.getInstance().getmContext());

            if(success){
                builder.setTitle("เพิ่มข้อมูลวัคซีน");
                builder.setMessage("เพิ่มข้อมูลวัคซีนเรียบร้อย");
                builder.setIcon(R.mipmap.ic_success);
                builder.setCancelable(true);
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }else {
                builder.setTitle("ล้มเหลว");
                builder.setMessage("เกิดข้อผิดพลาด เพิ่มข้อมูลไม่สำเร็จ");
                builder.setIcon(R.mipmap.ic_failed);
                builder.setCancelable(true);
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
