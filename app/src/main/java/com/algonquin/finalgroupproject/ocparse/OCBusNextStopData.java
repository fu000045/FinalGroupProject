package com.algonquin.finalgroupproject.ocparse;


public class OCBusNextStopData {


    // ** Next Stop
    public String tripDestination, tripStartTime, adjustedScheduleTime, latitude, longitude, gpsSpeed;


    // ** Next Stop Info
    public String getTripDestination() {
        return tripDestination;
    }
    public void setTripDestination(String tripDestination) { this.tripDestination = tripDestination;}

    public String getTripStartTime() {
        return tripStartTime;
    }
    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public String getAdjustedScheduleTime() { return adjustedScheduleTime;}
    public void setAdjustedScheduleTime(String adjustedScheduleTime) {
        this.adjustedScheduleTime = adjustedScheduleTime;}

    public String getLatitude() {
        return latitude ;
    }
    public void setLatitude(String latitude) { this.latitude = latitude;}

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) { this.longitude = longitude;}

    public String getGpsSpeed() {
        return gpsSpeed;
    }
    public void setGpsSpeed(String gpsSpeed) { this.gpsSpeed = gpsSpeed;}


}

