package com.example.address.model;

public class BossKillFeed extends Feed {

    public Achievement achievement;
    public boolean featOfStrength;
    public Criteria criteria;
    public long quantity;
    public String name;

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(final Achievement achievement) {
        this.achievement = achievement;
    }

    public boolean isFeatOfStrength() {
        return featOfStrength;
    }

    public void setFeatOfStrength(final boolean featOfStrength) {
        this.featOfStrength = featOfStrength;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(final Criteria criteria) {
        this.criteria = criteria;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
