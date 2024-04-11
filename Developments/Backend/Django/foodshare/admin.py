from django.contrib import admin
from foodshare.models import *

admin.site.register(Ngo)
admin.site.register(Users)
admin.site.register(Food)
admin.site.register(Notification)
admin.site.register(History)
admin.site.register(Report)

# admin.site.register(Image)
# admin.site.register(Food_Details)

# from .models import UserModelAdmin
# from django.contrib.auth.admin import UserAdmin as BaseUserAdmin

# class UserModelAdmin(BaseUserAdmin):
#     # display the admin site
#     list_display = ('id', 'email', 'username', 'is_admin', 'role', 'is_active', 'createdBy', 'createdDate', 'modifyBy', 'modifyDate', 'isDelete')
#     list_filter = ('role',)
#     fieldsets = (
#         ('User Credenticals', {'fields': ('email', 'password')}),
#         ('Personal Info', {'fields': ('username', 'role')}),
#         ('Others Detials', {'fields': ('createdBy', 'createdDate', 'modifyBy', 'modifyDate')}),
#         ('Permissions', {'fields': ('is_admin', 'is_active', 'isDelete')}),
#     )

#     # add_fieldsets is not a standard ModelAdmin attribute. UserModelAdmin
#     # overrides get_fieldsets to use this attribute when creating a user.
#     add_fieldsets = (
#         (None, {
#             'classes': ('wide',),
#             'fields': ('email', 'username', 'password', 'newpassword'),
#         }),
#     )
#     search_fields = ('email',)
#     ordering = ('email', 'id',)
#     filter_horizontal = ()









# from .models import Ngos, Donors
#, Volunteers, Farmers, Foods, History

# Register your models here.
# admin.site.register(Ngos)
# admin.site.register(Donors)

# admin.site.register(Volunteers)
# admin.site.register(Farmers)
# admin.site.register(Foods)
# admin.site.register(History)