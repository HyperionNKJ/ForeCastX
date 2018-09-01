package com.example.user.forecastx;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Poha extends Act implements Parcelable {
    private boolean[] harassmentType;

    Poha (boolean[] harassmentType) {
        this.harassmentType = harassmentType;
        combinedEvidenceWeights = new float[Constants.NUM_OF_EVIDENCE];
        generateCombinedEvidenceWeights();
    }

    protected void generateCombinedEvidenceWeights() {
        int numOfHarassment = 0;
        for (int i = 0; i < Constants.NUM_OF_HARASSMENT_TYPE; i++) {
            if (harassmentType[i]) {
                numOfHarassment++;
                for (int j = 0; j < Constants.NUM_OF_EVIDENCE; j++) {
                    combinedEvidenceWeights[j] += Constants.POHA_HARASSMENT_EVIDENCE_WEIGHTS[i][j];
                }
            }
        }
        if (numOfHarassment != 0) {
            for (int i = 0; i < Constants.NUM_OF_EVIDENCE; i++) {
                combinedEvidenceWeights[i] /= numOfHarassment;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(harassmentType);
        dest.writeFloatArray(combinedEvidenceWeights);
    }

    public Poha(Parcel source) {
        harassmentType = new boolean[Constants.NUM_OF_HARASSMENT_TYPE];
        combinedEvidenceWeights = new float[Constants.NUM_OF_EVIDENCE];
        source.readBooleanArray(harassmentType);
        source.readFloatArray(combinedEvidenceWeights);
    }

    public static final Parcelable.Creator<Poha> CREATOR = new Parcelable.Creator<Poha>() {
        public Poha createFromParcel(Parcel in) {
            return new Poha(in);
        }

        public Poha[] newArray(int size) {
            return new Poha[size];
        }
    };

    @Override
    public String toString() {
        return "Harassment = " + Arrays.toString(harassmentType) + "\nEvidenceWeights = " + Arrays.toString(combinedEvidenceWeights);
    }
}
