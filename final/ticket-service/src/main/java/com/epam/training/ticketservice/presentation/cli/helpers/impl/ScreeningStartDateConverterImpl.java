package com.epam.training.ticketservice.presentation.cli.helpers.impl;

import com.epam.training.ticketservice.presentation.cli.helpers.ScreeningStartDateConverter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScreeningStartDateConverterImpl implements ScreeningStartDateConverter {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Date convertStringDateToDate(String dateInStringFormat) throws ParseException {
        dateFormat.setLenient(false);
        return dateFormat.parse(dateInStringFormat);
    }

    @Override
    public String convertDateToStringDate(Date date) {
        return dateFormat.format(date);
    }
}
