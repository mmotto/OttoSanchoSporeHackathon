package com.example.frightmare.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class DistanceFromWaypoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_from_waypoints);
        ArrayList<Waypoint> points = new ArrayList<Waypoint>();
        int lastPointID = 0;
        boolean runFinished = false;
        double pX = 4; //Position of runner
        double pY = 3; //Position of runner
        Waypoint start = new Waypoint(0, 0);
        Waypoint finish = new Waypoint(0,5);
        boolean inputting = true; //User is inputting route. False when done

        while(inputting) { //TODO: This is for inputting waypoints. It does not work, and is not designed to.
            boolean tapped = false; //Wait for user to select a waypoint
            while(!tapped) {

                tapped = true; //Change so that once a user selects a point, the loop is exited
            }
        }


        System.out.println(getDistanceFromWaypoints(start, finish, pX, pY)); //CALCULATE DISTANCE
    }

    protected double getDistanceFromWaypoints(Waypoint WL, Waypoint WN, double x, double y) { //Get distance from runner to line between 2 waypoints.
        return Math.abs((WN.getY() - WL.getY()) * x - (WN.getX() - WL.getX()) * y + WN.getX() * WL.getY() - WN.getY() * WL.getX()) /
                Math.sqrt(Math.pow(WN.getY() - WL.getY(), 2) + Math.pow(WN.getX() - WL.getX(), 2));
    }

    private class Waypoint {
        private double xCoord, yCoord;
        public Waypoint(double x, double y) {
            xCoord = x;
            yCoord = y;
        }

        public double getX() {
            return xCoord;
        }

        public double getY() {
            return yCoord;
        }

        public double getDistance(Waypoint other) {
            return Math.sqrt((xCoord - other.getX())*(xCoord - other.getX()) + (yCoord-other.getY()) + (yCoord - other.getY()));
        }

        public double getDistance(double xPos, double yPos) {
            return Math.sqrt(Math.pow((xCoord - xPos), 2) + Math.pow((yCoord - yPos), 2));
        }
    }

    private class Runner {
        private double x;
        private double y;
        private double v; //Speed in meters per second
        private long lastUpdate; //Last time the position was updated, in milliseconds
        private long lastStopped;/ //Last time the position was not changed (ex: v = 0)
        private boolean stopped;
        private double minSpeed = 0.2; //Minimum speed, otherwise "stopped"

        public Runner(double x, double y, double v) {
            this.x = x;
            this.y = y;
            this.v = v;
        }

        public double getV() {
            return v;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean hasStopped() {
            return stopped;
        }

        public long getLastStoppedTime() {
            return lastStopped;
        }

        public void updatePosition(double newX, double newY, long time) {
            double cX = (newX - x) * Math.cos((newY + y)/2);
            double cY = (newY - y);
            double radius = 6371000; //Earth radius in meteres
            double dist = Math.sqrt(cX * cX + cY * cY) * radius;
            v = dist / (lastUpdate - time)*1000; //Calculate velocity in meters per second
            if(v <= minSpeed && !stopped) { //Is the runner stopped, and wasnt earlier?
                stopped = true;
                lastStopped = time; //Set stop time to current time
                //TODO: Manage what to do when the runner is newly stopped;
            }
            else if(v <= minSpeed) { //Has the runner been stopped already?
                //TODO: Manage what to do when the runner has been stopped for a given time,
                //TODO: including notifying the runner and potentially EMS.
            }
        }
    }
}
