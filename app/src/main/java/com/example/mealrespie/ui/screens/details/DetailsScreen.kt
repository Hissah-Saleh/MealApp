package com.example.mealrespie.ui.screens.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.mealrespie.ui.ui_component.CommonLayout
import com.example.mealrespie.ui.ui_component.ErrorUI
import com.example.mealrespie.ui.ui_component.LoadingIndicator
import com.example.mealrespie.ui.ui_component.MealDetailsUI
import com.example.mealrespie.ui.ui_component.NoInternet
import com.example.mealrespie.ui.ui_component.NoResultUI
import com.example.mealrespie.ui.ui_component.UIState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(
    argsId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    popBack: () -> Unit

) {

    val mealDetailsState by viewModel.mealDetailsState.collectAsState()

    LaunchedEffect(argsId) {
        viewModel.getMeal(argsId)
    }

    CommonLayout(
        onSearchClick = navigateToSearch,
        onBackClick = popBack,
    ) {

        when (val result = mealDetailsState) {
            is UIState.Error -> ErrorUI() {
                viewModel.getMeal(
                    argsId
                )
            }

            UIState.Loading -> LoadingIndicator()

            UIState.NoInternetConnection -> NoInternet(Modifier.fillMaxSize()) {
                viewModel.getMeal(
                    argsId
                )
            }

            is UIState.Success -> {
                if (result.data != null)
                    MealDetailsUI(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(16.dp),
                       result.data
                    )

            }

            UIState.EmptyResult -> {
                NoResultUI()
            }
        }
    }
}

