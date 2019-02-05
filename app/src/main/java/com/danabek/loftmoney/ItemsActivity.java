package com.danabek.loftmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        adapter = new ItemsAdapter(this);
        recycler = findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = (new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),((LinearLayoutManager) layoutManager).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        List<ItemPosition> items = new ArrayList<>();
        items.add(new ItemPosition("Milk","230"));
        items.add(new ItemPosition("bread","50"));
        items.add(new ItemPosition("juicy","280"));
        items.add(new ItemPosition("pan","3500"));
        items.add(new ItemPosition("cat","50000"));
        items.add(new ItemPosition("bird","180"));
        items.add(new ItemPosition("Car","180"));
        items.add(new ItemPosition("MobilePhone","180"));
        items.add(new ItemPosition("laptop","180"));

        adapter.setItems(items);
    }
}
