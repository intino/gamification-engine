package org.example.vaccine.mock;

import old.core.model.Item;
import old.core.model.Npc;
import old.core.model.Player;
import old.core.model.World;
import org.example.vaccine.control.box.ControlBox;

import java.util.List;

public class DisplayInfo {

    private static final ControlBox Box = ControlBoxMock.getControlBox();

    public static void main(String[] args) {
        final List<World> worlds = Box.engine().datamart().worlds();
        if(worlds.isEmpty())
            System.out.println("No worlds to display");
        else
            worlds.forEach(DisplayInfo::showWorld);
    }

    private static void showWorld(World world) {
        System.out.println(world.id());

        System.out.println("\n  Players:");
        for(Player player : world.players())
            System.out.println("    " + player.id());
        System.out.println("\n");

        System.out.println("  Npcs:");
        for(Npc npc : world.npcs())
            System.out.println("    " + npc.id());
        System.out.println("\n");

        System.out.println("  Items:");
        for(Item item : world.items())
            System.out.println("    " + item.id());
        System.out.println("\n");
    }
}
