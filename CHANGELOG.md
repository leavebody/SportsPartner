# Changelog
All notable changes to this project will be documented in this file.
## Iteration3 - 2017-10-30
### Added
* Backend: Set up PostGreSQL Datababse
* Backend: Model for
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
  - Profile:
    get profile
    update profile
    get interests
    update interests
    get user outline
    get profile comments
    create a new proflie comment
    get all sports
  - Activity:
    get activity details
    get activity outlines
    get upcoming activities
    get past activities
* Backend: Test for
  - Login
  - Sign up(as a person)
  - Profile:
    get profile
    update profile
    get interests
    update interests
    get user outline
    get profile comments
    get all sports
  - Activity:
    get activity details
    get activity outlines
    get upcoming activities
    get past activities
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
