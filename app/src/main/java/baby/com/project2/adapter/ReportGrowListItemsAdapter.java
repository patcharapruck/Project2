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
import baby.com.project2.activity.EditChildActivity;
import baby.com.project2.activity.EditGrowActivity;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.manager.singleton.SelectChildManager;
import baby.com.project2.manager.singleton.SelectGrowManager;
import baby.com.project2.view.KidModelClass;
import baby.com.project2.view.ReportGrowModelClass;

public class ReportGrowListItemsAdapter extends RecyclerView.Adapter<ReportGrowListItemsAdapter.CustomViewReportGrowList>{

    private Context context;
    private ArrayList<ReportGrowModelClass> items;

    public ReportGrowListItemsAdapter(Context context, ArrayList<ReportGrowModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewReportGrowList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewReportGrowList(LayoutInflater.from(context).inflate(R.layout.customview_list_report_grow, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewReportGrowList customViewReportGrowList, final int i) {
        customViewReportGrowList.TextViewHeightGrow.setText(String.valueOf(items.get(i).getG_height()));
        customViewReportGrowList.TextViewWeigthGrow.setText(String.valueOf(items.get(i).getG_weigth()));
        customViewReportGrowList.TextViewDateReportGrow.setText(items.get(i).getG_date());
        customViewReportGrowList.CardViewListGrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditGrowActivity.class);
                intent.putExtra("Gid",i);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return SelectGrowManager.getInstance().getItemsDto().getGrowup().size();
    }

    public class CustomViewReportGrowList extends RecyclerView.ViewHolder {

        TextView TextViewDateReportGrow,TextViewHeightGrow,TextViewWeigthGrow;
        CardView CardViewListGrow;

        public CustomViewReportGrowList(@NonNull View itemView) {
            super(itemView);

            TextViewHeightGrow = (TextView) itemView.findViewById(R.id.textview_height_reportgrow);
            TextViewWeigthGrow = (TextView) itemView.findViewById(R.id.textview_weigth_report_grow);
            TextViewDateReportGrow = (TextView) itemView.findViewById(R.id.textview_date_report_grow);
            CardViewListGrow = (CardView) itemView.findViewById(R.id.cardview_list_grow);

        }
    }
}
