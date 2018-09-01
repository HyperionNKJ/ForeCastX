package com.example.user.forecastx;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Cdra extends Act implements Parcelable {
    private boolean[] unreasonableInterferences;

    Cdra (boolean[] unreasonableInterferences) {
        this.unreasonableInterferences = unreasonableInterferences;
        combinedEvidenceWeights = new float[Constants.NUM_OF_EVIDENCE];
        generateCombinedEvidenceWeights();
    }

    protected void generateCombinedEvidenceWeights() {
        int numOfInterference = 0;
        for (int i = 0; i < Constants.NUM_OF_UNREASONABLE_INTERFERENCE; i++) {
            if (unreasonableInterferences[i]) {
                numOfInterference++;
                for (int j = 0; j < Constants.NUM_OF_EVIDENCE; j++) {
                    combinedEvidenceWeights[j] += Constants.CDRA_INTERFERENCE_EVIDENCE_WEIGHTS[i][j];
                }
            }
        }
        for (int i = 0; i < Constants.NUM_OF_EVIDENCE; i++) {
            combinedEvidenceWeights[i] /= numOfInterference;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(unreasonableInterferences);
        dest.writeFloatArray(combinedEvidenceWeights);
    }

    public Cdra(Parcel source) {
        unreasonableInterferences = new boolean[Constants.NUM_OF_UNREASONABLE_INTERFERENCE];
        combinedEvidenceWeights = new float[Constants.NUM_OF_EVIDENCE];
        source.readBooleanArray(unreasonableInterferences);
        source.readFloatArray(combinedEvidenceWeights);
    }

    public static final Parcelable.Creator<Cdra> CREATOR = new Parcelable.Creator<Cdra>() {
        public Cdra createFromParcel(Parcel in) {
            return new Cdra(in);
        }

        public Cdra[] newArray(int size) {
            return new Cdra[size];
        }
    };

    @Override
    public String toString() {
        return "UI = " + Arrays.toString(unreasonableInterferences) + "\nEvidenceWeights = " + Arrays.toString(combinedEvidenceWeights);
    }
}
