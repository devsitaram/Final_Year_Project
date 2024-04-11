from datetime import datetime
import os
import json
from django.contrib.auth import get_user_model
from django.contrib.auth import authenticate, login
from django.contrib.auth import authenticate
from django.contrib.auth import get_user_model
from django.conf import settings

from rest_framework import viewsets, status
from rest_framework.permissions import IsAuthenticated, IsAdminUser, AllowAny
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.response import Response
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework.authentication import TokenAuthentication
from rest_framework.exceptions import AuthenticationFailed, NotFound
from rest_framework.views import APIView
from django.core.exceptions import ObjectDoesNotExist

# model
from foodshare.models import *
from foodshare.serializer import *

# get NGO's profile
class NgoProfile(APIView):
    permission_classes = [IsAdminUser]
    def get(self, request):
        ngo = Ngo.objects.all()
        serializer = NgoSerializer(ngo, many=True)  # Serialize queryset
        return Response({"ngo_profiles": serializer.data})
    
class ReportDetails(APIView):
    def get(self, request):
        reports = Report.objects.all()
        serializer = ReportSerializer(reports, many=True)  # Serialize queryset
        return Response({"reports": serializer.data})
    
class NotificationDetails(APIView):
    def get(self, request):
        notifications = Notification.objects.all()
        serializer = NotificationsSerializer(notifications, many=True)  # Serialize queryset
        return Response({"notifications": serializer.data})

# for view json data
class UserViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    serializer_class = UserSerializer
    queryset = get_user_model().objects.all()

'''
login authentication
'''
class LoginUser(APIView):
    authentication_classes = [TokenAuthentication]  # Ensure TokenAuthentication is included
    permission_classes = [AllowAny]

    def post(self, request):
        try:
            email = request.data.get('email')
            password = request.data.get('password')

            if email and password:
                # Authenticate user
                auth_user = authenticate(request, username=email, password=password)
                if auth_user:
                    # Check if user account is active
                    user = Users.objects.filter(email=email).first()
                    if auth_user.is_active == True and user.is_delete == False:
                        # Generate tokens
                        refresh = RefreshToken.for_user(auth_user)
                        access_token = str(refresh.access_token)

                        # Serialize user data
                        serializer = UserSerializer(user)
                        
                        # Accessing the ImageField's URL
                        profile = user.photo_url.url if user.photo_url else None

                        # Return user data and tokens
                        response_auth = {
                            'id': serializer.data['id'],
                            'username': serializer.data['username'],
                            'email': serializer.data['email'],
                            'profile': profile,
                            'role': serializer.data['role'],
                            'access_token': access_token,
                        }
                        return Response({'message': 'Login successful', 'is_success': True, 'status': 200, "auth": response_auth})
                    else:
                        return Response({'message': 'The account is not active', 'is_success': False, 'status': 401})
                else:
                    return Response({'message': 'User is not authenticated', 'is_success': False, 'status': 401})
            else:
                return Response({'message': 'Please provide email and password', 'is_success': False, 'status': 400})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Register user
'''
# register user
class RegisterUser(APIView):
    authentication_classes = [TokenAuthentication]  # Ensure TokenAuthentication is included
    permission_classes = [AllowAny]

    def post(self, request):
        try:
            serializer = UserSerializer(data=request.data)
            if serializer.is_valid():
                # Extract validated data from the serializer
                email = serializer.validated_data['email']
                username = serializer.validated_data['username']
                role = serializer.validated_data['role']
                # Get the password from request data
                password = request.data.get('password')
                # Register user
                try:
                    Users.objects.create_user(email=email, username=username, role=role, password=password)
                    return Response({'message': 'User registered successfully', 'is_success': True, 'status': 200})
                except Exception as e:
                    return Response({'message': str(e), 'is_success': False, 'status': 500})
            else:
                return Response({'message': 'Enter a valid email address!', 'is_success': False, 'status': 400})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Home get all food:->
get all latest donate food (after 24 hours) 
'''
class AllFoodDetails(APIView):
    permission_classes = [AllowAny]
    def get(self, request):
        foods = Food.objects.all()
        serializer = FoodSerializer(foods, many=True)  # Serialize queryset
        return Response({"foods": serializer.data})

