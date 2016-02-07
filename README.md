# Project 1 - Instagram Popular photos

Instagram Popular photos is an android app that allows a user to check out popular photos from Instagram. The app utilizes Instagram API to display images and basic image information to the user.

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current popular photos** from Instagram
* [X] For each photo displayed, user can see the following details:
  * [X] Graphic, Caption, Username
  * [X] Relative timestamp, like count, user profile image

The following **optional** features are implemented:

* [X] User can **pull-to-refresh** popular stream to get the latest popular photos
* [X] Show latest comments for each photo
* [X] Display each photo with the same style and proportions as the real Instagram
* [X] Display each user profile image using a RoundedImageViewDisplay each user profile image using a [RoundedImageView](https://github.com/vinc3m1/RoundedImageView)
* [X] Display a nice default placeholder graphic for each image during loading
* [X] Improved the user interface through styling and coloring

The following **bonus** features are implemented:

* [X] Show last 2 comments for each photo
* [X] Allow user to view all comments for an image within a separate activity or dialog fragment
* [X] Allow video posts to be played in full-screen using the VideoView

The following **additional** features are implemented:

* [X] Implemented sticky header

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/rakheegupta/CodePath/raw/master/InstagramClient/InstagramDemo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



# Pre-work - Simple Todo

Simple Todo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Rakhee Gupta

Time spent: 60 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Use material design for activities
* [X] Activity Animations
* [X] Multi select and delete items
* [X] Slide to reveal and delete on each item
* [X] I did not use dialogfragment for edit activity to keep the material design but used it for priority selection. Therefore considering it done
* [X] Notifications for items due in next 5 minutes

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![Demo](https://github.com/rakheegupta/CodePath/raw/master/CodePathTodo/Demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
