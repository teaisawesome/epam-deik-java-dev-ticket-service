package com.epam.training.ticketservice.presentation.cli.helpers;

import java.text.ParseException;
import java.util.Date;

public interface ScreeningStartDateConverter {
    Date convertStringDateToDate(String dateInStringFormat) throws ParseException;

    String convertDateToStringDate(Date date);
}
