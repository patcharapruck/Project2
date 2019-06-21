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
        return 8;
    }

    public class CustomViewDevelopMentList extends RecyclerView.ViewHolder {

        TextView Topics,Side,TextViewDateDev;
        CardView CardViewDevelopment;
        CircularRevealCardView StatusDev;
        ExpandableRelativeLayout Mycontent;
        ImageButton ImageViewDev;
        RadioGroup CheckDev;
        RadioButton YesDev,NoDev;


        public CustomViewDevelopMentList(@NonNull View itemView) {
            super(itemView);

            Topics              = (TextView)itemView.findViewById(R.id.topics);
            Side                = (TextView)itemView.findViewById(R.id.side);
            TextViewDateDev     = (TextView)itemView.findViewById(R.id.textview_date_dev);
            CardViewDevelopment = (CardView)itemView.findViewById(R.id.cardview_development);
            StatusDev           = (CircularRevealCardView)itemView.findViewById(R.id.status_dev);
            ImageViewDev        = (ImageButton)itemView.findViewById(R.id.imageview_dev);
            CheckDev            = (RadioGroup)itemView.findViewById(R.id.check_dev);
            YesDev              = (RadioButton)itemView.findViewById(R.id.yes_dev);
            NoDev               = (RadioButton)itemView.findViewById(R.id.no_dev);
            Mycontent           = (ExpandableRelativeLayout) itemView.findViewById(R.id.mycontent);


        }
    }
}
