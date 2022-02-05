package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import com.example.deliveryapp.adapters.SearchResult;
import com.example.deliveryapp.adapters.TopSearch;
import com.example.deliveryapp.databinding.ActivitySearchBinding;

public class Search extends AppCompatActivity {

    ActivitySearchBinding activitySearchBinding;
    List<String> trending;
    List<String> searchHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        activitySearchBinding.layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchHistory = new ArrayList<>();

        activitySearchBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                activitySearchBinding.emptyLayout.setVisibility(View.GONE);
                activitySearchBinding.etSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                activitySearchBinding.etClear.setVisibility(View.VISIBLE);
                activitySearchBinding.layoutResult.setVisibility(View.VISIBLE);

                activitySearchBinding.tvShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activitySearchBinding.tvShop.setTextColor(getResources().getColor(R.color.teal_200));
                        activitySearchBinding.viewSelectedShop.setVisibility(View.VISIBLE);
                        activitySearchBinding.tvItems.setTextColor(getResources().getColor(R.color.darkGrey));
                        activitySearchBinding.viewSelectedItems.setVisibility(View.GONE);

                        SearchResult topSearch = new SearchResult(Search.this, trending, true);
                        activitySearchBinding.rvSearchResult.setLayoutManager(new LinearLayoutManager(Search.this, RecyclerView.VERTICAL, false));
                        activitySearchBinding.rvSearchResult.setAdapter(topSearch);
                    }
                });

                activitySearchBinding.tvItems.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activitySearchBinding.tvItems.setTextColor(getResources().getColor(R.color.teal_200));
                        activitySearchBinding.viewSelectedItems.setVisibility(View.VISIBLE);
                        activitySearchBinding.tvShop.setTextColor(getResources().getColor(R.color.darkGrey));
                        activitySearchBinding.viewSelectedShop.setVisibility(View.GONE);

                        SearchResult topSearch = new SearchResult(Search.this, trending);
                        activitySearchBinding.rvSearchResult.setLayoutManager(new LinearLayoutManager(Search.this, RecyclerView.VERTICAL, false));
                        activitySearchBinding.rvSearchResult.setAdapter(topSearch);
                    }
                });

                SearchResult topSearch = new SearchResult(Search.this, trending);
                activitySearchBinding.rvSearchResult.setLayoutManager(new LinearLayoutManager(Search.this, RecyclerView.VERTICAL, false));
                activitySearchBinding.rvSearchResult.setAdapter(topSearch);

                activitySearchBinding.etClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchHistory.add(activitySearchBinding.etSearch.getText().toString());
                        activitySearchBinding.etSearch.setText("");
                        activitySearchBinding.etClear.setVisibility(View.GONE);
                    }
                });

                String text = activitySearchBinding.etSearch.getText().toString();
                if (text.length() == 0) {
                    activitySearchBinding.etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_view, 0, 0, 0);
                    activitySearchBinding.emptyLayout.setVisibility(View.VISIBLE);
                    activitySearchBinding.layoutResult.setVisibility(View.GONE);

                    for (String str : searchHistory) {
                        Chip chip = addHistoryChip(Search.this, str);

                        chip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = chip.getText().toString();
                                activitySearchBinding.etSearch.setText(text);
                                activitySearchBinding.etSearch.setSelection(text.length());
                                activitySearchBinding.cgHistory.removeView(chip);
                            }
                        });
                        activitySearchBinding.cgHistory.addView(chip);
                    }
                    searchHistory.clear();
                } else {
                    activitySearchBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                searchHistory.add(text);
                                return true;
                            }
                            return false;
                        }
                    });
                }

            }
        });
        trending = new ArrayList<>();
        trending.add("Carrot");
        trending.add("Onion");
        trending.add("Tomato");
        trending.add("Ladies Finger");
        trending.add("Mint leaves");
        trending.add("Lemon");
        trending.add("Ginger");
        trending.add("Garlic");

        for (String str : trending) {
            Chip chip = addChip(Search.this, str);

            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = chip.getText().toString();
                    activitySearchBinding.etSearch.setText(text);
                    activitySearchBinding.etSearch.setSelection(text.length());
                }
            });

            activitySearchBinding.cgTrending.addView(chip);
        }

        TopSearch topSearch = new TopSearch(Search.this, trending);
        activitySearchBinding.rvEssentials.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activitySearchBinding.rvEssentials.setAdapter(topSearch);
        activitySearchBinding.rvTopSearches.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        activitySearchBinding.rvTopSearches.setAdapter(topSearch);

    }

    public Chip addChip(Context context, String text) {
        Chip chip = new Chip(context);
        chip.setText(text);
        chip.setChipBackgroundColorResource(R.color.chip_green);
        chip.setTextSize(14);
        chip.setTextColor(getResources().getColor(R.color.white));
        return chip;
    }

    public Chip addHistoryChip(Context context, String text) {
        Chip chip = new Chip(context);
        chip.setText(text);
        chip.setChipIconResource(R.drawable.ic_clock_grey);
//        chip.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_clock,0,0,0);
        chip.setChipBackgroundColorResource(R.color.grey);
        chip.setTextSize(14);
        chip.setTextColor(getResources().getColor(R.color.darkGrey));
        return chip;
    }
}