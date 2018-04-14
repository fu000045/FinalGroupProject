package com.algonquin.finalgroupproject.ocparse;


public class OCBusNumberData {


    // ** Route Summary For Stop
    public String routeNo, direction, routeHeading, stopDescription;

    public String name;
    public String id;

    // ** Route Summary For Stop
    public String getRouteNo() {
        return routeNo;
    }
    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRouteHeading() {
        return routeHeading;
    }
    public void setRouteHeading(String routeHeading) {
        this.routeHeading = routeHeading;
    }

    public String getStopDescription() {
        return stopDescription;
    }

    public void setStopDescription(String stopDescription) { this.stopDescription = stopDescription; }


}

