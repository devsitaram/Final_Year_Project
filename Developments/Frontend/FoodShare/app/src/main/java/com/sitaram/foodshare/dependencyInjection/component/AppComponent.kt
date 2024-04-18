package com.sitaram.foodshare.dependencyInjection.component

import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain.AdminHomeRepository
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain.AdminHomeUseCase
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseRepository
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseUseCase
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationRepository
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationUseCase
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersRepository
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailRepository
import com.sitaram.foodshare.features.dashboard.history.domain.HistoryRepository
import com.sitaram.foodshare.features.dashboard.home.domain.HomeRepository
import com.sitaram.foodshare.features.login.domain.LoginRepository
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.ManageAccountRepository
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordRepository
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileRepository
import com.sitaram.foodshare.features.register.domain.RegisterRepository
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailRepository
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersUseCase
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain.DonorHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain.DonorHistoryUseCase
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryUseCase
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain.DonationRatingRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.donationRating.domain.DonationRatingUseCase
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailUseCase
import com.sitaram.foodshare.features.dashboard.history.domain.HistoryUseCase
import com.sitaram.foodshare.features.dashboard.home.domain.HomeUseCase
import com.sitaram.foodshare.features.login.domain.LoginUseCase
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.ManageAccountUseCase
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordUseCase
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileUseCase
import com.sitaram.foodshare.features.dashboard.googleMap.domain.GoogleMapRepository
import com.sitaram.foodshare.features.dashboard.googleMap.domain.GoogleMapUseCase
import com.sitaram.foodshare.features.dashboard.setting.domain.SettingRepository
import com.sitaram.foodshare.features.dashboard.setting.domain.SettingUseCase
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain.NgoProfileRepository
import com.sitaram.foodshare.features.dashboard.setting.ngoProfile.domain.NgoProfileUseCase
import com.sitaram.foodshare.features.register.domain.RegisterUseCase
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Represents the main component or module of the dependency injection framework.
 * This AppComponent class is annotated with @Module to indicate that it's a Dagger module.
 * The @InstallIn(SingletonComponent::class) annotation specifies that this module will be installed in the
 * SingletonComponent, allowing its dependencies to be available for the entire lifetime of the application.
 * Typically, an AppComponent provides configurations and bindings for the entire application.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppComponent {

    // user's login use case
    @Provides
    @Singleton
    fun provideLoginAuthUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    @Provides
    @Singleton
    fun provideSettingUseCase(settingRepository: SettingRepository): SettingUseCase{
        return SettingUseCase(settingRepository)
    }

    @Provides
    @Singleton
    fun provideHomeUseCase(homeRepository: HomeRepository): HomeUseCase {
        return HomeUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideHistoryUseCase(historyRepository: HistoryRepository): HistoryUseCase {
        return HistoryUseCase(historyRepository)
    }

    // user's profiles
    @Provides
    @Singleton
    fun provideProfileUseCase(profileRepository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(profileRepository)
    }

    // all user's details
    @Provides
    @Singleton
    fun provideUsersUseCase(usersRepository: UsersRepository): UsersUseCase {
        return UsersUseCase(usersRepository)
    }

    // forgot password
    @Provides
    @Singleton
    fun providePasswordUseCase(forgotPasswordRepository: ForgotPasswordRepository): ForgotPasswordUseCase {
        return ForgotPasswordUseCase(forgotPasswordRepository)
    }

    // manage account
    @Provides
    @Singleton
    fun provideManageAccountUseCase(manageAccountRepository: ManageAccountRepository): ManageAccountUseCase {
        return ManageAccountUseCase(manageAccountRepository)
    }

    @Provides
    @Singleton
    fun provideUserDetailUseCase(userDetailRepository: UserDetailRepository): UserDetailUseCase {
        return UserDetailUseCase(userDetailRepository)
    }

    @Provides
    @Singleton
    fun provideFoodDetailUseCase(foodDetailRepository: FoodDetailRepository): FoodDetailUseCase {
        return FoodDetailUseCase(foodDetailRepository)
    }

    // foodDetails donation
    @Provides
    @Singleton
    fun provideDonationUseCase(donationRepository: DonationRepository): DonationUseCase {
        return DonationUseCase(donationRepository)
    }

    // google map
    @Provides
    @Singleton
    fun provideGoogleMapUseCase(googleMapRepository: GoogleMapRepository): GoogleMapUseCase {
        return GoogleMapUseCase(googleMapRepository)
    }

    @Provides
    @Singleton
    fun provideDistributedHistoryUseCase(pendingRepository: DistributedHistoryRepository): DistributedHistoryUseCase {
        return DistributedHistoryUseCase(pendingRepository)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseUseCase(localDatabaseRepository: LocalDatabaseRepository): LocalDatabaseUseCase {
        return LocalDatabaseUseCase(localDatabaseRepository)
    }

    @Provides
    @Singleton
    fun provideDonationRatingUseCase(donationRatingRepository: DonationRatingRepository): DonationRatingUseCase {
        return DonationRatingUseCase(donationRatingRepository)
    }

    @Provides
    @Singleton
    fun provideDonorHistoryUseCase(donorHistoryRepository: DonorHistoryRepository): DonorHistoryUseCase {
        return DonorHistoryUseCase(donorHistoryRepository)
    }

    @Provides
    @Singleton
    fun provideAdminHomeUseCase(adminHomeRepository: AdminHomeRepository): AdminHomeUseCase {
        return AdminHomeUseCase(adminHomeRepository)
    }

    // Ngo Profile
    @Provides
    @Singleton
    fun provideNgoProfileUseCase(ngoProfileRepository: NgoProfileRepository): NgoProfileUseCase {
        return NgoProfileUseCase(ngoProfileRepository)
    }

    // Notification
//    @Provides
//    @Singleton
//    fun provideNotificationUseCase(notificationRepository: NotificationRepository): NotificationUseCase {
//        return NotificationUseCase(notificationRepository)
//    }
}