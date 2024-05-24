from django.shortcuts import render
from django.http import HttpResponse
from django.shortcuts import redirect, render
from django.contrib.auth import authenticate, logout, login as auth_login
from django.db import connection
from django.apps import apps
from .forms import UserForm
from rest_framework.authtoken.models import Token
from rest_framework_simplejwt.tokens import RefreshToken
from django.shortcuts import redirect, get_object_or_404
from foodshare.models import *

# home
def home(request):
    cursor = connection.cursor()
    # Get table names from the specified database schema
    cursor.execute("SHOW TABLES FROM food_management_system")
    table_names = [row[0] for row in cursor.fetchall()]
    return render(request, 'home.html', {'list_of_table': table_names})

# Login user
def admin_login(request):
    if request.user.is_authenticated:
        return redirect('home')
    else:
        invalid_login = False
        if request.method == 'POST':
            email = request.POST.get('email')
            password = request.POST.get('password')

            # Authenticate user based on email and password
            auth_user = authenticate(request, email=email, password=password)
            
            if auth_user is not None:
                user = Users.objects.filter(email=email).first()
                if user.is_admin:
                    # Successful login for admin user
                    auth_login(request, auth_user)
                    refresh = RefreshToken.for_user(auth_user)
                    token = str(refresh.access_token)
                    request.session['token'] = token
                    return redirect('home')
                else:
                    invalid_login = True
            else:
                # Invalid login
                invalid_login = True

    return render(request, 'index.html', {'invalid_login': invalid_login})

# reset password
def reset_password(request):
    invalid_password = False

    if request.method == 'POST':
        email = request.POST.get('email')
        new_password = request.POST.get('new_password')
        confirm_password = request.POST.get('confirm_password')

        # Check if passwords match
        if new_password != confirm_password:
            invalid_password = True
        else:
            user = Users.objects.get(email=email)
            user.set_password(new_password)
            user.save()
            return redirect('admin_login')
    return render(request, 'reset_password.html', {'invalid_password': invalid_password})

# logout
def logout_view(request):
    if request.user.is_authenticated:
        try:
            token = Token.objects.get(user=request.user)
            token.delete()
        except Token.DoesNotExist:
            pass
    logout(request)
    return redirect('admin_login')

# Register user
def register_user(request):
    if request.method == 'POST':
        form = UserForm(request.POST)
        if form.is_valid():
            pass
    else:
        form = UserForm()
    context = {
        "form": form,
    }
    return render(request, "home.html", context)

# view data
def view_data(request, table_name):
    try:
        cursor = connection.cursor()
        cursor.execute("SHOW TABLES FROM food_management_system")
        table_names = [row[0] for row in cursor.fetchall()]
        cursor.execute(f"SELECT * FROM {table_name} WHERE is_delete=0")
        columns = [col[0] for col in cursor.description]
        data = cursor.fetchall()
        return render(request, 'view_data.html', {'data': data, 'columns': columns, 'table_name': table_name, 'list_of_table': table_names})
    except LookupError:
        return HttpResponse("Table doesn't exist or an error occurred.")

# register new user
def add_new_user(request, table_name):
    if request.method == 'POST':
        form = UserForm(request.POST)
        if form.is_valid():
            username = form.cleaned_data["username"]
            email = form.cleaned_data["email"]
            role = form.cleaned_data["role"]
            password = request.POST.get('password')
            Users.objects.create_user(email=email, username=username, role=role, password=password)
            return redirect("home")
    else:
        form = UserForm()
    context = {"form": form,}
    return render(request, "add_new_user.html", context)

# new user account verify
def activate(request, id=None):
    user = get_object_or_404(Users, id=id)
    if user.is_active == True:
        user.is_active = False
    else:
        user.is_active = True
    user.save()
    table_name = "foodshare_users"
    return redirect("view_data", table_name=table_name)

# how to get the argument from table is string name, and id is int
def delete_user(request, table_name, id=None):
    model_name = table_name.replace("foodshare_", "").capitalize()
    # Get the model class using apps.get_model
    model_class = apps.get_model(app_label='foodshare', model_name=model_name)
    
    # Retrieve the object with the given id
    obj = get_object_or_404(model_class, id=id)
    obj.is_delete = True
    obj.save()
    return redirect("view_data", table_name=table_name)


# def update_data(request, table_name, id=None):
#     patch method
#     model_name = table_name.replace("foodshare_", "").capitalize()
#     # Get the model class using apps.get_model
#     model_class = apps.get_model(app_label='foodshare', model_name=model_name)
#     model = DinamicForm(model_class)
#     ??? opent the 
#     how to make the dinamicly update the date for any modle where the update form is onen to update_data.html 