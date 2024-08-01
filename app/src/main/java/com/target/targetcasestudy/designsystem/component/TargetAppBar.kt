package com.target.targetcasestudy.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetAppBar(navController: NavController, title: String, modifier: Modifier = Modifier) {
    val currentBackStackEntry = navController.currentBackStackEntry
    val canNavigateBack = currentBackStackEntry?.destination?.route != null && navController.previousBackStackEntry != null

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (canNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        }
    )
}