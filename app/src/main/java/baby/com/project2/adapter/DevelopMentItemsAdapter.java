package baby.com.project2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import baby.com.project2.R;
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
    public void onBindViewHolder(@NonNull final CustomViewDevelopMent customViewDevelopMentList, int i) {
        customViewDevelopMentList.TextViewDataDevShow.setText(items.get(i).getBD_data());
    }

    @Override
    public int getItemCount() {
        return SizeDevManager.getInstance().getItemsDto().getSize_dev();
    }

    public class CustomViewDevelopMent extends RecyclerView.ViewHolder {

        TextView TextViewDataDevShow;
        CardView CardViewDataDev,StatusDev;

        public CustomViewDevelopMent(@NonNull View itemView) {
            super(itemView);

            TextViewDataDevShow     = (TextView)itemView.findViewById(R.id.textview_data_dev_show);
            CardViewDataDev = (CardView)itemView.findViewById(R.id.cardview_data_dev);
            StatusDev           = (CardView) itemView.findViewById(R.id.status_dev);

        }
    }
}
