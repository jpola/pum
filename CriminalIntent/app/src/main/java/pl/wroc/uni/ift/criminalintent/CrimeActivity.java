package pl.wroc.uni.ift.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends AppCompatActivity {

    // Zawsze Fragment jest umieszczany na jakimś activity.
    // U nas jest to CrimeActivity z którym powiązany jest layout activity_crime.xml
    // activity_crime.xml to prosty layout który wypełniony jest ramką w której będzie
    // wyświetlany fragment.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // FragmentManager zarządza fragmentami w obrębie tej aktywności.
        FragmentManager fm = getSupportFragmentManager();
        // Jeśli fragment już będzie stworzony to pobieramy z menagera.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // w przeciwnym razie to będzie null i musimy go stworzyć
        if (fragment == null) {
            // tworzenie fragmentu
            fragment = new CrimeFragment();
            // dodanie fragmentu do layoutu w miejscu fragment_container
            // reszta już jest załatwiana przez OS.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }
}
