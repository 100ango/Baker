/*In the name of Allah*/
package sani.ango.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sani.ango.bakingapp.R;
import sani.ango.bakingapp.data.model.Ingredients;

public class IngredientFragment extends Fragment {
    @Bind(R.id.recipe_ingredients)
    TextView ingredientList;

    private String list = "";

    public IngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_list, container, false);
        ButterKnife.bind(this, view);
        ingredientList.setText(list);
        return view;
    }

    public void setIngredient(List<Ingredients> ingredients){
       for(Ingredients ingredient : ingredients){
           list += ">" + ingredient.getIngredient();
           list += "\t" + ingredient.getQuantity();
           list += "\t" + ingredient.getMeasureType() + "\n";
       }
    }

    public String getIngredientList(){
        return list;
    }
}
