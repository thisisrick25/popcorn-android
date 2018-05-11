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

package pct.droid.provider.movies.api;

import pct.droid.provider.movies.api.model.ApiMovie;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("{page}")
    Single<ApiMovie[]> fetchMovies(@Path("page") int page, @Query("keywords") String keywords, @Query("genre") String genre, @Query("sort") String sort,
            @Query("order") int order, @Query("lang") String lang, @Query("limit") int limit);

}
