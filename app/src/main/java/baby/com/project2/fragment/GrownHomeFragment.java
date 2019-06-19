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

/**
 * A simple {@link Fragment} subclass.
 */
public class GrownHomeFragment extends Fragment implements View.OnClickListener {


    private Button ButtonAddData;
    private TextView TextViewDateUpdate,TextViewHeight,TextViewWidth;
    private CardView CardViewHeight,CardViewWidth,CardViewStatusBody;
    private TextView TextViewStatusHeight,TextViewStatusWidth,TextViewStatusBody;

    public GrownHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grown_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        ButtonAddData        = (Button)rootView.findViewById(R.id.button_add_data);

        TextViewDateUpdate   = (TextView)rootView.findViewById(R.id.textview_date_update);
        TextViewHeight       = (TextView)rootView.findViewById(R.id.textview_height);
        TextViewWidth        = (TextView)rootView.findViewById(R.id.textview_width);
        TextViewStatusHeight = (TextView)rootView.findViewById(R.id.textview_status_height);
        TextViewStatusWidth  = (TextView)rootView.findViewById(R.id.textview_status_width);
        TextViewStatusBody   = (TextView)rootView.findViewById(R.id.textview_status_body);

        CardViewHeight       = (CardView)rootView.findViewById(R.id.cardview_height);
        CardViewWidth        = (CardView)rootView.findViewById(R.id.cardview_width);
        CardViewStatusBody   = (CardView)rootView.findViewById(R.id.cardview_status_body);

        ButtonAddData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == ButtonAddData){
            Intent intent = new Intent(getContext(), ChildGrowActivity.class);
            getContext().startActivity(intent);
        }
    }
}
