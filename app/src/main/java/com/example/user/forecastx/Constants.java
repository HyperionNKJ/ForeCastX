package com.example.user.forecastx;

public class Constants {
    public static final int GENERAL_COST_OF_APPLICATION = 200;
    public static final double PERCENTAGE_COST_OF_EPO_APPLICATION = 1.16;
    public static final int MAX_NUM_OF_EVIDENCE_SHEETS = 200;
    public static final int NUM_OF_EVIDENCE = 8;
    public static final double EVIDENCE_DIMINISHING_FACTOR = 0.5;
    public static final int NUM_OF_UNREASONABLE_INTERFERENCE = 7;
    public static final int NUM_OF_HARASSMENT_TYPE = 5;
    public static final int NUM_OF_EFFECTIVE_COMPONENTS = 5; // Effective components are those that affects probability of success/failure. Ineffective components are instant win/lose.
    public static final int NUM_OF_OPTIONS_FOR_POHA_FREQUENCY = 5;
    public static final int NUM_OF_OPTIONS_FOR_CDRA_FREQUENCY = 10;
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
    public static final float[] POHA_AGGRESSOR_WEIGHTS = {50,30,30,20,10};
    public static final double[] CDRA_NUM_OF_TIME_MULTIPLIER = {0.5,0.75,1,1.05,1.1,1.15,1.2,1.3,1.4,1.5};
    public static final double[] POHA_NUM_OF_TIME_MULTIPLIER = {0.8,0.9,1,1.25,1.5};
    public static final double[] CDRA_FREQUENCY_MULTIPLIER = {1.3,1.15,1,0.8,0.6,0.4};
    public static final double[] POHA_FREQUENCY_MULTIPLIER = {1.3,1.15,1,0.8,0.6,0.4};
    public static final float[] REPONDENT_PARTICULARS_WEIGHTS = {25, 25};   // respondent particulars component will have free "50" weight
    public static final float[] CYBER_REPONDENT_PARTICULARS_WEIGHTS = {25, 100};


    // Use https://plot.ly/create/line-of-best-fit/#/ for curve fitting
    public static double[] componentResults = new double[NUM_OF_EFFECTIVE_COMPONENTS]; // In this order: Evidence, Frequency of harass, Type of aggressor, Availability of particulars, PTC
    public static double totalApplicationCost;

    // FLAGS
    public static boolean isCdra;
    public static boolean isBefore2014;
    public static boolean canAfford;
    public static boolean hasName;
    public static boolean hasAddress;
    public static boolean canDeliver;
}
