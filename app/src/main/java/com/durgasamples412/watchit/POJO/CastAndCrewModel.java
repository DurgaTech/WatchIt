package com.durgasamples412.watchit.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 24-01-2018.
 */

public class CastAndCrewModel {
    @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("cast")
        @Expose
        private List<Cast> cast = null;
        @SerializedName("crew")
        @Expose
        private List<Crew> crew = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public CastAndCrewModel() {
        }

        /**
         *
         * @param id
         * @param cast
         * @param crew
         */
        public CastAndCrewModel(int id, List<Cast> cast, List<Crew> crew) {
            super();
            this.id = id;
            this.cast = cast;
            this.crew = crew;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Cast> getCast() {
            return cast;
        }

        public void setCast(List<Cast> cast) {
            this.cast = cast;
        }

        public List<Crew> getCrew() {
            return crew;
        }

        public void setCrew(List<Crew> crew) {
            this.crew = crew;
        }

    }


