/*In the name of Allah*/
package sani.ango.bakingapp.util.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sani.ango.bakingapp.R;
import sani.ango.bakingapp.data.model.Recipe;
import sani.ango.bakingapp.util.Util;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MasterViewHolder>{
    List<Recipe> data;
    public MainAdapter(List<Recipe> data, ClickListener listener) {
        this.data = data;
        mClickListener = listener;
    }
    List<Integer> imageList;
    Context context;
    private ClickListener mClickListener;

    public interface ClickListener{
        void onClick(int index);
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        imageList = Util.getAllRecipeImages(context);
        View view = inflater.inflate(R.layout.main_list_item, viewGroup, false);
        MasterViewHolder viewHolder = new MasterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MasterViewHolder masterViewHolder, int i) {
        if (data != null && data.size() > 0)
        masterViewHolder.tv.setText(data.get(i).getRecipeName());
        if (!(data.get(i).getImageUrl().equals(""))){
            Picasso.with(context).load(data.get(i).getImageUrl())
                    .into(masterViewHolder.im);
        }
        else {
            masterViewHolder.im.setImageResource(imageList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MasterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        @Bind(R.id.recipe_name)
        TextView tv;

        @Bind(R.id.imageView)
        ImageView im;
        public MasterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            setFontStyle(tv);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            mClickListener.onClick(index);
        }
    }

    void setFontStyle(TextView text){
        Typeface typeface = Typeface.createFromAsset(
                context.getAssets(), "fonts/Raleway-ThinItalic.ttf");
        text.setTypeface(typeface);
    }

    public void swap(List<Recipe> data){
        this.data = data;
        this.notifyDataSetChanged();
    }
}
