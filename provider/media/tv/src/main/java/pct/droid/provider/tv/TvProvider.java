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

package pct.droid.provider.tv;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import butter.droid.provider.AbsMediaProvider;
import butter.droid.provider.base.filter.Filter;
import butter.droid.provider.base.filter.Genre;
import butter.droid.provider.base.filter.Sorter;
import butter.droid.provider.base.model.Episode;
import butter.droid.provider.base.model.Format;
import butter.droid.provider.base.model.Media;
import butter.droid.provider.base.model.Season;
import butter.droid.provider.base.model.Show;
import butter.droid.provider.base.model.Torrent;
import butter.droid.provider.base.nav.NavItem;
import butter.droid.provider.base.paging.ItemsWrapper;
import butter.droid.provider.base.paging.Paging;
import butter.droid.provider.base.util.Optional;
import butter.droid.provider.filter.Pager;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import pct.droid.provider.tv.api.TvService;
import pct.droid.provider.tv.api.model.ApiEpisode;
import pct.droid.provider.tv.api.model.ApiShow;
import pct.droid.provider.tv.api.model.ApiShowDetail;
import pct.droid.provider.tv.api.model.ApiTorrent;

public class TvProvider extends AbsMediaProvider {

    private static final Sorter SORTER_TRENDING = new Sorter("trending", R.string.sorter_vodo_trending);
    private static final Sorter SORTER_SEEDS = new Sorter("popularity", R.string.sorter_vodo_popularity);
    private static final Sorter SORTER_YEAR = new Sorter("year", R.string.sorter_vodo_year);
    private static final Sorter SORTER_DATE_ADDED = new Sorter("last added", R.string.sorter_vodo_date_added);
    private static final Sorter SORTER_RATING = new Sorter("rating", R.string.sorter_vodo_rating);
    private static final Sorter SORTER_TITLE = new Sorter("name", R.string.sorter_vodo_alphabet);

    private static final List<Genre> GENRES = Arrays.asList(
//        new Genre("all", R.string.genre_all),
        new Genre("action", R.string.genre_action),
        new Genre("adventure", R.string.genre_adventure),
        new Genre("animation", R.string.genre_animation),
        new Genre("comedy", R.string.genre_comedy),
        new Genre("crime", R.string.genre_crime),
//        new Genre("disaster", R.string.genre_disaster),
        new Genre("documentary", R.string.genre_documentary),
        new Genre("drama", R.string.genre_drama),
//        new Genre("eastern", R.string.genre_eastern),
        new Genre("family", R.string.genre_family),
        new Genre("fantasy", R.string.genre_fantasy),
//        new Genre("fan-film", R.string.genre_fan_film),
        new Genre("film-noir", R.string.genre_film_noir),
        new Genre("history", R.string.genre_history),
//        new Genre("holiday", R.string.genre_holiday),
        new Genre("horror", R.string.genre_horror),
//        new Genre("indie", R.string.genre_indie),
        new Genre("music", R.string.genre_music),
        new Genre("mystery", R.string.genre_mystery),
//        new Genre("road", R.string.genre_road),
        new Genre("romance", R.string.genre_romance),
        new Genre("science-fiction", R.string.genre_sci_fi),
//        new Genre("short", R.string.genre_short),
        new Genre("sports", R.string.genre_sport),
//        new Genre("suspense", R.string.genre_suspense),
        new Genre("thriller", R.string.genre_thriller),
//        new Genre("tv-movie", R.string.genre_tv_movie),
        new Genre("war", R.string.genre_war),
        new Genre("western", R.string.genre_western)
    );

    private static final List<NavItem> NAV_ITEMS = Arrays.asList(
            new NavItem(R.drawable.filter_trending, R.string.sorter_vodo_trending, SORTER_TRENDING),
            new NavItem(R.drawable.filter_popular_now, R.string.sorter_vodo_popularity, SORTER_SEEDS),
            new NavItem(R.drawable.filter_top_rated, R.string.sorter_vodo_rating, SORTER_RATING),
            new NavItem(R.drawable.filter_release_date, R.string.sorter_vodo_year, SORTER_YEAR),
            new NavItem(R.drawable.filter_updated, R.string.sorter_vodo_date_added, SORTER_DATE_ADDED),
            new NavItem(R.drawable.filter_a_to_z, R.string.sorter_vodo_alphabet, SORTER_TITLE)
    );

