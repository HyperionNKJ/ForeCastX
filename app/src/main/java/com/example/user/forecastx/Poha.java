package com.example.user.forecastx;

import java.util.ArrayList;

public class Poha {
    private static final double[][] interferenceEvidenceWeights = {{0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1},
                                                                   {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1}};
    private ArrayList<Integer> unreasonableInterferences;
    private ArrayList<Integer> allEvidenceWeights;

    Poha(ArrayList<Integer> unreasonableInterferences) {
        this.unreasonableInterferences = unreasonableInterferences;
        generateAllEvidenceWeights();
    }

    private void generateAllEvidenceWeights() {
        
    }
}
