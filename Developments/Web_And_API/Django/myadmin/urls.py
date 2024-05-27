from django.urls import path
from myadmin.views import *
from foodshare.views import *

urlpatterns = [
    # apis for web admin page
    path('', admin_login, name='admin_login'),
    path('api/add/user', register_user, name='get_register_user'),
    path('reset_password/', reset_password, name='reset_password'),
    path('logout/', logout_view, name='logout'),
    path('api/user/profile', UserProfile.as_view(), name='get_user_profile'),
    path('home/', home, name='home'),
    
    path('home/<str:table_name>/view/', view_data, name='view_data'),
    path('home/<str:table_name>/add/', add_new_user, name='add_new_user'), 
    path('activate/<int:id>/', activate, name='activate'),
    path('delete/<str:table_name>/<int:id>/', delete_user, name='delete'),
    path('update/<str:table_name>/<int:id>/', update_data, name='update')
]