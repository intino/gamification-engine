package org.example.cinepolis.control.box;

import io.intino.alexandria.message.Message;
import io.intino.alexandria.message.MessageReader;
import io.intino.gamification.util.time.TimeUtils;
import org.example.cinepolis.control.gamification.GamificationConfig;
import org.example.cinepolis.datahub.events.cinepolis.AssetAlert;
import org.example.cinepolis.datahub.events.cinepolis.HireEmployee;
import org.example.cinepolis.datahub.events.cinepolis.RegisterAsset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.*;

import static org.example.cinepolis.control.box.DatamartUtils.completeAlert;
import static org.example.cinepolis.control.box.DatamartUtils.generateAlert;

public class DatamartUpdater {

    public static void initialize(ControlBox box) {

        box.adapter().initialize(box.engine().graphViewer().world(GamificationConfig.WorldId));
        /*box.adapter().initialize();

        try {
            List<Theater> theaters = new ArrayList<>();

            File theaterFile = new File(box.configuration().home() + "/data/theaters/theaters.tsv");
            List<String> theaterLines = Files.readAllLines(theaterFile.toPath());
            for(String line : theaterLines) {
                if(line.endsWith("comercialSegment")) continue;
                theaters.add(new Theater(line.split("\t")));
            }

            File employeeFile = new File(box.configuration().home() + "/data/employees/employees.tsv");
            List<String> employeeLines = Files.readAllLines(employeeFile.toPath());
            for(String line : employeeLines) {
                if(line.endsWith("id")) continue;
                Employee employee = new Employee(line.split("\t"));
                Theater theater = theaters.stream().filter(t -> t.technician.equals(employee.id)).findFirst().orElse(null);
                if(theater != null) box.terminal().publish(hireEmployee(employee, theater));
            }

            Map<String, Asset> assetMap = new HashMap<>();

            Queue<Message> messageQueue = new PriorityQueue<>(Comparator.comparing(m -> tsOf(m.get("ts"))));

            for (File confFile : Objects.requireNonNull(new File(box.configuration().home() + "/data/assets/configuration").listFiles())) {
                messageQueue.addAll(getMessagesOf(getMessageReaderOf(confFile)));
            }

            //TODO REGISTRAR LOG
            System.out.println("------------------------------------------------");

            for (File statusFile : Objects.requireNonNull(new File(box.configuration().home() + "/data/assets/status").listFiles())) {
                messageQueue.addAll(getMessagesOf(getMessageReaderOf(statusFile)));
            }

            while(!messageQueue.isEmpty()) {
                Message message = messageQueue.poll();
                Asset asset = new Asset(message);
                if(!assetMap.containsKey(asset.ip)) {
                    assetMap.put(asset.ip, asset);
                } else {
                    assetMap.put(asset.ip, combine(assetMap.get(asset.ip), asset));
                }
            }

            assetMap.forEach((ip, asset) -> {
                Theater theater = theaters.stream().filter(t -> ip.startsWith(t.operationSegment)).findFirst().orElse(null);
                if(theater != null) box.terminal().publish(registerAsset(asset, theater));
            });

            //TODO REGISTRAR LOG
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //No se puede acceder al mundo de las listas
        //box.terminal().publish(deleteEmployee("e54"));
        //box.terminal().publish(deleteAsset("10.116.46.29"));

        Map<org.example.cinepolis.control.graph.Employee, ArrayList<org.example.cinepolis.control.graph.Asset>> assetsByEmployee = new HashMap<>();
        box.graph().assetList().forEach(a -> {
            org.example.cinepolis.control.graph.Employee employee = box.graph().employeeByArea(a.area());
            if(employee != null) {
                assetsByEmployee.computeIfAbsent(employee, k -> new ArrayList<>());
                assetsByEmployee.get(employee).add(a);
            }
        });

        box.terminal().publish(generateAlert("alert1", "10.96.18.45", AssetAlert.Importance.Important, 1, "Arregla el asset 10.96.18.45"));
        box.terminal().publish(generateAlert("alert2", "10.99.9.43", AssetAlert.Importance.Low, 2, "Arregla el asset 10.99.9.43"));
        box.terminal().publish(generateAlert("alert3", "10.98.2.43", AssetAlert.Importance.Medium, 3, "Arregla el asset 10.98.2.43"));
        box.terminal().publish(generateAlert("alert4", "10.100.7.25", AssetAlert.Importance.Medium, 3, "Arregla el asset 10.100.7.25"));

        box.terminal().publish(completeAlert("alert1", "10.96.18.45", "e12"));
        box.terminal().publish(completeAlert("alert2", "10.99.9.43", "e43"));   //Empleado mal, es e42
        box.terminal().publish(completeAlert("alert2", "10.98.2.43", "e46"));   //Alerta mal, es alert3
        box.terminal().publish(completeAlert("alert4", "10.100.7.24", "e24"));  //IP mal, es .25*/

        //TODO REGISTRAR LOG
        System.out.println();
    }

