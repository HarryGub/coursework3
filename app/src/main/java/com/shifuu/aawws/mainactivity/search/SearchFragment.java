package com.shifuu.aawws.mainactivity.search;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shifuu.aawws.databinding.FragmentSearchBinding;
import com.shifuu.aawws.mainactivity.OnBackPressedListener;
import com.shifuu.aawws.model.Box;
import com.shifuu.aawws.model.Item;
import com.shifuu.aawws.model.OnItemSelectedListener;
import com.shifuu.aawws.model.OnRackSelectedListener;
import com.shifuu.aawws.model.OnShelfSelectedListener;
import com.shifuu.aawws.model.Rack;
import com.shifuu.aawws.model.Shelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment implements OnBackPressedListener {

    private FragmentSearchBinding binding;

    private SearchViewModel viewModel;

    RacksFirestoreAdapter racksFirestoreAdapter;
    ShelvesFirestoreAdapter shelvesFirestoreAdapter;
    BoxFirestoreAdapter boxFirestoreAdapter;
    private List<Item> items;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);


        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fragSearchRecycler.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        initSearchView();

    }

    @Override
    public void onResume() {
        super.onResume();

        restoreAdapterFromStack();

    }

    private void initSearchView()
    {

        if(viewModel.getItems() == null)
        {
            items = new ArrayList<>();
            FirebaseFirestore
                    .getInstance()
                    .collection("items")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Item i = new Item();

                                    i.setItemId(document.getId());
                                    i.setName(document.get("name", String.class));
                                    i.setQua(String.valueOf(document.get("qua", Integer.class)));
                                    try {
                                        i.setBooked(document.get("booked", Integer.class));
                                    }catch (NullPointerException e)
                                    {
                                        i.setBooked(0);
                                    }

                                    items.add(i);
                                }
                            } else {
                                Log.d("SearchFrag#SearchView", "Error getting documents: ", task.getException());
                            }

                            Log.d("SearchFrag#SearchView", String.valueOf(items.size()));
                        }
                    });

            Log.d("SearchFrag#SearchView", items.toString());

            fillItemsWithPaths(items);
            viewModel.setItems(items);
        }


        viewModel.getItems().forEach(new Consumer<Item>() {
            @Override
            public void accept(Item item) {
                Log.d("SearchFrag#SearchView", item.toString());
            }
        });

        binding.fragUserSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                setSearchAdapter(viewModel.getItems(), query);

                binding.fragUserSearchView.clearFocus();

                Log.d("SearchFrag#SearchView", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    viewModel.setSearchAttached(false);
                    restoreAdapterFromStack();
                }
                Log.d("SearchFrag#SearchView#change", newText);
                return false;
            }
        });
    }

    private void fillItemsWithPaths(List<Item> items) {
        Map<String, String> map = new HashMap<>();

        FirebaseFirestore.getInstance().collection("warehouse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                List<String> racks = new ArrayList<>();
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot snapshot : task.getResult())
                    {
                        racks.add(snapshot.getId());
                    }

                    racks.forEach(new Consumer<String>() {
                        @Override
                        public void accept(String s) {
                            FirebaseFirestore
                                    .getInstance()
                                    .collection("warehouse/" + s + "/shelf")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                            if (task1.isSuccessful())
                                            {
                                                List<String> shelves = new ArrayList<>();
                                                for (QueryDocumentSnapshot snapshot1 : task1.getResult())
                                                {
                                                    shelves.add(snapshot1.getId());
                                                }

                                                shelves.forEach(new Consumer<String>() {
                                                    @Override
                                                    public void accept(String s1) {
                                                        FirebaseFirestore.getInstance()
                                                                .collection("warehouse/" + s + "/shelf/" + s1 + "/box")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                                        if (task2.isSuccessful())
                                                                        {
                                                                            List<String> boxes = new ArrayList<>();


                                                                            for (QueryDocumentSnapshot snapshot2 : task2.getResult())
                                                                            {



                                                                                String path = "warehouse/" + s + "/shelf/" + s1 + "/box/" + snapshot2.getId();
                                                                                String humanPath = "Стеллаж " + s + "\nполка " + s1 + "\nкоробка " + snapshot2.getId();

                                                                                FirebaseFirestore.getInstance()
                                                                                        .document(path).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful())
                                                                                                {
                                                                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                                                                    List<DocumentReference> list = (List<DocumentReference>) documentSnapshot.get("array");
                                                                                                    Log.d("asdas", path);

                                                                                                    if (list != null)
                                                                                                    {
                                                                                                        list.forEach(new Consumer<DocumentReference>() {
                                                                                                            @Override
                                                                                                            public void accept(DocumentReference documentReference) {
                                                                                                                map.put(documentReference.getId(), path);

                                                                                                                items.forEach(new Consumer<Item>() {
                                                                                                                    @Override
                                                                                                                    public void accept(Item item) {
                                                                                                                        if (item.getItemId().equals(documentReference.getId()))
                                                                                                                            item.setBox(humanPath);
                                                                                                                    }
                                                                                                                });

                                                                                                                Log.d("asdas", path + documentReference.getId());
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    });
                }
            }
        });

    }

    private void setSearchAdapter(List<Item> items, String query) {

        List<Item> queriedList = items.stream().filter(new Predicate<Item>() {
            @Override
            public boolean test(Item item) {
                return item.getName().contains(query);
            }
        }).collect(Collectors.toList());
        SearchAdapter adapter = new SearchAdapter(queriedList, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Item item) {
                Log.d("SearchFrag#setSearchAdapter", item.toString());

                getView().setVisibility(View.GONE);
                NavController navController = Navigation.findNavController(getView());
                NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToBookingFragment(item);
                navController.navigate(navDirections);
            }
        });

        binding.fragSearchRecycler.setAdapter(adapter);
        viewModel.setSearchAttached(true);

    }

    private void restoreAdapterFromStack() {

        if (viewModel.isSearchAttached())
        {
            Log.d("asfagsdfdfg", binding.fragUserSearchView.getQuery().toString());
            setSearchAdapter(viewModel.getItems(), binding.fragUserSearchView.getQuery().toString());
            return;
        }

        if (viewModel.getCurrentRack() == null)
        {
            setRacksAdapter();
            return;
        }

        if (viewModel.getCurrentShelf() == null) {
            setShelvesAdapter(viewModel.getCurrentRack());
            return;
        }

        setBoxAdapter(viewModel.getCurrentRack(), viewModel.getCurrentShelf());

    }

    private void setRacksAdapter() {
        Query queryRack = FirebaseFirestore.getInstance().collection("warehouse");

        FirestoreRecyclerOptions<Rack> optRack = new FirestoreRecyclerOptions.Builder<Rack>()
                .setQuery(queryRack, new SnapshotParser<Rack>() {
                    @NonNull
                    @Override
                    public Rack parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Log.d("SearchFrag#RackAdapter", snapshot.toString());
                        Rack r = new Rack();
                        r.setName(snapshot.getId());
                        return r;
                    }
                }).setLifecycleOwner(this).build();

        racksFirestoreAdapter = new RacksFirestoreAdapter(optRack, new OnRackSelectedListener() {
            @Override
            public void onRackSelected(Rack rack) {
                Log.d("SearchFrag#setRacksAdapter", rack.toString());

                setShelvesAdapter(rack);
            }
        });

        binding.fragSearchRecycler.setAdapter(racksFirestoreAdapter);
    }

    private void setShelvesAdapter(Rack rack) {
        Query queryShelf = FirebaseFirestore
                .getInstance()
                .collection("warehouse/" + rack.getName()+ "/shelf");
        Log.d("SearchFrag#setShelvesAdapter", "warehouse/" + rack.getName()+ "/shelf");

        FirestoreRecyclerOptions<Shelf> optShelf = new FirestoreRecyclerOptions.Builder<Shelf>()
                .setQuery(queryShelf, new SnapshotParser<Shelf>() {
                    @NonNull
                    @Override
                    public Shelf parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Shelf s = new Shelf();
                        s.setName(snapshot.getId());
                        Log.d("SearchFrag#setShelvesAdapter", rack.toString());
                        return s;
                    }
                }).setLifecycleOwner(this).build();
        shelvesFirestoreAdapter = new ShelvesFirestoreAdapter(optShelf, new OnShelfSelectedListener() {
            @Override
            public void onShelfSelected(Shelf shelf) {
                Log.d("SearchFrag", shelf.toString());
                setBoxAdapter(rack, shelf);
            }
        });


        binding.fragSearchRecycler.setAdapter(shelvesFirestoreAdapter);

        viewModel.setCurrentRack(rack);

    }

    private void setBoxAdapter(Rack rack, Shelf shelf)
    {
        Query Box = FirebaseFirestore
                .getInstance()
                .collection("warehouse/" + rack.getName()+ "/shelf/" + shelf.getName() + "/box");
        Log.d("SearchFrag#setBoxAdapter", "warehouse/" + rack.getName()+ "/shelf");

        FirestoreRecyclerOptions<Box> optBox = new FirestoreRecyclerOptions.Builder<Box>()
                .setQuery(Box, new SnapshotParser<Box>() {
                    @NonNull
                    @Override
                    public Box parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Box b = new Box();

                        b.setName(snapshot.getId());


                        List<DocumentReference> list = (List<DocumentReference>) snapshot.get("array");

                        b.setItemRefs(list);


                        Log.d("SearchFrag#setShelvesAdapter", b.toString());
                        return b;
                    }
                }).setLifecycleOwner(this).build();
        boxFirestoreAdapter = new BoxFirestoreAdapter(optBox, new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Item item) {
                Log.d("SearchFrag", item.toString());
                getView().setVisibility(View.GONE);
                NavController navController = Navigation.findNavController(getView());
                NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToBookingFragment(item);
                navController.navigate(navDirections);
            }
    });


        binding.fragSearchRecycler.setAdapter(boxFirestoreAdapter);

        viewModel.setCurrentRack(rack);
        viewModel.setCurrentShelf(shelf);

    }

    @Override
    public boolean onBackPressed() {
        if (viewModel.isSearchAttached())
        {
            viewModel.setSearchAttached(false);
            binding.fragUserSearchView.setQuery("", false);
            restoreAdapterFromStack();
            return false;
        }

        if (viewModel.getCurrentRack() == null)
            return true;

        if (viewModel.getCurrentShelf() == null) {
            viewModel.setCurrentRack(null);
            restoreAdapterFromStack();
            return false;
        }

        viewModel.setCurrentShelf(null);
        restoreAdapterFromStack();
        return false;
    }
}






















