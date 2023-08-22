package com.example.track_my_money.views.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.track_my_money.Adapters.TransactionsAdapter;
import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.Utils.Constants;
import com.example.track_my_money.Utils.Helper;
import com.example.track_my_money.ViewModel.MainViewModel;
import com.example.track_my_money.views.Fragments.AddTransactionFragment;
import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;  // to directly access any id without using find view by id
    Calendar calendar;
//    Realm realm;  // making an instance of database //ye view model mclass m shift krdiya

    int selectedTab = 0;  // this is to use all fragments
    // 0 = daily fragment; 1=monthly; 2=calander; 3 = summary; 4 = notes

    public MainViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        // YHA P hmne mainviewmodel wali class ko initialize kra liya
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

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
            new AddTransactionFragment().show(getSupportFragmentManager(), null);
        });

        // to apply on clicklistenr on tab item
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    Toast.makeText(MainActivity.this, "Showing Monthly Transactions Data", Toast.LENGTH_SHORT).show();
                    updateDate();
                } else if (tab.getText().equals("Daily")){
                    Toast.makeText(MainActivity.this, "Showing Daily Transactions", Toast.LENGTH_SHORT).show();
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


       // ArrayList<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction(Constants.INCOME, "Business", "GooglePay", "Some note here", new Date(), 500, 2));
//        transactions.add(new Transaction(Constants.EXPENSE, "Investment", "PAYTM", "Some note here", new Date(), 500, 3));
//        transactions.add(new Transaction(Constants.INCOME, "Loan", "Cash", "Some note here", new Date(), 500, 4));
//        transactions.add(new Transaction(Constants.EXPENSE, "Rent", "Bank", "Some note here", new Date(), 500, 5));
//        transactions.add(new Transaction(Constants.INCOME, "Salary", "Other", "Some note here", new Date(), 500, 6));

//        // all the code to save a transaction in database is written between begin transaction and commit transaction block
//        realm.beginTransaction();
//        // code to add transaction
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Business", "GooglePay", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Business", "GooglePay", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE, "Investment", "PAYTM", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Loan", "Cash", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE, "Rent", "Bank", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Salary", "Other", "Some note here", new Date(), 500, new Date().getTime()));
//        realm.commitTransaction();
//        ye code view model class m shift kriya
//        // to show transactions
//        RealmResults<Transaction> transactions = realm.where(Transaction.class).findAll();


        // is function m hm data ko observe krte h aur koi bhi change hone p ye hme show krdega

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);
               if (transactions.size() > 0){
                   binding.emptyState.setVisibility(View.GONE);
               }else{
                   binding.emptyState.setVisibility(View.VISIBLE);
               }
            }
        });

        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalAmountLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });

        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLbl.setText(String.valueOf(aDouble));
            }
        });          // ab income aur expense k vaale set krne k bad next step h fragment se data add krwana

        viewModel.getTransaction(calendar);

//        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this, transactions);
//        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
//        binding.transactionsList.setAdapter(transactionsAdapter);

    }

//    void setUpDatabase(){
//        Realm.init(this);
//        realm = Realm.getDefaultInstance();
//    }   //method to set database for user


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

// action bar ya toolbar m agar icon set krne h to menu lagana padta h
// optionsItemMenu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu); // to set menu on toolbar
        return super.onCreateOptionsMenu(menu);
    }
}