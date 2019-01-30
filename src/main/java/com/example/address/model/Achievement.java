package com.example.address.model;

import java.util.List;

public class Achievement {

    public long id;
    public String title;
    public long points;
    public String description;
    public List<Object> rewardItems;
    public String icon;
    public List<Criteria> criteria;
    public boolean accountWide;
    public long factionId;

}
