package com.example.address.deserialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class LongPropertyDeserializer implements JsonDeserializer<LongProperty> {

    @Override
    public LongProperty deserialize(final JsonElement json, final Type typeOf,
            final JsonDeserializationContext context) {

        return new SimpleLongProperty(json.getAsLong());
    }

}
