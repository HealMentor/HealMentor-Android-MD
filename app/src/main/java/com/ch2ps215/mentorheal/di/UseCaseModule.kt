package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.data.repository.FormRepository
import com.ch2ps215.mentorheal.domain.repository.IFormRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import com.ch2ps215.mentorheal.domain.usecase.DarkThemeUseCase
import com.ch2ps215.mentorheal.domain.usecase.EditUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.FormUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.OnboardingUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignInUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignOutUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignUpUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateEmailUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateGenderUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateNameUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidatePasswordUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateYesNoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase(
            errorBlankMessage = R.string.error_name_is_required,
            errorMinMessage = R.string.error_name_min_char,
            errorMaxMessage = R.string.error_name_max_char
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateFormUseCase() : ValidateFormUseCase {
        return ValidateFormUseCase(
            errorBlankMessage = R.string.error_form_is_required,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateYesNoUseCase() : ValidateYesNoUseCase {
        return ValidateYesNoUseCase(
            errorBlankMessage = R.string.error_form_is_required,
            errorInvalidMessage = R.string.error_invalid_is_required
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateGenderUseCase() : ValidateGenderUseCase {
        return ValidateGenderUseCase(
            errorBlankMessage = R.string.error_form_is_required,
            errorInvalidMessage = R.string.error_invalid_is_required
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(
            errorBlankMessage = R.string.error_password_is_required,
            errorMinMessage = R.string.error_password_min_char,
            errorMaxMessage = R.string.error_password_max_char,
            errorNotMatch = R.string.error_password_not_match
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase(
            errorBlankEmail = R.string.error_email_is_required,
            errorIncorrectMessage = R.string.error_invalid_email
        )
    }

    @Provides
    @ViewModelScoped
    fun provideFormUseCase(formRepository: IFormRepository): FormUseCase {
        return FormUseCase(formRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        userRepository: UserRepository): SignInUseCase {
        return SignInUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(userRepository: UserRepository): SignUpUseCase {
        return SignUpUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEditUserUseCase(userRepository: UserRepository): EditUserUseCase {
        return EditUserUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignOutUseCase(userRepository: UserRepository): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideOnboardingUseCase(userRepository: UserRepository): OnboardingUseCase {
        return OnboardingUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDarkThemeUseCase(userRepository: UserRepository): DarkThemeUseCase {
        return DarkThemeUseCase(userRepository)
    }
}
