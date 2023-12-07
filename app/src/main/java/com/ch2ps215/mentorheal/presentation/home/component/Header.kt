package com.ch2ps215.mentorheal.presentation.home.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ch2ps215.mentorheal.R
import java.util.*

@Composable
fun Header(
    modifier: Modifier = Modifier,
    name: String? = null,
    photo: String?,
    onClickShowMore: () -> Unit,
    onClickProfileImage: () -> Unit
) {
    Surface(
    color = MaterialTheme.colorScheme.surface,

    ) {
        Row {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.5F))
                    .clickable(onClick = onClickProfileImage)
            )

            Column(modifier = modifier.animateContentSize()) {
                val firstName = remember(name) {
                    name
                        ?.split(" ")
                        ?.firstOrNull()
                        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                }

                Text(
                    text = if (firstName == null) " " else stringResource(R.string.welcome_home, firstName),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.date_format, "1", "1", "2021"),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
