package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marvelcharapp.presentation.widgets.ImageCustom


@Composable
fun CharacterItem(
    character: CharacterUIModel,
    onCharacterClicked: (CharacterUIModel) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCharacterClicked(character) }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageCustom(imageUrl = character.image.getImageUrl())
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = character.name
        )
    }
}