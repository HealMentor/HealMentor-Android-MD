package com.ch2ps215.mentorheal.presentation.common.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
inline fun TwoLineDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.weight(1F),
            thickness = thickness,
        )
        content()
        Divider(
            modifier = Modifier.weight(1F),
            thickness = thickness,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TwoLineDivider() {
    MentorhealTheme {
        TwoLineDivider {
            Text(text = "Sign in with google")
        }
    }
}
