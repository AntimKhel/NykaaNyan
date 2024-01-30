package com.nykaa.nykaacat.feature.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nykaa.nykaacat.utils.UIState

@Composable
fun CatsScreen(
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel = hiltViewModel<CatsViewModel>()
) {
    val cats by viewModel.catsUIStateFlow.collectAsStateWithLifecycle()
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Crossfade(
            targetState = cats,
            label = "cats_anim"
        ) { uiState ->
            when (uiState) {
                is UIState.Error -> Shimmer()
                UIState.Loading -> Shimmer()
                is UIState.Success -> LazyColumn(
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.data?.let { list ->
                        items(
                            items = list,
                            key = { it.id }
                        ) {
                            AsyncImage(
                                model = it.url,
                                contentDescription = "cat"
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun Shimmer() {
    CircularProgressIndicator()
}