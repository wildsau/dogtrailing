package de.wildsau.dogtrailing.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import de.wildsau.dogtrailing.R;

/**
 * Created by becker on 19.03.2015.
 */
public enum SessionTagCategory {

    START(R.string.tag_category_start, R.drawable.ic_tag_start),
    CORNERS(R.string.tag_category_corners, R.drawable.ic_tag_corners),
    DISTRACTIONS(R.string.tag_category_distractions, R.drawable.ic_tag_distractions),
    SEARCH(R.string.tag_category_search, R.drawable.ic_tag_search),
    OVERALL(R.string.tag_category_overall, R.drawable.ic_tag_overall),
    HANDLER(R.string.tag_category_handler, R.drawable.ic_tag_handler);

    private final int resIdLocalizedName;
    private final int resIdLogoDrawable;

    SessionTagCategory(int resIdLocalizedName, int resIdLogoDrawable) {
        this.resIdLocalizedName = resIdLocalizedName;
        this.resIdLogoDrawable = resIdLogoDrawable;
    }

    public String getLocalizedName(Context context) {
        return context.getResources().getString(resIdLocalizedName);
    }

    public Drawable getTagCategoryLogo(Context context) {
        return context.getResources().getDrawable(resIdLogoDrawable);
    }

}
