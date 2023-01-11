package com.ot.playground.techtest.di

import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.repository.GithubRepoRepository.GithubRepoRepositoryImpl
import com.ot.playground.techtest.utils.coroutines.ApplicationDispatcherProvider
import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import com.ot.playground.techtest.viewmodel.DetailViewModel
import com.ot.playground.techtest.viewmodel.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FeedViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    single<GithubRepoRepository> { GithubRepoRepositoryImpl(get()) }
}

val coroutinesModule = module {
    single<DispatcherProvider> { ApplicationDispatcherProvider() }
}


val playground_app = listOf(appModule, coroutinesModule)