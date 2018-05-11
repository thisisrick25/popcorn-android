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

public class ApiRating {

    @SerializedName("percentage") private int percentage;
    @SerializedName("watching") private int watching;
    @SerializedName("votes") private int votes;
    @SerializedName("loved") private int loved;
    @SerializedName("hated") private int hated;

    public int getPercentage() {
        return percentage;
    }

    public int getWatching() {
        return watching;
    }

    public int getVotes() {
        return votes;
    }

    public int getLoved() {
        return loved;
    }

    public int getHated() {
        return hated;
    }
}
