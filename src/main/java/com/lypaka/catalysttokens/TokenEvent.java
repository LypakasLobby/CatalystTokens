package com.lypaka.catalysttokens;

import java.util.Map;

public class TokenEvent {

    private final String name;
    private final double baseValue;
    private final double chance;
    private Map<String, Integer> modifiersMap;
    private boolean modify;
    private final String permission;

    public TokenEvent (String name, double baseValue, double chance, Map<String, Integer> modifiersMap, String permission) {

        this.name = name;
        this.baseValue = baseValue;
        this.chance = chance;
        this.modifiersMap = modifiersMap;
        this.permission = permission;

    }

    public TokenEvent (String name, double baseValue, double chance, boolean modify, String permission) {

        this.name = name;
        this.baseValue = baseValue;
        this.chance = chance;
        this.modify = modify;
        this.permission = permission;

    }

    public void register() {

        EventRegistry.eventMap.put(this.name, this);

    }

    public String getName() {

        return this.name;

    }

    public double getBaseValue() {

        return this.baseValue;

    }

    public double getChance() {

        return this.chance;

    }

    public Map<String, Integer> getModifiersMap() {

        return this.modifiersMap;

    }

    public boolean doModification() {

        return this.modify;

    }

    public String getPermission() {

        return this.permission;

    }

}