    private static RegisterAsset registerAsset(Asset value, Theater theater) {
        RegisterAsset event = new RegisterAsset();
        event.ts(TimeUtils.currentInstant());
        event.id(value.ip);
        event.area(theater.code);
        event.name(value.name);
        return event;
    }

    private static HireEmployee hireEmployee(Employee employee, Theater theater) {
        HireEmployee event = new HireEmployee();
        event.ts(TimeUtils.currentInstant());
        event.id(employee.id);
        event.area(theater.code);
        event.age(30);
        event.phone(employee.phone);
        event.name(employee.name);
        return event;
    }

    private static Instant tsOf(Message.Value ts) {
        return Instant.parse(ts.asString().replaceAll(" ", ""));
    }

    private static MessageReader getMessageReaderOf(File file) throws IOException {
        String content = Files.readString(file.toPath());
        content = content.replace(":\n", ": \n");
        return new MessageReader(content);
    }

    private static List<Message> getMessagesOf(MessageReader reader) {
        List<Message> messages = new ArrayList<>();
        while (reader.hasNext()) messages.add(reader.next());
        return messages;
    }

    private static Asset combine(Asset oldAsset, Asset newAsset) {
        if(newAsset.ts != null) oldAsset.ts = newAsset.ts;
        if(newAsset.model != null) oldAsset.model = newAsset.model;
        if(newAsset.ip != null) oldAsset.ip = newAsset.ip;
        if(newAsset.theater != null) oldAsset.theater = newAsset.theater;
        if(newAsset.city != null) oldAsset.city = newAsset.city;
        if(newAsset.region != null) oldAsset.region = newAsset.region;
        if(newAsset.country != null) oldAsset.country = newAsset.country;
        if(newAsset.screen != null) oldAsset.screen = newAsset.screen;
        if(newAsset.sn != null) oldAsset.sn = newAsset.sn;
        if(newAsset.name != null) oldAsset.name = newAsset.name;
        if(newAsset.type != null) oldAsset.type = newAsset.type;
        if(newAsset.mode != null) oldAsset.mode = newAsset.mode;

        return oldAsset;
    }

    private static class Employee {

        private String name, surname, position, division, photo, email, phone, id;

        public Employee(String[] fields) {
            name = fields[0];
            surname = fields[1];
            position = fields[2];
            division = fields[3];
            photo = fields[4];
            email = fields[5];
            phone = fields[6];
            id = fields[7];
        }
    }

    private static class Theater {

        private String code, name, exhibitor, type, numberOfScreens, territory, city, province, email, technician, manager, operationSegment, comercialSegment;

        public Theater(String[] fields) {
            code = fields[0];
            name = fields[1];
            exhibitor = fields[2];
            type = fields[3];
            numberOfScreens = fields[4];
            territory = fields[5];
            city = fields[6];
            province = fields[7];
            email = fields[8];
            technician = fields[9];
            manager = fields[10];
            operationSegment = fields[11];
            comercialSegment = fields[12];
        }
    }

    private static class Asset {

        private String ts, model, ip, theater, city, region, country, screen, sn, name, type, mode;

        public Asset(Message message) {
            ts = message.get("ts").asString() != null ? String.valueOf(message.get("ts")) : null;
            model = message.get("model").asString() != null ? String.valueOf(message.get("model")) : null;
            ip = message.get("ip").asString() != null ? String.valueOf(message.get("ip")) : null;
            theater = message.get("theater").asString() != null ? String.valueOf(message.get("theater")) : null;
            city = message.get("city").asString() != null ? String.valueOf(message.get("city")) : null;
            region = message.get("region").asString() != null ? String.valueOf(message.get("region")) : null;
            country = message.get("country").asString() != null ? String.valueOf(message.get("country")) : null;
            screen = message.get("screen").asString() != null ? String.valueOf(message.get("screen")) : null;
            sn = message.get("sn").asString() != null ? String.valueOf(message.get("sn")) : null;
            name = message.get("name").asString() != null ? String.valueOf(message.get("name")) : null;
            type = message.get("type").asString() != null ? String.valueOf(message.get("type")) : null;
            mode = message.get("mode").asString() != null ? String.valueOf(message.get("mode")) : null;
            checkOtherParams(message.attributes());
        }

        private void checkOtherParams(List<String> attributes) {
            for (String attribute : attributes) {
                if(attribute.equals("ts")) continue;
                if(attribute.equals("model")) continue;
                if(attribute.equals("ip")) continue;
                if(attribute.equals("theater")) continue;
                if(attribute.equals("city")) continue;
                if(attribute.equals("region")) continue;
                if(attribute.equals("screen")) continue;
                if(attribute.equals("sn")) continue;
                if(attribute.equals("name")) continue;
                if(attribute.equals("country")) continue;
                if(attribute.equals("type")) continue;
                if(attribute.equals("mode")) continue;
                //TODO REGISTRAR LOG
                System.out.println(attribute);
            }
        }
    }
}
