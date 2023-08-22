package com.example.track_my_money.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.track_my_money.Models.Transaction;
import com.example.track_my_money.Utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();

    Realm realm;

    Calendar calendar;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setUpDatabase();
    }

    public void getTransaction(Calendar calendar){

        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        double income = 0;
        double expense = 0;
        double amount = 0;

        RealmResults<Transaction> NewTransactions = null;


        if (Constants.SELECTED_TAB == Constants.DAILY) {

            // to show transactions
            NewTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .findAll();

            // total income k textview m text set krwane k liye
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.INCOME).sum("amount").doubleValue();


            // total expense k textview m text set krwane k liye
            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.EXPENSE).sum("amount").doubleValue();

            // total amount k textview m text set krwane k liye
            amount = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .sum("amount").doubleValue();

//            totalIncome.setValue(income);
//            totalExpense.setValue(expense);
//            totalAmount.setValue(amount);  // niche paste krege to make it accesible to whole function
            // transactions.setValue(NewTransactions);

            // next hm in 3on total income, total expense, total amount k liye observer set krege main activity m

        }else if (Constants.SELECTED_TAB == Constants.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH, 0);

            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endTime = calendar.getTime();

            // to show transactions
             NewTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .findAll();

            // total income k textview m text set krwane k liye
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type", Constants.INCOME).sum("amount").doubleValue();


            // total expense k textview m text set krwane k liye
            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .equalTo("type", Constants.EXPENSE).sum("amount").doubleValue();

            // total amount k textview m text set krwane k liye
            amount = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", startTime)
                    .lessThan("date", endTime)
                    .sum("amount").doubleValue();


        }  // yha p calander wali fragment ko use krne k liye code aayega

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(amount);
        transactions.setValue(NewTransactions);

    }

    public void addTransaction(Transaction transaction){
        // all the code to save a transaction in database is written between begin transaction and commit transaction block
        realm.beginTransaction();
        // code to add transaction
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();

    }

    public void deleteTransaction(Transaction transaction){
            realm.beginTransaction();
            transaction.deleteFromRealm();
            realm.commitTransaction();
            getTransaction(calendar);
    }

    public void addTransactions(){
        // all the code to save a transaction in database is written between begin transaction and commit transaction block
        realm.beginTransaction();
        // code to add transaction
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Business", "GooglePay", "Some note here", new Date(), 500, new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Business", "GooglePay", "Some note here", new Date(), 500, new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE, "Investment", "PAYTM", "Some note here", new Date(), -500, new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Loan", "Cash", "Some note here", new Date(), 500, new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.EXPENSE, "Rent", "Bank", "Some note here", new Date(), -500, new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transaction(Constants.INCOME, "Salary", "Other", "Some note here", new Date(), 500, new Date().getTime()));
        realm.commitTransaction();

    }

    void setUpDatabase(){
        realm = Realm.getDefaultInstance();
    }   //method to set database for user


}


// here we have started integrating mvvm architecture