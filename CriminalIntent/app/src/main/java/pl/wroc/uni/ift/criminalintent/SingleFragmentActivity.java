package pl.wroc.uni.ift.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jpola on 07.08.17.
 */

// Klasa bazowa dla wszystkich aktywności które przechowują pojedynczy fragment
// W klasie pochodnej należy nadpisać metodę createFragment().

/**
 * Każda nowa aktywność będzie składała się z
 * Hostującego activity - w tym projekcie to:
 *          CrimeActivity - dla detali przestępstwa
 *          CrimeListActivity - aktywność prezentująca listę wszystkich dostępnych przestępstw
 * Fragment wyświetlającu UI. Jest to layout fragmentu (plik xml) oraz odpowiadająca mu klasa java
 * implementująca funkconalność. Np. CrimeListActivity wyświetla CrimeListFragment,
 * który ma zdefiniowane UI w pliku fragment_crime_list.xml
   */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
