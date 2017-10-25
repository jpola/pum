package pl.wroc.uni.ift.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jpola on 07.08.17.
 */
// Singleton - oznacza że w naszej aplikacji będzie istniała tylko jedna instancja tej klasy.
// Ma prywatny konstruktor - nie możemy go wywołać w innej części kodu
// aby operować na składowych tej klasy tworzymy go wywołując CrimeLab.get()
public class CrimeLab {

    // statyczne pole o typu tej klasy. Dziwne prawda? ;)
    private static CrimeLab sCrimeLab;


    // use an interface for declaring variables
    private List<Crime> mCrimes;

    // metoda get zamiast konstruktora
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
        {
            // przy pierwszym wywołaniu będzie to
            sCrimeLab = new CrimeLab(context);
        }
        // po pierwszym wywolaniu sCrimeLab jest już zainicjalizowany więc
        // zwracamy tylko zmienną
        return sCrimeLab;
    }

    // Uwaga: prywatny konstruktor
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

    //Getters and Setters

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
