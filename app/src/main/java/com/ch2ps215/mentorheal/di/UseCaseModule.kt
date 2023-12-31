package com.ch2ps215.mentorheal.di

import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.TrackerRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import com.ch2ps215.mentorheal.domain.usecase.DarkThemeUseCase
import com.ch2ps215.mentorheal.domain.usecase.DetectExpressionUseCase
import com.ch2ps215.mentorheal.domain.usecase.DetectFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.EditUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetArticleUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetExpressionDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFavoriteArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFormDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetTrackerUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.LikingArticleUseCase
import com.ch2ps215.mentorheal.domain.usecase.OnboardingUseCase
import com.ch2ps215.mentorheal.domain.usecase.SaveTrackerUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignInUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignOutUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignUpUseCase
import com.ch2ps215.mentorheal.domain.usecase.UpdateDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateEmailUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateGenderUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateNameUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidatePasswordUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateTitleUseCase
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
    fun provideValidateTitleUseCase(): ValidateTitleUseCase {
        return ValidateTitleUseCase(
            errorBlankMessage = R.string.error_name_is_required,
            errorMinMessage = R.string.error_name_min_char,
            errorMaxMessage = R.string.error_name_max_char
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
    fun provideValidateFormUseCase(): ValidateFormUseCase {
        return ValidateFormUseCase(
            errorBlankMessage = R.string.error_field_required
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateGenderUseCase(): ValidateGenderUseCase {
        return ValidateGenderUseCase(
            errorBlankMessage = R.string.error_field_required
        )
    }

    @Provides
    @ViewModelScoped
    fun provideValidateYesNoUseCase(): ValidateYesNoUseCase {
        return ValidateYesNoUseCase(
            errorBlankMessage = R.string.error_field_required
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDetectFormUseCase(
        detectionRepository: DetectionRepository,
        userRepository: UserRepository
    ): DetectFormUseCase {
        return DetectFormUseCase(detectionRepository, userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFormUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): GetFormUseCase {
        return GetFormUseCase(userRepository,detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        userRepository: UserRepository
    ): SignInUseCase {
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

    @Provides
    @ViewModelScoped
    fun provideGetArticlesUseCase(articleRepository: ArticleRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): GetFormDetectionUseCase {
        return GetFormDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTrackerUseCase(
        userRepository: UserRepository,
        trackerRepository: TrackerRepository
    ): GetTrackerUseCase {
        return GetTrackerUseCase(userRepository, trackerRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetExpressionDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): GetExpressionDetectionUseCase {
        return GetExpressionDetectionUseCase(userRepository, detectionRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideDetectExpressionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): DetectExpressionUseCase {
        return DetectExpressionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateDetectionUseCase(
        userRepository: UserRepository,
        detectionRepository: DetectionRepository
    ): UpdateDetectionUseCase {
        return UpdateDetectionUseCase(userRepository, detectionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetArticleUseCase(
        articleRepository: ArticleRepository
    ): GetArticleUseCase {
        return GetArticleUseCase(articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteArticlesUseCase(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ): GetFavoriteArticlesUseCase {
        return GetFavoriteArticlesUseCase(userRepository, articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLikingArticleUseCase(
        userRepository: UserRepository,
        articleRepository: ArticleRepository
    ): LikingArticleUseCase {
        return LikingArticleUseCase(userRepository, articleRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveTrackerUseCase(
        userRepository: UserRepository,
        trackerRepository: TrackerRepository
    ): SaveTrackerUseCase {
        return SaveTrackerUseCase(userRepository, trackerRepository)
    }


}
