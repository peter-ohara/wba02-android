package com.pascoapp.wba02_android.takeTestScreen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pascoapp.wba02_android.takeTestScreen.testContentScreens.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pascoapp.wba02_android.takeTestScreen.adapter.QuestionsPagerAdapter.TEST_END;


public class TestContent implements Parcelable {
    @SerializedName("id")
    @Expose
    public Integer id = -1;
    @SerializedName("priority")
    @Expose
    public Integer priority = 0;
    @SerializedName("type")
    @Expose
    public String type = "essay_type";
    @SerializedName("title")
    @Expose
    public String title = "";
    @SerializedName("content")
    @Expose
    public String content = "";
    @SerializedName("choices")
    @Expose
    public List<String> choices = new ArrayList<>();
    @SerializedName("comments")
    @Expose
    public List<Comment> comments = new ArrayList<>();
    @SerializedName("answers")
    @Expose
    public List<Object> answers = new ArrayList<>();
    @SerializedName("answer_histogram")
    @Expose
    public Map<String, Integer> answerHistogram = new HashMap<>();


    public final static Parcelable.Creator<TestContent> CREATOR = new Creator<TestContent>() {
        @SuppressWarnings({
                "unchecked"
        })
        public TestContent createFromParcel(Parcel in) {
            TestContent instance = new TestContent();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.priority = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.content = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.choices, (java.lang.String.class.getClassLoader()));
            in.readList(instance.comments, (Comment.class.getClassLoader()));
            in.readList(instance.answers, (java.lang.Object.class.getClassLoader()));
            in.readMap(instance.answerHistogram, (java.lang.Object.class.getClassLoader()));
            return instance;
        }

        public TestContent[] newArray(int size) {
            return (new TestContent[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(priority);
        dest.writeValue(type);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeList(choices);
        dest.writeList(comments);
        dest.writeList(answers);
        dest.writeMap(answerHistogram);
    }

    public int describeContents() {
        return 0;
    }

    public static TestContent createEndPage() {
        TestContent endPageData = new TestContent();
        endPageData.title = "END";
        endPageData.content = "";
        endPageData.type = TEST_END;
        endPageData.comments = new ArrayList<>();
        return endPageData;
    }


    @Override
    public String toString() {
        return "TestContent{" +
                "id=" + id +
                ", priority=" + priority +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                ", comments=" + comments +
                ", answers=" + answers +
                ", answerHistogram=" + answerHistogram +
                '}';
    }
}