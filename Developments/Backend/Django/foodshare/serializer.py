from rest_framework import serializers
from datetime import datetime
from django.core.exceptions import ValidationError
from django.core.files.images import get_image_dimensions
from foodshare.models import *

# view json data in web
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        exclude = ('password',)  # Exclude the password field
        
    def validate_image(self, value):
        try:
            get_image_dimensions(value)
        except AttributeError:
            raise serializers.ValidationError("The uploaded file is not recognized as a valid image.")
        except ValidationError:
            raise serializers.ValidationError("The uploaded image is either not an image or a corrupted image.")
        return value
    
    def to_representation(self, instance):
        representation = super().to_representation(instance)
        modify_date = representation.get('modify_date')

        if modify_date:
            if isinstance(modify_date, str):  # Check if modify_date is a string
                modify_date = datetime.strptime(modify_date, '%Y-%m-%d')
            
            representation['modify_date'] = modify_date.date()

        return representation

class NgoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ngo
        fields = "__all__"

class FoodSerializer(serializers.ModelSerializer):
    class Meta:
        model = Food
        fields = '__all__'

class HistorySerializer(serializers.ModelSerializer):
    class Meta:
        model = History
        fields = '__all__'

class ReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = Report
        fields = '__all__'

class NotificationsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Notification
        fields = '__all__'

