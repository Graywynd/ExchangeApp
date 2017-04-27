package com.example.saif.echangeapp;

import java.util.Comparator;

/**
 * Created by saif on 21/04/2017.
 */
public class CustomComparator implements Comparator<Cotation> {
    @Override
    public int compare(Cotation o1, Cotation o2) {
        String variation1 = o1.getVariation();
        String variation2 = o2.getVariation();
        Double v1 = Double.parseDouble(variation1.substring(1, variation1.length() - 1));
        Double v2 = Double.parseDouble(variation2.substring(1,variation2.length()-1));
        return v1.compareTo(v2);
    }
}