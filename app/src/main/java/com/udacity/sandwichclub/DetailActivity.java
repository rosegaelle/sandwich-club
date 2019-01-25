package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import java.util.List;
import java.util.stream.Collectors;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static Log logger = LogFactory.getLog(DetailActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        } catch(JSONException e) {
            logger.error(e.getMessage());
            closeOnError();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView akaTv = findViewById(R.id.also_known_tv);
        if (CollectionUtils.isNotEmpty(sandwich.getAlsoKnownAs())) {
            displayContentsAsList(akaTv, sandwich.getAlsoKnownAs());
        } else {
            hideEmptyTextView(akaTv);
            TextView alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
            hideEmptyTextView(alsoKnownLabelTv);
        }

        TextView originTv = findViewById(R.id.origin_tv);
        if (StringUtils.isNotBlank(sandwich.getPlaceOfOrigin())){
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            hideEmptyTextView(originTv);
            TextView originLabelTv = findViewById(R.id.origin_label_tv);
            hideEmptyTextView(originLabelTv);
        }

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        displayContentsAsList(ingredientsTv, sandwich.getIngredients());

        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());
    }

    private void hideEmptyTextView(TextView tv) {
        tv.setVisibility(View.GONE);
    }

    private void displayContentsAsList(TextView tv, List<String> tvContents) {
        tv.setText(tvContents.stream().collect(Collectors.joining(", ")));
    }
}
