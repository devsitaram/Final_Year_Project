# python manage.py test foodshare

from django.test import TestCase
from django.contrib.auth import get_user_model
from rest_framework.test import APIRequestFactory
from rest_framework.authtoken.models import Token
from rest_framework.test import force_authenticate
from .models import *
from .views import *
from .serializer import *
from unittest.mock import patch
from django.urls import reverse
from django.test import TestCase
from rest_framework.test import APIRequestFactory
from rest_framework.authtoken.models import Token
# from .views import RegisterUser

# Regiser user
class RegisterUserTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
    
    def test_register_user(self):
        data = {
            'email': 'test@example.com', 
            'username': 'testuser', 
            'role': 'normal', 
            'password': 'password'
        }
        request = self.factory.post('api/register/user', data)
        view = RegisterUser.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['message'], 'User registered successfully')
        self.assertEqual(response.data['is_success'], True)
        self.assertEqual(response.data['status'], 200)

        # Check if the user is created
        user = Users.objects.get(email='test@example.com')
        self.assertEqual(user.email, 'test@example.com')
        self.assertEqual(user.username, 'testuser')
        self.assertEqual(user.role, 'normal')
        self.assertTrue(user.check_password('password'))
        self.assertFalse(user.is_admin)


# # # Login
class LoginUserTestCase(TestCase):
    
    def setUp(self):
        self.factory = APIRequestFactory()
        # Register the user
        data = {
            'email': 'test@example.com', 
            'username': 'testuser', 
            'role': 'normal', 
            'password': 'test'
        }
        request = self.factory.post('/api/register/user/', data)
        view = RegisterUser.as_view()
        response = view(request)

        # Get the user
        self.user = Users.objects.get(email='test@example.com')
        self.user.is_active = True
        self.user.save()
        
        # Create a token for the user
        self.token = Token.objects.create(user=self.user)

    def test_login_user(self):
        # Log in the user
        request = self.factory.post('/api/authenticate/token/', {'email': 'test@example.com', 'password': 'test'})
        view = LoginUser.as_view()
        response = view(request)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['message'], 'Login successful')
        self.assertEqual(response.data['is_success'], True)
        self.assertEqual(response.data['status'], 200)

# # Ngo register
class NgoProfileTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.ngo = Ngo.objects.create(ngo_name="Test NGO", ngo_email="test@example.com", ngo_contact="1234567890")
    
    def test_get_ngo_profile(self):
        request = self.factory.get('/api/ngo-profile/')
        view = NgoProfile.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['message'], 'Success')
        self.assertEqual(response.data['is_success'], True)
        self.assertEqual(response.data['status'], 200)
        self.assertEqual(response.data['ngo']['ngo_name'], self.ngo.ngo_name)

# # Unit test 
class DeviceTokenViewTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.user = Users.objects.create_user(username='testuser', email='test@example.com', role='role', password='testpassword')

    def test_device_token_view(self):
        user_id = self.user.id
        token = "new_device_token"
        created_by = "test_user"

        request = self.factory.post(f'/api/fcm/device/token/save?user_id={user_id}&token={token}&created_by={created_by}')
        view = DeviceTokenView.as_view()
        response = view(request)

        self.assertEqual(response.status_code, 200)

        # Check if the device token is saved correctly
        device = Device.objects.get(user_id=user_id)
        self.assertEqual(device.token, token)
        self.assertEqual(device.created_by, created_by)

# # Email verify
class EmailVerifyTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.user = Users.objects.create_user(username='testuser', email='test@example.com', role="donor", password='test')

    def test_email_verify_success(self):
        email = 'test@example.com'
        request = self.factory.get('/api/email/verify/', {'query': email})
        view = EmailVerify.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)

# History
class HistoryDetailsTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.user = Users.objects.create_user(username='testuser', email='test@example.com', role='donor', password='testpassword')
        self.food = Food.objects.create(food_name='Test Food', donor=self.user)

    def test_history_details(self):
        request = self.factory.get('/api/food/history/all/')
        force_authenticate(request, user=self.user)
        view = HistoryDetails.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['is_success'], True)
        self.assertEqual(response.data['message'], "Success")

# Report and complaint to amdin
class ReportToUserTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.user = Users.objects.create_user(username='testuser', email='test@example.com', role='donor', password='testpassword')
        self.food = Food.objects.create(food_name='Test Food', donor=self.user)
        self.report = Report.objects.create(food=self.food, complaint_to='complaint@example.com', descriptions='Test description', created_by=self.user)

    def test_report_update(self):
        data = {
            'food': self.food.id,
            'complaint_to': 'complaint_updated@example.com',
            'descriptions': 'Updated description',
            'created_by': self.user.id
        }
        request = self.factory.post('/api/user/report/', data)
        view = ReportToUser.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['is_success'], True)

# Donation Rating
class DonationCompletedRatingTestCase(TestCase):
    def setUp(self):
        self.factory = APIRequestFactory()
        self.user = Users.objects.create_user(username='testuser', email='test@example.com', role='volunteer', password='testpassword')
        self.food = Food.objects.create(food_name='Test Food', donor=self.user)
        self.history = History.objects.create(food=self.food, volunteer=self.user)

    def test_donation_completed_rating(self):
        data = {
            'id': self.history.id,
            'descriptions': 'Test description',
            'location': 'Test location',
            'rating': 5
        }
        request = self.factory.patch('/api/donation/completed/rating/', data)
        view = DonationCompletedRating.as_view()
        response = view(request)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data['is_success'], True)