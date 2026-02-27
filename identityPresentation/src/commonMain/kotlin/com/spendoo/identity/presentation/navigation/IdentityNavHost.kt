package com.spendoo.identity.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spendoo.designsystem.components.text.Text
import com.spendoo.home.api.HomeFeatureApi
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun IdentityNavHost(
    homeFeatureApi: HomeFeatureApi = koinInject(),
    updateBottomNavigationVisibility: (Boolean) -> Unit = {},
    startDestination: BaseRoute = HomeRoute
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navController.currentBackStack.collectLatest {
            if (navController.currentDestination?.route in routsWithBottomNavigation) {
                updateBottomNavigationVisibility(true)
            } else {
                updateBottomNavigationVisibility(false)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<OnBoardingRoute> {
                Box(
                    Modifier.fillMaxSize().background(Color.Red),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Column {
                        Text("OnBoarding", LocalTextStyle.current)
                        Text("finish", LocalTextStyle.current, modifier = Modifier.clickable {
                            navController.navigate(LoginRoute) {
                                popUpTo(OnBoardingRoute) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }
            }
            composable<HomeRoute> {
                homeFeatureApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility
                )
            }
            composable<LoginRoute> {
                Box(
                    Modifier.fillMaxSize().background(Color.Green),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Column {
                        Text("Login", LocalTextStyle.current)
                        Text(
                            "go to sign up",
                            LocalTextStyle.current,
                            modifier = Modifier.clickable {
                                navController.navigate(SignUpRoute)
                            })
                    }
                }
            }
            composable<SignUpRoute> {
                Box(
                    Modifier.fillMaxSize().background(Color.Blue),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Column {
                        Text("SignUp", LocalTextStyle.current)
                        Text("go to home", LocalTextStyle.current, modifier = Modifier.clickable {
                            navController.navigate(HomeRoute) {
                                popUpTo(SignUpRoute) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

private val routsWithBottomNavigation = listOf(
    HomeRoute::class.qualifiedName,
)