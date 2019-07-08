package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import baby.com.project2.activity.DataIntroActivity;
import baby.com.project2.manager.singleton.intro.AgeIntroManager;
import baby.com.project2.view.IntroModelClass;

public class IntroListAdapter extends RecyclerView.Adapter<IntroListAdapter.CustomViewIntro>{

    private Context context;
    private ArrayList<IntroModelClass> items;


    public IntroListAdapter(Context context, ArrayList<IntroModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewIntro onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewIntro(LayoutInflater.from(context).inflate(R.layout.customview_list_intro, viewGroup, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final CustomViewIntro customViewIntro, final int i) {

           customViewIntro.AgeIntro.setText(items.get(i).getAge_intro());
           customViewIntro.Mycontent.collapse();
           customViewIntro.CardViewIntro.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   customViewIntro.Mycontent.toggle();
               }
           });
           customViewIntro.TextViewNameIntro.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, DataIntroActivity.class);
                   intent.putExtra("id",items.get(i).getId_ageintro());
                   context.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return AgeIntroManager.getInstance().getItemsDto().getAge_intro().size();
    }



    public class CustomViewIntro extends RecyclerView.ViewHolder {

        TextView AgeIntro,TextViewNameIntro;
        CardView CardViewIntro;
        ExpandableRelativeLayout Mycontent;

        public CustomViewIntro(@NonNull View itemView) {
            super(itemView);

            AgeIntro = (TextView)itemView.findViewById(R.id.age_intro);
            TextViewNameIntro = (TextView)itemView.findViewById(R.id.textview_name_intro);
            Mycontent = (ExpandableRelativeLayout)itemView.findViewById(R.id.mycontent);
            CardViewIntro = (CardView)itemView.findViewById(R.id.cardview_intro);
        }


    }
}
