package com.example.address.deserialization;

import java.lang.reflect.Type;

import com.example.address.model.AchievementFeed;
import com.example.address.model.BossKillFeed;
import com.example.address.model.CriteriaFeed;
import com.example.address.model.Feed;
import com.example.address.model.LootFeed;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FeedDeserializer implements JsonDeserializer<Feed> {

    @Override
    public Feed deserialize(final JsonElement json, final Type typeOf, final JsonDeserializationContext context) {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement jType = jsonObject.get("type");
        final String type = jType.getAsString();

        if ("LOOT".equals(type)) {
            return context.deserialize(json, LootFeed.class);
        }
        if ("BOSSKILL".equals(type)) {
            return context.deserialize(json, BossKillFeed.class);
        }
        if ("ACHIEVEMENT".equals(type)) {
            return context.deserialize(json, AchievementFeed.class);
        }
        if ("CRITERIA".equals(type)) {
            return context.deserialize(json, CriteriaFeed.class);
        }
        return null;
    }

}
