package org.example.cinepolis.control.box;

import org.example.cinepolis.datahub.events.cinepolis.AssetAlert;

import static org.example.cinepolis.control.box.DatamartUtils.*;

public class DatamartUpdater {

    public static void initialize(ControlBox box) {
        box.graph().clear();
        box.engine().datamart().clear();

        box.adapter().initialize();

        box.terminal().publish(newEmployee("empleado1", "Pepe", 29, "622785202", "area1"));
        box.terminal().publish(newEmployee("empleado2", "Jose", 30, "622785203", "area2"));
        box.terminal().publish(newEmployee("empleado3", "Rafa", 42, "622785204", "area3"));
        box.terminal().publish(newEmployee("empleado4", "Pedro", 18, "622785205", "area4"));
        box.terminal().publish(newEmployee("empleado5", "Juan", 35, "622785206", "area5"));

        box.terminal().publish(newAsset("asset1", "Proyector 1", "area1"));
        box.terminal().publish(newAsset("asset2", "Proyector 2", "area1"));
        box.terminal().publish(newAsset("asset3", "Proyector 3", "area1"));
        box.terminal().publish(newAsset("asset4", "Proyector 4", "area2"));
        box.terminal().publish(newAsset("asset5", "Proyector 5", "area2"));
        box.terminal().publish(newAsset("asset6", "Proyector 6", "area2"));
        box.terminal().publish(newAsset("asset7", "Proyector 7", "area3"));
        box.terminal().publish(newAsset("asset8", "Proyector 8", "area3"));
        box.terminal().publish(newAsset("asset9", "Proyector 9", "area3"));
        box.terminal().publish(newAsset("asset10", "Proyector 10", "area4"));
        box.terminal().publish(newAsset("asset11", "Proyector 11", "area4"));
        box.terminal().publish(newAsset("asset12", "Proyector 12", "area4"));
        box.terminal().publish(newAsset("asset13", "Proyector 13", "area5"));
        box.terminal().publish(newAsset("asset14", "Proyector 14", "area5"));
        box.terminal().publish(newAsset("asset15", "Proyector 15", "area5"));

        newWorkingDay(box);

        box.terminal().publish(deleteEmployee("empleado1"));
        box.terminal().publish(deleteAsset("asset4"));

        box.terminal().publish(generateAlert("alert1", "asset5", AssetAlert.Importance.Important, 1, "Arregla el asset 5"));
        box.terminal().publish(generateAlert("alert2", "asset7", AssetAlert.Importance.Low, 2, "Arregla el asset 7"));
        box.terminal().publish(generateAlert("alert3", "asset10", AssetAlert.Importance.Medium, 3, "Arregla el asset 10"));
        box.terminal().publish(generateAlert("alert4", "asset15", AssetAlert.Importance.Medium, 3, "Arregla el asset 15"));

        box.terminal().publish(completeAlert("alert1", "asset5", "empleado2"));
        box.terminal().publish(completeAlert("alert2", "asset10", "empleado3"));
        box.terminal().publish(completeAlert("alert3", "asset10", "empleado3"));
        box.terminal().publish(completeAlert("alert4", "asset15", "empleado5"));

        System.out.println();
    }
}
