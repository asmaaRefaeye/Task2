package com.example.creativejsontask.view;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.creativejsontask.R;
import com.example.creativejsontask.adapter.DataAdapter;
import com.example.creativejsontask.model.Data;
import com.example.creativejsontask.model.EndlessRecyclerViewScrollListener;
import com.example.creativejsontask.model.ListItem;
import com.example.creativejsontask.network.DataRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Data> online_items;
    DataAdapter adapter;
    ProgressBar mProgress;
    GridLayoutManager layoutManager;
    SwipeRefreshLayout RefreshLayout;
    int current_page;
    ArrayList<ListItem> cache_items ;
//    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        online_items = new ArrayList<>();
        cache_items = new ArrayList<>();
//        sqliteHelper = new SqliteHelper(this);

        recyclerView = findViewById(R.id.recyclerview);
        mProgress = findViewById(R.id.mProgress);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DataAdapter(MainActivity.this, online_items,cache_items);
        recyclerView.setAdapter(adapter);

        if (is_online())
        {
            current_page=1;
            loadData(current_page);
        }
        else
            Toast.makeText(MainActivity.this, "برجاء الإتصال بالنت", Toast.LENGTH_SHORT).show();

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
            {
                mProgress.setVisibility(View.VISIBLE);
                current_page=page;
                loadData(current_page);
            }
        });


        RefreshLayout =  findViewById(R.id.RefreshLayout);
        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (is_online())
                {
                    if (online_items.size()>0)
                        online_items.clear();

                    current_page=1;
                    loadData(current_page);
                    RefreshLayout.setRefreshing(false);
                }
                else
                    Toast.makeText(MainActivity.this, "برجاء الإتصال بالنت", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadData(int page)
    {
        if (is_online())
        {
//            if (current_page==1 )
//            {
////                sqliteHelper.deleteTable();
//                online_items.clear();
//            }
            mProgress.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final DataRequest request = retrofit.create(DataRequest.class);
            Call<ArrayList<Data>> call = request.getJson(page);
            call.enqueue(new Callback<ArrayList<Data>>() {
                @Override
                public void onResponse(Call<ArrayList<Data>> call, Response<ArrayList<Data>> response) {

                    if (response.isSuccessful()) {
                        try
                        {
                            adapter.data_type = "online";
                            online_items.addAll(response.body());
                            adapter.notifyDataSetChanged();
                            mProgress.setVisibility(View.GONE);

//                            cache_items = new ArrayList<>();
//                            for (int i=0;i<online_items.size();i++)
//                            {
//                                String repoName = online_items.get(i).getName();
//                                String description = online_items.get(i).getDescription();
//                                String userName = online_items.get(i).getOwner().getLogin();
//                                boolean fork = online_items.get(i).isFork();
//                                String repo_url = online_items.get(i).getHtmlUrl();
//                                String owner_url = online_items.get(i).getOwner().getHtmlUrl();
//                                cache_items.add(new ListItem(repoName,description,userName,fork,repo_url,owner_url));
//                            }


                        } catch (Exception e) {
                        }
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<Data>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }
        else
        {
            Toast.makeText(this, "برجاء الإتصال بالنت", Toast.LENGTH_SHORT).show();
            mProgress.setVisibility(View.GONE);
//            adapter.data_type="cache";
//            cache_items = sqliteHelper.show_all_Repos();
//            adapter = new DataAdapter(MainActivity.this, online_items,cache_items);
//            recyclerView.setAdapter(adapter);
//            mProgress.setVisibility(View.GONE);
        }
    }

    private boolean is_online()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        if (network != null && network.isConnected())
            return true;
        else
            return false;
    }
}
