package com.example.user.forecastx;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Act implements Parcelable {
    protected float[] combinedEvidenceWeights;

    /* Algorithm:
     * 1) Rank all available evidences by its weight.
     * 2) Multiply each evidence by the multiplier factor. Multiplier: First = 1, Second = 0.5, Third = 0.25 ... (Diminishing factor of 0.5)
     * 3) Sum each evidence's result. Sum = final evidence strength. Sum capped at 100.
     */
    public double generateEvidenceStrength(boolean[] availableEvidences) {
        ArrayList<Float> availableEvidencesStrength = new ArrayList<>();
        double finalEvidenceStrength = 0;
        for (int i = 0; i < Constants.NUM_OF_EVIDENCE; i++) {
            if (availableEvidences[i]) {
                availableEvidencesStrength.add(combinedEvidenceWeights[i]);
            }
        }
        Collections.sort(availableEvidencesStrength);
        for (int i = 0; i < availableEvidencesStrength.size(); i++) {
            finalEvidenceStrength += (availableEvidencesStrength.get(i) * Math.pow(Constants.EVIDENCE_DIMINISHING_FACTOR, availableEvidencesStrength.size() - 1 - i));
        }
        if (finalEvidenceStrength <= 100) {
            return finalEvidenceStrength;
        } else {
            return 100;
        }
    }

    protected abstract void generateCombinedEvidenceWeights();
}
