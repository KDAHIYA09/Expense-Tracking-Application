package com.example.track_my_money.Utils;

import android.widget.Switch;

import com.example.track_my_money.Models.Category;
import com.example.track_my_money.R;

import java.util.ArrayList;

public class Constants {
    public static String INCOME = "Income";
    public static String EXPENSE = "Expense";

    public static ArrayList<Category> categories;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALANDER = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;


    public static void setCatogeries(){
        categories = new ArrayList<>();
        categories.add(new Category("Business", R.drawable.bags_shopping_svgrepo_com, R.color.catcolor1));
        categories.add(new Category("Investment", R.drawable.investment_insurance_svgrepo_com,R.color.catcolor2));
        categories.add(new Category("Salary", R.drawable.money_bag_svgrepo_com, R.color.catcolor3));
        categories.add(new Category("Rent", R.drawable.rent_house_svgrepo_com, R.color.catcolor4));
        categories.add(new Category("Loan", R.drawable.payday_loan_svgrepo_com, R.color.catcolor5));
        categories.add(new Category("Other", R.drawable.card_transfer_svgrepo_com, R.color.catcolor6));

    }

    // ye hm method bna rhe catogry icon chnage krne k liye category slected k basis p
    public static Category getCaategoryDetails(String categoryName){
        for (Category cat:
             categories) {
            if (cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }

    public static int getAccountsColor(String accountName){
        int color = 0;
        switch(accountName){
            case "PAYTM":
                return R.color.paytmColor;
            case "GooglePay":
                return R.color.googlepayColor;
            case "Cash":
                return R.color.cashColor;
            case "Bank":
                return R.color.bankColor;
            case "Other":
                return R.color.otherColor;
            default:
                return R.color.defaultTxtColor;
        }
    }

}
