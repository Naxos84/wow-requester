package com.example.address.model;

public class CharacterResponse {
    private long lastModified;
    private String name;
    private String realm;
    private String battlegroup;
    private long clazz;
    private long race;
    private long gender;
    private long level;
    private long achievementPoints;
    private String thumbnail;
    private String calcClass;
    private long faction;
    private long totalHonorableKills;

    public long getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    public String getRealm() {
        return realm;
    }

    public String getBattlegroup() {
        return battlegroup;
    }

    public long getClazz() {
        return clazz;
    }

    public long getRace() {
        return race;
    }

    public long getGender() {
        return gender;
    }

    public long getLevel() {
        return level;
    }

    public long getAchievementPoints() {
        return achievementPoints;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCalcClass() {
        return calcClass;
    }

    public long getFaction() {
        return faction;
    }

    public long getTotalHonorableKills() {
        return totalHonorableKills;
    }
}
