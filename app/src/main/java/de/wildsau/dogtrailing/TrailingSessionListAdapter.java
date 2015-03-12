package de.wildsau.dogtrailing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.wildsau.dogtrailing.entities.DaoSession;
import de.wildsau.dogtrailing.entities.TrailingSession;
import de.wildsau.dogtrailing.entities.TrailingSessionDao;

/**
 * Created by becker on 12.02.2015.
 */
public class TrailingSessionListAdapter extends RecyclerView.Adapter<TrailingSessionListAdapter.ViewHolder> {


    private List<TrailingSession> items;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TrailingSessionListAdapter(DaoSession session) {
        QueryBuilder<TrailingSession> qb = session.getTrailingSessionDao().queryBuilder();

        qb.orderDesc(TrailingSessionDao.Properties.Created);

        items = qb.listLazy();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TrailingSessionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_list_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView myText = (TextView) holder.view.findViewById(R.id.card_view).findViewById(R.id.info_text);
        myText.setText(items.get(position).getTitle());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }
}

