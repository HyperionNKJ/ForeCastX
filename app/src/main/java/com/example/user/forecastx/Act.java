package com.example.user.forecastx;

import android.os.Parcelable;

public abstract class Act implements Parcelable {
    protected float[] combinedEvidenceWeights;

    protected abstract void generateCombinedEvidenceWeights();
}
