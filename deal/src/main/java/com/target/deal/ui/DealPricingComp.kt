package com.target.deal.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.target.deal.designsystem.resource.FontSize
import com.target.deal.designsystem.resource.Red40
import com.target.deal.designsystem.resource.Size

@Composable
fun PriceComp(modifier: Modifier = Modifier, regularPrice: String, salePrice: String) {
    if (salePrice.isEmpty() && regularPrice.isEmpty())
        return

    Row(modifier = modifier, verticalAlignment = Alignment.Bottom) {
        if (salePrice.isEmpty())
            Text(text = regularPrice, fontSize = FontSize.large, fontWeight = FontWeight.W700)
        else {
            Text(
                text = salePrice,
                fontSize = FontSize.large,
                fontWeight = FontWeight.W700,
                color = Red40
            )
            Spacer(modifier = modifier.width(Size.medium))
            Text(text = "reg. $regularPrice", fontSize = FontSize.medium)
        }
    }
}


@Composable
fun FulFillmentComp(modifier: Modifier = Modifier, fulfillment: String) {
    Spacer(modifier = modifier.height(Size.xxSmall))
    Text(text = fulfillment, fontSize = FontSize.small)
}