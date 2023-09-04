package com.example.track_my_money.views.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.track_my_money.AccountsFragment;
import com.example.track_my_money.Adapters.TransactionsAdapter;
import com.example.track_my_money.HomeFragment;
import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.MoreFragment;
import com.example.track_my_money.PrivacyPolicyFragment;
import com.example.track_my_money.ProfileFragment;
import com.example.track_my_money.RateUsFragment;
import com.example.track_my_money.ShareUsFragment;
import com.example.track_my_money.StatsFragment;
import com.example.track_my_money.TransactionHistoryFragment;
import com.example.track_my_money.Utils.Constants;
import com.example.track_my_money.Utils.Helper;
import com.example.track_my_money.ViewModel.MainViewModel;
import com.example.track_my_money.views.Fragments.AddTransactionFragment;
import com.example.track_my_money.R;
import com.example.track_my_money.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;  // to directly access any id without using find view by id
    Calendar calendar;
    Menu botom_nav_menu, drawer_menu;
//    Realm realm;  // making an instance of database //ye view model mclass m shift krdiya

//    int selectedTab = 0;  // this is to use all fragments
//    // 0 = daily fragment; 1=monthly; 2=calander; 3 = summary; 4 = notes
    // main activity m fragment apply krne k liye is cod ko home fragment m leke chale gye

    public MainViewModel viewModel;
    // to apply mvvm
    // ttttttt

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();
        replaceFragment(new HomeFragment());
        //to show home fragment whenever app opens
        binding.navView.setCheckedItem(R.id.nav_home);


       binding.navView.bringToFront();
       // setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        // YHA P hmne mainviewmodel wali class ko initialize kra liya
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Constants.setCatogeries();

//        setUpDatabase(); // setting up database for user
        // ye sara kaam hm view model class m krege

        calendar = Calendar.getInstance();
        //updateDate(); //function to set current date in textview

        botom_nav_menu = binding.navigationBar.getMenu();
        MenuItem menuItem1 = botom_nav_menu.findItem(R.id.bottom_home);
        MenuItem menuItem2 = botom_nav_menu.findItem(R.id.bottom_stats);
        MenuItem menuItem3 = botom_nav_menu.findItem(R.id.bottom_account);
        MenuItem menuItem4 = botom_nav_menu.findItem(R.id.bottom_more);

        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new HomeFragment());
                menuItem1.setChecked(true);
                binding.toolbar.setTitle("Transactions");
                return true;
            }
        });
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new StatsFragment());
                menuItem2.setChecked(true);
                binding.toolbar.setTitle("Statistics");
                return true;
            }
        });
        menuItem3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                menuItem3.setChecked(true);
                replaceFragment(new AccountsFragment());
                binding.toolbar.setTitle("Accounts");
                return true;
            }
        });
        menuItem4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                menuItem4.setChecked(true);
                replaceFragment(new MoreFragment());
                binding.toolbar.setTitle("More Actions");
                return true;
            }
        });


        // drawer layout menu konclick listner
        drawer_menu = binding.navView.getMenu();
        MenuItem profile = drawer_menu.findItem(R.id.nav_profile);
        MenuItem home = drawer_menu.findItem(R.id.nav_home);
        MenuItem history = drawer_menu.findItem(R.id.nav_history);
        MenuItem rateUs = drawer_menu.findItem(R.id.nav_rate_us);
        MenuItem ShareUs = drawer_menu.findItem(R.id.nav_share_us);
        MenuItem privacyPolicy = drawer_menu.findItem(R.id.nav_privacy_policy);
        MenuItem LogOut = drawer_menu.findItem(R.id.nav_log_out);

        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new ProfileFragment());
                profile.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Your Profile");
                return true;
            }
        });
        history.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new TransactionHistoryFragment());
                history.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Transaction History");
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new HomeFragment());
                home.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Transactions");
                return true;
            }
        });
        ShareUs.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new ShareUsFragment());
                ShareUs.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Transactions");
                return true;
            }
        });
        rateUs.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new RateUsFragment());
                rateUs.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Transactions");
                return true;
            }
        });
        privacyPolicy.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                replaceFragment(new PrivacyPolicyFragment());
                privacyPolicy.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.toolbar.setTitle("Transactions");
                return true;
            }
        });
        LogOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                //replaceFragment(new ProfileFragment());
                Toast.makeText(MainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
                LogOut.setChecked(true);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });




