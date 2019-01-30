package com.example.address.deserialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StringPropertyDeserializer implements JsonDeserializer<StringProperty> {

    @Override
    public StringProperty deserialize(final JsonElement json, final Type typeOf,
            final JsonDeserializationContext context) {
        return new SimpleStringProperty(json.getAsString());
    }

}
