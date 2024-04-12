from django.urls import path, include
from django.contrib import admin
from django.contrib.auth.models import User
from notification.views import *

urlpatterns = [
    path('api/notification/send', SendNotificationView.as_view(), name='send_notification'),
]