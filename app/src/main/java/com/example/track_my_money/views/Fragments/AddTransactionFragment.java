package com.example.track_my_money.views.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.track_my_money.Adapters.AccountAdapter;
import com.example.track_my_money.Adapters.CategoryAdapter;
import com.example.track_my_money.Models.Account;
import com.example.track_my_money.Models.Category;
import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.R;
import com.example.track_my_money.Utils.Constants;
import com.example.track_my_money.Utils.Helper;
import com.example.track_my_money.databinding.FragmentAddTransactionBinding;
import com.example.track_my_money.databinding.ListDialogBinding;
import com.example.track_my_money.views.Activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {



    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//to use binding here
    FragmentAddTransactionBinding binding;

    Transaction transaction;    //transaction ko initialise krwa diya yha

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater); //yha p sidha inflator liya cuz upar hmne layout inflator argument pass kiya hua h

        transaction = new Transaction();

        // click krne p textview ka aur uske text ka color change krwane k liye
        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.dafault_selector));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.defaultTxtColor));

                transaction.setType(Constants.INCOME);
            }
        });

        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.dafault_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.defaultTxtColor));

                transaction.setType(Constants.EXPENSE);
            }
        });

        // to show a calander on clicking in the text view ka code
        binding.date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
                        calendar.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());

//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,YYYY");
//                            String dateToShow = dateFormat.format(calendar.getTime());
//                            binding.date.setText(dateToShow);
//                        }

                        String dateToShow = Helper.formatdate(calendar.getTime());
                        binding.date.setText(dateToShow);

                        transaction.setDate(calendar.getTime());
                        transaction.setId(calendar.getTime().getTime());

                    }
                });
                datePickerDialog.show();
            }
        });

        binding.catogary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater); //jis recycler view m set krwana h dialog box uski binding kr rhe h yha
                AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();         //to create dialog box
                categoryDialog.setView(dialogBinding.getRoot());
//                ArrayList<Category> categories = new ArrayList<>();
//                categories.add(new Category("Business", R.drawable.bags_shopping_svgrepo_com, R.color.catcolor1));
//                categories.add(new Category("Investment", R.drawable.investment_insurance_svgrepo_com,R.color.catcolor2));
//                categories.add(new Category("Salary", R.drawable.money_bag_svgrepo_com, R.color.catcolor3));
//                categories.add(new Category("Rent", R.drawable.rent_house_svgrepo_com, R.color.catcolor4));
//                categories.add(new Category("Loan", R.drawable.payday_loan_svgrepo_com, R.color.catcolor5));
//                categories.add(new Category("Other", R.drawable.card_transfer_svgrepo_com, R.color.catcolor6));

               //yha hmne catorgries arraylist k items define krdiye
                // isko professional bnane kliye hm isko costant class m le gye

                // yha hmne jo adapter class bnai h usko call krdiya aur usme is class ka context and arraylist(catogries) pass krdi
                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListner() {
                    @Override
                    public void onCategoryClicked(Category category) {
                        binding.catogary.setText(category.getCategoryName());
                        transaction.setCategory(category.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                //yha hm layout manager set krge gridlayout manager
                dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                // next hmne recycler view m adapter set krwadiya
                dialogBinding.recyclerView.setAdapter(categoryAdapter);

                categoryDialog.show(); //dialog box ko show krwa do

            }
        });

        // account wale text view p recycler view set krwane k bad ab hm user se choose krwaege kosa account aur usko textview m set krege
        // same process k sath
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater); //jis recycler view m set krwana h dialog box uski binding kr rhe h yha
                AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();         //to create dialog box
                accountsDialog.setView(dialogBinding.getRoot());
                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(new Account(0,"PAYTM"));
                accounts.add(new Account(0,"GooglePay"));
                accounts.add(new Account(0,"Cash"));
                accounts.add(new Account(0,"Bank"));
                accounts.add(new Account(0,"Other"));

                AccountAdapter accountAdapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountClickListner() {
                    @Override
                    public void onAccountSelected(Account account) {
                        binding.account.setText(account.getAccountName());
                        transaction.setAccount(account.getAccountName());
                        accountsDialog.dismiss();
                    }
                });
                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(accountAdapter);

                accountsDialog.show();
            }
        });

        binding.saveTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(binding.amount.getText().toString());
                String note = binding.note.getText().toString();

                transaction.setNote(note);
                if (transaction.getType().equals(Constants.EXPENSE)){
                    transaction.setAmount(amount*-1);
                }else{
                    transaction.setAmount(amount);
                }

                ((MainActivity)getActivity()).viewModel.addTransaction(transaction);
                ((MainActivity)getActivity()).getTransaction();

                dismiss();

            }
        });

        return binding.getRoot() ;
    }
}