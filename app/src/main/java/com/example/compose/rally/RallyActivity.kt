package com.example.compose.rally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.components.RallyTabRow
import com.example.compose.rally.ui.overview.OverviewScreen
import com.example.compose.rally.ui.theme.RallyTheme

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
//        var currentScreen: RallyDestination by remember { mutableStateOf(Overview) }
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        // Fetch your currentDestination:
        val currentDestination = currentBackStack?.destination
        // Change the variable to this and use Overview as a backup screen if this returns null
        val currentScreen =
            rallyTabRowScreens.find { it.route == currentDestination?.route } ?: Overview

        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = rallyTabRowScreens,
                    // Pass the callback like this,
                    // defining the navigation action when a tab is selected:
                    onTabSelected = { newScreen ->
                        //Fixed multiple screens
                        navController.navigateSingleTopTo(newScreen.route)
//                        navController.navigate(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                //Nav Host
                RallyNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )

                /* Old Code. */
/*                NavHost(
                    navController = navController,
                    startDestination = Overview.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // builder parameter will be defined here as the graph
                    composable(route = Overview.route) {
                        OverviewScreen(
                            onAccountClick = { accountType ->
                                navController.navigateToSingleAccount(accountType)
                            }
                        )
                    }
                    composable(route = Accounts.route) {
                        AccountsScreen(
                            onAccountClick = { accountType ->
                                navController.navigateToSingleAccount(accountType)
                            }
                        )
                    }
                    composable(route = Bills.route) {
                        BillsScreen()
                    }
                    //sending nav args by pattern: "route/{argument}"
                    composable(
                        route = SingleAccount.routeWithArgs,
                        arguments = SingleAccount.arguments,
                        deepLinks = SingleAccount.deepLinks
                    ) { navBackStackEntry ->
                        // Retrieve the passed argument
                        val accountType =
                            navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)

                        // Pass accountType to SingleAccountScreen
                        SingleAccountScreen(accountType)
                    }
                }*/
            }
        }
    }


}



///* Extension Function For launch single top. */
//fun NavHostController.navigateSingleTopTo(route: String) =
//    this.navigate(route) {
//
//        //Pop backstack given destination - e.g. start destination
//        popUpTo(
//            this@navigateSingleTopTo.graph.findStartDestination().id
//        ) {
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }
//
//private fun NavHostController.navigateToSingleAccount(accountType: String) {
//    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
//}
