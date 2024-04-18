from django.urls import path
from myadmin.views import *
from foodshare.views import *

urlpatterns = [
    # apis for web admin page
    path('', admin_login, name='admin_login'),
    path('api/register/user', register_user, name='get_register_user'),
    path('reset_password/', reset_password, name='reset_password'),
    path('logout/', logout_view, name='logout'),
    path('api/user/profile', UserProfile.as_view(), name='get_user_profile'),
    path('api/account/verify', UserAccountVerifyView.as_view(), name='set_account_verify'),
    path('home/', home, name='home'),
    
    path('home/<str:table_name>/view/', view_data, name='view_data'),
    path('home/<str:table_name>/add/', add_new_user, name='add_new_user'), 
    path('home/<str:table_name>/<int:row_id>/delete/', delete_row, name='delete_row'),
]