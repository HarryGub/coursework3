package com.shifuu.aawws.mainactivity.booking;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shifuu.aawws.R;
import com.shifuu.aawws.databinding.FragmentBookingBinding;
import com.shifuu.aawws.model.Item;

import java.util.Map;

public class BookingFragment extends Fragment {

    private BookingViewModel viewModel;
    private FragmentBookingBinding binding;

    private Item item;

    public static BookingFragment newInstance() {
        return new BookingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentBookingBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);

        item = BookingFragmentArgs.fromBundle(getArguments()).getItem();


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fragBookingName.setText(item.getName());
        binding.fragBookingQua.setText("Всего: " + item.getQua() + "\nБронировано: " + item.getBooked());

        binding.fragBookingQuaToBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setQuaToBook(editable.toString());
            }
        });

        binding.fragBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookItem(viewModel.getQuaToBook());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("BookingFragment", item.toString());
    }

    private void bookItem(String qua)
    {
        if (qua == null || qua.isEmpty())
        {
            Toast.makeText(getContext(), "Введите количество для брони",Toast.LENGTH_LONG).show();
            return;
        }

        if(Integer.parseInt(qua) + item.getBooked() > Integer.parseInt(item.getQua()))
        {
            Toast.makeText(getContext(), "Нельзя бронировать больше фактического количества",Toast.LENGTH_LONG).show();
            return;
        }


        Map<String, Object> map = new ArrayMap<>();
        map.put("name", item.getName());
        map.put("qua", qua);
        map.put("uId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("item", item.getItemId());

        FirebaseFirestore.getInstance().collection("booking").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {


                FirebaseFirestore.getInstance()
                        .collection("items")
                        .document(item.getItemId())
                        .update("booked", item.getBooked() + Integer.parseInt(qua))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Бронь успешна",Toast.LENGTH_LONG).show();

                                NavController navController = Navigation.findNavController(getView());
                                navController.popBackStack();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Не удалось совершить бронь",Toast.LENGTH_LONG).show();
                    }
                });

    }
}











