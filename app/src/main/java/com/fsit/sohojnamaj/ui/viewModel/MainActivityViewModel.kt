/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.PrayerPreferenceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: PrayerSettingRepository,
) : ViewModel() {
    val loadingState: StateFlow<LoadingActivityUiState> = MutableStateFlow(LoadingActivityUiState.Loading)
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.prayerPreferenceData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )


    }


sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val userData: PrayerPreferenceModel) : MainActivityUiState
}
sealed interface LoadingActivityUiState {
    object Loading : LoadingActivityUiState
    object Complete: LoadingActivityUiState
}
