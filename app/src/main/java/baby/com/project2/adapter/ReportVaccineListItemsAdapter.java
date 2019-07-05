package baby.com.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.EditGrowActivity;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.manager.singleton.DataVaccineManager;
import baby.com.project2.manager.singleton.SelectGrowManager;
import baby.com.project2.view.ReportGrowModelClass;
import baby.com.project2.view.ReportVaccineModelClass;

public class ReportVaccineListItemsAdapter extends RecyclerView.Adapter<ReportVaccineListItemsAdapter.CustomViewReportVaccineList>{

    private Context context;
    private ArrayList<ReportVaccineModelClass> items;

    public ReportVaccineListItemsAdapter(Context context, ArrayList<ReportVaccineModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewReportVaccineList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewReportVaccineList(LayoutInflater.from(context).inflate(R.layout.customview_list_report_vaccine, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewReportVaccineList customViewReportVaccineList, final int i) {
        customViewReportVaccineList.TextViewNameVaccine.setText(String.valueOf(items.get(i).getNams()));
        customViewReportVaccineList.TextViewDate.setText(String.valueOf(items.get(i).getDate()));
        customViewReportVaccineList.AgeVaccine.setText(items.get(i).getAge());
        customViewReportVaccineList.StatusVaccine.setText(items.get(i).getStatus());

    }

    @Override
    public int getItemCount() {
        return DataVaccineManager.getInstance().getItemsDto().getDatavaccine().size();
    }

    public class CustomViewReportVaccineList extends RecyclerView.ViewHolder {

        TextView TextViewNameVaccine,AgeVaccine,StatusVaccine,TextViewDate;
        ImageView DeleteVaccine;
        //CardView CardViewListGrow;

        public CustomViewReportVaccineList(@NonNull View itemView) {
            super(itemView);

           TextViewNameVaccine = (TextView)itemView.findViewById(R.id.textview_name_vaccine);
           AgeVaccine = (TextView)itemView.findViewById(R.id.age_vaccine);
           StatusVaccine = (TextView)itemView.findViewById(R.id.status_vaccine);
           TextViewDate = (TextView)itemView.findViewById(R.id.textview_date);
           DeleteVaccine = (ImageView)itemView.findViewById(R.id.delete_vaccine);

        }
    }
}
