package baby.com.project2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.KidListItemsAdapter;
import baby.com.project2.view.KidModelClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FragmentTransaction f1,f2,f3,f4;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        setFragment();

    }

    private void setFragment() {

        f1 = getChildFragmentManager().beginTransaction();
        f1.replace(R.id.fragment1, new GrownHomeFragment());
        f1.commit();

        f2 = getChildFragmentManager().beginTransaction();
        f2.replace(R.id.fragment2, new DevelorHomeFragment());
        f2.commit();

        f3 = getChildFragmentManager().beginTransaction();
        f3.replace(R.id.fragment3, new VaccineHomeFragment());
        f3.commit();

        f4 = getChildFragmentManager().beginTransaction();
        f4.replace(R.id.fragment4, new MilkHomeFragment());
        f4.commit();

    }

}
