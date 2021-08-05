package org.example.cinepolis.control.box;

import io.intino.gamification.util.time.TimeUtils;
import org.example.cinepolis.datahub.events.cinepolis.*;

public class DatamartUtils {

    public static DeregisterAsset deleteAsset(String id) {
        return new DeregisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static RegisterAsset newAsset(String id, String name, String area) {
        return new RegisterAsset()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .area(area);
    }

    public static HireEmployee newEmployee(String id, String name, int age, String phone, String area) {
        return new HireEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .name(name)
                .age(age)
                .phone(phone)
                .area(area);
    }

    public static DismissEmployee deleteEmployee(String id) {
        return new DismissEmployee()
                .ts(TimeUtils.currentInstant())
                .id(id);
    }

    public static AssetAlert generateAlert(String id, String asset, AssetAlert.Importance importance, int limitHours, String description) {
        return new AssetAlert()
                .ts(TimeUtils.currentInstant())
                .id(id)
                .asset(asset)
                .importance(importance)
                .limitHours(limitHours)
                .description(description);
    }

    public static FixedAsset completeAlert(String alert, String asset, String employee) {
        return new FixedAsset()
                .ts(TimeUtils.currentInstant())
                .alert(alert)
                .asset(asset)
                .employee(employee);
    }
}
