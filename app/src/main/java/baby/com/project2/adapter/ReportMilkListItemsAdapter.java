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
import baby.com.project2.activity.EditMilkActivity;
import baby.com.project2.manager.singleton.milk.SelectMilkManager;
import baby.com.project2.view.ReportMilkModelClass;

public class ReportMilkListItemsAdapter extends RecyclerView.Adapter<ReportMilkListItemsAdapter.CustomViewReportMilkList>{

    private Context context;
    private ArrayList<ReportMilkModelClass> items;

    public ReportMilkListItemsAdapter(Context context, ArrayList<ReportMilkModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewReportMilkList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewReportMilkList(LayoutInflater.from(context).inflate(R.layout.customview_list_report_milk, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewReportMilkList customViewReportMilkList, final int i) {

        customViewReportMilkList.TextViewReportDateMilk.setText(items.get(i).getM_date());
        customViewReportMilkList.TextViewReportTimeMilk.setText(items.get(i).getM_time());
        customViewReportMilkList.TextViewReportAgeMilk.setText(String.valueOf(items.get(i).getM_age()));
        customViewReportMilkList.TextViewReportTypeMilk.setText(items.get(i).getM_foodtype());
        customViewReportMilkList.TextViewReportNameMilk.setText(items.get(i).getM_namefood());
        customViewReportMilkList.TextViewReportAmountMilk.setText(String.valueOf(items.get(i).getM_amount()));
        customViewReportMilkList.TextViewReportVolumeMilk.setText(items.get(i).getM_volume());
        customViewReportMilkList.CardViewReportMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditMilkActivity.class);
                intent.putExtra("id",items.get(i).getM_id());
                intent.putExtra("name",items.get(i).getM_namefood());
                intent.putExtra("type",items.get(i).getM_foodtype());
                intent.putExtra("age",items.get(i).getM_age());
                intent.putExtra("date",items.get(i).getM_date());
                intent.putExtra("time",items.get(i).getM_time());
                intent.putExtra("amount",items.get(i).getM_amount());
                intent.putExtra("volum",items.get(i).getM_volume());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SelectMilkManager.getInstance().getItemsDto().getMilk().size();
    }

    public class CustomViewReportMilkList extends RecyclerView.ViewHolder {

        TextView TextViewReportDateMilk,TextViewReportTimeMilk,TextViewReportAgeMilk,
                TextViewReportTypeMilk,TextViewReportNameMilk,TextViewReportAmountMilk,TextViewReportVolumeMilk;
        ImageView ImageViewFood;
        CardView CardViewReportMilk;

        public CustomViewReportMilkList(@NonNull View itemView) {
            super(itemView);

            TextViewReportDateMilk = (TextView)itemView.findViewById(R.id.textview_report_date_milk);
            TextViewReportTimeMilk = (TextView)itemView.findViewById(R.id.textview_report_time_milk);
            TextViewReportAgeMilk = (TextView)itemView.findViewById(R.id.textview_report_age_milk);
            TextViewReportTypeMilk = (TextView)itemView.findViewById(R.id.textview_report_type_milk);
            TextViewReportNameMilk = (TextView)itemView.findViewById(R.id.textview_report_name_milk);
            TextViewReportAmountMilk = (TextView)itemView.findViewById(R.id.textview_report_amount_milk);
            TextViewReportVolumeMilk = (TextView)itemView.findViewById(R.id.textview_report_volume_milk);
            ImageViewFood = (ImageView)itemView.findViewById(R.id.imageview_food);
            CardViewReportMilk = (CardView)itemView.findViewById(R.id.cardview_report_milk);

        }
    }
}
