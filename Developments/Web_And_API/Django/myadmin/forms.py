from foodshare.models import Users
from django import forms

# single model
class UserForm(forms.ModelForm):
    class Meta:
        model = Users
        fields = ["email", "username", "role"]


# dinamic
def DynamicForm(model_class):
    class Meta:
        model = model_class
        fields = "__all__"

    return type('DynamicForm', (forms.ModelForm,), {'Meta': Meta})
