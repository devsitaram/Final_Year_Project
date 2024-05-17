from django.shortcuts import render

# views.py

from django.shortcuts import render
from django.http import JsonResponse
from .models import Room, Message
from .consumers import ChatConsumer
from asgiref.sync import async_to_sync

def create_room(request):
    if request.method == 'POST':
        username = request.POST['username']
        room = request.POST['room']
        try:
            get_room = Room.objects.get(room_name=room)
            return JsonResponse({'message': 'The account does not have authentication permission.', 'room_created': False, 'status': 401})
            return JsonResponse({'room_created': False, 'room_name': room, 'username': username})
        except Room.DoesNotExist:
            new_room = Room(room_name=room)
            new_room.save()
            return JsonResponse({'room_created': True, 'room_name': room, 'username': username})
    return render(request, 'index.html')

def room(request, room_name, username):
    return render(request, 'room.html', {'room_name': room_name, 'username': username})

def send_message(request):
    if request.method == 'POST':
        room_name = request.POST['room_name']
        sender = request.POST['sender']
        message = request.POST['message']

        # Send message to WebSocket
        room_group_name = 'chat_%s' % room_name
        async_to_sync(ChatConsumer.channel_layer.group_send)(
            room_group_name,
            {
                'type': 'send_message',
                'message': message,
                'sender': sender
            }
        )
        return JsonResponse({'message_sent': True})
    else:
        return JsonResponse({'message_sent': False})