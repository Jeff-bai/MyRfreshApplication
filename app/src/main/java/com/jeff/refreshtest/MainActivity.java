package com.jeff.refreshtest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRefreshLoadmoreListener {

    private SmartRefreshLayout refresh;
    private RecyclerView recycler;
    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0;i<5;i++){
            list.add("------我是第"+i+"个------");
        }

        refresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshLoadmoreListener(this);
        refresh.setEnableAutoLoadmore(false);
        refresh.setEnableRefresh(true);
        refresh.setEnableLoadmore(false);
        refresh.setRefreshHeader(new MyHeader(this));
//        refresh.setRefreshHeader(new hea)
        refresh.setRefreshFooter(new ClassicsFooter(this));
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new MyAdapter(this);
        adapter.setDate(list);
        recycler.setAdapter(adapter);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recycler);
        recycler.addOnItemTouchListener(new OnRecyclerItemClickListener(recycler){
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
//                if (vh.getLayoutPosition()!=list.size()-1) {
                    itemTouchHelper.startDrag(vh);
//                }
            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        list.clear();
        list.add("2");
        list.add("3");
        list.add("2");
        list.add("3");
        list.add("2");
        list.add("3");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setDate(list);
                refresh.finishLoadmore();
            }
        },2000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        list.clear();
        list.add("123");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setRefreshDate(list);
                refresh.finishRefresh();
            }
        },2000);

    }
}
