package com.durgasamples412.watchit.POJO;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.durgasamples412.watchit.POJO.Dates;
import com.durgasamples412.watchit.POJO.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 24-01-2018.
 */

public class UpComingMovieModel {
        @SerializedName("results")
        @Expose
        private List<Result> results = new ArrayList<>();
        @SerializedName("page")
        @Expose
        private int page;
        @SerializedName("total_results")
        @Expose
        private int totalResults;
        @SerializedName("dates")
        @Expose
        private Dates dates;
        @SerializedName("total_pages")
        @Expose
        private int totalPages;

        /**
         * No args constructor for use in serialization
         *
         */


        public UpComingMovieModel(List<Result> results) {


            this.results = results;

        }

    public UpComingMovieModel() {

    }


    public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public Dates getDates() {
            return dates;
        }

        public void setDates(Dates dates) {
            this.dates = dates;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

    }




