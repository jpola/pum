package pl.wroc.uni.ift.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jpola on 07.08.17.
 */

//CrimeListFragment wykorzsytuje RecyclerView do wyświetlania wszystkich itemów (przestępstw)

/**
 * RecyclerView: Do jesgo poprawnego zaimplementowania musimy napisać dwie dodatkowe klasy
 *      ViewHolder - definiuje jak wygląda item. Jest to zdefiniowane w list_item_crime.xml
 *      Adapter - Klasa która wie jak połączyć źródło danych do wyświetlania i przekazać
 *      odpowiednio spreparowany ViewHolder (czyli pojedynczy item) do RecyclerView;
 *      RecyclerView w zależności od rozmiaru ViewHoldera oraz rozmiaru ekranu wie ile elementów musi
 *      aktualnie wyswietlić na telefonie. Dzięki temu nie musi tworzyć wszystkich elementów na raz.
 *      Tylko wyświetla to co akutalnie jest potrzebne.
 */

// Skojarzony z tą klasą fragmet_crime_list.xml implementuje tylko jeden widget RecyclerView
//wypełniający cały dostępny obszr. HolderView sprecyzuje jak każdy z wyświetlanych
// elementów będzie wyglądał
public class CrimeListFragment extends Fragment {

    // pole przechowujące recyclerView;
    private RecyclerView mCrimeRecyclerView;

    // adapter do recyclera - wie jak z danych stworzyć ViewHolder który wyświetlany jest
    // w recyclerze.
    private CrimeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Podłączenie recyclerea z xmla do zmiennej
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        // Sprecyzowanie jak mają być przewijane elementy.
        // metoda getActivity() zwraca kontekst hostującej ten fragment aktywności
        // w tym przypadku jest to CrimeListActivity bo z niego wywołaliśmy ten fragment.
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        // Pobranie instancji do klasy CrimeLab która jest singletonem,
        // W przypadku poprawnie zaimplementowanego singletonu nie mamy
        // możliwości wywołania konstruktora gdyż jest on prywatny. Zamiast tego
        // pojawiają sie metody typu get lub getInstance lub instance w zależności od
        // konwencji dewelopera.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        List<Crime> crimes = crimeLab.getCrimes();

        // Adapter ma dostęp do źródła danych w tym przypadku do listy przestępstw.
        mAdapter = new CrimeAdapter(crimes);
        // wstawiamy adapter do recycler viewera. Adapter wie jak generować widoki
        // poszczególnych itemów do wyśwetlenia czyli obiektow kalsy ViewHolder.
        mCrimeRecyclerView.setAdapter(mAdapter);

    }



    // Implementacja Crime Holdera (ViewHolder) dodatkowo implementuje OnclickListener
    // aby można kliknąć na dany item w liście.
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // widgety w danym itemie
        private TextView mTitleTextView;
        private TextView mDateTextView;

        // pomocnicza zmienna
        private Crime mCrime;

        // konstruktor zawsze musi miec takie parametry bo będzie wywoływany z Adaptera
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            // pompowanie itema (podobnie jak w Activity)
            // nasz widok przechowywany jest w zmiennej itemView.
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            // ustawiamy że ten widok będzie możliwy do kliknięcia.
            itemView.setOnClickListener(this);

            // ustawiamy pola naszego widoku itemu które uzupełnimy później
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);

        }

        // zawsze w klasie bind uzupełniamy dane do wyświetlenia na widgetach holdera
        // tu nie interesuje nas który dokładnie jest to element z listy (to załatwi Adapter)
        // tu musimy wiedzieć co do nas przychodzi a jest to instancja klasy Crime z której
        // wyciągniemy tytuł i datę.
        public void bind(Crime crime) {

            mCrime = crime;

            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }


        // Obsługa kliknięcia. Adapter odpowiedni obsłuży indeks podobnie jak w metodzie bind
        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " Clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }



    //Implementacja Adaptera - CrimeAdapter. Implementujemy Szablon klasy z parametrem CrimeHolder
    // Czyli z obiektem który będziemy chcieli wyświetlić na liście
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        // W konstruktorzre ustawiamy nasze źródło danych
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        //Jak stworzyć pojedynczy element na liście t.j. jak stworzyć Holdera
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        // Tutaj Adapter podłącza dane do Holdera. position to pozycja na liście.
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        // Adapter musi wiedzieć ile jest wszystkich obiektów do wyświetlenia.
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
