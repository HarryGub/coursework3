package com.shifuu.aawws.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.shifuu.aawws.R;
import com.shifuu.aawws.databinding.ActivityMainBinding;
import com.shifuu.aawws.loginactivity.LoginActivity;
import com.shifuu.aawws.mainactivity.search.SearchFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null)
        {
            exitToLogout();
        }

        Toast.makeText(getApplicationContext(), "Вы вошли как \n" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

        binding.bottomBarHome.bottomBarNavView.setSelectedItemId(R.id.nav_bottom_search);
        binding.bottomBarHome.bottomBarNavView.setOnItemSelectedListener(this);


    }

    private void exitToLogout() {
        Intent i = new Intent(this, LoginActivity.class);
        this.startActivity(i);
        this.finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        NavController navController =
                Navigation.findNavController(binding.contentHome.navHostFragmentContentMain);

        switch (id) {
            case R.id.nav_bottom_scheme: {
                navController.popBackStack(R.id.searchFragment, false);
                navController.navigate(R.id.schemeFragment);
                break;}
            case R.id.nav_bottom_acc: {
                navController.popBackStack(R.id.searchFragment, false);
                navController.navigate(R.id.userFragment);
                break;}
            case R.id.nav_bottom_search: navController.popBackStack(R.id.searchFragment, false); break;

        }

        return true;
    }

    @Override
    public void onBackPressed() {

        NavController navController =
                Navigation.findNavController(binding.contentHome.navHostFragmentContentMain);

        NavDestination id = navController.getCurrentDestination();

        if (id.getId() == R.id.searchFragment)
        {
            handleSearchFragmentOnBackPressed();
            return;
        }

        super.onBackPressed();
        binding.bottomBarHome.bottomBarNavView.setSelectedItemId(R.id.nav_bottom_search);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void handleSearchFragmentOnBackPressed() {
        binding.bottomBarHome.bottomBarNavView.setSelectedItemId(R.id.nav_bottom_search);
        SearchFragment fragment = (SearchFragment) getForegroundFragment();

        if (fragment.onBackPressed())
            super.onBackPressed();

    }

    private Fragment getForegroundFragment(){
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }


}

