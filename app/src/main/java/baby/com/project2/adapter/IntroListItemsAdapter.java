package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.manager.singleton.AgeIntroManager;
import baby.com.project2.manager.singleton.DataIntroManager;
import baby.com.project2.view.IntroDataModelClass;
import baby.com.project2.view.IntroModelClass;

public class IntroListItemsAdapter extends RecyclerView.Adapter<IntroListItemsAdapter.CustomViewIntroData>{

    private Context context;
    private ArrayList<IntroDataModelClass> items;


    public IntroListItemsAdapter(Context context, ArrayList<IntroDataModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewIntroData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewIntroData(LayoutInflater.from(context).inflate(R.layout.customview_list_data_intro, viewGroup, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CustomViewIntroData customViewIntroData, final int i) {

        customViewIntroData.TextViewDataIntroShow.setText(items.get(i).getI_data());

    }

    @Override
    public int getItemCount() {
        return DataIntroManager.getInstance().getItemsDto().getDataintro().size();
    }



    public class CustomViewIntroData extends RecyclerView.ViewHolder {

        TextView TextViewDataIntroShow;

        public CustomViewIntroData(@NonNull View itemView) {
            super(itemView);

            TextViewDataIntroShow = (TextView)itemView.findViewById(R.id.textview_data_intro_show);
        }


    }
}
