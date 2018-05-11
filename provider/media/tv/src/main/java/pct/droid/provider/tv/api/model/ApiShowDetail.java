/*
 * This file is part of Butter.
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Butter. If not, see <http://www.gnu.org/licenses/>.
 */

package pct.droid.provider.tv.api.model;

import com.google.gson.annotations.SerializedName;

public class ApiShowDetail {

    @SerializedName("_id") private String id;
    @SerializedName("imdb_id") private String imdbId;
    @SerializedName("tvdb_id") private String tvdbId;
    @SerializedName("title") private String title;
    @SerializedName("year") private int year;
    @SerializedName("slug") private String slug;
    @SerializedName("synopsis") private String synopsis;
    @SerializedName("runtime") private int runtime;
    @SerializedName("country") private String country;
    @SerializedName("network") private String network;
    @SerializedName("air_day") private String airDay;
    @SerializedName("air_time") private String airTime;
    @SerializedName("status") private String status;
    @SerializedName("num_seasons") private int seasons;
    @SerializedName("genres") private String[] genres;
    @SerializedName("images") private ApiImages images;
    @SerializedName("rating") private ApiRating rating;
    @SerializedName("episodes") private ApiEpisode[] episodes;

    public String getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTvdbId() {
        return tvdbId;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSlug() {
        return slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getAirDay() {
        return airDay;
    }

    public String getAirTime() {
        return airTime;
    }

    public String getStatus() {
        return status;
    }

    public int getSeasons() {
        return seasons;
    }

    public String[] getGenres() {
        return genres;
    }

    public ApiImages getImages() {
        return images;
    }

    public ApiRating getRating() {
        return rating;
    }

    public ApiEpisode[] getEpisodes() {
        return episodes;
    }
}
