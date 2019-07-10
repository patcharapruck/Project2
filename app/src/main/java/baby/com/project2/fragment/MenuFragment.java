package baby.com.project2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import baby.com.project2.R;
import baby.com.project2.activity.AddMilkActivity;
import baby.com.project2.activity.ChildGrowActivity;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.activity.VaccineActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {


    private CardView CardViewMilk,CardViewVac,CardViewDev,CardViewGrow;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        CardViewGrow = (CardView)rootView.findViewById(R.id.cardview_grow);
        CardViewDev = (CardView)rootView.findViewById(R.id.cardview_dev);
        CardViewVac = (CardView)rootView.findViewById(R.id.cardview_vac);
        CardViewMilk = (CardView)rootView.findViewById(R.id.cardview_milk);


        CardViewGrow.setOnClickListener(this);
        CardViewDev.setOnClickListener(this);
        CardViewVac.setOnClickListener(this);
        CardViewMilk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == CardViewGrow){
            Intent intent = new Intent(getContext(), ChildGrowActivity.class);
            startActivity(intent);
        }
        if(v == CardViewDev){
            Intent intent = new Intent(getContext(), DevelopMentActivity.class);
            startActivity(intent);
        }
        if(v == CardViewVac){
            Intent intent = new Intent(getContext(), VaccineActivity.class);
            startActivity(intent);
        }
        if(v == CardViewMilk){
            Intent intent = new Intent(getContext(), AddMilkActivity.class);
            startActivity(intent);
        }
    }
}