'''
Profile:->
get user profile details
'''
class UserProfile(APIView):
    permission_classes = [IsAuthenticated]
    def get(self, request):
        try:
            user = request.user
            serialized_user = UserSerializer(user)  # Serialize the user data
            return Response({"message": "Success", "is_success": True, "status": 200, "user_profile": serialized_user.data})
        except AuthenticationFailed as ex:
            return Response({"message": "Token is invalid or expired", "is_success": False, 'status': 401})
        except Exception as ex:
            return Response({"message": "User not authenticated", "is_success": False, 'status': 400})

# update user profile     
class UpdateProfile(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            user_id = request.query_params.get('id') 

            if not user_id:
                return Response({"error": "No user ID provided"}, status=400)

            user = Users.objects.filter(id=user_id).first()

            if not user:
                return Response({"error": "User not found"}, status=404)

            serializer = UserSerializer(user, data=request.data, partial=True)

            if serializer.is_valid():
                serializer.save()
                return Response({
                    "message": "Profile is updated",
                    "is_success": True,
                    "user_profile": serializer.data,
                    "status": 200
                })
            else:
                return Response({
                    "message": "Validation error",
                    "errors": serializer.errors,
                    "is_success": False,
                    "status": 400
                })

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})


# Update User Profile Image
class UpdateProfilePicture(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]
    parser_classes = (MultiPartParser, FormParser)  # Add MultiPartParser and FormParser

    def patch(self, request):
        try:
            user_id = request.data.get('id')
            profile = request.FILES.get('image')

            if not user_id:
                return Response({"message": "No user ID provided", "is_success": False, "status":400})

            user = Users.objects.filter(id=user_id).first()
            if not user:
                return Response({"message": "User not found", "is_success": False, "status":404})

            # Update the profile image
            if profile:
                # Save the image file to the desired directory
                file_path = os.path.join(settings.MEDIA_ROOT, 'user_images', profile.name)
                with open(file_path, 'wb') as destination:
                    for chunk in profile.chunks():
                        destination.write(chunk)

                user.modify_by = user.username
                user.modify_date = datetime.now().strftime('%Y-%m-%d')
                user.photo_url = file_path

                user.save()
                serialized_user = UserSerializer(user)

            return Response({
                "message": "Profile image is updated",
                "is_success": True,
                "status": 200,
                "user_profile": serialized_user.data
            })

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Forgot password:->
1) email verify
2) forgot, or reset password
'''
class EmailVerify(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny] 
    def get(self, request):
        try:
            email = request.query_params.get('query')

            if not email:
                return Response({"message": "Please check your email to reset password", "is_success": False, 'status': 400})

            user = Users.objects.filter(email=email).first()

            if user:
                # Check if user account is active and not delete
                if user.is_active == True and user.is_delete == False:
                    return Response({"message": "Email verify is successful", "is_success": True, 'status': 200})
                else:
                    return Response({"message": "This user is deactived.", "is_success": False, 'status': 400})
            
            else:
                return Response({"message": "Email does not exist. Pleasse provide the correct email address!", 'status': 404})

        except Exception:
            return Response({"message": "Enter the valid email address", "is_success": False, 'status': 500})
    
# update user account email and psaword
class UpdatePassword(APIView):
    authentication_classes = [TokenAuthentication]  # Ensure TokenAuthentication is included
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            email = request.data.get('email')
            new_password = request.data.get('new_password')

            if not email:
                return Response({"message": "The user email is empty!", "is_success": False, 'status': 400})

            user = Users.objects.filter(email=email).first()
            if not user:
                return Response({"message": "User not found", "is_success": False, 'status': 404})

            if user is not None:
                if new_password != '' and isinstance(new_password, str):
                    user.set_password(new_password)
                    user.modify_by = user.username
                    user.modify_date = datetime.now().strftime('%Y-%m-%d')
                    user.save()
                    return Response({"message": "The password has been successfully updated.", "is_success": True, 'status': 200})

            return Response({"message": "Both email and password are empty, no changes made", "is_success": False, 'status': 400})

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
All User:-> 
1) Get all user 
2) New user account verify ro activate
'''
# find the user by search
class GetAllUsers(APIView):
    # permission_classes = [IsAdminUser]
    authentication_classes = [TokenAuthentication]  # Ensure TokenAuthentication is included
    permission_classes = [AllowAny]
    def get(self, request):
        try:
            users = Users.objects.all().order_by('-created_date')

            if not users.exists():
                return Response({"message": "No users found", "is_success": False, 'status': 500})
            # return the data
            result = UserSerializer(users, many=True)
            return Response({"is_success": True, "message": "Get user details successfully",  'status': 200, "users": result.data})

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

