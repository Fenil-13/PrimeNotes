package com.fenil.primenote.utils;

import com.fenil.primenote.models.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleDateProvider {
    private static final String SAMPLE_TEXT_1="A Simple Note";
    private static final String SAMPLE_TEXT_2="Good Morning";
    private static final String SAMPLE_TEXT_3="Have a nice Day";

    private static Date getDate(int diffAmount){
        GregorianCalendar calendar=new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND,diffAmount);
        return calendar.getTime();
    }

    public static List<Note> getSampleData(){
        List<Note> noteList=new ArrayList<>();
        noteList.add(new Note(getDate(0),SAMPLE_TEXT_1));
        noteList.add(new Note(getDate(-1),SAMPLE_TEXT_2));
        noteList.add(new Note(getDate(-2),SAMPLE_TEXT_3));
        return  noteList;
    }
}
