package com.ch2ps215.mentorheal.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.presentation.main.BotNavMenus

@Composable
fun NavigationBarMain(
    onNavigation: (String) -> Unit,
    botNavVisibilityProvider: () -> Boolean,
    currentRouteProvider: (String) -> Boolean
) {
    AnimatedVisibility(
        visible = botNavVisibilityProvider(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column {
            NavigationBar(
                tonalElevation = 4.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {

                for (menu in BotNavMenus) {
                    val (title, icons, route) = menu
                    val (outlined, filled) = icons

                    val selected = currentRouteProvider(route)

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            onNavigation(route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (selected) filled else outlined,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(text = stringResource(title))
                        }
                    )
                }
            }
            Surface(tonalElevation = 4.dp) {
                val density = LocalDensity.current
                val navigationBarHeight = with(density) {
                    WindowInsets.navigationBars.getBottom(density).toDp()
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(navigationBarHeight)
                )
            }
        }
    }
}
