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

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.EditGrowActivity;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.util.SharedPrefDayMonthYear;
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Format form = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
        Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
        Date d = null;
        try {
            d = sdf.parse(items.get(i).getG_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(d);
        String f = form.format(d);
        String f2 = formatter2.format(d);
        int yth = Integer.parseInt(f2)+543;
        String datefullTh = f+" "+yth;

        customViewReportGrowList.TextViewDateReportGrow.setText(datefullTh);
        customViewReportGrowList.textview_age_grow.setText(SharedPrefDayMonthYear.getInstance(Contextor.getInstance().getmContext()).getKeydateformat());

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

        TextView TextViewDateReportGrow,TextViewHeightGrow,TextViewWeigthGrow,textview_age_grow;
        CardView CardViewListGrow;

        public CustomViewReportGrowList(@NonNull View itemView) {
            super(itemView);

            TextViewHeightGrow = (TextView) itemView.findViewById(R.id.textview_height_reportgrow);
            TextViewWeigthGrow = (TextView) itemView.findViewById(R.id.textview_weigth_report_grow);
            TextViewDateReportGrow = (TextView) itemView.findViewById(R.id.textview_date_report_grow);
            CardViewListGrow = (CardView) itemView.findViewById(R.id.cardview_list_grow);
            textview_age_grow = (TextView) itemView.findViewById(R.id.textview_age_grow);

        }
    }
}
