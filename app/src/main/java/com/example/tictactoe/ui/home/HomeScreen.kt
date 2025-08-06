package com.example.tictactoe.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tictactoe.R

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), navigateToGame: () -> Unit) {

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { homeViewModel.onCreateGame() }) { Text(stringResource(R.string.home_button_create_game)) }
        Spacer(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(1f))
        TextField(
            onValueChange = { homeViewModel.onValueChangeSearchGame() },
            value = uiState.searchGameValue
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { homeViewModel.onSearchGame() }) { Text(stringResource(R.string.home_button_search_game)) }
        Spacer(modifier = Modifier.weight(1f))

    }
}

