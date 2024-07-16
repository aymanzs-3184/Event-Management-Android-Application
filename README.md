# Events Management Application (EMA) - README

## Overview

Welcome to the Events Management Application (EMA). This Android app is designed to help event organizers manage and categorize events efficiently. 
The app includes functionalities such as user registration, login, event and category creation, and an interactive dashboard for managing events.

Note : All the Java Classes related to the project are located in the directory : app/src/main/java/com/example/assignment_1
All the layout files related to the project are located in the directory : app/src/main/res/layout

## Features

- **User Authentication**: 
  - Register new event organizers (Sign Up)
  - Login functionality with username and password validation

- **Event Management**: 
  - Create and manage event categories
  - Create and manage individual events
  - Categorize events (one-to-many relationship: one category can have multiple events, but each event belongs to only one category)
  
- **Dashboard**: 
  - Interactive dashboard for easy navigation and management
  - Floating Action Button (FAB) for quick actions
  - Persistent storage using SharedPreferences
  
- **Navigation and UI**: 
  - Navigation drawer for easy access to different sections
  - Options menu for additional actions and settings
  - RecyclerView for efficient display of lists
  - Fragments for modular UI management

## UI Components

1. **Sign Up/Register Activity**: 
   - Register new users with a form (Username, Password, Password Confirmation)
   - Store registration details in SharedPreferences
   - Show registration status via Toast messages
   - Responsive design for both portrait and landscape orientations
   - Button to launch Login Activity for already registered users

2. **Login Activity**: 
   - Login form (Username, Password)
   - Validate credentials with stored data in SharedPreferences
   - Show login status via Toast messages
   - Remember the last saved username and prefill the username field

3. **Dashboard Activity**: 
   - Buttons to launch New Event Category and Add Event activities
   - Floating Action Button (FAB) for saving events quickly
   - Navigation drawer with menu items for viewing all categories, adding new categories, viewing all events, and logging out
   - Options menu with items for refreshing content, clearing event form, deleting all categories, and deleting all events

4. **New Event Category Activity**: 
   - Form to capture event category details (Name, Event Count, Is Active)
   - Save category details to SharedPreferences
   - Show Toast message on successful save with generated Category ID

5. **New Event Activity**: 
   - Form to capture event details (Name, Category ID, Tickets Available, Is Active)
   - Save event details to SharedPreferences
   - Show Toast message on successful save with generated Event ID and Category ID

6. **Fragments**: 
   - **FragmentListCategory**: Display a list of categories using RecyclerView
   - **FragmentListEvent**: Display a list of events using RecyclerView

7. **ListCategoryActivity**: 
   - Display FragmentListCategory within the layout
   - Toolbar for navigation back to the Dashboard

8. **ListEventActivity**: 
   - Display FragmentListEvent within the layout
   - Toolbar for navigation back to the Dashboard

## Data Storage

- **SharedPreferences**: 
  - Store user credentials, event categories, and events
  - Use GSON library to convert ArrayList to plain text and vice-versa

## Usage

1. **Register a New User**: 
   - Open the app and fill in the registration form.
   - Click the "Register" button to save the details.

2. **Login**: 
   - Enter the registered username and password.
   - Click the "Login" button to access the Dashboard.

3. **Create a New Event Category**: 
   - From the Dashboard, click on the "New Event Category" button.
   - Fill in the category details and save.

4. **Add a New Event**: 
   - From the Dashboard, click on the "Add Event" button.
   - Fill in the event details and save.

5. **Manage Events and Categories**: 
   - Use the navigation drawer and options menu for various management tasks.

## Note

- Incoming SMS functionality for filling event forms is not implemented in this version.
- Ensure that only one credential (username and password) is managed.

## Development Tool

This Android Application has been developed using Android Studio.
To view and run the application, please clone this repository and open it in Android Studio.

## About the Developer

I, Ayman Zuhair Shashavali, developed this Events Management Application as part of an Android Development unit (FIT-2081) at Monash University. 
This project demonstrates my skills in mobile application development and user-centric design. 
Through this app, I have shown my ability to create user-friendly and efficient solutions for real-world needs.

---

Thank you for using ! If you have any questions or need further assistance, please feel free to contact me.
