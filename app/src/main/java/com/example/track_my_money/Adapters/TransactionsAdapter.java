package com.example.track_my_money.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_my_money.Models.Category;
import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.R;
import com.example.track_my_money.Utils.Constants;
import com.example.track_my_money.Utils.Helper;
import com.example.track_my_money.databinding.RowTransactionBinding;
import com.example.track_my_money.views.Activities.MainActivity;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactioViewHolder> {

    Context context;
    //ArrayList<Transaction> transactions; // ab database bnate tym hm arraylist ki bajae realm result use krege
    // Arraylist hmne use kri thi ustum data ko store krwane k liye but here we will deal with real time database and data hence we will use realm database and realmresult keyword
    RealmResults<Transaction> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactioViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull TransactioViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.binding.transctionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLabel.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formatdate(transaction.getDate()));
        holder.binding.TranasactionCatogary.setText(transaction.getCategory());

        Category transactionCategory = Constants.getCaategoryDetails(transaction.getCategory());
        holder.binding.catogaryIcon.setImageResource(transactionCategory.getCategoryImage());
        holder.binding.catogaryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCatColor()));

        holder.binding.accountLabel.setBackgroundTintList(ColorStateList.valueOf(context.getColor(Constants.getAccountsColor(transaction.getAccount()))));

        // ab acc toincome and expense hm text color ko change krege
        if (transaction.getType().equals(Constants.INCOME)){
            holder.binding.transctionAmount.setTextColor(context.getColor(R.color.greenColor));
        } else if (transaction.getType().equals(Constants.EXPENSE)) {
            holder.binding.transctionAmount.setTextColor(context.getColor(R.color.redColor));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure you want to delete the transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)context).viewModel.deleteTransaction(transaction);
                    }
                });

                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactioViewHolder extends RecyclerView.ViewHolder{

        // ab hmm us view ko yha bind krdege jisme hme data ko set krana h
        // ie yha hmus recyler view ko le lege jiske liye ye aapter bnaya h
        RowTransactionBinding binding;

        public TransactioViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RowTransactionBinding.bind(itemView);


        }
    }

}
