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
  - [Chat](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#Chat)
  - [Chat_Member](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#chat-member)
  - [Comment_Activity](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-activity)
  - [Comment_Facility](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-facility)
  - [Comment_Moment](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-moment)
  - [Comment_Profile](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#comment-profile)
  - [Friend](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#friend)
  - [Interest](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#interest)
  - [Moment](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#moment)
  - [Moment_Like](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#moment-like)
  - [Notification](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#notification)
  - [Pending_Friend_Request](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#pending-friend-request)
  - [Person](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#person)
  - [Person_Sports_Interest](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#person-sports-interest)
  - [Sport](https://github.com/jhu-oose/2017-group-4/wiki/Databases-Design#sport)
* Backend: Model for
  - [User](https://github.com/jhu-oose/2017-group-4/wiki/RESTful-Endpoints-Object-Model#user)
  - Sport
  - Person
  - Interest
  - ProfileComment
  - Activity
  - ActivityComment
  - ActivityMember
  - Authorization
  - FriendList
  - PendingFriendRequest
  - Facility
  - Friend
  - Moments
  - Notification
  - FacilityProvider
* Backend: View Model for
  - User
  - Signup
  - Login
  - Person
  - Interest
  - ProfileComment
  - Activity
  - ActivityOutline
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
* Andoird: models for:
  - Profile
  - ProfileComment
  - SportsActivity
  - SportsActivityOutline
  - Sport
  - UserOutline
