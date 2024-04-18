from django.db import models
from datetime import datetime
from django.core.validators import MinValueValidator, MaxValueValidator
from django.contrib.auth.models import BaseUserManager, AbstractBaseUser

# create manager
class UserManager(BaseUserManager):
    # normal user
    def create_user(self, email, username, role, password=None, newpassword=None):
        """
        Creates and saves a User with the given email, username, role and password.
        """
        if not email:
            raise ValueError('Users must have an email address')

        user = self.model(
            email=self.normalize_email(email),
            username=username,
            role = role,
        )

        user.set_password(password)
        user.save(using=self._db)
        return user
    
    # super user
    def create_superuser(self, email, username, role, password=None):
        """
        Creates and saves a superuser with the given email, username, role, and password.
        """
        user = self.create_user(
            email=email,
            username=username,
            role=role,
            password=password,
        )
        user.is_active = True
        user.is_admin = True
        user.is_superuser = True
        user.save(using=self._db)
        return user

# create custom User table AbstractBaseUser
class Users(AbstractBaseUser):
    email = models.EmailField(verbose_name='Email', max_length=255, unique=True)
    username = models.CharField(max_length=100)
    role = models.CharField(max_length=10, null=True)
    
    address = models.CharField(max_length=100, null=True)
    contact_number = models.CharField(max_length=16, unique=True, null=True)
    gender = models.CharField(max_length=10, null=True)
    date_of_birth = models.DateField(default=datetime.now, null=True)
    abouts_user = models.TextField(max_length=500, null=True)
    photo_url = models.ImageField(upload_to='user_images/', null=True, max_length=500)

    is_admin = models.BooleanField(default=False)
    is_active = models.BooleanField(default=False)
    created_by = models.CharField(max_length=100, null=True, default='Self')
    created_date = models.DateField(auto_now_add=True)
    modify_by = models.CharField(max_length=50, null=True)
    modify_date = models.DateField(null=True)
    is_delete = models.BooleanField(default=False)

    objects = UserManager() # call the usermanager classs

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', 'role',]

    def __str__(self):
        return self.username

    def has_perm(self, perm, obj=None):
        "Does the user have a specific permission?"
        return True # self.is_admin

    def has_module_perms(self, app_label):
        "Does the user have permissions to view the app `app_label`?"
        return True

    @property
    def is_staff(self):
        "Is the user a member of staff?"
        return True # return self.is_admin


# crate the Food model
class Food(models.Model):
    FOOD_TYPE_CHOICES = (
        ('Others', 'Others'),
        ('Cake', 'Cake'),
        ('Green vegetables', 'Green vegetables'),
        ('Biscuits & Chocolates', 'Biscuits & Chocolates'),
        ('Sweet Snack', 'Sweet Snack'),
        ('Stable Food', 'Stable Food'),
        ('Fruits', 'Fruits'),
        ('Meets', 'Meets'),
        ('Water & Cold Drinks', 'Water & Cold Drinks'),
    )
    STATUS_CHOICES = (('New', 'New'), ('Pending', 'Pending'), ('Completed', 'Completed'),)

    food_name = models.CharField(max_length=100)
    food_types = models.CharField(max_length=50, choices=FOOD_TYPE_CHOICES, default='Others')
    quantity = models.IntegerField(null=True)
    expire_time = models.CharField(max_length=10, null=True)
    pick_up_location = models.CharField(max_length=100)
    latitude = models.DecimalField(max_digits=22, decimal_places=16, default=0.0)
    longitude = models.DecimalField(max_digits=22, decimal_places=16, default=0.0)
    descriptions = models.TextField(null=True)
    stream_url = models.ImageField(upload_to='food_images/', null=True, max_length=500)
    status = models.CharField(max_length=20, choices=STATUS_CHOICES, default='New')
    created_by = models.CharField(max_length=100, null=True)
    created_date = models.DateField(auto_now_add=True)
    modify_by = models.CharField(max_length=50, null=True)
    modify_date = models.DateField(null=True)
    donor = models.ForeignKey(Users, on_delete=models.CASCADE, null=True) # FK (donor id)
    is_delete = models.BooleanField(default=False)
    
# Reports
class Report(models.Model):
    COMPLAINT_CHOICES = (
        ('donor', 'Complaint to Donor'), 
        ('volunteer', 'Complaint to Volunteer'), 
        ('farmer', 'Complaint to Farmer'),
    )
    complaint_to  = models.CharField(max_length=30, choices=COMPLAINT_CHOICES, null=True)
    descriptions = models.TextField(null=True)
    is_verify = models.BooleanField(default=False)
    created_by = models.CharField(max_length=50, null=True)
    created_date = models.DateField(auto_now_add=datetime.now)
    modify_by = models.CharField(max_length=50, null=True)
    modify_date = models.DateField(null=True)
    is_delete = models.BooleanField(default=False)
    food = models.ForeignKey(Food, on_delete=models.CASCADE) #FK
    
# create the History table
class History(models.Model):
    STATUS_CHOICES = (('Pending', 'Pending'), ('Completed', 'Completed'),)

    descriptions = models.TextField(max_length=300, null=True)
    distributed_location = models.CharField(max_length=100, null=True)
    rating_point = models.IntegerField(validators=[MinValueValidator(0), MaxValueValidator(5)], default=0)
    distributed_date = models.DateField(null=True)
    status = models.CharField(max_length=20, choices=STATUS_CHOICES, default='Pending')
    created_by = models.CharField(max_length=50, null=True)
    created_date = models.DateField(auto_now_add=datetime.now)
    modify_by = models.CharField(max_length=50, null=True)
    modify_date = models.DateField(null=True)
    is_delete = models.BooleanField(default=False)
    volunteer = models.ForeignKey(Users, on_delete=models.CASCADE, null=True) # FK (volunteer id)
    food = models.ForeignKey(Food, on_delete=models.CASCADE, null=True) # FK (food id)

# create the Ngos model
class Ngo(models.Model):
    # ngo_id = models.AutoField(primary_key=True)
    ngo_name = models.CharField(max_length=100, unique=True)
    ngo_email = models.EmailField(max_length=50, unique=True)
    ngo_location = models.CharField(max_length=100, null=True)
    ngo_contact = models.CharField(max_length=15, unique=True)
    established_date = models.DateField(datetime.now)
    abouts_ngo = models.TextField(null=True)
    ngo_stream_url = models.ImageField(upload_to='food_images/', null=True)
    created_by = models.CharField(max_length=100, null=True)
    created_date = models.DateField(auto_now_add=True)
    modify_by = models.CharField(max_length=50, null=True)
    modify_date = models.DateField(null=True)
    is_delete = models.BooleanField(default=False)

    # create the notification table
class Notification(models.Model):
    token = models.TextField(null=True)
    created_by = models.CharField(max_length=100, null=True)
    created_date = models.DateField(auto_now_add=True)
    is_delete = models.BooleanField(default=False)
    user = models.ForeignKey(Users, on_delete=models.CASCADE) # FK