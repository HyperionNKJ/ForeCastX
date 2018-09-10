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
    public static final double[] POHA_FREQUENCY_MULTIPLIER = {1.8,1.65,1.5,1.3,1,0.8};
    public static final float[] RESPONDENT_PARTICULARS_WEIGHTS = {25, 25};   // respondent particulars component will have free "50" weight
    public static final float[] CYBER_REPONDENT_PARTICULARS_WEIGHTS = {25, 100};
    public static final float PTC_RESPONDENT_CAN_ATTEND_WEIGHT = -10;
    public static final float PTC_PER_MEDIATION_WEIGHT = 30;

    // Use https://plot.ly/create/line-of-best-fit/#/ for curve fitting
    public static double[] componentResults = new double[NUM_OF_EFFECTIVE_COMPONENTS]; // In this order: Evidence, Frequency of harass, Type of aggressor, Availability of particulars, PTC
    public static double totalApplicationCost;

    // FLAGS
    public static boolean isCdra;
    public static boolean isOver2YearsAgo;
    public static boolean isBefore2014;
    public static boolean hasLowFrequency;
    public static boolean canAfford;
    public static boolean hasName;
    public static boolean hasAddress;
    public static boolean canDeliver;
    public static boolean respondentIsAttending;    // Instant win or Divide 2 probability
    public static boolean isEpoApplication;     // Divide 1.5 probability because need prove imminent
    public static boolean hasEpoBefore;    // higher probability because granted protection before

    public static StringBuilder systemMessage = new StringBuilder();

    public static double computeFinalProbability() {
        if (isBefore2014 && !isCdra) {
            return 0;
        }
        double finalProbability = 0;
        for (int i = 0; i < NUM_OF_EFFECTIVE_COMPONENTS; i++) {
            if (isCdra && i == 2) {
                continue;
            }
            finalProbability += componentResults[i];
        }
        finalProbability /= ((isCdra) ? NUM_OF_EFFECTIVE_COMPONENTS - 1 : NUM_OF_EFFECTIVE_COMPONENTS);

        // apply multiplier
        if (!respondentIsAttending) {
            return 100;
        } else {
            finalProbability /= 1.1;
        }
        if (isEpoApplication) {
            finalProbability /= 1.2;
        } else if (hasEpoBefore) {
            finalProbability *= 1.3;
        }
        return finalProbability;
    }
}
