package android.vn.lcd.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.vn.lcd.data.Contact;
import android.vn.lcd.interfaces.IViewConstructor;
import android.vn.lcd.adapter.ContactAdapter;
import android.vn.lcd.sql.ContactHelper;
import android.vn.lcd.utils.AlphabetSort;

import java.util.ArrayList;
import java.util.Collections;

public class ListContactActivity extends AppCompatActivity implements IViewConstructor, SearchView.OnQueryTextListener {


    private RecyclerView mListContactView;
    private ContactAdapter mAdapter;
    private ContactHelper contactHelper;
    private ArrayList<Contact> mDataList;
    private boolean isUpdate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_contact);

        initParams();

        initLayout();

        initListener();

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = (MenuItem) menu.findItem(R.id.search_box);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        if (searchManager != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        mAdapter.filter(s);

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1: {
                loadAutoChangeHeadNumberScreen();
                break;
            }
            case R.id.item2: {
                loadChangeHeadNumberScreen();
                break;
            }
            case R.id.item3: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void initParams() {
        contactHelper = ContactHelper.getInstance(getApplicationContext());
    }

    @Override
    public void initLayout() {
        mListContactView = findViewById(R.id.list_contact);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mListContactView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        mDataList = contactHelper.getContactList();
        Collections.sort(mDataList, new AlphabetSort());
        mAdapter = new ContactAdapter(getApplicationContext(), mDataList);
        mListContactView.setAdapter(mAdapter);
    }

    private void loadAutoChangeHeadNumberScreen() {
        Intent intent = new Intent(this, AutoChangeHeadNumberActivity.class);
        startActivity(intent);
    }

    private void loadChangeHeadNumberScreen() {
        Intent intent = new Intent(this, ChangeHeadNumberActivity.class);
        startActivity(intent);
    }
}
