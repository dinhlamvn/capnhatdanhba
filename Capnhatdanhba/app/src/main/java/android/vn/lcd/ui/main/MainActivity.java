package android.vn.lcd.ui.main;

import android.Manifest;
import android.lcd.vn.capnhatdanhba.R;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.view.View;
import android.vn.lcd.base.BaseActivity;
import android.vn.lcd.base.BaseFragment;
import android.vn.lcd.ui.listcontact.ListContactFragment;
import android.vn.lcd.ui.listduplicate.ListDuplicateContactAdapter;
import android.vn.lcd.ui.listduplicate.ListDuplicateContactFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ListContactFragment listContactFragment = new ListContactFragment();

    private ListDuplicateContactFragment listDuplicateContactFragment = new ListDuplicateContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fbtExecute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listContactFragment.executeUpdateContact();
            }
        });

        BottomNavigationView bottomNavigationView  = findViewById(R.id.bnvFunction);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                if (item.getItemId() == R.id.itFindSame) {
                    attachFragment(listDuplicateContactFragment);
                } else {
                    attachFragment(listContactFragment);
                }
                return false;
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
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }

    @Override
    public void onActionBarConfiguration(@NotNull ActionBar actionBar) {

    }

    @Override
    public int viewMainId() {
        return R.id.container;
    }
}
