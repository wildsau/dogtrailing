package de.wildsau.dogtrailing.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.wildsau.dogtrailing.R;
import de.wildsau.dogtrailing.model.SessionTag;

/**
 * Created by becker on 16.03.2015.
 */
public class TagView extends LinearLayout implements View.OnClickListener {

    private final TextView tagText;
    private final ImageView removeButton;
    private final ImageView tagLogo;

    private SessionTag value;

    public TagView(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);

        setBackgroundResource(R.drawable.rounded_background);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_tag, this, true);

        tagText = (TextView) findViewById(R.id.tag_text);
        removeButton = (ImageView) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(this);
        tagLogo = (ImageView) findViewById(R.id.tag_logo);
    }

    public SessionTag getValue() {
        return value;
    }

    public void setValue(SessionTag value) {
        this.value = value;

        tagText.setText(value.getLocalizedName(getContext()));
        tagLogo.setImageDrawable(value.getCategory().getTagCategoryLogo(getContext()));

        invalidate();
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        float density = getResources().getDisplayMetrics().density;
        Log.i("TEST", "Density: " + density + " Height in px: " + tagLogo.getHeight() + " DP: " + (int) (tagLogo.getHeight() / density + 0.5f));
    }
}
