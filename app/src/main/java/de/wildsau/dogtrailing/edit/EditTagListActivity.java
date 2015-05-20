package de.wildsau.dogtrailing.edit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import java.util.EnumSet;
import java.util.Locale;

import de.wildsau.dogtrailing.R;
import de.wildsau.dogtrailing.model.SessionTag;
import de.wildsau.dogtrailing.model.SessionTagCategory;

public class EditTagListActivity extends ActionBarActivity implements TagListFragment.OnSelectionChangedListener {


    public static final String EXTRA_SESSION_TAGS = "de.wildsau.dogtrailing.SELECTED_SESSION_TAGS";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    private EnumSet<SessionTag> selectedSessionTags;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag_list);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            selectedSessionTags = (EnumSet<SessionTag>) intent.getSerializableExtra(EXTRA_SESSION_TAGS);
        } else {
            selectedSessionTags = (EnumSet<SessionTag>) savedInstanceState.getSerializable(EXTRA_SESSION_TAGS);
        }

        getSupportActionBar().hide();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onSessionTagClicked(SessionTag tag, boolean checked) {
        if (checked) {
            selectedSessionTags.add(tag);
        } else {
            selectedSessionTags.remove(tag);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(EXTRA_SESSION_TAGS, selectedSessionTags);
    }

    public void okButtonClicked(View view) {
        Toast.makeText(this, selectedSessionTags.toString(), Toast.LENGTH_LONG).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return TagListFragment.newInstance(selectedSessionTags, SessionTagCategory.values()[position]);
        }

        @Override
        public int getCount() {
            return SessionTagCategory.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            SessionTagCategory cat = SessionTagCategory.values()[position];

            SpannableStringBuilder sb = new SpannableStringBuilder("  "); // space added before text for convenience

            Drawable drawable = cat.getTagCategoryLogo(getApplicationContext());
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.append("\n");
            sb.append(cat.getLocalizedName(getApplicationContext()));

            return sb;
        }
    }
}
