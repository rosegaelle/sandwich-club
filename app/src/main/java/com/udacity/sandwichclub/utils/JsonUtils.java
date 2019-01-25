package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich sandwich = new Sandwich();

        JSONObject sandwichJson = new JSONObject(json);
        JSONObject nameJson = sandwichJson.getJSONObject(NAME);
        sandwich.setMainName(nameJson.getString(MAIN_NAME));

        JSONArray akaJsonArr = nameJson.getJSONArray(ALSO_KNOWN_AS);
        sandwich.setAlsoKnownAs(convertJsonArrayToStringList(akaJsonArr));

        sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));

        sandwich.setDescription(sandwichJson.getString(DESCRIPTION));

        sandwich.setImage(sandwichJson.getString(IMAGE));

        JSONArray ingredientsJsonArr = sandwichJson.getJSONArray(INGREDIENTS);
        sandwich.setIngredients(convertJsonArrayToStringList(ingredientsJsonArr));

        return sandwich;
    }

    private static List<String> convertJsonArrayToStringList(JSONArray jsonArray) throws JSONException {
        List<String> stringList = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            stringList.add(jsonArray.getString(i));
        }

        return stringList;
    }
}
