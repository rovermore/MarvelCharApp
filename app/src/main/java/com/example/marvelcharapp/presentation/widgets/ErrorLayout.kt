package com.example.marvelcharapp.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marvelcharapp.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    onReloadCLicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(6.dp),
            painter = painterResource(id = R.drawable.ic_baseline_error_24),
            contentDescription = "error logo"
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = stringResource(id = R.string.error_string),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Button(
            onClick = { onReloadCLicked() },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Blue, contentColor = Color.White)
        ){
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = stringResource(id = R.string.reload),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}

@Preview(widthDp = 340, showBackground = true , backgroundColor = 0xFFFFFF)
@Composable
private fun ErrorPreview() {
    ErrorView(onReloadCLicked = {})
}