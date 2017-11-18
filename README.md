# Sports Partner
[![Build Status](https://travis-ci.com/jhu-oose/2017-group-4.svg?token=qu7s5Cf7sE84eRpgCzqR&branch=master)](https://travis-ci.com/jhu-oose/2017-group-4)

Playing sports with others is a good way to experience the goodness of life. Nowadays, many people want to play sports but they cannot find a companion. Sports Partner aims at sports enthusiasts who want to find sports partners. Users can create or join a sports activity with location and time specified. They can chat with fellows, share their moments and rate the activities and the sports facilities in our app. The goal of our app is to facilitate sports matching, promote social networking and encourage healthy lifestyle.
## Deployment(New!)
The Server has been deployed in heroku and it becomes the subtree of this git repo. The git address of the Server now becomes ``https://git.heroku.com/oosesportspartner.git``. We need to use git subtree command to manage the repo, some command examples are (authentication is needed for the heroku repo):
```
git remote add heroku https://git.heroku.com/oosesportspartner.git
git subtree push --prefix code/Server heroku master
git push origin dev
git subtree pull --prefix code/Server heroku master
git pull origin dev
```

The address of the Server is ``https://oosesportspartner.herokuapp.com``. The default setting of heroku repo is private, so please let our members know if you want to be the collaborator and see the details of the deployment.
## Database(New!)
The database has moved to heroku postgreSQL database. Although our original database is also an online postgreSQL databse, we move it to heroku because we can get rid of the "To many connections" problem and keep the integrity of the server.
## Build and Run
Our code consists of two part. The Server part is in ``/code/Server`` folder. The Android part is in ``/code/Android`` folder.

### Andriod

#### Environment
- Android Studio(Version 171.4498382)
- JRE: 1.8.0
- JVM: OpenJDK 64-bit Server
- Android Virtual Device: API 24, Android 7.0, resolution: 1080 x 1920 xxhdpi

#### Build 
- Use the Android Studio to open the ``/code/Android/Sports Partner`` as an Android project.
- Click Build->Make Project to bulid the project.
- Click Run->run'app',and then choose an Android virtuale device to run the project.

### Server
- Build project: In Intelij, open ``/code/Server/pom.xml`` as project, then click Build->Build Project. (Java Version:1.8)
- Run the server: run ``/code/Server/src/main/java/com/sportspartner/main/Bootstrap.java``
- Run the test: run the 4 files in ``/code/Server/src/test/java/com/sportspartner/unittest/``


## Play with the Sports Partner App on Andorid

After you build both the Android and the server projects, you can play with our app!

For the iterration 3, you can do the following things in our app.
- Signup: enter your name and password, then the app will login automaticly.
- Login: Since our app is still in developing, we suggest you login with username: ``u1``, password: ''p1'' to explore the current functions.
- Profile: After you login in using ``u1``, you will jump to the profile page to view the basic info of ``u1``.
  Besides, you can see the upcomming activities in the bottom of this page. 
  - Scroll down to the bottom to refresh the page. You will see more upcomming activities as well as the history activities.
- Navigation bar: Click on the top left button to arouse the navigation bar, press the button then you will jump to the correspoding page.
- Home Page: You can view your upcomming activities and recommended activities. If you want to create or search certain activities, just click on the corresponding buttons.
- Create new Activity Page: You can choose type of sports, date, time and write description for you activity. However, the ``Create`` and ``Cancel`` buttons do nothing at this moment.
- Search new Activity Page: You can choose type of sports, date, time and write description for you activity. However, the ``Join`` button does nothing at this moment.
- Other Pages: They are empty and needed to be impelemented.
- Logout: Go to the navigation bar and click signout, you will successfully signout.
