package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.manager.singleton.intro.DataIntroManager;
import baby.com.project2.view.IntroDataModelClass;

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
        String url = "https://admitbaby.000webhostapp.com/";
        customViewIntroData.TextViewDataIntroShow.setText(items.get(i).getI_data());
        Glide.with(context)
                .load(url+items.get(i).getI_image())
                .into(customViewIntroData.ImageViewIntro);
    }

    @Override
    public int getItemCount() {
        return DataIntroManager.getInstance().getItemsDto().getDataintro().size();
    }



    public class CustomViewIntroData extends RecyclerView.ViewHolder {

        TextView TextViewDataIntroShow;
        ImageView ImageViewIntro;

        public CustomViewIntroData(@NonNull View itemView) {
            super(itemView);

            TextViewDataIntroShow = (TextView)itemView.findViewById(R.id.textview_data_intro_show);
            ImageViewIntro = (ImageView)itemView.findViewById(R.id.imageview_intro);
        }


    }
}
