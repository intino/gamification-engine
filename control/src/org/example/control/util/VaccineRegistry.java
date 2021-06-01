package org.example.control.util;

import java.util.HashMap;
import java.util.Map;

public final class VaccineRegistry {

    private static final Map<String, Integer> VaccinesInfo = new HashMap<>();

    static {
        VaccinesInfo.put("Astra Zeneca", 2);
        VaccinesInfo.put("Pfizer", 2);
        VaccinesInfo.put("Moderna", 1);
    }

    public static int doseCountOf(String vaccineName) {
        return VaccinesInfo.getOrDefault(vaccineName, 0);
    }
}
