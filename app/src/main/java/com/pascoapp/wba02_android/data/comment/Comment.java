package com.pascoapp.wba02_android.data.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("parent")
    @Expose
    public Object parent;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("file")
    @Expose
    public Object file;
    @SerializedName("file_url")
    @Expose
    public Object fileUrl;
    @SerializedName("file_mime_type")
    @Expose
    public Object fileMimeType;
    @SerializedName("fullname")
    @Expose
    public String fullname;
    @SerializedName("profile_url")
    @Expose
    public String profileUrl;
    @SerializedName("profile_picture_url")
    @Expose
    public String profilePictureUrl;
    @SerializedName("created_by_admin")
    @Expose
    public Boolean createdByAdmin;
    @SerializedName("created_by_current_user")
    @Expose
    public Boolean createdByCurrentUser;
    @SerializedName("upvote_count")
    @Expose
    public Integer upvoteCount;
    @SerializedName("user_has_upvoted")
    @Expose
    public Boolean userHasUpvoted;
    public final static Parcelable.Creator<Comment> CREATOR = new Creator<Comment>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Comment createFromParcel(Parcel in) {
            Comment instance = new Comment();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.parent = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.created = ((String) in.readValue((String.class.getClassLoader())));
            instance.modified = ((String) in.readValue((String.class.getClassLoader())));
            instance.content = ((String) in.readValue((String.class.getClassLoader())));
            instance.file = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.fileUrl = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.fileMimeType = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.fullname = ((String) in.readValue((String.class.getClassLoader())));
            instance.profileUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.profilePictureUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdByAdmin = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.createdByCurrentUser = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.upvoteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.userHasUpvoted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            return instance;
        }

        public Comment[] newArray(int size) {
            return (new Comment[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(parent);
        dest.writeValue(created);
        dest.writeValue(modified);
        dest.writeValue(content);
        dest.writeValue(file);
        dest.writeValue(fileUrl);
        dest.writeValue(fileMimeType);
        dest.writeValue(fullname);
        dest.writeValue(profileUrl);
        dest.writeValue(profilePictureUrl);
        dest.writeValue(createdByAdmin);
        dest.writeValue(createdByCurrentUser);
        dest.writeValue(upvoteCount);
        dest.writeValue(userHasUpvoted);
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", parent=" + parent +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                ", fileUrl=" + fileUrl +
                ", fileMimeType=" + fileMimeType +
                ", fullname='" + fullname + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", createdByAdmin=" + createdByAdmin +
                ", createdByCurrentUser=" + createdByCurrentUser +
                ", upvoteCount=" + upvoteCount +
                ", userHasUpvoted=" + userHasUpvoted +
                '}';
    }
}