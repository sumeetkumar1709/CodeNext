package com.segmnf.myapplication.Utils;

import com.segmnf.myapplication.Adapter.LeaderboardAdpater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaderboardModel implements Comparable<LeaderboardModel> {
    String userid;
    String score;
    String timetaken;

    public LeaderboardModel(String userid, String score, String timetaken) {
        this.userid = userid;
        this.score = score;
        this.timetaken = timetaken;
    }

    public LeaderboardModel() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTimetaken() {
        return timetaken;
    }

    public void setTimetaken(String timetaken) {
        this.timetaken = timetaken;
    }


    /*
	public compareTo(Racer racer)
	if returned value equals -1 object represented by this is smaller
	if returned value equals 1 parameter object (racer) is smaller
*/

    @Override
    public int compareTo(LeaderboardModel o) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        long thisdate = 0;
        long objectdate = 0;
        try {

            thisdate = format.parse(this.timetaken).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            objectdate = format.parse(o.timetaken).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(this.getScore())>Integer.parseInt(o.getScore()) ||
                Integer.parseInt(this.getScore())==Integer.parseInt(o.getScore()) &&
                        thisdate< objectdate)
            return -1;   // current object has better score

        else
        if (Integer.parseInt(this.getScore())==Integer.parseInt(o.getScore()) &&
                thisdate== objectdate) return 0;
        else return 1;
    }
}
