package com.example.buzz.model;

import java.lang.reflect.Type;

import com.google.common.collect.Iterables;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
/**
 * Serializer class that flattens json
 *
 */
public class ProjectBuzzFlatSerializer implements JsonSerializer<ProjectBuzzData> {

	@Override
	public JsonElement serialize(final ProjectBuzzData src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject projectData = context.serialize(src.getProjectData()).getAsJsonObject();
		final JsonObject socialMediaData = context.serialize(src.getSocialMediaData()).getAsJsonObject();
		
		final JsonObject json = new JsonObject();

		Iterables.concat(projectData.entrySet(), socialMediaData.entrySet()).forEach((entry) -> {
			json.add(entry.getKey(), entry.getValue());
		});

		
        return json;
	}
}