# account verify
class UserAccountVerify(APIView):
    permission_classes = [IsAdminUser]
    def post(self, request):
        try:
            update_user_id = request.data.get('id')
            update_query = request.data.get('query')

            if not update_user_id:
                return Response({"message": "User id is empty!", "is_success": False, 'status': 400})

            user = Users.objects.filter(id=update_user_id).first()

            if not user:
                return Response({"message": "User not found", "is_success": False, "status": 404})

            if update_query is not None:
                if isinstance(update_query, bool):
                    user.is_active = update_query
                    user.modify_by = "Admin"
                    user.modify_date = datetime.now().strftime('%Y-%m-%d')
                    user.save()
                else:
                    return Response({"message": "Invalid update value, must be a boolean", "is_success": False, "status": 400})

            users = Users.objects.all().order_by('-created_date')
            serialized_user = UserSerializer(users, many=True)
            return Response({"message": "Get user details successfully", "is_success": True, 'status': 200, "users": serialized_user.data})

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Manage Account:->
'''
# account verify
class DeleteAccount(APIView):
    authentication_classes = [TokenAuthentication]  # Ensure TokenAuthentication is included
    permission_classes = [AllowAny]
    def patch(self, request):
        try:
            email = request.data.get('email')
            update_query = request.data.get('query')

            if not email:
                return Response({"message": "No user ID provided", "is_success": False, "status":400})

            user = Users.objects.filter(email=email).first()

            if not user:
                return Response({"message": "User not found", "is_success": False, "status":404})

            if update_query is not None:
                if isinstance(update_query, bool):
                    user.is_delete = update_query
                    user.modify_by = user.username
                    user.modify_date = datetime.now().strftime('%Y-%m-%d')
                    # user.is_active = False
                    user.save()
                    return Response({"message": "The user account has been successfully deleted.", "is_success": True, "status":200})

                else:
                    return Response({"message": "Invalid update query value, must be a boolean", "status":400})

            return Response({"message": "The user account is emapty!", "is_success": False, "status":300})

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Donate food
'''
class AddNewFood(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]
    parser_classes = [MultiPartParser, FormParser]

    def post(self, request):
        try:
            serializer = FoodSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()  

                return Response({"message": "Donation successfully", "is_success": True, "status": 200})
            else:
                return Response({"message": serializer.errors, "is_success": False, "status": 400})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Get new donate food
