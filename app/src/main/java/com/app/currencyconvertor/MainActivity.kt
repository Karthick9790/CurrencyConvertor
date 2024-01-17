package com.app.currencyconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.currencyconvertor.ui.screen.convertor.ConversionScreen
import com.app.currencyconvertor.ui.screen.iban.IBANScreen
import com.app.currencyconvertor.ui.theme.CurrencyConvertorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CurrencyConvertorTheme {
                var navigationSelectedItem by remember {
                    mutableStateOf(0)
                }

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            com.app.currencyconvertor.ui.screen.bottomnavigation.BottomNavigationItem()
                                .bottomNavigationItems().forEachIndexed { index, navigationItem ->

                                NavigationBarItem(
                                    selected = index == navigationSelectedItem,
                                    label = {
                                        Text(navigationItem.label)
                                    },
                                    icon = {
                                        Icon(
                                            navigationItem.icon,
                                            contentDescription = navigationItem.label
                                        )
                                    },
                                    onClick = {
                                        navigationSelectedItem = index
                                        navController.navigate(navigationItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = Screens.Convertor.route,
                        modifier = Modifier.padding(paddingValues = paddingValues)) {
                        composable(Screens.Convertor.route) {
                            ConversionScreen()
                        }
                        composable(Screens.IBAN.route) {
                            IBANScreen()
                        }
                    }
                }
            }
        }
    }
}

sealed class Screens(val route : String) {
    object Convertor : Screens("convertor_route")
    object IBAN : Screens("iban_route")
}