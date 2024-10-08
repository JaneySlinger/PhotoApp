# PhotoApp

## App Description
The app allows users to search for images from the Flickr Api. If a user taps on an image they are taken to the detail screen where they can view more information about the image.

![App preview](https://github.com/JaneySlinger/PhotoApp/blob/2bb1dea89689a33e4f1ff9b72b2cfa9eed456698/Screenshots/exampleScreenshot.png)

If the user taps on the profile on the main screen it will update to a search of that user's images.

### Searching
Users can search by either a term, tag, or userId.
- Term - any String value
- Tag - starting with # any amount of space separated tags e.g. "#dogs #cats". Whether the search is for Any or All of the tags is controlled with the switch. 
- UserId - starting with @ followed by the userId. Currently the userId is not surfaced to users. In future this would map the userName to the UserId for easier search.

## Running the project
The project was developed in Android Studio Jellyfish with Gradle 8.6 and AGP 8.4.2 due to build issues intermittently occurring on Windows with the most recent version of gradle and Android Studio.

## Adding a Flickr Api Key
To enable the network calls to the flickr api you will need an API key.

### Get an API key
If you don't already have an API key for the Flickr Api [create an account and request one here](  https://www.flickr.com/services/apps/create/apply).

### Add the key to the project

Once you have the key, create a file called apiKey.properties in the root of the project with the following contents.

```FLICKR_API_KEY="your api key here" ```

Rebuild the project and the service calls should work as the key will be fetched from the build config.

## Architecture
The app is built with MVVM architecture. This results in a single source of truth for the state and a unidirectional data flow. As view models don't reference the views they can be unit tested independently.

Network layer
- retrofit service

Data layer
- repository
- paging data source

UI layer
- Viewmodels
- Compose layout files

## Libraries Used
### Network
- Retrofit
- Moshi
- Coil - used for asynchronous image loading to render the images in the app from the corresponding urls returned from the network.

### Dependency Injection
- Hilt - used to enable dependency injection in the project, including of the repository and paging source, and to enable swapping dependencies out in test.

### Unit test mocks
- Mockito - used for mocking classes in unit tests.

### Data paging
- Jetpack paging library - Used to enable paging of data from the flickr api so that it can be incrementally loaded as the user scrolls down the results leading to a better use of network and memory resources. 



