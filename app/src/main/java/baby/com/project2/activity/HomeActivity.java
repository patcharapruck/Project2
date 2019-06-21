package baby.com.project2.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.KidListItemsAdapter;
import baby.com.project2.fragment.HomeFragment;
import baby.com.project2.fragment.MenuFragment;
import baby.com.project2.view.KidModelClass;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    ArrayList<KidModelClass> items;
    KidListItemsAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_logout) {

//            SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).logout();

            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initInstances();
    }

    private void initInstances() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.item_menu:
                        fragment = new MenuFragment();
                        break;

                    case R.id.item_drink:
                      //  fragment = new DrinkFragment();
                        break;

                    case R.id.item_pr:
                      //  fragment = new PRFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.item_home);

        setRecyclerView();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new KidListItemsAdapter(HomeActivity.this, items);

        recyclerView = findViewById(R.id.recycler_view_kids);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 8; i++) {
            try {
                items.add(new KidModelClass("kkk","Fluk","09/06/2540"));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }
}
