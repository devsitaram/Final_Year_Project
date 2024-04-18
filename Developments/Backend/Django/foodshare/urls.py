from django.urls import path, include
from django.contrib import admin
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)
from rest_framework.routers import DefaultRouter
from rest_framework_simplejwt.views import TokenVerifyView
from foodshare.views import * # import all from views

urlpatterns = [
    # by default api
    path('admin/', admin.site.urls),
    path('api-auth/', include('rest_framework.urls')),
    path('api/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/token/verify/', TokenVerifyView.as_view(), name='token_verify'),

    # apis for mobile
    path('api/authenticate/token', LoginUser.as_view(), name='get_authentication_token'),
    path('api/register/user', RegisterUser.as_view(), name='get_register_user'),
    path('api/logout/user', LogoutView.as_view(), name='get_logout_user'),
    
    # Profile
    path('api/user/profile', UserProfile.as_view(), name='get_user_profile'),
    path('api/update/profile', UpdateProfile.as_view(), name='set_update_profile'),
    path('api/update/profile/image', UpdateProfilePicture.as_view(), name='set_update_profile'),
    path('api/foods/all', AllFoodDetails.as_view(), name='get_food_details'),
    path('api/fcm/device/token/save', DeviceTokenView.as_view(), name='set_device_details'),

    # NGOs api
    path('api/ngo/profile', NgoProfile.as_view(), name='get_ngo_profile'),
    path('api/register/ngo', AddNgoView.as_view(), name="add_ngo_profile"),
    # users
    path('api/all/types/user', GetAllUsersView.as_view(), name='get_users_by_search'),
    path('api/account/verify', UserAccountVerifyView.as_view(), name='set_account_verify'),

    # password
    path('api/email/verify', EmailVerify.as_view(), name='get_email_verify'),
    path('api/update/password', UpdatePassword.as_view(), name='set_update_password'),

    # manage account
    path('api/account/delete', DeleteAccount.as_view(), name='set_delete_account'),

    # foods
    path('api/food/new/post', AddNewFoodView.as_view(), name="set_donate_food"),
    path('api/food/new/get', GetNewFoods.as_view(), name='get_food_details_new'),
    path('api/food/details/history', GetFoodHistorys.as_view(), name='get_food_details_history'),
    path('api/food/history/all', HistoryDetails.as_view(), name='get_history_details'),

    # Donor side
    path('api/food/donation/histories/donor', DonationHistory.as_view(), name="get_donor_history"),
    path('api/update/donate/food', DonateFoodUpdate.as_view(), name="get_donate_food_update"),
    path('api/update/food/image', UpdateDonateFoodImage.as_view(), name="get_update_food_image"),
    path('api/food/deleted', FoodDelete.as_view(), name="set_food_delete"),

    # Food Accept
    path('api/food/accept', AcceptFood.as_view(), name="get_accept_food"),
    path('api/food/donate/rating', DonationCompletedRating.as_view(), name="get_donation_completed"),
    path('api/food/history/status', GetHistoryFoodDetails.as_view(), name="get_pending_food"),
    path('api/history/delete', DeleteHistoryFood.as_view(), name="set_delete_history_food"),

    #Report To User
    path('api/user/report', ReportToUser.as_view(), name="get_report_to_user"),
    path('api/get/report', GetReportView.as_view(), name="get_report_details"),
    path('api/verify/report', VerifyReportView.as_view(), name="get_verify_report"),

    # notification
    # path('api/view/notification', AddNewNotification.as_view(), name="set_new_notification"),
    path('api/get/all/notification', GetNotifications.as_view(), name="get_new_notification"),
    # data
    path('api/data/count', GetAllNumberOfDataView.as_view(), name="get_total_data"),

    # test
    path('api/get/token', GetNewToken.as_view(), name="get_all_token"),
]

# view json data in web
router = DefaultRouter()
router.register('user', UserViewSet, basename='user')
urlpatterns += router.urls