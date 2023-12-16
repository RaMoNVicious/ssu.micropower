package com.ssu.micropower.app

import com.ssu.micropower.api.AuthInterceptor
import com.ssu.micropower.api.provideAuthApi
import com.ssu.micropower.api.provideLocationApi
import com.ssu.micropower.api.provideOkHttpClient
import com.ssu.micropower.api.provideRetrofit
import com.ssu.micropower.api.provideWeatherApi
import com.ssu.micropower.database.AppDatabase
import com.ssu.micropower.repositories.UserRepository
import com.ssu.micropower.usecases.AuthUseCase
import com.ssu.micropower.repositories.IUserRepository
import com.ssu.micropower.usecases.IAuthUseCase
import com.ssu.micropower.repositories.ILocationRepository
import com.ssu.micropower.usecases.ILocationUseCase
import com.ssu.micropower.api.INetworkManager
import com.ssu.micropower.repositories.IWeatherRepository
import com.ssu.micropower.usecases.IWeatherUseCase
import com.ssu.micropower.repositories.LocationRepository
import com.ssu.micropower.usecases.LocationUseCase
import com.ssu.micropower.api.NetworkManager
import com.ssu.micropower.repositories.WeatherRepository
import com.ssu.micropower.usecases.WeatherUseCase
import com.ssu.micropower.ui.auth.AuthViewModel
import com.ssu.micropower.ui.location.LocationViewModel
import com.ssu.micropower.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    factory { AuthInterceptor(get()) }

    factory { provideOkHttpClient(get()) }
    factory { provideAuthApi(get()) }
    factory { provideLocationApi(get()) }
    factory { provideWeatherApi(get()) }

    single { provideRetrofit(get()) }

    single<INetworkManager> { NetworkManager(androidContext()) }

    single<IUserRepository> { UserRepository(get(), get()) }
    single<ILocationRepository> { LocationRepository(get(), get()) }
    single<IWeatherRepository> { WeatherRepository(get(), get()) }

    single { AppDatabase.getInstance(androidApplication()).localWeatherDao() }
    single { AppDatabase.getInstance(androidApplication()).localUserDao() }
    single { AppDatabase.getInstance(androidApplication()).localLocationDao() }
}

val useCaseModule = module {
    single<IAuthUseCase> { AuthUseCase(get(), get(), get(), get(), get()) }
    single<ILocationUseCase> { LocationUseCase(get(), get(), get()) }
    single<IWeatherUseCase> { WeatherUseCase(get(), get()) }
}

val appModule = module {
    single { SessionManager(androidContext()) }
    viewModel { AuthViewModel(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { LocationViewModel(get()) }
}

