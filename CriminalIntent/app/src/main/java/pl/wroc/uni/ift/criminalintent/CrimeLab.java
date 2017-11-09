package pl.wroc.uni.ift.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jpola on 07.08.17.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    // use an interface for declaring variables
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // Context argument will be used later for databases
    private CrimeLab(Context context) {
        // and here use an implementation when instantiating
        mCrimes = new ArrayList<>();

        // Generate 100 dummy crimes
        for (int i = 0; i < 100; ++i) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // every second
            mCrimes.add(crime);
        }

    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
