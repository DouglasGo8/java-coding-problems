package com.packtpub.java.coding.problems.date.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.lang.System.out;

public class App {


    @Test
    public void beganInJdk8() {

        /*
        var date1 = LocalDate.now();
        var date2 = LocalDateTime.now();
        var time1 = LocalTime.now();
        var zone1 = ZonedDateTime.now();
        var offSetTime1 = OffsetTime.now();
        var offSetDateTime = OffsetDateTime.now();
        */

        // 06 is the month, 01 is the day
        var date1 = LocalDate.parse("2020-06-01");
        out.println(date1);
        var time1 = LocalDateTime.parse("2020-06-01T11:20:15");
        out.println(time1);
        var zone1 = ZonedDateTime.parse("2020-06-01T10:15:30+09:00[Asia/Tokyo]");
        out.println(zone1);

    }

    @Test
    public void formatDate() {

        var date = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd");
        out.println(formatter.format(date));

        var localDate = LocalDate.now();
        var formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        out.println(formatter1.format(localDate));

        out.println(LocalDate.now().format(formatter1.ofPattern("yyyy-MM-dd")));
    }
}
