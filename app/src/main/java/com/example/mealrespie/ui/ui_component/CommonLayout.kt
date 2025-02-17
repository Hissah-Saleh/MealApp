package com.example.mealrespie.ui.ui_component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonLayout (
    title: String="",
    showBackButton: Boolean= true,
    showSearchButton: Boolean= true,
    onBackClick: ()-> Unit= {},
    onSearchClick:()-> Unit={},
    content: @Composable ((PaddingValues) -> Unit)
){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),

        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
    TopAppBar(
        windowInsets = WindowInsets(top = 0.dp),
        title = {
            Text(
                title,
                maxLines = 1,
            )
        },
        navigationIcon = {

            if (showBackButton)
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
        },
        actions = {
            if (showSearchButton)
                IconButton(onClick = { onSearchClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
        },
    )

        },
    ) { innerPadding ->
        content(innerPadding)
    }
}