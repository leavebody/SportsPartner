# Sports Partner
[![Build Status](https://travis-ci.com/jhu-oose/2017-group-4.svg?token=qu7s5Cf7sE84eRpgCzqR&branch=master)](https://travis-ci.com/jhu-oose/2017-group-4)

Playing sports with others is a good way to experience the goodness of life. Nowadays, many people want to play sports but they cannot find a companion. Sports Partner aims at sports enthusiasts who want to find sports partners. Users can create or join a sports activity with location and time specified. They can chat with fellows, share their moments and rate the activities and the sports facilities in our app. The goal of our app is to facilitate sports matching, promote social networking and encourage healthy lifestyle.

## Build and Run
Our code consists of two part. The Server part is in ``/code/Server`` folder. The Android part is in ``/code/Android`` folder.

### Andriod

#### Environment
- Android Studio(Version 171.4498382)
- JRE: 1.8.0
- JVM: OpenJDK 64-bit Server
- Android Virtual Device: API 24, Android 7.0, resolution: 1080 x 1920 xxhdpi

#### Build 
- Use the Android Studio to open the ``/code/Android`` as an Android project.
- Click Build->Make Project to bulid the project.
- Click Run->run'app',and then choose a Android virtuale device to run the project.

### Server
- Build project: In Intelij, open ``/code/Server/pom.xml`` as project, then click Build->Build Project. (Java Version:1.8)
- Run the server: run ``/code/Server/src/main/java/com/sportspartner/main/Bootstrap.java``
- Run the test: run the 4 files in ``/code/Server/src/test/java/com/sportspartner/unittest/``


