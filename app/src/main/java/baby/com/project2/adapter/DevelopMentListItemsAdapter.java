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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.manager.singleton.TypeDevManager;
import baby.com.project2.view.DevelopMentModelClass;
import baby.com.project2.view.KidModelClass;

public class DevelopMentListItemsAdapter extends RecyclerView.Adapter<DevelopMentListItemsAdapter.CustomViewDevelopMentList>{

    private Context context;
    private ArrayList<DevelopMentModelClass> items;

    public DevelopMentListItemsAdapter(Context context, ArrayList<DevelopMentModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewDevelopMentList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewDevelopMentList(LayoutInflater.from(context).inflate(R.layout.customview_list_development, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewDevelopMentList customViewDevelopMentList, int i) {
        customViewDevelopMentList.TextViewType.setText(items.get(i).getData());
        customViewDevelopMentList.Mycontent.collapse();
        customViewDevelopMentList.CardViewDevelopment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewDevelopMentList.Mycontent.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return TypeDevManager.getInstance().getItemsDto().getTypeDev().size();
    }

    public class CustomViewDevelopMentList extends RecyclerView.ViewHolder {

        TextView TextViewType;
        CardView CardViewDevelopment;
        ExpandableRelativeLayout Mycontent;


        public CustomViewDevelopMentList(@NonNull View itemView) {
            super(itemView);

            TextViewType              = (TextView)itemView.findViewById(R.id.textview_type);
            CardViewDevelopment = (CardView)itemView.findViewById(R.id.cardview_development);
            Mycontent           = (ExpandableRelativeLayout) itemView.findViewById(R.id.mycontent);

        }
    }
}
