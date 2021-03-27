package com.example.xolotv.responses;

import com.google.gson.annotations.SerializedName;
import com.example.xolotv.model.TVShowDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
