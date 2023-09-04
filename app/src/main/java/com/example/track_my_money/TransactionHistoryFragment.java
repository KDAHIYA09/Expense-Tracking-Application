package com.example.track_my_money;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.track_my_money.Adapters.TransactionsAdapter;
import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.Utils.Constants;
import com.example.track_my_money.Utils.Helper;
import com.example.track_my_money.ViewModel.MainViewModel;
import com.example.track_my_money.databinding.FragmentHomeBinding;
import com.example.track_my_money.databinding.FragmentTransactionHistoryBinding;
import com.example.track_my_money.views.Activities.MainActivity;
import com.example.track_my_money.views.Fragments.AddTransactionFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;

public class TransactionHistoryFragment extends Fragment {

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentTransactionHistoryBinding binding;

    Calendar calendar;

    public MainViewModel viewModel;

    int selectedTab = 0;  // this is to use all fragments
    // 0 = daily fragment; 1=monthly; 2=calander; 3 = summary; 4 = notes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionHistoryBinding.inflate(inflater);

        // YHA P hmne mainviewmodel wali class ko initialize kra liya
        viewModel = new ViewModelProvider((ViewModelStoreOwner) getViewLifecycleOwner()).get(MainViewModel.class);

        Constants.setCatogeries();

//        setUpDatabase(); // setting up database for user
        // ye sara kaam hm view model class m krege

        calendar = Calendar.getInstance();
        updateDate(); //function to set current date in textview



        binding.nextDateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (Constants.SELECTED_TAB == Constants.DAILY){
                    calendar.add(Calendar.DATE, 1);
                }else if (Constants.SELECTED_TAB == Constants.MONTHLY){
                    calendar.add(Calendar.MONTH, 1);
                }
                updateDate();
            }
        });

        binding.previousDateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (Constants.SELECTED_TAB == Constants.DAILY){
                    calendar.add(Calendar.DATE, -1);
                }else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
            }
        });


        binding.floatingActionBtn.setOnClickListener(c-> {
            new AddTransactionFragment().show(getChildFragmentManager(), null);
        });

        // to apply on clicklistenr on tab item
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    Toast.makeText(getActivity(), "Showing Monthly Transactions Data", Toast.LENGTH_SHORT).show();
                    updateDate();
                } else if (tab.getText().equals("Daily")){
                    Toast.makeText(getActivity(), "Showing Daily Transactions", Toast.LENGTH_SHORT).show();
                    Constants.SELECTED_TAB = 0;
                    updateDate();
                }
            }
// yha p hm monthly tab m jaye to month wise transaction aaye ye try krrhe h

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        // is function m hm data ko observe krte h aur koi bhi change hone p ye hme show krdega

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(getContext(), transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);
                if (transactions.size() > 0){
                    binding.emptyState.setVisibility(View.GONE);
                }else{
                    binding.emptyState.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalAmountLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLbl.setText(String.valueOf(aDouble));
            }
        });          // ab income aur expense k vaale set krne k bad next step h fragment se data add krwana

        viewModel.getTransaction(calendar);




        return binding.getRoot();
    }

    // data live show krwane k liye hm 1 function bnate h yha
    public void getTransaction(){
        viewModel.getTransaction(calendar);
    }

    // @RequiresApi(api = Build.VERSION_CODES.N)
    void updateDate() {
//        SimpleDateFormat dateFormat = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            dateFormat = new SimpleDateFormat("dd MMMM,YYYY");
//        }

        // according to selected tab hm method r krege
        if (Constants.SELECTED_TAB == Constants.DAILY){
            binding.currentDate.setText(Helper.formatdate(calendar.getTime()));
        } else if (Constants.SELECTED_TAB == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatdateByMonth(calendar.getTime()));
        }

        // binding.currentDate.setText(Helper.formatdate(calendar.getTime()));
        //method for getting current date
        viewModel.getTransaction(calendar);
    }

}