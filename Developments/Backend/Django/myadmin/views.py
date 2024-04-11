from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.shortcuts import redirect, render
from django.contrib.auth import authenticate, logout, login as auth_login
from django.db import connection

from rest_framework.authtoken.models import Token
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.permissions import IsAdminUser
from rest_framework.authentication import TokenAuthentication
from foodshare.models import *
# Create your views here.

# home
def home(request):
    cursor = connection.cursor()
    # Get table names from the specified database schema
    cursor.execute("SHOW TABLES FROM waste_food_management_system")
    table_names = [row[0] for row in cursor.fetchall()]

    return render(request, 'home.html', {'list_of_table': table_names})

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
            # update the user's password in the database
            user = Users.objects.get(email=email)
            user.set_password(new_password)
            user.save()
            return redirect('admin_login')

    return render(request, 'reset_password.html', {'invalid_password': invalid_password})

# logout
def logout_view(request):
    if request.user.is_authenticated:
        # If using TokenAuthentication in DRF, revoke the token
        try:
            token = Token.objects.get(user=request.user)
            token.delete()
        except Token.DoesNotExist:
            pass

    logout(request)
    return redirect('admin_login')

# view data
def view_data(request, table_name):
    try:
        cursor = connection.cursor()
        cursor.execute(f"SELECT * FROM {table_name}")
        columns = [col[0] for col in cursor.description]  # Fetching column names
        data = cursor.fetchall()
        print(f"Column is : {columns}")
        print(f"Data is : {data}")
        return render(request, 'view_data.html', {'data': data, 'columns': columns, 'table_name': table_name})
    except LookupError:
        return HttpResponse("Table doesn't exist or an error occurred.")

def add_data(request, table_name):
    print(table_name)
    return HttpResponse(request)

# delete 
def delete_row(request, table_name, row_id):
    if request.method == 'POST':
        return JsonResponse({'message': 'is_delete updated successfully'})
    #     try:
    #         # Get the model class dynamically based on the provided table_name
    #         model = apps.get_model(app_label='foodshare', model_name=table_name)

    #         if model:
    #             # Retrieve the specific row from the model using the provided row_id
    #             row = model.objects.get(id=row_id)

    #             # Set is_delete to True
    #             row.is_delete = True
    #             row.save()

    #             return JsonResponse({'message': 'is_delete updated successfully'})
    #         else:
    #             return JsonResponse({'error': f"Table '{table_name}' does not exist"}, status=404)
    #     except model.DoesNotExist:
    #         return JsonResponse({'error': 'Row does not exist'}, status=404)
    #     except Exception as e:
    #         return JsonResponse({'error': str(e)}, status=500)
    # else:
    #     return JsonResponse({'error': 'Invalid request method. Expected POST'}, status=400)
