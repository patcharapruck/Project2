package baby.com.project2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.circularreveal.cardview.CircularRevealCardView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.dto.vaccine.SelectListVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
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

    @Override
    public void onBindViewHolder(@NonNull final CustomViewVaccineList customViewVaccineList, int i) {

        customViewVaccineList.Vaccine.setText(items.get(i).getVaccine());
        customViewVaccineList.Type.setText(items.get(i).getType());

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
        CardView CardViewVaccine;
        CircularRevealCardView StatusVac;
        ExpandableRelativeLayout Mycontent;
        RadioGroup CheckVac;
        RadioButton YesVac,NoVac;


        public CustomViewVaccineList(@NonNull View itemView) {
            super(itemView);

            Vaccine             = (TextView)itemView.findViewById(R.id.vaccine);
            Type                = (TextView)itemView.findViewById(R.id.type);
            TextViewDateVac     = (TextView)itemView.findViewById(R.id.textview_date_vac);
            CardViewVaccine     = (CardView)itemView.findViewById(R.id.cardview_vaccine);
            StatusVac           = (CircularRevealCardView)itemView.findViewById(R.id.status_vac);
            CheckVac            = (RadioGroup)itemView.findViewById(R.id.check_vac);
            YesVac              = (RadioButton)itemView.findViewById(R.id.yes_vac);
            NoVac               = (RadioButton)itemView.findViewById(R.id.no_vac);
            Mycontent           = (ExpandableRelativeLayout) itemView.findViewById(R.id.mycontent);


        }
    }
}