    private static final List<Sorter> SORTERS = Arrays.asList(SORTER_SEEDS, SORTER_YEAR, SORTER_DATE_ADDED, SORTER_RATING, SORTER_TITLE,
            SORTER_TRENDING);

    private static final int ITEMS_PER_PAGE = 50;

    private final TvService tvService;

    public TvProvider(final TvService tvService) {
        this.tvService = tvService;
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

        return tvService.fetchShows(page, query, genre, sorter, -1, null, ITEMS_PER_PAGE)
                .flatMapObservable(Observable::fromArray)
                .map(this::mapApiShow)
                .cast(Media.class)
                .toList()
                .map(m -> new ItemsWrapper(m, new Paging(String.valueOf(page + 1), m.size() >= ITEMS_PER_PAGE)));
    }

    @NonNull @Override public Single<Media> detail(final Media media) {
        return Single.just(media)
                .map(media1 -> media.getId())
                .flatMap(tvService::fetchShow)
                .map(this::mapApiShowDetails);
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
        return Single.just(Optional.of(SORTER_SEEDS));
    }

    private Show mapApiShow(@NonNull ApiShow apiShow) {
        return new Show(apiShow.getId(), apiShow.getTitle(), apiShow.getYear(), new Genre[0],
                apiShow.getRating().getPercentage() / 100F, apiShow.getImages().getPoster(), apiShow.getImages().getBanner(),
                "", new Season[0]);
    }

    private Show mapApiShowDetails(@NonNull ApiShowDetail apiShow) {

        SparseArray<SparseArray<Episode>> episodes = new SparseArray<>(apiShow.getSeasons());
        for (final ApiEpisode apiEpisode : apiShow.getEpisodes()) {
            Map<String, ApiTorrent> torrents = apiEpisode.getTorrents();
            List<Torrent> t = new ArrayList<>();

            for (final String size : torrents.keySet()) {
                Format format = parseFormat(size);
                ApiTorrent apiTorrent = torrents.get(size);
                if (apiTorrent == null) {
                    continue;
                }

                t.add(new Torrent(apiTorrent.getUrl(), format, ((int) apiTorrent.getSize()), -1, apiTorrent.getPeer(), apiTorrent.getSeed()));
            }
            Torrent[] torrentArray = new Torrent[t.size()];
            t.toArray(torrentArray);

            Episode episode = new Episode(apiEpisode.getTvdbId(), apiEpisode.getTitle(), apiShow.getYear(), new Genre[0],
                    apiShow.getRating().getPercentage() / 100F, apiShow.getImages().getPoster(), apiShow.getImages().getBanner(),
                    apiEpisode.getOverview(), torrentArray, apiEpisode.getEpisode());

            SparseArray<Episode> episodeArray = episodes.get(apiEpisode.getSeason());
            if (episodeArray == null) {
                episodeArray = new SparseArray<>();
                episodes.put(apiEpisode.getSeason(), episodeArray);
            }

            episodeArray.put(apiEpisode.getEpisode(), episode);
        }

        Season[] seasons = new Season[episodes.size()];
        for (int i = 0; i < episodes.size(); i++) {
            int sk = episodes.keyAt(i);

            SparseArray<Episode> s = episodes.get(sk);
            Episode[] e = new Episode[s.size()];
            for (int j = 0; j < s.size(); j++) {
                e[j] = s.get(s.keyAt(j));
            }

            seasons[i] = new Season(apiShow.getId(), "Season " + i + 1, apiShow.getYear(), new Genre[0],
                    apiShow.getRating().getPercentage() / 100F, apiShow.getImages().getPoster(), apiShow.getImages().getBanner(),
                    apiShow.getSynopsis(), e);
        }

        return new Show(apiShow.getId(), apiShow.getTitle(), apiShow.getYear(), new Genre[0],
                apiShow.getRating().getPercentage() / 100F, apiShow.getImages().getPoster(), apiShow.getImages().getBanner(),
                apiShow.getSynopsis(), seasons);
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
                    if (apiQuality.endsWith("p")) {
                        apiQuality = apiQuality.substring(0, apiQuality.length() - 1);
                    }
                    quality = Integer.parseInt(apiQuality);
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
