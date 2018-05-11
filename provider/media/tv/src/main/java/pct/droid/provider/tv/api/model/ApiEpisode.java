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
import java.util.Map;

public class ApiEpisode {

    @SerializedName("torrents") private Map<String, ApiTorrent> torrents;
    @SerializedName("first_aired") private long firstAired;
    @SerializedName("overview") private String overview;
    @SerializedName("title") private String title;
    @SerializedName("episode") private int episode;
    @SerializedName("season") private int season;
    @SerializedName("tvdb_id") private String tvdbId;

    public Map<String, ApiTorrent> getTorrents() {
        return torrents;
    }

    public long getFirstAired() {
        return firstAired;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
    }

    public String getTvdbId() {
        return tvdbId;
    }
}
