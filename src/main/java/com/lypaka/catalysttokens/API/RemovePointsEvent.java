package com.lypaka.catalysttokens.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RemovePointsEvent extends Event {

    private final ServerPlayerEntity player;
    private double points;

    public RemovePointsEvent (ServerPlayerEntity player, double points) {

        this.player = player;
        this.points = points;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public double getPoints() {

        return this.points;

    }

    public void setPoints (double points) {

        this.points = points;

    }

}
