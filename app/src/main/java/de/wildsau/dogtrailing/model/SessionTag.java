package de.wildsau.dogtrailing.model;

import android.content.Context;

import java.util.EnumSet;

import de.wildsau.dogtrailing.R;

/**
 * Created by becker on 16.03.2015.
 */
public enum SessionTag {

    START_OK(1, SessionTagCategory.START, R.string.tag_start_ok),
    START_TOO_FAST(2, SessionTagCategory.START, R.string.tag_start_too_fast),
    START_TOO_SLOW(3, SessionTagCategory.START, R.string.tag_start_too_slow),
    START_STUCK(4, SessionTagCategory.START, R.string.tag_start_stuck),
    START_UNINTERESTED(5, SessionTagCategory.START, R.string.tag_start_uninterested),

    CORNERS_OK(6, SessionTagCategory.CORNERS, R.string.tag_corners_ok),
    CORNERS_CIRCLING(7, SessionTagCategory.CORNERS, R.string.tag_corners_circling),
    CORNERS_OVERRUNNING(8, SessionTagCategory.CORNERS, R.string.tag_corners_overrunning),
    CORNERS_HIGH_NOSE(9, SessionTagCategory.CORNERS, R.string.tag_corners_high_nose),
    CORNERS_STUCK(10, SessionTagCategory.CORNERS, R.string.tag_corners_stuck),

    SEARCH_OK(11, SessionTagCategory.SEARCH, R.string.tag_search_ok),
    SEARCH_TOO_FAST(12, SessionTagCategory.SEARCH, R.string.tag_search_too_fast),
    SEARCH_TOO_SLOW(13, SessionTagCategory.SEARCH, R.string.tag_search_too_slow),
    SEARCH_EXACT_ON_TRACK(14, SessionTagCategory.SEARCH, R.string.tag_search_exact_on_ontrack),
    SEARCH_IN_THE_WIND(15, SessionTagCategory.SEARCH, R.string.tag_search_in_the_wind),
    SEARCH_BORED(16, SessionTagCategory.SEARCH, R.string.tag_search_bored),

    OVERALL_OK(17, SessionTagCategory.OVERALL, R.string.tag_overall_ok),
    OVERALL_HECTIC(18, SessionTagCategory.OVERALL, R.string.tag_overall_hectic),
    OVERALL_VARYING_SPEED(19, SessionTagCategory.OVERALL, R.string.tag_overall_varying_speed),
    OVERALL_UNCONCENTRATED(20, SessionTagCategory.OVERALL, R.string.tag_overall_unconcentrated),
    OVERALL_BORED(21, SessionTagCategory.OVERALL, R.string.tag_overall_bored),
    OVERALL_DISTRACTED(22, SessionTagCategory.OVERALL, R.string.tag_overall_distracted),
    OVERALL_BAD_SHAPE(23, SessionTagCategory.OVERALL, R.string.tag_overall_bad_shape),

    HANDLER_UNCERTAIN(24, SessionTagCategory.HANDLER, R.string.tag_handler_uncertain),
    HANDLER_CONCENTRATED(25, SessionTagCategory.HANDLER, R.string.tag_handler_concentrated),
    HANDLER_BORED(26, SessionTagCategory.HANDLER, R.string.tag_handler_bored),

    DISTRACTIONS_ACCEPTED(27, SessionTagCategory.DISTRACTIONS, R.string.tag_distractions_accepted),
    DISTRACTIONS_NOT_ACCEPTED(28, SessionTagCategory.DISTRACTIONS, R.string.tag_distractions_not_accepted),
    DISTRACTIONS_SEARCHES_BACK(29, SessionTagCategory.DISTRACTIONS, R.string.tag_distractions_searches_back),
    DISTRACTIONS_SEARCHES_SUPPORT(30, SessionTagCategory.DISTRACTIONS, R.string.tag_distractions_searches_support),
    DISTRACTIONS_STUCK(31, SessionTagCategory.DISTRACTIONS, R.string.tag_distractions_stuck);

    private final int id;
    private final SessionTagCategory category;
    private final int resIdLocalizedName;


    SessionTag(int id, SessionTagCategory category, int resIdLocalizedName) {
        this.id = id;
        this.category = category;
        this.resIdLocalizedName = resIdLocalizedName;
    }

    public SessionTagCategory getCategory() {
        return this.category;
    }

    public String getLocalizedName(Context context) {
        return context.getResources().getString(resIdLocalizedName);
    }

    private static EnumSet<SessionTag> startTags = EnumSet.of(START_OK,
            START_TOO_FAST,
            START_TOO_SLOW,
            START_STUCK,
            START_UNINTERESTED);

    private static EnumSet<SessionTag> cornersTags = EnumSet.of(CORNERS_OK,
            CORNERS_CIRCLING,
            CORNERS_OVERRUNNING,
            CORNERS_HIGH_NOSE,
            CORNERS_STUCK);

    private static EnumSet<SessionTag> searchTags = EnumSet.of(SEARCH_OK,
            SEARCH_TOO_FAST,
            SEARCH_TOO_SLOW,
            SEARCH_EXACT_ON_TRACK,
            SEARCH_IN_THE_WIND,
            SEARCH_BORED);

    private static EnumSet<SessionTag> overallTags = EnumSet.of(OVERALL_OK,
            OVERALL_HECTIC,
            OVERALL_VARYING_SPEED,
            OVERALL_UNCONCENTRATED,
            OVERALL_BORED,
            OVERALL_DISTRACTED,
            OVERALL_BAD_SHAPE);

    private static EnumSet<SessionTag> handlerTags = EnumSet.of(HANDLER_UNCERTAIN,
            HANDLER_CONCENTRATED,
            HANDLER_BORED);

}
