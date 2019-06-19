package baby.com.project2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import baby.com.project2.R;
import baby.com.project2.activity.ChildGrowActivity;
import baby.com.project2.activity.DevelopMentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelorHomeFragment extends Fragment implements View.OnClickListener {

    private Button ButtonAddData;
    private CardView CardViewStatusLanguage,CardViewStatusUseLanguage
            ,CardViewStatusMuscle,CardViewStatusMovement,CardViewStatusJustmyself;
    private TextView TextViewStatusLanguage,TextViewStatusUseLanguage
            ,TextViewStatusMuscle,TextViewStatusMovement,TextViewStatusJustmyself;

    public DevelorHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_develor_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        ButtonAddData        = (Button)rootView.findViewById(R.id.button_add_data);

        TextViewStatusLanguage      = (TextView)rootView.findViewById(R.id.textview_status_language);
        TextViewStatusUseLanguage   = (TextView)rootView.findViewById(R.id.textview_status_use_language);
        TextViewStatusMuscle        = (TextView)rootView.findViewById(R.id.textview_status_muscle);
        TextViewStatusMovement      = (TextView)rootView.findViewById(R.id.textview_status_movement);
        TextViewStatusJustmyself    = (TextView)rootView.findViewById(R.id.textview_status_justmyself);

        CardViewStatusLanguage      = (CardView)rootView.findViewById(R.id.cardview_status_language);
        CardViewStatusUseLanguage   = (CardView)rootView.findViewById(R.id.cardview_status_use_language);
        CardViewStatusMuscle        = (CardView)rootView.findViewById(R.id.cardview_status_muscle);
        CardViewStatusMovement      = (CardView)rootView.findViewById(R.id.cardview_status_movement);
        CardViewStatusJustmyself    = (CardView)rootView.findViewById(R.id.cardview_status_justmyself);

        ButtonAddData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), DevelopMentActivity.class);
        getContext().startActivity(intent);
    }
}
