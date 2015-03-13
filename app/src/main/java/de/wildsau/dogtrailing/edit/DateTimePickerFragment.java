package de.wildsau.dogtrailing.edit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import de.wildsau.dogtrailing.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link de.wildsau.dogtrailing.edit.DateTimePickerFragment.OnDateTimeChangedListener} interface
 * to handle interaction events.
 * Use the {@link DateTimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateTimePickerFragment extends DialogFragment implements TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {

    private static final String ARG_DATE_TIME = "DATE_TIME";

    private OnDateTimeChangedListener onDateTimeChangedListener;

    private TimePicker timePicker;
    private DatePicker datePicker;

    private java.text.DateFormat timeFormat;
    private java.text.DateFormat dateFormat;


    private Calendar calendar = Calendar.getInstance();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static DateTimePickerFragment newInstance(Date dateTime) {
        DateTimePickerFragment fragment = new DateTimePickerFragment();
        Bundle args = new Bundle();

        args.putLong(ARG_DATE_TIME, dateTime.getTime());

        fragment.setArguments(args);
        return fragment;
    }

    public DateTimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeFormat = DateFormat.getTimeFormat(getActivity());
        dateFormat = DateFormat.getDateFormat(getActivity());

        if (savedInstanceState != null) {
            // If no date was saved, take current time.
            Log.i(getTag(), "Restored Instance state!");
            calendar.setTimeInMillis(savedInstanceState.getLong(ARG_DATE_TIME, System.currentTimeMillis()));
        } else if (getArguments() != null) {
            // for initial setup take passed dateTime.
            calendar.setTimeInMillis(getArguments().getLong(ARG_DATE_TIME, System.currentTimeMillis()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(getTag(), "Stored Instance state!");
        outState.putLong(ARG_DATE_TIME, calendar.getTimeInMillis());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_time_picker, null);

        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        timePicker.setOnTimeChangedListener(this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                this);


        builder.setView(view)
                .setTitle(getDateTimeString())
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        if (onDateTimeChangedListener != null) {
                            onDateTimeChangedListener.onDateTimeChanged(DateTimePickerFragment.this, calendar.getTime());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nadas!
                    }
                });
        return builder.create();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        updateUI();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        updateUI();
    }

    private void updateUI() {
        String text = getDateTimeString();

        getDialog().setTitle(text);
    }

    private String getDateTimeString() {
        return dateFormat.format(calendar.getTime()) + " - " + timeFormat.format(calendar.getTime());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onDateTimeChangedListener = (OnDateTimeChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDateTimeChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDateTimeChangedListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnDateTimeChangedListener {
        public void onDateTimeChanged(DateTimePickerFragment dateTimePickerFragment, Date dateTime);
    }

}
