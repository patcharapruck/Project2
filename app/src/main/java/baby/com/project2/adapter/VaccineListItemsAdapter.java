package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.circularreveal.cardview.CircularRevealCardView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectListVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.singleton.DataVaccineManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.VaccineManager;
import baby.com.project2.view.DevelopMentModelClass;
import baby.com.project2.view.VaccineModelClass;

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
    public void onBindViewHolder(@NonNull final CustomViewVaccineList customViewVaccineList, int i) {

        int size = 0;
        String datetoday = customViewVaccineList.dateDto.getDateString();
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
            DecimalFormat formatter = new DecimalFormat("00");

            String id = formatter.format(customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getV_id());
            int ck = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_status();

            String place = "";

            try {
                place = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_plase();
            }catch (Exception e){
                place = "";
            }

            String date = customViewVaccineList.dataVaccineDto.getDatavaccine().get(j).getFKcv_date();

            if(id.equals(items.get(i).getV_id())){

                customViewVaccineList.TextViewDateVac.setText(date);

                switch (ck){
                    case 0:
                        customViewVaccineList.StatusVac.setVisibility(View.VISIBLE);
                        customViewVaccineList.StatusVac.setCardBackgroundColor(R.color.very_low);
                        customViewVaccineList.NoVac.setChecked(true);
                        break;
                    case 1:
                        customViewVaccineList.StatusVac.setVisibility(View.VISIBLE);
                        customViewVaccineList.StatusVac.setCardBackgroundColor(R.color.colorMain);
                        customViewVaccineList.YesVac.setChecked(true);;
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

            dateDto = DateManager.getInstance().getDateDto();
            dataVaccineDto = DataVaccineManager.getInstance().getItemsDto();
        }
    }
}
