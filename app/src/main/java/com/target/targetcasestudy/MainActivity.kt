package com.target.targetcasestudy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.target.designsystem.resource.TargetTheme
import com.target.deal.ui.DealDetailScreen
import com.target.deal.ui.DealDetailViewModel
import com.target.deal.ui.DealsListScreen
import com.target.deal.ui.DealsListViewModel
import com.target.deal.ui.route.DealsRoute
import com.target.deal.ui.route.DealsRouter
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TargetTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DealsRoute.DealsList.name
                ) {
                    composable(DealsRoute.DealsList.name) {
                        val router: DealsRouter = get { parametersOf(navController) }
                        val dealsListViewModel: DealsListViewModel = koinViewModel()
                        dealsListViewModel.updateRouter(router)
                        DealsListScreen(
                            stateFlow = dealsListViewModel.screenStateFlow,
                            sideEffectFlow = dealsListViewModel.sideEffectFlow,
                            navController = navController,
                            onEvent = dealsListViewModel::onEvent
                        )
                    }

                    composable(DealsRoute.DealDetail.name,
                        arguments = listOf(navArgument("dealId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val dealId = backStackEntry.arguments?.getInt("dealId") ?: 0
                        val dealDetailViewModel: DealDetailViewModel =
                            koinViewModel { parametersOf(dealId) }
                        DealDetailScreen(
                            stateFlow = dealDetailViewModel.screenStateFlow,
                            sideEffectFlow = dealDetailViewModel.sideEffectFlow,
                            navController = navController,
                            onEvent = dealDetailViewModel::onEvent
                        )
                    }
                }

            }
        }
    }
}
