package com.example.meal.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.meal.R
import com.example.mealrespie.ui.ui_component.CommonLayout
import com.example.mealrespie.ui.ui_component.ErrorUI
import com.example.mealrespie.ui.ui_component.LoadingIndicator
import com.example.mealrespie.ui.ui_component.MealDetailsUI
import com.example.mealrespie.ui.ui_component.NoInternet
import com.example.mealrespie.ui.ui_component.NoResultUI
import com.example.mealrespie.ui.ui_component.UIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    popBack: () -> Unit
) {
    var searchQuery = remember { mutableStateOf("") }
    val mealDetailsState by viewModel.mealDetailsState.collectAsState()


    CommonLayout(
        title = LocalContext.current.getString(R.string.search),
        showSearchButton = false,
        onBackClick = popBack
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = { viewModel.searchMeals(searchQuery.value) },
                active = false,
                onActiveChange = { },
                windowInsets = WindowInsets(top = 0.dp),
                colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                trailingIcon = {
                    if (searchQuery.value.isNotEmpty())
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                searchQuery.value = ""
                            }
                        )
                },

                tonalElevation = 0.dp,
            ) {}

            when (val detailsResult = mealDetailsState) {
                is UIState.Error ->
                    ErrorUI{
                        viewModel.searchMeals(searchQuery.value)
                    }

                UIState.Loading ->
                    LoadingIndicator()

                UIState.NoInternetConnection ->
                    NoInternet(Modifier.fillMaxSize()) { viewModel.searchMeals(searchQuery.value) }

                is UIState.Success -> {

                    Column(Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()) {

                        detailsResult.data?.forEachIndexed { index, item ->
                            if (item != null)
                                Card(Modifier.padding(bottom = 10.dp)) {
                                MealDetailsUI(modifier = Modifier.padding(16.dp), result = item)
                                }
                        }

                    }

                }

                UIState.EmptyResult -> NoResultUI()
                else -> {}
            }

        }
    }
}