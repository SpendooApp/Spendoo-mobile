package com.spendoo.identity.presentation.screen.signup

import androidx.navigation.navOptions
import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.model.RegisterRequest
import com.spendoo.identity.domain.repository.AuthenticationRepository
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.identity.presentation.navigation.HomeRoute
import com.spendoo.identity.presentation.navigation.SignUpRoute
import com.spendoo.identity.presentation.shared.BaseViewModel
import kotlinx.datetime.LocalDate

class SignUpViewModel(
    private val registerRepository: RegisterRepository,
    private val authenticationRepository: AuthenticationRepository,
) : BaseViewModel<SignUpUiState>(SignUpUiState()), SignUpInteractionListener {
    override fun onSignUpClicked() {
        tryToCall(
            block = {
                registerRepository.register(
                    RegisterRequest(
                        email = "",
                        fullName = "",
                        birthDate = LocalDate(2000, 1, 1),
                        gender = Gender.MALE,
                        password = ""
                    )
                )
            },
            onSuccess = { registrationResponse ->
                println("Success: $registrationResponse")
                authenticationRepository.saveAuthTokens(
                    AuthenticationTokens(
                        accessToken = registrationResponse.accessToken,
                        refreshToken = registrationResponse.refreshToken
                    )
                )
                navigate(
                    HomeRoute,
                    navOptions = navOptions { popUpTo(SignUpRoute, inclusive = true) }
                )
            },
            onError = {
                println("Error: $it")
            }
        )
    }
}