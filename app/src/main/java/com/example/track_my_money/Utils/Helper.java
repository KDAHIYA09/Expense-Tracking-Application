package com.example.track_my_money.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String formatdate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        return dateFormat.format(date);
    }

    public static String formatdateByMonth(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyyy");
        return dateFormat.format(date);
    }



}

// static kkeyword ki help se hmare ye class pure project m hm use krsakte h it is shared among all instances of classes
