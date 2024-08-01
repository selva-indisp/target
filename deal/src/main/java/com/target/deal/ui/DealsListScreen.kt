package com.target.deal.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.target.deal.R
import com.target.deal.designsystem.component.LifecycleObserver
import com.target.deal.designsystem.component.TDSLoader
import com.target.deal.designsystem.component.TargetAppBar
import com.target.deal.designsystem.resource.FontSize
import com.target.deal.designsystem.resource.Green40
import com.target.deal.designsystem.resource.Size
import com.target.deal.ui.DealsListViewModel.Event
import com.target.deal.ui.DealsListViewModel.SideEffect
import com.target.deal.ui.DealsListViewModel.State
import com.target.deal.ui.model.PresentableDeal
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DealsListScreen(
    stateFlow: StateFlow<State>,
    sideEffectFlow: StateFlow<SideEffect>,
    navController: NavController,
    onEvent: (Event) -> Unit,
) {
    val state by stateFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        sideEffectFlow.collectLatest { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowError -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_LONG).show()
                    onEvent(Event.OnErrorShown)
                }
                SideEffect.Idle -> {}
            }
        }
    }

    LifecycleObserver(onCreate = { onEvent(Event.OnScreenCreated) })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TargetAppBar(navController = navController, title = "Deals") }
    ) { contentPadding ->
        if (state.isLoading)
            TDSLoader()
        DealsListComp(
            list = state.dealsList,
            padding = contentPadding,
            onClick = { onEvent(Event.OnDealClicked(it)) }
        )
    }
}

@Composable
private fun DealsListComp(
    list: PersistentList<PresentableDeal>,
    padding: PaddingValues,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize().padding(padding), verticalArrangement = Arrangement.spacedBy(Size.medium)
    ) {
        items(count = list.size, key = { list[it].id }) {
            val deal = list[it]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Size.medium)
                    .clickable(onClick = { onClick(deal.id) })
            ) {
                AsyncImage(
                    model = deal.imageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(Size.medium))
                )
                Spacer(modifier = Modifier.width(Size.large))
                Column {
                    PriceComp(
                        modifier = Modifier,
                        regularPrice = deal.regularPrice,
                        salePrice = deal.salePrice
                    )
                    FulFillmentComp(Modifier, fulfillment = deal.fulfillment)
                    NameComp(modifier = Modifier, name = deal.title)
                    AvailabilityComp(
                        modifier = Modifier,
                        availability = deal.availability,
                        aisle = deal.aisle
                    )
                }
            }
            if (it != list.lastIndex)
                Divider()
        }
    }
}

@Composable
private fun AvailabilityComp(modifier: Modifier, availability: String, aisle: String) {
    Spacer(modifier = modifier.height(Size.medium))
    Row {
        Text(text = availability, fontSize = FontSize.small, color = Green40)
        Spacer(modifier = modifier.width(Size.xSmall))
        Text(text = "in $aisle", fontSize = FontSize.small)
    }
}

@Composable
private fun NameComp(modifier: Modifier, name: String) {
    Spacer(modifier = modifier.height(Size.medium))
    Text(text = name, fontSize = FontSize.medium)
}