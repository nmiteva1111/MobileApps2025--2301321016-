# Bookshelf (Mobile Apps Project)

## Idea
Bookshelf is an Android application for managing a personal book library. Users can add, edit, delete and browse books, search by title/author/genre, filter by genre and share book information.

## How it works
- The app stores books locally using **Room** database.
- The main screen shows a list of saved books (RecyclerView).
- Users can:
    - **Add** a new book (title, author, year, genre)
    - **Edit** an existing book
    - **Delete** a book
    - **Search** by title/author/genre
    - **Filter** the list by genre
    - Open **Details** screen and **Share** a book via Android Share Intent
- Data persists after restarting the application (Room storage).

## Architecture
- **MVVM** architecture
- **Repository** layer between ViewModel/UI and Room DAO
- UI layer: Fragments + Navigation Component
- Data layer: Room (Entity, DAO, Database)

## User Flow
1. Open app → Book List screen
2. Optional: Search and/or select genre filter
3. Tap **Add Book** → fill data → Save
4. Tap a book → Details screen (Share available)
5. Edit a book → update data → Save
6. Delete a book → book is removed

## How to run
1. Open the project in **Android Studio**
2. Sync Gradle
3. Run the app on an emulator or physical device (Min SDK 24)

## Test accounts
N/A (no authentication)

## Tests
- Unit tests: `BookValidatorTest`
- UI test (Espresso): `MainFlowTest`

## Screenshots
Screenshots will be added here.

## APK
The release APK will be located in:
`/apk/app-release.apk`
