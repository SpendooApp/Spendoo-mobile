package com.spendoo.identity.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spendoo.designsystem.components.text.Text
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.identity.presentation.screen.signup.SignUpScreen
import org.koin.compose.koinInject

@Composable
fun IdentityNavGraph(
    navController: NavHostController,
    startDestination: BaseRoute,
    updateBottomNavigationVisibility: (Boolean) -> Unit,
    homeFeatureApi: HomeFeatureApi = koinInject(),
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
        composable<SignUpRoute> { SignUpScreen() }
        composable<ProfileRoute> {
            Box(
                Modifier.fillMaxSize().background(Color.Blue),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Column {
                    Text("Profile", LocalTextStyle.current)
                }
            }
        }
    }
}