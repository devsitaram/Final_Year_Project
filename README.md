<<<<<<< HEAD
# Food Donation System

Welcome to the Food Donation project (Waste Food Donation System and Food Share App)! This system facilitates the donation and distribution of food to those in need. Below are the instructions on how to open and run the project.

## Installation

1. Android Studio (Jellyfish 2023.3.1):
   - Install the latest version for debugging and running the mobile application.

2. Visual Studio Code:
   - Use it to run the backend server and the web application.

4. MySQL Server (MySQL Installer 8.0.36):
   - Install MySQL Server for establishing the database connection.
  
3. ngrok (tools):
   - Use ngrok to convert localhost from http://127.0.0.1:8000/ to https://....api/. This is necessary for developing the Android app securely in the production phase. If you have any alternative approach, you can try it.

## Overal description How to Download or Installation the android stuidio, MySql Database, VS Code, and Ngrok
https://drive.google.com/file/d/1lg1bI-V5_UZj_JgQ4GbvJUQun7R1rAiV/view?usp=sharing

## Programming Language version
1. Python version -> Python "3.11.9"
2. Django Version -> 4.2.7
3. java version -> openjdk version "11.0.16.1" (Optional)
4. mysql -> version 8.0.36

## Web User (Admmin):
-> Activate the envirnment veraible and runser the project
   * command: setting.py file have ALLOWED_HOSTS ngrok path update
   * pip install firebase-admin (Firebase admin)
   * .\venv\Scripts\activate (activate the vertual environment)
   * command: pip install -r rerequirement.txt (for all used package install)
   * command: python manage.py runserver (run server)
   * click the server link (http://127.0.0.1:8000/)
   * login the user details: email: "admin@gmail.com", password: "admin"
   
   second options
   * create the supper user and insert the user login details (command: python manage.py createsuperuser)
   * the web is successfuly open

## Mobile User (Volunteer, Donor and Admin):
-> Open the project in android studion where ApiUrl class have API_BASE_URL update here copy to past the ngrok base url
   * Run the poject
   * Open the Mobile app and if new user register to login the resgiter details and login the system

   otherwise
   * Donor: usernaem: "donor@gmail.com", password: "donor"
   * volunteer: username: "volunteer@gmail.com, pasword: ""volunteer"
   * admin details also login the app

   * The donor can donate the new food and volunteer can accept the food where api pick-up to distribute the food.

## Majoor or Unik Features
-> View donate location in Google Map
-> Local Notification and push notifiation
-> Donation or volunteer complaint for administration
-> Donate food with food details and donor profile details
-> View History
-> Donation Rating
=======
Welcome to the Food Donation System project! This system facilitates the donation and distribution of food to those in need. Below are the instructions on how to open and run the project.

Installation
Android Studio (Jellyfish 2023.3.1):

Install the latest version for debugging and running the mobile application.
Visual Studio Code:

Use it to run the backend server and the web application.
MySQL Server (MySQL Installer 8.0.36):

Install MySQL Server for establishing the database connection.
ngrok (tools):

Use ngrok to convert localhost from http://127.0.0.1:8000/ to https://....api/. This is necessary for developing the Android app securely in the production phase. If you have any alternative approach, you can try it.
>>>>>>> c3f6082f6765ba48a0ccca10645dd78cce2d6cf5
