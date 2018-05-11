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

package pct.droid.provider.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butter.droid.provider.AbsMediaProvider;
import butter.droid.provider.base.filter.Filter;
import butter.droid.provider.base.filter.Genre;
import butter.droid.provider.base.filter.Sorter;
import butter.droid.provider.base.model.Format;
import butter.droid.provider.base.model.Media;
import butter.droid.provider.base.model.Movie;
import butter.droid.provider.base.model.Torrent;
import butter.droid.provider.base.nav.NavItem;
import butter.droid.provider.base.paging.ItemsWrapper;
import butter.droid.provider.base.paging.Paging;
import butter.droid.provider.base.util.Optional;
import butter.droid.provider.filter.Pager;
import pct.droid.provider.movies.api.MoviesService;
import pct.droid.provider.movies.api.model.ApiMovie;
import pct.droid.provider.movies.api.model.ApiTorrent;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoviesProvider extends AbsMediaProvider {

    private static final Sorter SORTER_TRENDING = new Sorter("trending", pct.droid.provider.movies.R.string.sorter_vodo_trending);
    private static final Sorter SORTER_SEEDS = new Sorter("popularity", pct.droid.provider.movies.R.string.sorter_vodo_popularity);
    private static final Sorter SORTER_YEAR = new Sorter("year", pct.droid.provider.movies.R.string.sorter_vodo_year);
    private static final Sorter SORTER_DATE_ADDED = new Sorter("last added", pct.droid.provider.movies.R.string.sorter_vodo_date_added);
    private static final Sorter SORTER_RATING = new Sorter("rating", pct.droid.provider.movies.R.string.sorter_vodo_rating);
    private static final Sorter SORTER_TITLE = new Sorter("name", pct.droid.provider.movies.R.string.sorter_vodo_alphabet);

    private static final List<Genre> GENRES = Arrays.asList(
        new Genre("all", R.string.genre_all),
        new Genre("action", pct.droid.provider.movies.R.string.genre_action),
        new Genre("adventure", pct.droid.provider.movies.R.string.genre_adventure),
        new Genre("animation", pct.droid.provider.movies.R.string.genre_animation),
        new Genre("comedy", pct.droid.provider.movies.R.string.genre_comedy),
        new Genre("crime", pct.droid.provider.movies.R.string.genre_crime),
        new Genre("disaster", R.string.genre_disaster),
        new Genre("documentary", pct.droid.provider.movies.R.string.genre_documentary),
        new Genre("drama", pct.droid.provider.movies.R.string.genre_drama),
        new Genre("eastern", R.string.genre_eastern),
        new Genre("family", pct.droid.provider.movies.R.string.genre_family),
        new Genre("fantasy", pct.droid.provider.movies.R.string.genre_fantasy),
        new Genre("fan-film", R.string.genre_fan_film),
        new Genre("film-noir", pct.droid.provider.movies.R.string.genre_film_noir),
        new Genre("history", pct.droid.provider.movies.R.string.genre_history),
        new Genre("holiday", R.string.genre_holiday),
        new Genre("horror", pct.droid.provider.movies.R.string.genre_horror),
        new Genre("indie", R.string.genre_indie),
        new Genre("music", pct.droid.provider.movies.R.string.genre_music),
        new Genre("mystery", pct.droid.provider.movies.R.string.genre_mystery),
        new Genre("road", R.string.genre_road),
        new Genre("romance", pct.droid.provider.movies.R.string.genre_romance),
        new Genre("science-fiction", pct.droid.provider.movies.R.string.genre_sci_fi),
        new Genre("short", R.string.genre_short),
        new Genre("sports", pct.droid.provider.movies.R.string.genre_sport),
        new Genre("suspense", R.string.genre_suspense),
        new Genre("thriller", pct.droid.provider.movies.R.string.genre_thriller),
        new Genre("tv-movie", R.string.genre_tv_movie),
        new Genre("war", pct.droid.provider.movies.R.string.genre_war),
        new Genre("western", pct.droid.provider.movies.R.string.genre_western)
    );

    private static final List<NavItem> NAV_ITEMS = Arrays.asList(
            new NavItem(pct.droid.provider.movies.R.drawable.filter_trending, pct.droid.provider.movies.R.string.sorter_vodo_trending, SORTER_TRENDING),
            new NavItem(pct.droid.provider.movies.R.drawable.filter_popular_now, pct.droid.provider.movies.R.string.sorter_vodo_popularity, SORTER_SEEDS),
            new NavItem(pct.droid.provider.movies.R.drawable.filter_top_rated, pct.droid.provider.movies.R.string.sorter_vodo_rating, SORTER_RATING),
            new NavItem(pct.droid.provider.movies.R.drawable.filter_release_date, pct.droid.provider.movies.R.string.sorter_vodo_year, SORTER_YEAR),
            new NavItem(pct.droid.provider.movies.R.drawable.filter_updated, pct.droid.provider.movies.R.string.sorter_vodo_date_added, SORTER_DATE_ADDED),
            new NavItem(pct.droid.provider.movies.R.drawable.filter_a_to_z, pct.droid.provider.movies.R.string.sorter_vodo_alphabet, SORTER_TITLE)
    );

    private static final List<Sorter> SORTERS = Arrays.asList(SORTER_SEEDS, SORTER_YEAR, SORTER_DATE_ADDED, SORTER_RATING, SORTER_TITLE,
            SORTER_TRENDING);

    private static final int ITEMS_PER_PAGE = 50;

    private final MoviesService moviesService;

    public MoviesProvider(final MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @NonNull @Override public Single<ItemsWrapper> items(@Nullable final Filter filter, @Nullable Pager pager) {

        String query = null;
        String genre = null;
        String sorter = null;

        if (filter != null) {
            if (filter.getGenre() != null) {
                genre = filter.getGenre().getKey();
            }

            if (filter.getSorter() != null) {
                sorter = filter.getSorter().getKey();
            }

            query = filter.getQuery();
        }

        final int page;
        if (pager != null && pager.getEndCursor() != null) {
            page = Integer.parseInt(pager.getEndCursor());
        } else {
            page = 1;
        }

        return moviesService.fetchMovies(page, query, genre, sorter, -1, null, ITEMS_PER_PAGE)
                .flatMapObservable(Observable::fromArray)
                .map(this::mapApiMovie)
                .cast(Media.class)
                .toList()
                .map(m -> new ItemsWrapper(m, new Paging(String.valueOf(page + 1), m.size() >= ITEMS_PER_PAGE)));
    }

    @NonNull @Override public Single<Media> detail(final Media media) {
        return Single.just(media);
    }

    @NonNull @Override public Maybe<List<Sorter>> sorters() {
        return Maybe.just(SORTERS);
    }

    @NonNull @Override public Maybe<List<Genre>> genres() {
        return Maybe.just(GENRES);
    }

    @NonNull @Override public Maybe<List<NavItem>> navigation() {
        return Maybe.just(NAV_ITEMS);
    }

    @NonNull @Override public Single<Optional<Sorter>> getDefaultSorter() {
        return Single.just(Optional.of(SORTER_TRENDING));
    }

    private Movie mapApiMovie(@NonNull ApiMovie apiMovie) {

        Map<String, Map<String, ApiTorrent>> torrents = apiMovie.getTorrents();

        Map<String, ApiTorrent> lang = torrents.get("en");
        if (lang == null) {
            torrents.get(torrents.keySet().iterator().next());
        }

        List<Torrent> t = new ArrayList<>();

        for (final String size : lang.keySet()) {
            Format format = parseFormat(size);
            ApiTorrent apiTorrent = lang.get(size);

            t.add(new Torrent(apiTorrent.getUrl(), format, ((int) apiTorrent.getSize()), -1, apiTorrent.getPeer(), apiTorrent.getSeed()));
        }
        Torrent[] torrentArray = new Torrent[t.size()];
        t.toArray(torrentArray);

        return new Movie(apiMovie.getId(), apiMovie.getTitle(), apiMovie.getYear(), new Genre[0],
                apiMovie.getRating().getPercentage() / 100F, apiMovie.getImages().getPoster(), apiMovie.getImages().getBanner(),
                apiMovie.getSynopsis(), torrentArray, apiMovie.getTrailer());
    }

    private Format parseFormat(@Nullable String apiQuality) {
        int formatType;
        int quality;
        if ("3D".equals(apiQuality)) {
            formatType = Format.FORMAT_3D;
            quality = 0;
        } else {
            formatType = Format.FORMAT_NORMAL;
            if (apiQuality != null) {
                try {
                    quality = Integer.parseInt(apiQuality.substring(0, apiQuality.indexOf('p')));
                } catch (NumberFormatException e) {
                    quality = 0;
                }
            } else {
                quality = 0;
            }
        }

        return new Format(quality, formatType);
    }

}
