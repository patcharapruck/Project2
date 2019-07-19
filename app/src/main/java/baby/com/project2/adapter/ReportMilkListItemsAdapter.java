package baby.com.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Format form = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
        Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
        Date d = null;
        try {
            d = sdf.parse(items.get(i).getM_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(d);
        String f = form.format(d);
        String f2 = formatter2.format(d);
        int yth = Integer.parseInt(f2)+543;
        String datefullTh = f+" "+yth;


        customViewReportMilkList.TextViewReportDateMilk.setText(datefullTh);
        customViewReportMilkList.TextViewReportTimeMilk.setText(items.get(i).getM_time());
        customViewReportMilkList.TextViewReportAgeMilk.setText(items.get(i).getM_age());
        customViewReportMilkList.TextViewReportTypeMilk.setText(items.get(i).getM_foodtype());
        customViewReportMilkList.TextViewReportNameMilk.setText(items.get(i).getM_namefood());
        customViewReportMilkList.TextViewReportAmountMilk.setText(String.valueOf(items.get(i).getM_amount()));
        customViewReportMilkList.TextViewReportVolumeMilk.setText(items.get(i).getM_volume());


        if(items.get(i).getM_image().length()<1||items.get(i).getM_image()==null){
                customViewReportMilkList.ImageViewFood.setImageResource(R.mipmap.ic_baby_milk);
        }else{
            byte[] decodedString = Base64.decode(items.get(i).getM_image(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            customViewReportMilkList.ImageViewFood.setImageBitmap(decodedByte);
        }

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
                intent.putExtra("image",items.get(i).getM_image());
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
