# Fetch Rewards Android Application

A mobile coding exercise for Fetch Rewards, implemented as an Android application.

## Problem Statement:

**Fetch Rewards Coding Exercise - Software Engineering - Mobile**

### What do I need to submit?
Develop a native Android app in Kotlin or Java that retrieves data from [this link](https://fetch-hiring.s3.amazonaws.com/hiring.json).

The application should satisfy the following requirements:
- Display all the items grouped by "listId".
- Sort the results first by "listId" then by "name" when displaying.
- Filter out any items where "name" is blank or null.
- The final output should be presented to the user in an easily readable list format.

Ensure that the project is buildable using the latest (non-pre release) tools and supports the current release mobile OS.

### How do I submit it?
Submit the application by providing a link to a public repository, such as GitHub or BitBucket.

## Features and Implementation:

### Initial Setup:
- Setup an Android application with a main activity.
- Introduced a `RecyclerView` to display a list of items fetched from a JSON URL.

### Data Integration:
- Utilized the `OkHttpClient` library to handle HTTP requests for data fetching.
- Parsed the received JSON response to populate the `RecyclerView`.

### Filtering Mechanism:
- Implemented a filtering mechanism to exclude items with `null` or empty "name" fields.

### Sorting Mechanism:
- Users can sort the displayed items through options available in the app bar. The available sorting methods include "Name Only" or "List + Name".

### ActionBar Enhancements:
- Integrated additional options within the app bar, including a toggle switch and sorting options, to enhance the user experience.

### Error Handling and Debugging:
- Addressed various issues related to data parsing, sorting functionalities, and UI updates to ensure smooth functioning.

## How to Run:
1. Clone the repository.
2. Open the project in Android Studio.
3. Ensure you have an active internet connection.
4. Run the app on an emulator or a physical device.
