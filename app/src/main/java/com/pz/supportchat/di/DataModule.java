package com.pz.supportchat.di;

import android.app.Application;
import com.pz.supportchat.storage.SharedPreferencesKeyValueStorage;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;


@Module(
        complete = false,
        library = true
)
final class DataModule {

    private static final String STORAGE_FILE_NAME = "SHARED_PREFERENCE_STORAGE";

    @Provides
    @Singleton
    SharedPreferencesKeyValueStorage provideSharedPreferenceStorage(final Application application) {
        return new SharedPreferencesKeyValueStorage(application, STORAGE_FILE_NAME);
    }
}
