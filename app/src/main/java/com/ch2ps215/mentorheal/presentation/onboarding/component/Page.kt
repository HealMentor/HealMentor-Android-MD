package com.ch2ps215.mentorheal.presentation.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun Page(
    painter: Painter,
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Image(
            painter = painter,
            contentDescription = title,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .widthIn(80.dp, 180.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            contentScale = ContentScale.FillWidth

        )
        Text(
            text = subTitle,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PagePreview() {
    MentorhealTheme {
        Page(
            painter = painterResource(R.drawable.ic_onboarding1),
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            subTitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
        )
    }
}
