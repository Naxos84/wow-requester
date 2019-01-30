package com.example.address.model;

import java.util.ArrayList;
import java.util.List;

public class LootFeed extends Feed {

    public long itemId;
    public String context;
    public List<Integer> bonusList;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(final long itemId) {
        this.itemId = itemId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public List<Integer> getBonusList() {
        return bonusList == null ? new ArrayList<>() : bonusList;
    }

    public void setBonusList(final List<Integer> bonusList) {
        this.bonusList = bonusList;
    }

}
