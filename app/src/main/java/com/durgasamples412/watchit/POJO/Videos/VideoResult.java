
package com.durgasamples412.watchit.POJO.Videos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResult {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<VideoDetails> results = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public VideoResult() {
    }

    /**
     * 
     * @param id
     * @param results
     */
    public VideoResult(int id, List<VideoDetails> results) {
        super();
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoDetails> getResults() {
        return results;
    }

    public void setResults(List<VideoDetails> results) {
        this.results = results;
    }

}
