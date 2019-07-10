package baby.com.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.EditGrowActivity;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.view.ReportDevModelClass;
import baby.com.project2.view.ReportGrowModelClass;

public class ReportDevListItemsAdapter extends RecyclerView.Adapter<ReportDevListItemsAdapter.CustomViewReportDevList>{

    private Context context;
    private ArrayList<ReportDevModelClass> items;

    public ReportDevListItemsAdapter(Context context, ArrayList<ReportDevModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewReportDevList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewReportDevList(LayoutInflater.from(context).inflate(R.layout.customview_list_report_dev, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewReportDevList customViewReportDevList, final int i) {

        String s;

        if(items.get(i).getStatus() == 1){
            s = "ทำได้";
        }else {
            s = "ทำไม่ได้";
        }

        customViewReportDevList.TextViewDate.setText(items.get(i).getDate());
        customViewReportDevList.TextViewNameDev.setText(items.get(i).getName());
        customViewReportDevList.TypeDev.setText(items.get(i).getType());
        customViewReportDevList.AgeDev.setText(items.get(i).getAge());
        customViewReportDevList.StatusDev.setText(s);

    }

    @Override
    public int getItemCount() {
        return DataDevManager.getInstance().getItemsDto().getDatadev().size();
    }

    public class CustomViewReportDevList extends RecyclerView.ViewHolder {

        TextView TextViewDate,TextViewNameDev,TypeDev,AgeDev,StatusDev;


        public CustomViewReportDevList(@NonNull View itemView) {
            super(itemView);

            TextViewDate = (TextView)itemView.findViewById(R.id.textview_date);
            TextViewNameDev = (TextView)itemView.findViewById(R.id.textview_name_dev);
            TypeDev = (TextView)itemView.findViewById(R.id.type_dev);
            AgeDev = (TextView)itemView.findViewById(R.id.age_dev);
            StatusDev = (TextView)itemView.findViewById(R.id.status_dev);

        }
    }
}