// main activity m fragment apply krne k liye is cod ko home fragment m leke chale gye yha se------>>>>>> 1...
//        binding.nextDateBtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                if (Constants.SELECTED_TAB == Constants.DAILY){
//                    calendar.add(Calendar.DATE, 1);
//                }else if (Constants.SELECTED_TAB == Constants.MONTHLY){
//                    calendar.add(Calendar.MONTH, 1);
//                }
//                updateDate();
//            }
//        });

//        binding.previousDateBtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                if (Constants.SELECTED_TAB == Constants.DAILY){
//                    calendar.add(Calendar.DATE, -1);
//                }else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
//                    calendar.add(Calendar.MONTH, -1);
//                }
//                updateDate();
//            }
//        });

//
//        binding.floatingActionBtn.setOnClickListener(c-> {
//            new AddTransactionFragment().show(getSupportFragmentManager(), null);
//        });

//        // to apply on clicklistenr on tab item
//        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getText().equals("Monthly")){
//                    Constants.SELECTED_TAB = 1;
//                    Toast.makeText(MainActivity.this, "Showing Monthly Transactions Data", Toast.LENGTH_SHORT).show();
//                    updateDate();
//                } else if (tab.getText().equals("Daily")){
//                    Toast.makeText(MainActivity.this, "Showing Daily Transactions", Toast.LENGTH_SHORT).show();
//                    Constants.SELECTED_TAB = 0;
//                    updateDate();
//                }
//            }
//// yha p hm monthly tab m jaye to month wise transaction aaye ye try krrhe h
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });   ------>>>>>> yha tk code home fragment m shift krdiya 1....


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



// main activity m fragment apply krne k liye is cod ko home fragment m leke chale gye ----->>>>>>>> yha se 2...
        // is function m hm data ko observe krte h aur koi bhi change hone p ye hme show krdega
//
//        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
//
//        viewModel.transactions.observe(this, new Observer<RealmResults<Transaction>>() {
//            @Override
//            public void onChanged(RealmResults<Transaction> transactions) {
//                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this, transactions);
//                binding.transactionsList.setAdapter(transactionsAdapter);
//               if (transactions.size() > 0){
//                   binding.emptyState.setVisibility(View.GONE);
//               }else{
//                   binding.emptyState.setVisibility(View.VISIBLE);
//               }
//            }
//        });
//
//        viewModel.totalAmount.observe(this, new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.totalAmountLbl.setText(String.valueOf(aDouble));
//            }
//        });
//
//        viewModel.totalIncome.observe(this, new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.incomeLbl.setText(String.valueOf(aDouble));
//            }
//        });
//
//        viewModel.totalExpense.observe(this, new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                binding.expenseLbl.setText(String.valueOf(aDouble));
//            }
//        });          // ab income aur expense k vaale set krne k bad next step h fragment se data add krwana
//
//        viewModel.getTransaction(calendar);  --------->>>>>> yha tk 2...

//        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this, transactions);
//        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
//        binding.transactionsList.setAdapter(transactionsAdapter);

    }

//    void setUpDatabase(){
//        Realm.init(this);
//        realm = Realm.getDefaultInstance();
//    }   //method to set database for user

// main activity m fragment apply krne k liye is cod ko home fragment m leke chale gye ----->>>> 3.....
    // data live show krwane k liye hm 1 function bnate h yha
    public void getTransaction(){
        viewModel.getTransaction(calendar);
    }


//   // @RequiresApi(api = Build.VERSION_CODES.N)
//    void updateDate() {
////        SimpleDateFormat dateFormat = null;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
////            dateFormat = new SimpleDateFormat("dd MMMM,YYYY");
////        }
//
//        // according to selected tab hm method r krege
//        if (Constants.SELECTED_TAB == Constants.DAILY){
//            binding.currentDate.setText(Helper.formatdate(calendar.getTime()));
//        } else if (Constants.SELECTED_TAB == Constants.MONTHLY){
//            binding.currentDate.setText(Helper.formatdateByMonth(calendar.getTime()));
//        }
//
//       // binding.currentDate.setText(Helper.formatdate(calendar.getTime()));
//        //method for getting current date
//        viewModel.getTransaction(calendar);
//    } ----->>>> yhatk3....


    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // set karta hai Navigation item me
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // menu ki coding isme hoge
        return true;
    }
}