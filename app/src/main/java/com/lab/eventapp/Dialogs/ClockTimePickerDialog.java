package com.lab.eventapp.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.lab.eventapp.AddEventActivity;
import com.lab.eventapp.R;

import org.joda.time.LocalTime;


/**
 * Created by Adam on 2015-11-28.
 */
public class ClockTimePickerDialog extends DialogFragment
{
    private TimePicker clockPicker;
    private boolean start;


    public ClockTimePickerDialog()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        start = getArguments().getBoolean("start");

        View v = inflater.inflate(R.layout.dialog_clock_picker, container);
        Button btnSave = (Button)v.findViewById(R.id.btnSave);
        Button btnCancel = (Button)v.findViewById(R.id.btnCancel);

        clockPicker = (TimePicker)v.findViewById(R.id.timePicker);
        clockPicker.setIs24HourView(true);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventActivity activity = (AddEventActivity) getActivity();
               //int hours = clockPicker.getCurrentHour();
                int minutes = clockPicker.getCurrentMinute();
                 int hours = clockPicker.getCurrentHour();
                LocalTime time = new LocalTime(hours,minutes);
                activity.getTimeFromDialog(time, start);

                getDialog().dismiss();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return v;
    }
}
