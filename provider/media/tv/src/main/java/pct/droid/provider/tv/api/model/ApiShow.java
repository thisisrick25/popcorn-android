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

public class ApiShow {

    @SerializedName("_id") private String id;
    @SerializedName("imdb_id") private String imdbId;
    @SerializedName("tvdb_id") private String tvdbId;
    @SerializedName("title") private String title;
    @SerializedName("year") private int year;
    @SerializedName("slug") private String slug;
    @SerializedName("num_seasons") private int seasons;
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

    public ApiImages getImages() {
        return images;
    }

    public ApiRating getRating() {
        return rating;
    }
}
