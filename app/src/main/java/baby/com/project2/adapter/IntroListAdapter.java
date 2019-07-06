package baby.com.project2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.intro.SelectAgeIntroDto;
import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.AgeIntroManager;
import baby.com.project2.manager.singleton.DataVaccineManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.InsertVaccineManager;
import baby.com.project2.manager.singleton.VaccineManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.IntroModelClass;
import baby.com.project2.view.VaccineModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroListAdapter extends RecyclerView.Adapter<IntroListAdapter.CustomViewIntro>{

    private Context context;
    private ArrayList<IntroModelClass> items;
    private int statuss = 0;
    private String V_id;
    private String datetoday;
    private String place;


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
