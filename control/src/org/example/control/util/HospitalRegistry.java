package org.example.control.util;

import java.util.HashMap;
import java.util.Map;

public final class HospitalRegistry {

    private static final Map<String, String> HospitalLocations = new HashMap<>();

    static {
        HospitalLocations.put("Dr. Negrín", "Las Palmas de Gran Canaria");
        HospitalLocations.put("Hospital Universitario La Paz", "Madrid");
        HospitalLocations.put("Hospital Clínic de Barcelona", "Barcelona");
        HospitalLocations.put("Hospital General Universitario Gregorio Marañón", "Madrid");
        HospitalLocations.put("C.H.U. Insular Materno Infantil de Canarias", "Las Palmas de Gran Canaria");
    }

    public static String locationOf(String hospitalName) {
        return HospitalLocations.getOrDefault(hospitalName, "Unknown");
    }
}
