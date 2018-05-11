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

public class ApiTorrent {

    @SerializedName("url") private String url;
    @SerializedName("seed") private int seed;
    @SerializedName("peer") private int peer;
    @SerializedName("size") private long size;
    @SerializedName("filesize") private String filesize;
    @SerializedName("provider") private String provider;

    public String getUrl() {
        return url;
    }

    public int getSeed() {
        return seed;
    }

    public int getPeer() {
        return peer;
    }

    public long getSize() {
        return size;
    }

    public String getFilesize() {
        return filesize;
    }

    public String getProvider() {
        return provider;
    }
}
