package com.donation.fda.presentation.ui.dashboard.donor

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material3.Divider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.donation.fda.theme.Purple40
import com.donation.fda.theme.backgroundLayoutColor
import com.donation.fda.theme.black
import com.donation.fda.theme.gray
import com.donation.fda.theme.green
import com.donation.fda.theme.primaryColor
import com.donation.fda.theme.white

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DonorBtnNavBarViewScreen() {
    val navController = rememberNavController()
    var isPostActive by remember { mutableStateOf(false) } // Remember the state
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape,
                elevation = 0.dp,
                backgroundColor = white,
            ) {
                BottomNav(navController = navController, onClick = { isPostActive = false })
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                contentColor = primaryColor,
                onClick = {
                    ScreenList.Post.route.let {
                        navController.navigate(it) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        isPostActive = true
                    }
                    ScreenList.Post.route.let {
                        navController.navigate(it)
                    }
                },
                backgroundColor = if (isPostActive) {
                    primaryColor
                } else {
                    gray // Change to your desired color
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AddToPhotos,
                    contentDescription = "Add icon",
                    tint = if (isPostActive) {
                        white
                    } else {
                        Color.White // Change to your desired color
                    }
                )
            }
        }
    ) {
        MainScreenNavigation(navController)
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun BottomNav(navController: NavController, onClick: () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = white, contentColor = primaryColor, modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    screen?.icon?.let { it ->
                        Icon(
                            imageVector = it,
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = if (currentRoute?.hierarchy?.any { it.route == screen.route } == true) {
                                primaryColor
                            } else {
                                gray
                            }
                        )
                    }
                },
                label = {
                    screen?.title?.let { it ->
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute?.hierarchy?.any { it.route == screen.route } == true) {
                                primaryColor
                            } else {
                                gray
                            }
                        )
                    }
                },
                selected = currentRoute?.hierarchy?.any { it.route == screen?.route } == true,
                onClick = {
                    screen?.route?.let { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    onClick()
                }
            )
        }
    }
}

@Composable
fun MainScreenNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = BtnNavScreen.Home.route) {
        //home
        composable(BtnNavScreen.Home.route) {
            HomeScreen()
        }

        //History
        composable(BtnNavScreen.History.route) {
            HistoryScreen()
        }

        //post
        composable(ScreenList.Post.route) {
            PostScreen()
        }

        //profile
        composable(BtnNavScreen.Profile.route) {
            ProfileScreen()
        }

        //setting
        composable(BtnNavScreen.Setting.route) {
            SettingScreen()
        }
    }
}


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLayoutColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            content = {
                items(30) {
                    Text(text = "HomeScreen", color = Color.Black)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Divider()
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                }
            }
        )
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLayoutColor), contentAlignment = Alignment.Center
    ) {
        Text(text = "ProfileScreen", color = Color.White)
    }
}

@Composable
fun PostScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "CameraScreen", color = Color.White)
    }
}

@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "PickupScreen", color = Color.White)
    }
}

@Composable
fun SettingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "SettingScreen", color = Color.White)
    }
}