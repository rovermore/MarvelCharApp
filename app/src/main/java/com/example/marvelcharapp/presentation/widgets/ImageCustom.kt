package com.example.marvelcharapp.presentation.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.marvelcharapp.R

@Composable
fun ImageCustom(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    size: Dp = 56.dp
) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .diskCachePolicy(CachePolicy.DISABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .error(R.drawable.ic_baseline_error_24)
            .fallback(R.drawable.ic_baseline_error_24)
            .build(),
        contentDescription = null,
        onSuccess = {},
        onLoading = {},
        onError = {},
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    )
}