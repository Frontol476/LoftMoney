package com.danabek.loftmoney;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    private ItemsAdapter adapter;

    public ItemsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ItemsAdapter(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler = view.findViewById(R.id.recycler);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        //Add devider
        RecyclerView.LayoutManager layoutManager = (new LinearLayoutManager(requireContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),((LinearLayoutManager) layoutManager).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        adapter.setItems(createTempItems());
    }

   private List<ItemPosition> createTempItems(){
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

       return items;
   }
}
