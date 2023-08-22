package com.example.track_my_money.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_my_money.Models.Account;
import com.example.track_my_money.Models.Category;
import com.example.track_my_money.R;
import com.example.track_my_money.databinding.RowAccoutnsBinding;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountsViewHolder>{

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountClickListner{
        void onAccountSelected(Account account);
    }

    AccountClickListner accountClickListner;

    public AccountAdapter(Context context, ArrayList<Account> accountArrayList, AccountClickListner accountClickListner) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accountClickListner = accountClickListner;
    }

    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accoutns, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());

        // to set account selected in textview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountClickListner.onAccountSelected(account);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder{

       RowAccoutnsBinding binding;

       public AccountsViewHolder(@NonNull View itemView) {
           super(itemView);
           binding = RowAccoutnsBinding.bind(itemView);
       }
   }

}
//this class is created for account textview . yha p hmne 1 hi recycler view banaya h phle catogry item seet krwa diyethe ab account same way m krwa diyw