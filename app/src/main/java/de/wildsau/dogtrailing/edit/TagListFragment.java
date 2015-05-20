package de.wildsau.dogtrailing.edit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;

import de.wildsau.dogtrailing.R;
import de.wildsau.dogtrailing.model.SessionTag;
import de.wildsau.dogtrailing.model.SessionTagCategory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link de.wildsau.dogtrailing.edit.TagListFragment.OnSelectionChangedListener} interface
 * to handle interaction events.
 * Use the {@link TagListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TagListFragment extends Fragment {

    private static final String ARG_SELECTED_SESSION_TAGS = "selected_sessions_tags";
    private static final String ARG_SESSION_TAG_CATEGORY = "session_tag_category";

    private EnumSet<SessionTag> selectedSessionTags;
    private SessionTagCategory category;

    private SessionTagAdapter dataAdapter;

    private OnSelectionChangedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedSessionTags Parameter 1.
     * @param category            Parameter 2.
     * @return A new instance of fragment TagListFragment.
     */
    public static TagListFragment newInstance(EnumSet<SessionTag> selectedSessionTags, SessionTagCategory category) {
        TagListFragment fragment = new TagListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SELECTED_SESSION_TAGS, selectedSessionTags);
        args.putSerializable(ARG_SESSION_TAG_CATEGORY, category);

        fragment.setArguments(args);
        return fragment;
    }

    public TagListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedSessionTags = (EnumSet<SessionTag>) getArguments().getSerializable(ARG_SELECTED_SESSION_TAGS);
            category = (SessionTagCategory) getArguments().getSerializable(ARG_SESSION_TAG_CATEGORY);
        }
        //TODO: Handle no args set!
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tag_list, container, false);


        ArrayList<SessionTag> tagList = new ArrayList<SessionTag>();

        for (SessionTag i : SessionTag.values()) {
            if (i.getCategory().equals(category)) {
                tagList.add(i);
            }
        }

        dataAdapter = new SessionTagAdapter(this.getActivity(), R.layout.tag_info, tagList);

        final ListView listView = (ListView) rootView.findViewById(R.id.select_tag_list);

        listView.setAdapter(dataAdapter);

        for (SessionTag i : selectedSessionTags) {
            int position = dataAdapter.getPosition(i);
            if (position >= 0) {
                listView.setItemChecked(position, true);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean checked = listView.isItemChecked(position);
                SessionTag tag = dataAdapter.getItem(position);

                mListener.onSessionTagClicked(tag, checked);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSelectionChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSelectionChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSelectionChangedListener {

        void onSessionTagClicked(SessionTag tag, boolean checked);

    }

    private class SessionTagAdapter extends ArrayAdapter<SessionTag> {

        private ArrayList<SessionTag> tagList = new ArrayList<SessionTag>();

        public SessionTagAdapter(Context context, int textViewResourceId,
                                 ArrayList<SessionTag> tagList) {
            super(context, textViewResourceId, tagList);

            this.tagList.addAll(tagList);
        }

        private class ViewHolder {
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("TagView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.tag_info, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tag_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            SessionTag tag = tagList.get(position);

            holder.name.setText(tag.getLocalizedName(this.getContext()));

            return convertView;

        }
    }
}
