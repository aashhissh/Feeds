package com.ashish.feeds.presentation.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ashish.feeds.R;
import com.ashish.feeds.domain.executor.impl.ThreadExecutor;
import com.ashish.feeds.presentation.models.FeedModel;
import com.ashish.feeds.presentation.presenters.MainPresenter;
import com.ashish.feeds.presentation.presenters.impl.MainPresenterImpl;
import com.ashish.feeds.presentation.ui.adapters.FeedsAdapter;
import com.ashish.feeds.threading.MainThreadImpl;
import com.ashish.feeds.utils.CodeUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout srlContainer;

    private Menu mymenu;
    private MainPresenter mainPresenter;
    private FeedsAdapter feedsAdapter;
    private ArrayList<FeedModel> feeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        init();
    }

    private void initComponent() {
        // init refresh layout for refreshing list
        srlContainer = findViewById(R.id.srl_container);
        srlContainer.setOnRefreshListener(this);

        // init recycler for feeds
        feedsAdapter = new FeedsAdapter(feeds);

        RecyclerView rvFeeds = findViewById(R.id.rv_feeds);
        rvFeeds.setLayoutManager(new LinearLayoutManager(this));
        rvFeeds.setAdapter(feedsAdapter);
    }

    private void init() {
        mainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.resume();
    }

    @Override
    public void showProgress() {
        srlContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlContainer.setRefreshing(false);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add our menu
        getMenuInflater().inflate(R.menu.main, menu);

        // We should save our menu so we can use it to reset our updater.
        mymenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                srlContainer.setRefreshing(true);
                mainPresenter.getFeedsDataFromServer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean isConnectedToInternet() {
        return CodeUtil.isConnectedToInternet(this);
    }

    @Override
    public void updateTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void updateFeedsList(List<FeedModel> feeds) {
        this.feeds.clear();
        this.feeds.addAll(feeds);
        feedsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mainPresenter.getFeedsDataFromServer();
    }
}
