package com.sitaram.foodshare.dependencyInjection.module

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.data.AdminHomeRepoImpl
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.adminHome.domain.AdminHomeRepository
import com.sitaram.foodshare.features.dashboard.localDatabase.data.LocalDatabaseRepoImpl
import com.sitaram.foodshare.features.dashboard.localDatabase.domain.LocalDatabaseRepository
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.data.DonationRepositoryImpl
import com.sitaram.foodshare.features.dashboard.dashboardDonor.post.domain.DonationRepository
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.data.UsersRepositoryImpl
import com.sitaram.foodshare.features.dashboard.foodDetail.data.FoodDetailRepositoryImpl
import com.sitaram.foodshare.features.dashboard.history.data.HistoryRepositoryImpl
import com.sitaram.foodshare.features.dashboard.home.data.HomeRepositoryImpl
import com.sitaram.foodshare.source.remote.api.ApiRetrofitInstance.Companion.getRetrofitInstance
import com.sitaram.foodshare.source.remote.api.ApiService
import com.sitaram.foodshare.features.login.data.LoginRepositoryImpl
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.data.ManageAccountRepositoryImpl
import com.sitaram.foodshare.features.forgotpassword.data.ForgotPasswordRepositoryImpl
import com.sitaram.foodshare.features.dashboard.profile.data.ProfileRepositoryImpl
import com.sitaram.foodshare.features.register.data.RegisterRepositoryImpl
import com.sitaram.foodshare.features.dashboard.userProfileDetails.data.UserDetailRepositoryImpl
import com.sitaram.foodshare.source.local.DatabaseHelper.Companion.getDatabaseInstance
import com.sitaram.foodshare.source.local.RoomDao
import com.sitaram.foodshare.features.dashboard.dashboardAdmin.users.domain.UsersRepository
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.data.DonorHistoryRepoImpl
import com.sitaram.foodshare.features.dashboard.dashboardDonor.donorHistory.domain.DonorHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.data.DistributedHistoryRepoImpl
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.pending.domain.DistributedHistoryRepository
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.data.UpdateFoodHistoryRepoImpl
import com.sitaram.foodshare.features.dashboard.dashboardVolunteer.updateFoodHistory.domain.UpdateFoodHistoryRepository
import com.sitaram.foodshare.features.dashboard.foodDetail.domain.FoodDetailRepository
import com.sitaram.foodshare.features.dashboard.history.domain.HistoryRepository
import com.sitaram.foodshare.features.dashboard.home.domain.HomeRepository
import com.sitaram.foodshare.features.login.domain.LoginRepository
import com.sitaram.foodshare.features.dashboard.setting.manageAccount.domain.ManageAccountRepository
import com.sitaram.foodshare.features.forgotpassword.domain.ForgotPasswordRepository
import com.sitaram.foodshare.features.dashboard.profile.domain.ProfileRepository
import com.sitaram.foodshare.features.dashboard.googleMap.data.GoogleMapRepositoryImpl
import com.sitaram.foodshare.features.dashboard.googleMap.domain.GoogleMapRepository
import com.sitaram.foodshare.features.dashboard.userProfileDetails.domain.UserDetailRepository
import com.sitaram.foodshare.features.register.domain.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Represents the main component or module of the dependency injection framework.
 * This AppModule is responsible for providing configurations and bindings for the dependencies.
 * It is annotated with @Module to indicate that it's a Dagger module.
 * The @InstallIn(SingletonComponent::class) annotation specifies that the bindings defined in this module
 * will be available for the entire lifetime of the application, as it's installed in the SingletonComponent.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // get room database instance
    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): RoomDao {
        return getDatabaseInstance(context).userDao()
    }

    // get subject retrofit instance
    @Provides
    @Singleton
    fun provideApiRetrofitInstance(@ApplicationContext context: Context): ApiService {
        return getRetrofitInstance(context).create(ApiService::class.java)
    }

    //user authentication and profile
    @Provides
    @Singleton
    fun provideLoginUserRepoImpl(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRegisterUserRepoImpl(apiService: ApiService): RegisterRepository {
        return RegisterRepositoryImpl(apiService)
    }

    //user authentication and profile
    @Provides
    @Singleton
    fun provideHomeRepoImpl(apiService: ApiService, roomDao: RoomDao): HomeRepository {
        return HomeRepositoryImpl(apiService, roomDao)
    }

    @Provides
    @Singleton
    fun provideHistoryRepoImpl(apiService: ApiService): HistoryRepository {
        return HistoryRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProfileRepoImpl(apiService: ApiService, @ApplicationContext context: Context): ProfileRepository {
        return ProfileRepositoryImpl(apiService, context)
    }

    @Provides
    @Singleton
    fun provideUsersRepoImpl(apiService: ApiService): UsersRepository {
        return UsersRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providePasswordRepoImpl(apiService: ApiService): ForgotPasswordRepository {
        return ForgotPasswordRepositoryImpl(apiService)
    }

    // manage account
    @Provides
    @Singleton
    fun provideManageAccountRepoImpl(apiService: ApiService): ManageAccountRepository {
        return ManageAccountRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUserDetailRepoImpl(roomDao: RoomDao): UserDetailRepository {
        return UserDetailRepositoryImpl(roomDao)
    }

    // foodDetails details
    @Provides
    @Singleton
    fun provideFoodDetailRepoImpl(apiService: ApiService): FoodDetailRepository {
        return FoodDetailRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDonationRepoImpl(apiService: ApiService): DonationRepository {
        return DonationRepositoryImpl(apiService)
    }

    // google map
    @Provides
    @Singleton
    fun provideGoogleMapRepoImpl(@ApplicationContext context: Context): GoogleMapRepository {
        return GoogleMapRepositoryImpl(context, LocationServices.getFusedLocationProviderClient(context))
    }

    @Provides
    @Singleton
    fun provideDistributedHistoryRepoImpl(apiService: ApiService, roomDao: RoomDao): DistributedHistoryRepository {
        return DistributedHistoryRepoImpl(apiService, roomDao)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseRepoImpl(roomDao: RoomDao): LocalDatabaseRepository {
        return LocalDatabaseRepoImpl(roomDao)
    }

    @Provides
    @Singleton
    fun provideUpdateFoodHistoryRepoImpl(apiService: ApiService, roomDao: RoomDao): UpdateFoodHistoryRepository {
        return UpdateFoodHistoryRepoImpl(apiService, roomDao)
    }

    @Provides
    @Singleton
    fun provideDonorHistoryRepoImpl(apiService: ApiService): DonorHistoryRepository {
        return DonorHistoryRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAdminHomeRepoImpl(apiService: ApiService): AdminHomeRepository {
        return AdminHomeRepoImpl(apiService)
    }
}