package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.EditMilkActivity;
import baby.com.project2.activity.InsertDevActivity;
import baby.com.project2.activity.InsertVacActivity;
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

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        final SelectDataVaccineDto dataVaccineDto = DataVaccineManager.getInstance().getItemsDto();
        final String V_id;
        String datetoday = dateFormat.format(calendar.getTime());;
        String place = "";
        String fkcv_id="";
        int update = 0;
        int size = 0;
        DecimalFormat formatter = new DecimalFormat("00");
        V_id = items.get(i).getV_id();

        try {
            size = dataVaccineDto.getDatavaccine().size();
        }catch (Exception e){
            size = 0;
        }

        customViewVaccineList.StatusVac.setVisibility(View.INVISIBLE);
        customViewVaccineList.Vaccine.setText(items.get(i).getVaccine());
        customViewVaccineList.Type.setText(items.get(i).getType());


        for(int j=0;j<size;j++){

            String id = formatter.format(dataVaccineDto.getDatavaccine().get(j).getV_id());

            if(id.equals(V_id)){
                place = dataVaccineDto.getDatavaccine().get(j).getFKcv_plase();
                datetoday = dataVaccineDto.getDatavaccine().get(j).getFKcv_date();
                fkcv_id = formatter.format(dataVaccineDto.getDatavaccine().get(j).getFKcv_id());
                customViewVaccineList.StatusVac.setVisibility(View.VISIBLE);

                if(dataVaccineDto.getDatavaccine().get(j).getFKcv_status()==1){
                    customViewVaccineList.StatusVac.setImageResource(R.mipmap.ic_success);
                }else {
                    customViewVaccineList.StatusVac.setImageResource(R.mipmap.ic_failed);
                }
            }
        }
        final String finalFkcv_id = fkcv_id;
        final String finalDatetoday = datetoday;
        final String finalPlace = place;
        final int finalUpdate = update;
        customViewVaccineList.CardViewVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsertVacActivity.class);
                intent.putExtra("id",finalFkcv_id);
                intent.putExtra("vid",items.get(i).getV_id());
                intent.putExtra("type",items.get(i).getType());
                intent.putExtra("name",items.get(i).getVaccine());
                intent.putExtra("date",finalDatetoday);
                intent.putExtra("place",finalPlace);
                intent.putExtra("update",finalUpdate);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        SelectVaccineDto vaccineDto = VaccineManager.getInstance().getItemsDto();
        return vaccineDto.getVaccine().size();
    }

    public class CustomViewVaccineList extends RecyclerView.ViewHolder {

        TextView Vaccine, Type;
        CardView CardViewVaccine;
        ImageView StatusVac;
        ;

        public CustomViewVaccineList(@NonNull View itemView) {
            super(itemView);

            Vaccine = (TextView) itemView.findViewById(R.id.vaccine);
            Type = (TextView) itemView.findViewById(R.id.type);
            CardViewVaccine = (CardView) itemView.findViewById(R.id.cardview_vaccine);
            StatusVac = (ImageView) itemView.findViewById(R.id.status_vac);

        }

    }
}
