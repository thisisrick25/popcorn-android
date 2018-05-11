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

import butter.droid.provider.base.ProviderScope;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import pct.droid.provider.tv.api.TvService;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TvModule {

    @Provides @ProviderScope @Tv HttpUrl providerUrl() {
        return HttpUrl.parse("http://tv-v2.api-fetch.website/");
    }

    @Provides @ProviderScope @Tv CallAdapter.Factory provideCallAdapter() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides @ProviderScope @Tv Gson provideGson() {
        return new Gson();
    }

    @Provides @ProviderScope @Tv Converter.Factory provideConverter(@Tv Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides @ProviderScope @Tv Retrofit provideRetrofit(OkHttpClient client, @Tv HttpUrl url, @Tv CallAdapter.Factory callAdapter,
            @Tv Converter.Factory converter) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addCallAdapterFactory(callAdapter)
                .addConverterFactory(converter)
                .build();
    }

    @Provides @ProviderScope @Tv TvService provideTvService(@Tv Retrofit retrofit) {
        return retrofit.create(TvService.class);
    }

    @Provides @ProviderScope TvProvider provideTv(@Tv TvService service) {
        return new TvProvider(service);
    }

}
