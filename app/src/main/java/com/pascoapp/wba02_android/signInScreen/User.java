package com.pascoapp.wba02_android.signInScreen;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("profile_url")
    @Expose
    public String profileUrl;
    @SerializedName("auth_token")
    @Expose
    public String authToken;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            User instance = new User();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.displayName = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
            instance.profileUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.authToken = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(displayName);
        dest.writeValue(email);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(profileUrl);
        dest.writeValue(authToken);
    }

    public int describeContents() {
        return 0;
    }

}