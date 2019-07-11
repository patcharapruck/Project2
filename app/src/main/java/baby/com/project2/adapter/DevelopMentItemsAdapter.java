package baby.com.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.EditGrowActivity;
import baby.com.project2.activity.InsertDevActivity;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.develorment.SizeDevManager;
import baby.com.project2.view.DevelopMentDataModelClass;

public class DevelopMentItemsAdapter extends RecyclerView.Adapter<DevelopMentItemsAdapter.CustomViewDevelopMent>{

    private Context context;
    private ArrayList<DevelopMentDataModelClass> items;

    public DevelopMentItemsAdapter(Context context, ArrayList<DevelopMentDataModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewDevelopMent onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewDevelopMent(LayoutInflater.from(context).inflate(R.layout.customview_list_development_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewDevelopMent customViewDevelopMentList, final int i) {
        customViewDevelopMentList.TextViewDataDevShow.setText(items.get(i).getBD_data());
        customViewDevelopMentList.StatusDev.setVisibility(View.INVISIBLE);
        SelectDataDevDto selectDataDevDto = DataDevManager.getInstance().getItemsDto();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String formatDateTime = dateFormat.format(calendar.getTime());

        String fkcd_id="";
        final int update=0;
        int size=0;

        try {
            size = selectDataDevDto.getDatadev().size();
        }catch (Exception e){
            size = 0;
        }

        if(size!=0){
            for(int j=0;j<size;j++){
                if(selectDataDevDto.getDatadev().get(j).getBD_id().equals(items.get(i).getBD_id())){
                    customViewDevelopMentList.StatusDev.setVisibility(View.VISIBLE);
                    formatDateTime = selectDataDevDto.getDatadev().get(j).getFKcd_date();
                    fkcd_id = selectDataDevDto.getDatadev().get(j).getFKcd_id();

                    if(selectDataDevDto.getDatadev().get(j).getFKcd_status()==1){
                        customViewDevelopMentList.StatusDev.setImageResource(R.mipmap.ic_success);
                    }else {
                        customViewDevelopMentList.StatusDev.setImageResource(R.mipmap.ic_failed);
                    }

                }
            }

        }

        final String finalDate = formatDateTime;
        final String finalFkcd_id = fkcd_id;
        customViewDevelopMentList.CardViewDataDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsertDevActivity.class);
                intent.putExtra("fkcd_id", finalFkcd_id);
                intent.putExtra("id",items.get(i).getBD_id());
                intent.putExtra("type",items.get(i).getId_type());
                intent.putExtra("data",items.get(i).getBD_data());
                intent.putExtra("date", finalDate);
                intent.putExtra("update", update);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SizeDevManager.getInstance().getItemsDto().getSize_dev();
    }

    public class CustomViewDevelopMent extends RecyclerView.ViewHolder {

        TextView TextViewDataDevShow;
        ImageView StatusDev;
        CardView CardViewDataDev;

        public CustomViewDevelopMent(@NonNull View itemView) {
            super(itemView);

            TextViewDataDevShow   = (TextView)itemView.findViewById(R.id.textview_data_dev_show);
            CardViewDataDev = (CardView)itemView.findViewById(R.id.cardview_data_dev);
            StatusDev       = (ImageView) itemView.findViewById(R.id.status_dev);

        }
    }

}
