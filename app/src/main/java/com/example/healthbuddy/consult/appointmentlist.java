package com.example.healthbuddy.consult;

public class appointmentlist {

    private appointmentlist() {

    }
    private String DoctorName;
    private String DoctorPhone;
    private String Time;

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getDoctorPhone() {
        return DoctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        DoctorPhone = doctorPhone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMeetingID() {
        return MeetingID;
    }

    public void setMeetingID(String meetingID) {
        MeetingID = meetingID;
    }

    public appointmentlist(String doctorName, String doctorPhone, String time, String meetingID) {
        DoctorName = doctorName;
        DoctorPhone = doctorPhone;
        Time = time;
        MeetingID = meetingID;
    }

    private String MeetingID;
}