'''
class GetNewFoods(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def get(self, request):
        try:
            query = request.query_params.get('query')
            # Calculate the date 24 hours ago
            if query:
                foods = Food.objects.filter(status=query, is_delete=False).order_by('-id').all()
            else:
                foods = Food.objects.filter(status='New', is_delete=False).order_by('-id').all()

            if not foods:
                return Response({"message": "New food is not found!", "is_success": False, "status": 400})
            
            # Serialize food data along with associated user details
            food_data = []
            for food in foods:
                food_dict = FoodSerializer(food).data
                user_data = UserSerializer(food.donor).data
                food_dict['donor'] = user_data
                food_data.append(food_dict)
            return Response({"message": "New donation food details", "is_success": True, "status": 200, "foods": food_data})
        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': status.HTTP_500_INTERNAL_SERVER_ERROR})

'''
Delete Food
'''
class FoodDelete(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            food_id = request.query_params.get('food_id') 
            username = request.query_params.get('username') 
             # Assuming the food_id is provided in the request data
            if food_id is not None:
                food = Food.objects.get(id=food_id)
                food.modify_by = username
                food.modify_date = datetime.now().strftime('%Y-%m-%d')
                food.is_delete = True
                food.save()
                return Response({"message": "Deleted successfully", "is_success": True, "status": 200})
            else:
                return Response({"message": "Food item not found", "is_success": False, "status": 404})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': status.HTTP_500_INTERNAL_SERVER_ERROR})


'''
Get history details by donor
'''
# History
class GetFoodHistorys(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def get(self, request):
        histories = []
        try:
            volunteer = request.query_params.get('query')
            if volunteer:
                history_details = History.objects.filter(volunteer=volunteer, is_delete=False).all()
                if not history_details:
                    return Response({"is_success": True, "message": "No history found for this volunteer.", "histories": []})

                for entry in history_details:
                    food_data = FoodSerializer(entry.food).data
                    donor_data = UserSerializer(entry.food.donor).data
                    volunteer_data = UserSerializer(entry.volunteer).data

                    history = {
                        'food_history': HistorySerializer(entry).data,
                        'food': food_data,
                        'donor': donor_data,
                        'volunteer': volunteer_data
                    }

                    histories.append(history)

            else: 
                all_history = History.objects.all()
                for entry in all_history:
                    food_data = FoodSerializer(entry.food).data
                    donor_data = UserSerializer(entry.food.donor).data
                    volunteer_data = UserSerializer(entry.volunteer).data

                    history = {
                        'food_history': HistorySerializer(entry).data,
                        'food': food_data,
                        'donor': donor_data,
                        'volunteer': volunteer_data
                    }

                    histories.append(history)

            return Response({"is_success": True, "message": "History details successfully received.", "histories": histories})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})

'''
Get all histories
'''
class HistoryDetails(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def get(self, request):
        try:
            #foods = Foods.objects.select_related('donor').prefetch_related('history_set__volunteer').order_by('-created_date')
            foods = Food.objects.all()
            if not foods:
                return Response({"message": "No food found", "is_success": False, "status": 400})

            serialized_histories = []
            for food in foods:
                food_data = FoodSerializer(food).data
                donor_data = UserSerializer(food.donor).data
                for history in food.history_set.all():
                    serialized_history = {
                        'food': food_data,
                        'donor': donor_data,
                        'history': HistorySerializer(history).data,
                        'volunteer': UserSerializer(history.volunteer).data
                    }
                    serialized_histories.append(serialized_history)

            return Response({"message": "Success", "is_success": True, "status": 200, "foods": serialized_histories})
        
        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

class AddNgo(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]
    parser_classes = (MultiPartParser, FormParser)  # Add MultiPartParser and FormParser

    def post(self, request):
        try:
            image_file = request.FILES.get('image_url')  # Retrieve the uploaded image file
            
            if not image_file:
                return Response({"message": "No image file provided", "is_success": False, "status":400})

            image_directory = 'food_images'
            if not os.path.exists(image_directory):
                os.makedirs(image_directory)

            # Generate a unique filename for the image
            image_filename = os.path.join(image_directory, image_file.name)

            # Save the image file to the specified directory
            with open(image_filename, 'wb') as f:
                for chunk in image_file.chunks():
                    f.write(chunk)

            # Create the Ngos instance with only the ngo_stream_url field set
            ngo_instance = Ngo.objects.create(ngo_stream_url=image_filename)

            return Response({"message": "NGO profile created successfully", "is_success": True, "status":404})
        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': status.HTTP_500_INTERNAL_SERVER_ERROR})

# Accept the donation food
class AcceptFood(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def post(self, request):
        try:
            update_food_id = request.data.get('food_id')
            update_status = request.data.get('status')
            user_id = request.data.get('user_id')
            username = request.data.get('username')

            if not update_food_id:
                return Response({"message": "Food ID is empty!", "is_success": False, 'status': 400})

            food = Food.objects.filter(id=update_food_id).first()

            if not food:
                return Response({"message": "Food not found", "is_success": False, "status": 404})

            if update_status is not None:
                if isinstance(update_status, str):
                    food.status = update_status
                    food.modify_by = username
                    food.modify_date = datetime.now().strftime('%Y-%m-%d')
                    food.save()

                    # Create a History object
                    history_data = {
                        'status': update_status,
                        'volunteer': user_id,
                        'food': food.id,
                        'created_by': username
                    }

                    history_serializer = HistorySerializer(data=history_data)
                    if history_serializer.is_valid():
                        history_serializer.save()
                        return Response({"message": "Food is successfully accepted", "is_success": True, 'status': 200})
                    else:
                        return Response({"message": "Invalid history data", "is_success": False, "status": 400})
                else:
                    return Response({"message": "Invalid update status", "is_success": False, "status": 400})
            else:
                return Response({"message": "Status is empty", "is_success": False, "status": 400})

        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status":500})

# get pending
class GetHistoryFoodDetails(APIView):

    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def get(self, request):
        try:
            user_id = request.query_params.get('user_id')
            status = request.query_params.get('status')
            # Filter History table to find entries associated with the volunteer and with the provided status
            history_entries = History.objects.filter(volunteer_id=user_id, status=status, is_delete=False).order_by('-id')

            if history_entries:
                # Retrieve Food details for pending history items
                pending_history = []
                for entry in history_entries:
                    # Get the Food details for each history entry
                    food_data = FoodSerializer(entry.food).data
                    user_data = UserSerializer(entry.food.donor).data
                    # Create a dictionary containing both the history entry and its associated food details
                    entry_details = {
                        "history_details": HistorySerializer(entry).data,
                        "food_details": food_data,
                        "user_details": user_data
                    }

                    pending_history.append(entry_details)

                return Response({"message": "Success", "is_success": True, 'status': 200, "history": pending_history})
            else:
                return Response({"message": "History is found", "is_success": False, 'status': 400})

        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

class DonationHistory(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def get(self, request):
        try:
            donor_id = request.query_params.get("id")  # Using query_params to get the donor_id

            # Query to get all foods donated by the donor
            donated_foods = Food.objects.filter(donor_id=donor_id, is_delete=False).order_by('-created_date')
            if not donated_foods:
                return Response({"message": "No food history found", "is_success": False, "status": 400})
             
            # Initialize an empty list to store donation entries
            donation_entries = []
            # Loop through each donated food
            for food in donated_foods:
                food_data = FoodSerializer(food).data  # Serialize the food data
                donor_data = UserSerializer(food.donor).data

                # Get the history for the current food item
                history = History.objects.filter(food=food).first()

                if history:
                    history_data = HistorySerializer(history).data  # Serialize the history data
                    volunteer_data = UserSerializer(history.volunteer).data  # Serialize the volunteer data
                else:
                    history_data = {}  # Empty dictionary if history is not available
                    volunteer_data = {}  # Empty dictionary if history is not available

                # Constructing the donation entry
                donation_entry = {
                    'foods': food_data,
                    'histories': history_data,
                    'volunteer': volunteer_data,
                    'donor': donor_data
                }
                donation_entries.append(donation_entry)

            return Response({"message": "success", "is_success": True, "status": 200, "data": donation_entries})

        except ObjectDoesNotExist:
            raise NotFound({"message": "Donor or donation history not found.", "is_success": False, "status": 400})  # Raise a 404 Not Found exception if donor or donation history not found
        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

# UPdate the donate food
class DonateFoodUpdate(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            food_id = request.query_params.get('food_id')
            
            if not food_id:
                return Response({"error": "No food id provided", "is_success": False, "status": 400})

            food = Food.objects.filter(id=food_id).first()

            if not food:
                return Response({"error": "Food not found", "is_success": False, "status": 404})

            serializer = FoodSerializer(food, data=request.data, partial=True)

            if serializer.is_valid():
                food.modify_date = datetime.now().strftime('%Y-%m-%d')
                serializer.save()

            return Response({
                "message": "Donate food is updated",
                "is_success": True,
                "status": 200,
                "food": serializer.data
            })

        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": status.HTTP_500_INTERNAL_SERVER_ERROR})

class UpdateDonateFoodImage(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]
    parser_classes = (MultiPartParser, FormParser)  # Add MultiPartParser and FormParser

    def patch(self, request):
        try:
            food_id = request.data.get('id')
            image = request.FILES.get('image')
            print(image)
            if not food_id:
                return Response({"message": "No food id provided", "is_success": False, "status":400})

            food = Food.objects.filter(id=food_id).first()

            if not food:
                return Response({"message": "User not found", "is_success": False, "status":404})

            # Update the Food image
            if image:
                # Save the image file to the desired directory
                file_path = os.path.join(settings.MEDIA_ROOT, 'food_images', image.name)
                with open(file_path, 'wb') as destination:
                    for chunk in image.chunks():
                        destination.write(chunk)

                food.modify_by = "Self"
                food.modify_date = datetime.now().strftime('%Y-%m-%d')
                food.stream_url = file_path
                food.save()

                serialized_food = FoodSerializer(food).data

            return Response({
                "message": "Food image is updated",
                "is_success": True,
                "status": 200,
                "food": serialized_food
            })

        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

#Delete food history 
class DeleteHistoryFood(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            # Get the history_id and username from query parameters
            history_id = request.query_params.get("history_id")
            username = request.query_params.get("username")

            history = History.objects.get(id=history_id)
            history.is_delete = True
            history.modify_by = username
            history.modify_date = datetime.now().strftime('%Y-%m-%d')
            # Save the changes
            history.save()

            return Response({"message": "Food history deleted successfully.", "is_success": True, "status":200})
        except History.DoesNotExist:
            return Response({"message": "Food history with the provided ID does not exist.", "is_success": False, "status":404})
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status":500})


# Report User
class ReportToUser(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def post(self, request):
        try:
            serializer = ReportSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()  # Save the serializer instance to the database
                return Response({"message": "Report has been sumbited", "is_success": True, "status": 200})
            else:
                return Response({"message": "Report is not sumbit", "is_success": False, "status": 400})
        except Exception:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

# Update User Profile Image
class DonationCompleted(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def patch(self, request):
        try:
            history_id = request.data.get('id')
            descriptions = request.data.get('descriptions')
            location = request.data.get('location')
            rating = request.data.get('rating')

            if not history_id:
                return Response({"message": "No history Id provided", "is_success": False, "status": 400})

            history = History.objects.filter(id=history_id).first()
            if not history:
                return Response({"message": "History not found", "is_success": False, "status": 404})
            
            history.distributed_date = datetime.now().strftime('%Y-%m-%d')
            history.descriptions = descriptions
            history.distributed_location = location
            history.rating_point = rating
            history.status = 'Completed'
            history.save()

            food_id = history.food.id
            food = Food.objects.filter(id=food_id).first()

            if food:
                food.status = 'Completed'
                food.save()

                return Response({"message": "Food donation is successful.", "is_success": True, "status": 200})
            else:
                return Response({"message": "Food not found for the given history ID.", "is_success": False, "status": 404})

        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", 'is_success': False, 'status': 500})
        
class GetReports(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def get(self, request):
        try:
            # Retrieve only reports where is_verify is False and order them by created_date in descending order
            reports_entries = Report.objects.filter(is_verify=False).order_by('-created_date')            

            pending_history = []
            for entry in reports_entries:
                # Get the Food details for each history entry
                reports_data = ReportSerializer(entry).data
                food_data = FoodSerializer(entry.food).data
                user_data = UserSerializer(entry.food.donor).data
                # Create a dictionary containing both the history entry and its associated food details
                entry_details = {
                    "report": reports_data,
                    "food": food_data,
                    "user": user_data
                }

                pending_history.append(entry_details)

            serialized_reports = {
                "message": "Get Report is successful.", 
                "is_success": True, 
                "status": 200,
                "data": pending_history
            }

            return Response(serialized_reports)
        except Exception as e:
            return Response({"message": "Sorry, something went wrong on our end. Please try again later.", "is_success": False, "status": 500})

class VerifyReport(APIView):
    authentication_classes = [TokenAuthentication]
    permission_classes = [IsAuthenticated]
    permission_classes = [AllowAny]

    def patch(self, request):
        report_id = request.query_params.get("id")
        is_verify = request.query_params.get("is_verify")

        try:
            report = Report.objects.get(id=report_id)
        except Report.DoesNotExist:
            return Response({"message": "Report not found.", "is_success": False, "status":404})

        report.is_verify = is_verify
        report.save()

        return Response({"message": "Report is verified.", "is_success": True, "status":200})