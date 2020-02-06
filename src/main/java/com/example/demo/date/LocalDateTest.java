package com.example.demo.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class LocalDateTest {
    @Test
    public void localDateTest(){
        //获取当前年月日
        LocalDate localDate = LocalDate.now();
        //构造指定的年月日
        LocalDate localDate1 = LocalDate.of(2019,9,11);
        int year = localDate.getYear();
        int year1 = localDate.get(ChronoField.YEAR);
        Month month = localDate.getMonth();
        int month1 = localDate.get(ChronoField.MONTH_OF_YEAR);
        int day = localDate.getDayOfMonth();
        int day1 = localDate.get(ChronoField.DAY_OF_MONTH);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK);
        System.out.println(year+" "+year1+" "+month+" "+month1+" "+day+" "+ day1);
    }

    @Test
    public void localTimeTest(){
        LocalTime localTime = LocalTime.now();
        LocalTime localTime1 = LocalTime.of(13,51,10);
        int hour = localTime.getHour();
        int hour1 = localTime.get(ChronoField.HOUR_OF_DAY);
        int minute = localTime.getMinute();
        int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR);
        int second  = localTime.getSecond();
        int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE);
        System.out.println(hour + " " + hour1 + " " + minute + " " + minute1 + " " + second + " " + second1);
    }

    @Test
    public void localDateTimeTest(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019,Month.SEPTEMBER, 10, 14, 46, 56);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate,localTime);
        LocalDateTime localDateTime3 = localDate.atTime(localTime);
        LocalDateTime localDateTime4 = localTime.atDate(localDate);
        System.out.println(localDateTime);
        System.out.println(localDateTime1);
        System.out.println(localDateTime2);
        System.out.println(localDateTime3);
        System.out.println(localDateTime4);
    }

    @Test
    public void instantTest(){
        Instant instant = Instant.now();
        long currentSecond = instant.getEpochSecond();
        System.out.println(currentSecond);
        long currentMilli = instant.toEpochMilli();
        System.out.println(currentMilli);
        System.out.println(System.currentTimeMillis());
        //个人觉得如果只是为了获取秒数或者毫秒数，使用System.currentTimeMillis()来得更方便
    }

    /**
     * LocalDate、LocalTime、LocalDateTime、Instant为不可变对象，修改这些对象会返回一个副本
     */
    @Test
    public void localUpdateTest(){
        LocalDateTime localDateTime = LocalDateTime.of(2019,Month.SEPTEMBER, 10, 14, 46,56);
        System.out.println(localDateTime);
        //增加一年
        localDateTime = localDateTime.plusYears(1);
        System.out.println(localDateTime);
        localDateTime = localDateTime.plus(1, ChronoUnit.YEARS);
        System.out.println(localDateTime);
        //减少一个月
        localDateTime = localDateTime.minusMonths(1);
        System.out.println(localDateTime);
        localDateTime = localDateTime.minus(1,ChronoUnit.MONTHS);
        System.out.println(localDateTime);
        //修改年为2019
        localDateTime = localDateTime.withYear(2020);
        System.out.println(localDateTime);
        //修改年为2022
        localDateTime = localDateTime.with(ChronoField.YEAR, 2022);
        System.out.println(localDateTime);
        //比如有些时候想知道这个月的最后一天是几号、下个周末是几号，通过提供的时间和日期API可以很快得到答案
        LocalDate localDate = LocalDate.now();
        //LocalDate localDate1 = localDate.with(firstDayOfYear());
    }

    /**
     * DateTimeFormatter默认提供了多种格式化方式，如果默认提供的不能满足要求，可通过DateTimeFormatter的ofPattern方法创建自定义格式化方法
     */
    @Test
    public void localFormatTest(){
        LocalDate localDate = LocalDate.of(2019,9,10);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(s1);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s2);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = localDate.format(dateTimeFormatter);
        System.out.println(s3);
    }

    @Test
    public void localAnalyseTest(){
        LocalDate localDate = LocalDate.parse("20190910",DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate localDate1 = LocalDate.parse("2019-09-10", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(localDate);
        System.out.println(localDate1);
    }
}
