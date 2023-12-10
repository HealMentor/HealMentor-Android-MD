package com.ch2ps215.mentorheal.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.presentation.common.component.noRippleClickable
import com.ch2ps215.mentorheal.presentation.common.component.shimmer
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import java.util.Date

private val ContentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    label: String,
    articles: List<Article>,
    onClickShowMore: () -> Unit,
    onClickArticle: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.noRippleClickable(onClick = onClickShowMore),
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(R.string.see_more),
                color = MaterialTheme.colorScheme.primary,
            )
        }
        if (articles.isEmpty()) {
            ArticlesPlaceholder()
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = ContentPadding
            ) {
                items(items = articles, key = { it.id }) { article ->
                    if (article.photo.isNotEmpty()) {
                        Article(
                            modifier = Modifier.width(240.dp),
                            id = article.id,
                            title = article.title,
                            photo = article.photo[0],
                            onClick = onClickArticle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticlesPlaceholder(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentSize(align = Alignment.TopStart, unbounded = true)
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) {
            ArticlePlaceholder(
                modifier = Modifier
                    .width(240.dp)
                    .shimmer(
                        shape = MaterialTheme.shapes.medium,
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesPreview() {
    MentorhealTheme {
        Articles(
            label = stringResource(R.string.latest_articles),
            articles = (0..10).map {
                Article(
                    id = 1,
                    title = "title $it",
                    body = "",
                    createdAt = Date(),
                    updatedAt = Date(),
                    source = "",
                    photo = listOf("https://i.pinimg.com/564x/d7/f8/5e/d7f85e8343547676774a4ffdffc96143.jpg"),
                    author = "",
                    liked = false
                )
            },
            onClickShowMore = { },
            onClickArticle = { }
        )
    }
}
