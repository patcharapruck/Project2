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

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.InsertVacActivity;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.manager.singleton.vaccine.VaccineManager;
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

        final DecimalFormat formatter = new DecimalFormat("00");

        customViewReportVaccineList.TextViewNameVaccine.setText(String.valueOf(items.get(i).getNams()));
//        customViewReportVaccineList.TextViewDate.setText(String.valueOf(items.get(i).getDate()));
//        customViewReportVaccineList.AgeVaccine.setText(items.get(i).getAge());
        customViewReportVaccineList.StatusVaccine.setText(items.get(i).getStatus());

        customViewReportVaccineList.CardViewVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsertVacActivity.class);
                intent.putExtra("id",formatter.format(items.get(i).getFk_id()));
                intent.putExtra("vid",formatter.format(items.get(i).getV_id()));
                intent.putExtra("type",items.get(i).getType());
                intent.putExtra("name",items.get(i).getNams());
                intent.putExtra("date",items.get(i).getDate());
                intent.putExtra("place",items.get(i).getPlace());
                intent.putExtra("update",1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataVaccineManager.getInstance().getItemsDto().getDatavaccine().size();
    }

    public class CustomViewReportVaccineList extends RecyclerView.ViewHolder {

        TextView TextViewNameVaccine,AgeVaccine,StatusVaccine,TextViewDate;
        CardView CardViewVac;
        //CardView CardViewListGrow;

        public CustomViewReportVaccineList(@NonNull View itemView) {
            super(itemView);

           TextViewNameVaccine = (TextView)itemView.findViewById(R.id.textview_name_vaccine);
//           AgeVaccine = (TextView)itemView.findViewById(R.id.age_vaccine);
           StatusVaccine = (TextView)itemView.findViewById(R.id.status_vaccine);
//           TextViewDate = (TextView)itemView.findViewById(R.id.textview_date);
//           DeleteVaccine = (ImageView)itemView.findViewById(R.id.delete_vaccine);
            CardViewVac = (CardView)itemView.findViewById(R.id.cardview_vac);

        }
    }
}
