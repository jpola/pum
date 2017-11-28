package pl.wroc.uni.ift.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jpola on 07.08.17.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment()
    {
        return new CrimeListFragment();
    }
}
