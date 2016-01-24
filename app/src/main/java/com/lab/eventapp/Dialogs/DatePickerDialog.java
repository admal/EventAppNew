package com.lab.eventapp.Dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.lab.eventapp.AddEventActivity;
import com.lab.eventapp.R;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Adam on 2015-11-28.
 */
public class DatePickerDialog extends DialogFragment
{
    private DatePicker datePicker;
    private boolean start = true;
    public DatePickerDialog(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        start = getArguments().getBoolean("start");

        View v = inflater.inflate(R.layout.dialog_date_picker, container);
        Button btnSave = (Button)v.findViewById(R.id.btnSave);
        Button btnCancel = (Button)v.findViewById(R.id.btnCancel);
        datePicker = (DatePicker)v.findViewById(R.id.datePicker);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventActivity activity = (AddEventActivity)getActivity();
                //here must be datePicker.getMonth()+1 because datePicker starts counting months from 0,
                // but joda date framework counts months from 1
                LocalDate date = new LocalDate(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
                activity.getDateFromDialog(date,start);
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
