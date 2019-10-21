package android.vn.lcd.ui.main;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.util.Preconditions;

import android.view.MenuItem;
import android.view.View;
import android.vn.lcd.base.BaseActivity;
import android.vn.lcd.ui.listcontact.ListContactFragment;
import android.vn.lcd.ui.listduplicate.ListDuplicateContactFragment;

import com.adomino.ddsdb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends BaseActivity
        implements View.OnClickListener, MainViewListener {

    private ListContactFragment listContactFragment = new ListContactFragment();

    private ListDuplicateContactFragment listDuplicateContactFragment = new ListDuplicateContactFragment();

    private static CurrentPage currentPage = CurrentPage.CHANGE_HEAD_NUMBER_PAGE;

    private final MainViewListener viewListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fbtExecute).setOnClickListener(this);

        BottomNavigationView bottomNavigationView  = findViewById(R.id.bnvFunction);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId() == R.id.itConvert) {
                    currentPage = CurrentPage.CHANGE_HEAD_NUMBER_PAGE;
                    attachFragment(listContactFragment);
                } else if (item.getItemId() == R.id.itFindSame){
                    currentPage = CurrentPage.REMOVE_DUPLICATE_PAGE;
                    attachFragment(listDuplicateContactFragment);
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                attachFragment(listContactFragment);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(
                    List<PermissionRequest> permissions,
                    PermissionToken token
            ) {

            }
        }).check();
    }

    @Override
    public void onClick(View v) {
        switch (currentPage) {
            case CHANGE_HEAD_NUMBER_PAGE: {
                viewListener.executeUpdateContactList();
                break;
            }
            case REMOVE_DUPLICATE_PAGE: {
                viewListener.executeRemoveContactList();
                break;
            }
        }
    }

    @Override
    public void onActionBarConfiguration(@NotNull ActionBar actionBar) {

    }

    @Override
    public int viewMainId() {
        return R.id.container;
    }

    @Override
    public void executeUpdateContactList() {
        listContactFragment.executeUpdateContact();
    }

    @Override
    public void executeRemoveContactList() {
        listDuplicateContactFragment.removeDuplicatePhoneNumber();
    }
}
