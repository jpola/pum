package pl.wroc.uni.ift.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by jpola on 04.08.17.
 */

// Klasa obsługująca Fragment który będzie wyświetlany na ekranie
// Implementacja jest bliźniaczo podobna do Activity z kilkoma różnicami
// 1. Widok (fragment_crime) pompujemy w metodzie onCreateView
// 2. Inne dane standardowo w onCreate.

// Kolejność wywołania metod jest następująca. Najpierw onCreateView, potem onCreate.
// chemy mieć już gotowy, napompowany widok i podłączone wszystkie
// elementy widoku fragmentu aby potem w kolejnym kroku wykorzystać je w metodzie onCreate2`



public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // here is difference from Activity onCreate. Fragment lifetime methods are public whereas
    // Activity's methods are protected. They have to be because they are called by activities
    // which is hosting the fragment. public give an access for outside the package.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // hook up the fields from layout
        mTitleField = v.findViewById(R.id.crime_title);
        // Listener do obsługi zdarzeń podczas edycji tekstu
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }



}
