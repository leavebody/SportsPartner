# Changelog
All notable changes to this project will be documented in this file.
## Iteration3 - 2017-10-30
### Added
* Backend: Set up PostGreSQL Datababse and Create tables
  - [User](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#user)
  - [Person](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#person)
  - [Facility_Provider](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#facility-provider)
  - [Facility](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#facility)
  - [Activity](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#activity)
  - [Activity_Member](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#activity-member)
  - [Authorization](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#authorization)
  - [Person](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#person)
  - [Person_Sports_Interest](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#person-sports-interest)
  - [Sport](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#sport)
  - [Interest](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#interest)
  - [Comment_Activity](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-activity)
  - [Comment_Facility](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-facility)
  - [Comment_Moment](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-moment)
  - [Comment_Profile](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-profile)
  - [Friend](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#friend)
  - [Moment](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#moment)
  - [Moment_Like](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#moment-like)
  - [Notification](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#notification)
  - [Pending_Friend_Request](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#pending-friend-request)
  - [Chat](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#Chat)
  - [Chat_Member](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#chat-member)
  
* Backend: Model for
  - [User](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#user)
  - [Sport](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#sport)
  - [Person](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#profile)
  - [Interest](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#interest)
  - [ProfileComment](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#profilecomment)
  - [Activity](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#activity)
  - [ActivityComment](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#activitycomment)
  - [ActivityMember](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#activitymember)
  - [Authorization](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#authorization)
  - [FriendList](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#friendlist)
  - [PendingFriendRequest](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#pendingfriendrequest)
  - [Facility](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#facility)
  - [Moment](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#moment)
  - [Notification](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#notification)
  - [FacilityProvider](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#facilityprovider)
* Backend: View Model for
  - [LoginVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#loginvo)
  - [SignupVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#signupvo)
  - [UserOutlineVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#useroutlinevo)
  - [PersonVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#personvo)
  - [ProfileCommentVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#profilecommentvo)
  - [InterestVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#interestvo)
  - [ActivityVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#activityvo)
  - [ActivityOutlineVO](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-View-Model#activityoutlinevo)
* Backend: Dao and DaoImpl for
  - User
  - Sport
  - Person
  - Interest
  - ProfileComment
  - Activity
  - ActivityComment
  - ActivityMember
  - Authorization
  - FriendList
  - Facility
  - Friend
* Backend: Controller and Service for
  - Login
  - Sign up(as a person or facility provider)
  - Get profile
  - Update profile
  - Get interests
  - Update interests
  - Get user outline
  - Get profile comments
  - Create a new proflie comment
  - Get all sports
  - Get activity details
  - Get activity outlines
  - Get upcoming activities
  - Get past activities
* Backend: Test for
  - Login
  - Sign up(as a person)
  - Get profile
  - Update profile
  - Get interests
  - Update interests
  - Get user outline
  - Get profile comments
  - Create a new proflie comment
  - Get all sports
  - Get activity details
  - Get activity outlines
  - Get upcoming activities
  - Get past activities
* Android: Activities for 
  - Basic
  - Home
  - Profile
  - CreateSportsActivity
  - SearchSportsActivity
  - SportsActivityDeatil
* Android: xmls for:
  - Profile
  - Home
  - CreateSportsActivity
  - SearchSportsActivity
  - SportsActivityDeatil 
  - Navigation Bar
  - ToolBar
* Andoird: models for:
  - Profile
  - ProfileComment
  - SportsActivity
  - SportsActivityOutline
  - Sport
  - UserOutline
* Android: Send volley requests and parse responds for:
  - All the endpoints related to sport activities 
  - All the endpoints related to user
  - All the endpoints related to profile

## Iteration4 (2017-10-31 ~ 2017-11-17)
### Added
* Android: Updated the Activities and the xmls for:
  - EditProfile
    - Change Profile Photo. (Access the file system of the Android device)
    - Change Basic Info.
    - Change Interest. (Use RecyclerView)
  - FriendList
    - View the Profile of each friend
    - Add/ Delete friend
  - SportsActivityDeatil
    - Show the profile photo of each member
    - Show Delete/Join/Leave button based on whether the user is the member or creator of this activity
  - Profile
    - Show Delete/Add/Edit button based on the relationship between the user and the owner of the profile
  - CreateActivity
    - Add Google API to choose the location
  - Notification
    - Show notifications and respond to it.
  - Setting
    - Add the night mode setting 
* Android: Updated the Datebase:
  - Add NightMode Table to deal with the notification night mode
  - Add Notifiaction Table to deal with the unread notificaitons
