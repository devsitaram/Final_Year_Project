# Generated by Django 4.2.7 on 2024-04-20 19:19

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('foodshare', '0009_alter_users_is_active'),
    ]

    operations = [
        migrations.RenameField(
            model_name='notification',
            old_name='status',
            new_name='is_delete',
        ),
    ]
