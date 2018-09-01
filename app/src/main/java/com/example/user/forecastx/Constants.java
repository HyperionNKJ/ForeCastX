package com.example.user.forecastx;

public class Constants {
    public static final int NUM_OF_EVIDENCE = 8;
    public static final int NUM_OF_UNREASONABLE_INTERFERENCE = 7;
    public static final int NUM_OF_HARASSMENT_TYPE = 5;
    public static final float[][] CDRA_INTERFERENCE_EVIDENCE_WEIGHTS =
                    {{10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10}};
    public static final float[][] POHA_HARASSMENT_EVIDENCE_WEIGHTS =
                    {{10,10,10,10,10,10,10,10},
                    {10,10,10,10,10,10,10,10},
                    {10,10,10,10,10,10,10,10},
                    {10,10,10,10,10,10,10,10},
                    {10,10,10,10,10,10,10,10}};
}
