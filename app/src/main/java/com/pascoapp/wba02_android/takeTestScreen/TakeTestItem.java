package com.pascoapp.wba02_android.takeTestScreen;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TakeTestItem implements Parcelable {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("profile_picture_url")
    @Expose
    public String profilePictureUrl;
    @SerializedName("quiz_contents")
    @Expose
    public List<TestContent> testContents = null;
    public final static Parcelable.Creator<TakeTestItem> CREATOR = new Creator<TakeTestItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TakeTestItem createFromParcel(Parcel in) {
            TakeTestItem instance = new TakeTestItem();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.profilePictureUrl = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.testContents, (TestContent.class.getClassLoader()));
            return instance;
        }

        public TakeTestItem[] newArray(int size) {
            return (new TakeTestItem[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(profilePictureUrl);
        dest.writeList(testContents);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "TakeTestItem{" +
                "title='" + title + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", testContents=" + testContents +
                '}';
    }
}