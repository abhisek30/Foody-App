package com.example.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//inject remoteDataSource inside Repository
//@ActivityRetainedScoped - Scope annotation for bindings that should exist for the life of an activity, surviving configuration.
//as Repository will be injected in viewModel added ActivityRetainedScope to survive configuration changes and having same instance
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}