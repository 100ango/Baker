/*In the name of Allah*/
package sani.ango.bakingapp.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sani.ango.bakingapp.R;
import sani.ango.bakingapp.data.model.Steps;



public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MasterViewHolder> {

    private List<Steps> steps;
    private ClickListener mClickListener;
    Context context;

    public interface ClickListener{
        void onClick(int index);
    }
    public MasterAdapter(List<Steps> list, ClickListener listener) {
        steps = list;
        mClickListener = listener;
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.master_list_item, parent, false);
        MasterViewHolder holder = new MasterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MasterViewHolder holder, int position) {
        holder.stepTextView.setText(steps.get(position).getStepTitle());
        if (!(steps.get(position).getThumbnailURL().equals(""))){
            Picasso.with(context).load(steps.get(position)
                    .getThumbnailURL()).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class MasterViewHolder extends ViewHolder implements View.OnClickListener{
        @Bind(R.id.stepTextView)
        TextView stepTextView;

        @Bind(R.id.image_thumbnail)
        ImageView thumbnail;

        @Bind(R.id.stepListLayout)
        LinearLayout layout;
        public MasterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            mClickListener.onClick(index);
            //layout.setBackgroundColor(
                //    context.getResources().getColor(R.color.colorPrimary));
        }
    }
}
