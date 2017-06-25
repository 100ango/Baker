/*In the name of Allah*/
package sani.ango.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredients implements Parcelable {
    @SerializedName ("ingredient")
    @Expose
    public String ingredient;

    @SerializedName ("quantity")
    @Expose
    public String quantity;

    @SerializedName ("measure")
    @Expose
    public String measureType;

    public Ingredients(String ingre, String qty, String type) {
        ingredient = ingre;
        quantity = qty;
        measureType = type;
    }

    protected Ingredients(Parcel in) {
        ingredient = in.readString();
        quantity = in.readString();
        measureType = in.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasureType() {
        return measureType;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeString(quantity);
        dest.writeString(measureType);
    }
}
