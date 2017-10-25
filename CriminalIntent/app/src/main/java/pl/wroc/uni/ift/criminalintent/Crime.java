package pl.wroc.uni.ift.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jpola on 04.08.17.
 */

// POD - plain old data object.
// prosta klasa przechowujaca dane o przestępstwie.
public class Crime {

    //immutable universally unique identifier (UUID)
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    // simple constructor. It will generate random ID for a crime
    // and assign today's date to mDate field.
    // UUID will guarantee that id will not be repeated for a very long time.
    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
