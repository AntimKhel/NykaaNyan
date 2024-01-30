package com.nykaa.nykaacat.feature.dashboard

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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
                is UIState.Error -> LoadingAnimation()
                UIState.Loading -> LoadingAnimation()
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
fun LoadingAnimation() {
    val animation = rememberInfiniteTransition(label = "load_anim")
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(progress)
                .alpha(1f - progress)
                .border(
                    20.dp,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    shape = CircleShape
                )
        )
    }
}