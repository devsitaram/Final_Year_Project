from django.urls import path
from . import views
from foodshare.views import *

urlpatterns = [
    # apis for web admin page
    path('', views.admin_login, name='admin_login'),
    path('reset_password/', views.reset_password, name='reset_password'),
    path('logout/', views.logout_view, name='logout'),
    path('api/user/profile/', UserProfile.as_view(), name='get_user_profile'),
    path('home/', views.home, name='home'),
    
    path('home/<str:table_name>/view/', views.view_data, name='view_data'),
    path('home/<str:table_name>/add/', views.add_data, name='add_data'),
    path('home/<str:table_name>/<int:row_id>/delete/', views.delete_row, name='delete_row'),
]