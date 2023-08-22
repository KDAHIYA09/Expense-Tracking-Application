package com.example.track_my_money.Models;

public class Category {

    private String categoryName;
    private int categoryImage;
    private int catColor;

    public Category() {
    }

    public Category(String categoryName, int categoryImage, int catColor) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.catColor = catColor;
    }

    public int getCatColor() {
        return catColor;
    }

    public void setCatColor(int catColor) {
        this.catColor = catColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
