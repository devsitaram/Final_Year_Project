
from firebase_admin import messaging
from rest_framework.response import Response
from rest_framework.permissions import AllowAny
from rest_framework.views import APIView
from rest_framework.response import Response
from firebase_admin import messaging

class SendNotificationView(APIView):
    permission_classes = [AllowAny]
    
    def post(self, request):
        list_of_tokens = [
            'f6wpRufYSBi4QaCgvLh_q-:APA91bEYEl_YJZfiZMbq5qbNC6W5EPF0eSEi8ZkPqMtJ7DgfFvVvBAa3MeeCny_ZI2ufAlnYOzHlg4a35j15WAbbfWFHiGYztv0LxEi7yGl7h6E6Fyh6IliJKil73ME783aoz6eLn09-',
            ]
        title = 'Title 4 Food Donation'
        body = 'The food is available for donation. Please pick up the food for distribution.'
        return send_notifications(title, body, list_of_tokens)
    
# Firebase push notification
def send_notifications(title, body, list_of_tokens):
    try:
        # Create the message
        message = messaging.MulticastMessage(
            notification=messaging.Notification(
                title=title,
                body=body,
            ),
            tokens=list_of_tokens,
        )
        
        # Define headers
        headers = {
            'Content-Type': 'application/json',
            'Authorization': 'AAAAw-NCzFM:APA91bHGq2kIJh4m9wBnNEFWIdMfwI_jSCcBxOCSPWDi0Sgvr_A2B8LyQVpPaA5baBVYmGV2ld5Q8osOTDOoJVfECNvjkue9cOVqf7TlKHpz6mS0MrNxXfoWU11_79TOAOnNGqzXMdUa'  # Replace YOUR_SERVER_KEY with your actual FCM server key
        }
        messaging.send_multicast(message) # send message for multi device
        return Response({'message': 'Notifications sent to all devices', 'is_success': True, 'status': 200})
        
    except Exception as e:
        return Response({'message': 'Failed to send notifications', 'is_success': False, 'status': 500})

    




































# def send_notification(list_of_tokens):
#     try:
#         title = 'New Food Donation'
#         body = 'The food is available for distribution'

#         message = messaging.MulticastMessage(
#             notification=messaging.Notification(
#                 title=title,
#                 body=body,
#             ),
#             tokens=list_of_tokens,
#         )

#         response = messaging.send_multicast(message)
#         return Response({'message': f'Notifications sent to all devices {response}', 'is_success': True, 'status': 200})
        
#     except Exception as e:
#         return Response({'message': 'Server error', 'is_success': False, 'status': 500})