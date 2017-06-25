/*In the name of Allah*/
package sani.ango.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable{
    @SerializedName ("name")
    @Expose
    public String recipeName;

    @SerializedName ("steps")
    @Expose
    public ArrayList<Steps> recipeSteps;

    @SerializedName ("ingredients")
    @Expose
    public List<Ingredients> recipeIngredients;

    public Recipe (String name, ArrayList<Steps> steps, ArrayList<Ingredients> ingredient) {
        recipeName = name;
        recipeSteps = steps;
        recipeIngredients = ingredient;
    }

    protected Recipe(Parcel in) {
        recipeName = in.readString();
        recipeSteps = in.createTypedArrayList(Steps.CREATOR);
        recipeIngredients = in.createTypedArrayList(Ingredients.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeName() {
        return recipeName;
    }

    public List<Ingredients> getIngredients() {
        return recipeIngredients;
    }

    public List<Steps> getSteps() {
        return recipeSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeName);
        dest.writeTypedList(recipeSteps);
        dest.writeTypedList(recipeIngredients);
    }
}
