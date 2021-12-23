package com.zt.common

import com.zt.common.bean.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @IntoSet
    @Provides
    fun provideUser(): User {
        return User()
    }

    @IntoSet
    @Provides
    fun provideString(): String {
        return "app"
    }

    @IntoSet
    @Provides
    fun provideInt(): Int {
        return 0
    }

}
