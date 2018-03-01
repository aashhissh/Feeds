package com.ashish.feeds.presentation.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements MainPresenter.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private SwipeRefreshLayout srlContainer;
    private View vError;

    private MainPresenter mainPresenter;
    private FeedsAdapter feedsAdapter;
    private ArrayList<FeedModel> feeds = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce;
    private RecyclerView rvFeeds;

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

        //init error view
        vError = findViewById(R.id.error_view);
        vError.setOnClickListener(this);

        // init recycler for feeds
        feedsAdapter = new FeedsAdapter(feeds);

        rvFeeds = findViewById(R.id.rv_feeds);
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
        getFeedsDataFromServer();
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
        CodeUtil.showToast(this, message);
        vError.setVisibility(View.VISIBLE);
        rvFeeds.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add our menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getFeedsDataFromServer();
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
        vError.setVisibility(View.GONE);
        rvFeeds.setVisibility(View.VISIBLE);
        this.feeds.clear();
        this.feeds.addAll(feeds);
        feedsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        getFeedsDataFromServer();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_view:
                vError.setVisibility(View.GONE);
                getFeedsDataFromServer();
                break;
        }
    }

    private void getFeedsDataFromServer() {
        if(CodeUtil.isConnectedToInternet(this)) {
            mainPresenter.getFeedsDataFromServer();
        } else {
            showError(getString(R.string.no_internet));
        }
    }
}
