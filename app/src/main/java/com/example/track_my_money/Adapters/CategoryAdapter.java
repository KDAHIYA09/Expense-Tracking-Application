package com.example.track_my_money.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.track_my_money.Models.Category;
import com.example.track_my_money.R;
import com.example.track_my_money.databinding.SampleLayoutCatogaryItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    // we make this interface to set image and text in catory text view when an item from arraylist is selected
    public interface CategoryClickListner{
        void onCategoryClicked(Category category);
    }

    CategoryClickListner categoryClickListner;

    public CategoryAdapter(Context context, ArrayList<Category> categories, CategoryClickListner categoryClickListner){
        this.context = context;
        this.categories = categories;
        this.categoryClickListner = categoryClickListner;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_layout_catogary_item, parent, false));
        // here we are setting the sample item to the view
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.catogaryTxt.setText(category.getCategoryName());
        holder.binding.catogaryIcon.setImageResource(category.getCategoryImage());
        holder.binding.catogaryIcon.setBackgroundTintList(context.getColorStateList(category.getCatColor())); // to apply icon color custom to each item
        // now ab jis icon p click ho vo set hojaye is text view m uske liye we will an interface catogryClickListner above

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.onCategoryClicked(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        SampleLayoutCatogaryItemBinding binding;    // jo sample item bnaya tha dialog box k lie usko use kr rhe h
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLayoutCatogaryItemBinding.bind(itemView);  // bind kouse krte h jb function m inflate ka argument given na ho
        }
    }

}
