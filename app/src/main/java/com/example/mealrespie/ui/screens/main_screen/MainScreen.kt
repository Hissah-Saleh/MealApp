package com.example.mealrespie.ui.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.meal.R
import com.example.mealrespie.data.models.MealItem
import com.example.mealrespie.ui.ui_component.CommonLayout
import com.example.mealrespie.ui.ui_component.ErrorUI
import com.example.mealrespie.ui.ui_component.LoadingIndicator
import com.example.mealrespie.ui.ui_component.NoInternet
import com.example.mealrespie.ui.ui_component.UIState


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit,
    navigateToSearch: () -> Unit
) {

    val meals by viewModel.meals.collectAsState()
    val category by viewModel.categories.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsState()


    LaunchedEffect(selectedIndex) {
        viewModel.getSelectedMealByCategory()
    }
    CommonLayout("", false, onSearchClick = navigateToSearch) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            when (val result = category) {
                is UIState.Success ->
                    ScrollableTabRow(
                        selectedTabIndex = selectedIndex,
                        modifier = Modifier.fillMaxWidth(),

                        tabs = {
                            result.data?.forEachIndexed { index, item ->
                                Tab(
                                    selected = selectedIndex == index,
                                    onClick = { viewModel.updateIndex(index) },
                                    text = { Text(text = item.category.orEmpty()) }
                                )
                            }
                        }

                    )

                UIState.Loading -> LoadingIndicator()
                is UIState.Error -> ErrorUI()
                is UIState.NoInternetConnection -> {
                    NoInternet(Modifier.fillMaxSize()) {
                        viewModel.getAllCategorizedMeals()
                        if (meals is UIState.NoInternetConnection)
                            viewModel.getSelectedMealByCategory()

                    }
                }

                else -> {}
            }

            when (val result = meals) {
                is UIState.Success -> result.data?.let {
                    LazyColumn(
                        Modifier.fillMaxSize(),
                    ) {
                        itemsIndexed(result.data) { index, item ->
                            MealItem(item) { id ->
                                if (id != null)
                                    navigateToDetails(id)
                            }

                            HorizontalDivider(thickness = 1.dp)
                        }
                    }
                }

                UIState.Loading -> LoadingIndicator()
                is UIState.Error -> ErrorUI()
                is UIState.NoInternetConnection -> {
                    NoInternet(Modifier.fillMaxSize()) {
                        viewModel.getSelectedMealByCategory()

                    }
                }

                else -> {}

            }


        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MealItem(item: MealItem?, onClick: (String?) -> Unit) {
    Row(Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth()
        .clickable { onClick(item?.mealId) }, verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = item?.mealThumb + "/preview",
            contentDescription = item?.mealName,
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape),
            failure = placeholder(R.drawable.error_svgrepo_com)
        )

        Text(item?.mealName.orEmpty(), modifier = Modifier.padding(start = 10.dp))

    }
}