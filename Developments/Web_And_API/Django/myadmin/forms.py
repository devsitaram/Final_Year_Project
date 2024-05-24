from foodshare.models import Users
from django import forms

# single model
class UserForm(forms.ModelForm):
    class Meta:
        model = Users
        fields = ["email", "username", "role"]


# # dinamic
# class DinamicForm(forms.ModelForm, model):
    
#     class Meta:
#         model = model
#         fields = "__all__"
# )
# ????
