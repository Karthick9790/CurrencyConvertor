package com.app.currencyconvertor.ui.screen.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.currencyconvertor.Screens

@Composable
fun AppBottomNavigation() {


}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Convertor",
                icon = Icons.Filled.Home,
                route = Screens.Convertor.route
            ),
            BottomNavigationItem(
                label = "IBAN",
                icon = Icons.Filled.Check,
                route = Screens.IBAN.route
            ),
        )
    }
}