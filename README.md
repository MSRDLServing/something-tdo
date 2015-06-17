Something t'do
=============

A project I did for the Mobile Development course. 

Something t’do is a relevant and customizable calendar of events. A calendar of events is a chronological list of events ranging from concerts and sports to galleries and festivals. All events include a date, location, category and description. 

Something t’do for Android will implement a user specific event calendar. Something t’do will aggregate event data from an external source and allow the user to specify their interests in order to create the most relevant event calendar. The application will allow the user to sign in, thus saving user preferences from session to session. Given a specific date Something t’do will display a week’s worth of events based not only on the user specified interests but also within a predefined distance to the given location.  Location can be explicitly specified by the user or the application will default to the device’s current GPS location. The date may also be explicitly specified by the user or the application will default to the device’s current date.

Something t'do has the following features:

1.Register and login: A user needs to first register an account to login the app. Different users use different accounts to login.
2.Account: Each account maintains a specific user’s profile information and interests.
3.Set Interests: On the first use, after the login-splash-screen, a user is provided a list of preferred interests in which the must select.
4.Map view: A user can choose a map view that shows all events near the defined location.
5.List view: A user can list all events (7 days since specified date) based on distance, defined date, and interests.
6.Event Detail: A user can select an event from either Map or List view and display all of the event details.
7.Settings: A user can change settings in the app, such as the starting date of the event list, the location of the listed events, re-set the user defined interests, and change login information (password), etc. 
8.Invite a friend: A user can send an invite to a friend to a desired event.

Implementation:
1. It follows the MVC design.
2. It passes and maintains data among activities.
3. It uses both explicit and implicit intents to invoke activities.
4. Most of its activities use fragments. 
5. It supporting persistent storage. 
6. It dynamically fetch events by using Eventful API
7. It uses AsyncTask to boost performance.
7. It uses Robotium to unit test the use cases.
8. It has also been tested with Monkey Test.




