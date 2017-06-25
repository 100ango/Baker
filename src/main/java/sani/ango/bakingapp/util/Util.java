/*In the name of Allah*/
package sani.ango.bakingapp.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sani.ango.bakingapp.R;

public class Util {
    public static String getJSonURL(Context context){
        return context.getString(R.string.apiURL);
    }

    public static List<Integer> getAllRecipeImages(Context context){
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.nutella_pie);
        list.add(R.drawable.brownies);
        list.add(R.drawable.yellow_cake);
        list.add(R.drawable.cheese_cake);

        return list;
    }
}
