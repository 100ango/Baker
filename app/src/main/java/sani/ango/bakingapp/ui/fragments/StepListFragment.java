/*In the name of Allah*/
package sani.ango.bakingapp.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sani.ango.bakingapp.R;

public class StepListFragment extends Fragment{
    @Bind(R.id.step_text_view)
    TextView stepTextView;

    private String step;

    public StepListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_list_item, container, false);
        ButterKnife.bind(this, rootView);
        setFontStyle(stepTextView);
        stepTextView.setText(step);
        return rootView;
    }

    public void setStepTextView(String step){
        this.step = step;
    }

    void setFontStyle(TextView text){
        Typeface typeface = Typeface.createFromAsset(
                getActivity().getAssets(), "fonts/Raleway-ThinItalic.ttf");
        text.setTypeface(typeface);
    }
}
