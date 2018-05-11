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

package pct.droid.provider.movies.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ApiMovie {

    @SerializedName("_id") private String id;
    @SerializedName("imdb_id") private String imdbId;
    @SerializedName("title") private String title;
    @SerializedName("year") private int year;
    @SerializedName("synopsis") private String synopsis;
    @SerializedName("runtime") private int runtime;
    @SerializedName("released") private long released;
    @SerializedName("trailer") private String trailer;
    @SerializedName("certification") private String certification;
    @SerializedName("torrents") private Map<String, Map<String, ApiTorrent>> torrents;
    @SerializedName("genres") private String[] genres;
    @SerializedName("images") private ApiImages images;
    @SerializedName("rating") private ApiRating rating;

    public String getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getRuntime() {
        return runtime;
    }

    public long getReleased() {
        return released;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getCertification() {
        return certification;
    }

    public Map<String, Map<String, ApiTorrent>> getTorrents() {
        return torrents;
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
}
