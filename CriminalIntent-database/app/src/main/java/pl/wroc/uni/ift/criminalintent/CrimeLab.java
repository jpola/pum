package pl.wroc.uni.ift.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pl.wroc.uni.ift.criminalintent.database.CrimeBaseHelper;
import pl.wroc.uni.ift.criminalintent.database.CrimeCursorWrapper;
import pl.wroc.uni.ift.criminalintent.database.CrimeDbSchema;
import pl.wroc.uni.ift.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by jpola on 07.08.17.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    // use an interface for declaring variables
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // Context argument will be used later for databases
    private CrimeLab(Context context) {
        // and here use an implementation when instantiating
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        //all crimes
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            // Android has limited number of handlers for open files
            // if you don't close it it will crash your app eventually;
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ? ",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime) {
        Log.d("CrimeLab", "updateCrime isSolved: " +  crime.isSolved());


        String uuidString = crime.getId().toString();

        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ? ",
                new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    public void addCrime(Crime c) {

        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
        /*
            second null parameter meaning;

            Sometimes you want to insert an empty row, in that case ContentValues have no content
            value, and you should use nullColumnHack. For example, you want to insert an empty
            row into a table student(id, name), which id is auto generated and name is null.

            You could invoke like this:
            <code>
                ContentValues cv = new ContentValues();
                db.insert("student", "name", cv);
            <\code>
         */
    }
}
