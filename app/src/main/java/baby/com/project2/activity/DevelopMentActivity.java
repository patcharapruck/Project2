package baby.com.project2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.DevelopMentListItemsAdapter;
import baby.com.project2.adapter.KidListItemsAdapter;
import baby.com.project2.view.DevelopMentModelClass;
import baby.com.project2.view.KidModelClass;

public class DevelopMentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ArrayList<DevelopMentModelClass> items;
    DevelopMentListItemsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_ment);
        initInstances();
    }

    private void initInstances(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_dev);

        setRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new DevelopMentListItemsAdapter(DevelopMentActivity.this, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(DevelopMentActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 8; i++) {
            try {
                items.add(new DevelopMentModelClass("kkk","Fluk","09/06/2540"));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }
}
