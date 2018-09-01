package com.example.user.forecastx;

public class Constants {
    public static final int NUM_OF_EVIDENCE = 8;
    public static final double EVIDENCE_DIMINISHING_FACTOR = 0.5;
    public static final int NUM_OF_UNREASONABLE_INTERFERENCE = 7;
    public static final int NUM_OF_HARASSMENT_TYPE = 5;
    public static final int NUM_OF_EFFECTIVE_COMPONENTS = 5; // Effective components are those that affects probability of success/failure. Ineffective components are instant win/lose.
    public static final float[][] CDRA_INTERFERENCE_EVIDENCE_WEIGHTS =
                    {{10, 20, 60, 80, 95, 80, 70, 50},
                    {10, 20, 0, 80, 95, 70, 70, 50},
                    {10, 20, 10, 80, 95, 10, 70, 50},
                    {10, 20, 10, 80, 95, 0, 70, 50},
                    {10, 20, 0, 80, 95, 0, 70, 50},
                    {10, 20, 10, 80, 95, 70, 70, 50},
                    {10, 20, 60, 80, 95, 80, 70, 50}};
    public static final float[][] POHA_HARASSMENT_EVIDENCE_WEIGHTS =
                    {{10,20,60,80,95,67,70,50},
                    {10,20,60,80,95,67,70,50},
                    {10,20,60,80,95,30,70,50},
                    {10,20,60,80,95,30,70,50},
                    {10,20,0,80,95,30,70,50}};
    public static double[] componentResults = new double[NUM_OF_EFFECTIVE_COMPONENTS]; // In this order: Evidence, Frequency of harass, Type of aggressor, Availability of particulars, PTC
}
