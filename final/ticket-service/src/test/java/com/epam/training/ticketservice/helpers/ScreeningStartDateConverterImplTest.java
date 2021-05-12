package com.epam.training.ticketservice.helpers;

import com.epam.training.ticketservice.presentation.cli.helpers.impl.ScreeningStartDateConverterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreeningStartDateConverterImplTest {

    private ScreeningStartDateConverterImpl underTest;

    @BeforeEach
    public void init() {
        underTest = new ScreeningStartDateConverterImpl();
    }

    @Test
    public void testConvertStringDateToDateShouldReturnWithCorrectDate() throws ParseException {

        // Given
        String dateInStringFormat = "2021-11-20 16:00";
        Date expected = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateInStringFormat);

        //When
        Date actual = underTest.convertStringDateToDate(dateInStringFormat);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testConvertDateToStringDateShouldReturnWithCorrectDateInStringFormat() throws ParseException {
        // Given
        String expected = "2021-11-20 16:00";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(expected);

        // When
        String actual = underTest.convertDateToStringDate(date);

        //Then
        Assertions.assertEquals(expected, actual);
    }
}
