from foodshare.models import Users
from django import forms


class UserForm(forms.ModelForm):
    class Meta:
        model = Users
        fields = ["email", "username", "role"]