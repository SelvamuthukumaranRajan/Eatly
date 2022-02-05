package com.example.deliveryapp.adapters;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class SubcategoryParentListItem implements ParentListItem {
    public String mTitle;
    private List<SubcategoryChildListItem> mChildItemList;
    public SubcategoryParentListItem(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public List<SubcategoryChildListItem> getChildItemList() {
        return mChildItemList;
    }

    public void setChildItemList(List<SubcategoryChildListItem> list) {
        mChildItemList = list;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
