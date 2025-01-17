package com.target.deal.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.target.deal.R
import com.target.designsystem.component.LifecycleObserver
import com.target.designsystem.component.TDSLoader
import com.target.designsystem.component.TargetAppBar
import com.target.designsystem.resource.FontSize
import com.target.designsystem.resource.Red40
import com.target.designsystem.resource.Size
import com.target.deal.ui.DealDetailViewModel.Event
import com.target.deal.ui.DealDetailViewModel.SideEffect
import com.target.deal.ui.DealDetailViewModel.State
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DealDetailScreen(
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
        topBar = {
            TargetAppBar(
                navController = navController,
                title = stringResource(id = R.string.title_details)
            )
        }
    ) { contentPadding ->
        if (state.isLoading)
            TDSLoader()
        state.deal?.let { deal ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Top
                ) {
                    Card(
                        modifier = Modifier.padding(all = Size.medium),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        DealImage(url = deal.imageUrl)
                        Spacer(modifier = Modifier.height(Size.xLarge))
                        Text(
                            text = deal.title,
                            fontSize = FontSize.large,
                            fontWeight = FontWeight.W400
                        )
                        Spacer(modifier = Modifier.height(Size.large))
                        PriceComp(regularPrice = deal.regularPrice, salePrice = deal.salePrice)
                        FulFillmentComp(fulfillment = deal.fulfillment)
                    }
                    Spacer(modifier = Modifier.height(Size.medium))
                    Card(
                        modifier = Modifier.padding(all = Size.medium),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = "Product details",
                            fontSize = FontSize.large,
                            fontWeight = FontWeight.W700
                        )
                        Spacer(modifier = Modifier.height(Size.large))
                        Text(text = deal.description)
                    }
                }

                AddToCartCta(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(all = Size.medium)
                )
            }
        }
    }
}

@Composable
fun DealImage(url: String, modifier: Modifier = Modifier) {
    if (url.isEmpty())
        return
    AsyncImage(
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        error = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(Size.medium))
    )
}

@Composable
private fun AddToCartCta(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { },
        colors = ButtonDefaults.buttonColors(containerColor = Red40),
        shape = RoundedCornerShape(Size.xSmall)
    ) {
        Text(
            text = stringResource(id = R.string.add_to_cart_cta),
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.W700
        )
    }
}