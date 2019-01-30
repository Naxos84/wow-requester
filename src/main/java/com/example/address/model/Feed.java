package com.example.address.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Feed {

    private StringProperty type;

    public LongProperty timestamp;

    public String getType() {
        return type.getValue();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(final String type) {
        this.type = new SimpleStringProperty(type);
    }

    public Long getTimestamp() {
        return timestamp.getValue();
    }

    public LongProperty timestampProperty() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = new SimpleLongProperty(timestamp);
    }

}
