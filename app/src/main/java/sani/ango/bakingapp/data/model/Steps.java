/*In the name of Allah*/
package sani.ango.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {
    @SerializedName ("shortDescription")
    @Expose
    public String stepTitle;

    @SerializedName ("description")
    @Expose
    public String stepDescription;

    @SerializedName ("videoURL")
    @Expose
    public String videoURL;

    @SerializedName ("thumbnailURL")
    @Expose
    public String thumbnailURL;

    public Steps(String title, String description, String video, String thumbnail) {
        stepTitle = title;
        stepDescription = description;
        videoURL = video;
        thumbnailURL = thumbnail;
    }

    protected Steps(Parcel in) {
        stepTitle = in.readString();
        stepDescription = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stepTitle);
        dest.writeString(stepDescription);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
