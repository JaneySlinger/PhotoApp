# PhotoApp

## Adding a Flickr Api Key

### Get an API key
If you don't already have an API key for the Flickr Api [create an account and request one here](  https://www.flickr.com/services/apps/create/apply).

### Add the key to the project

Once you have the key, create a file called apiKey.properties in the root of the project with the following contents.

```FLICKR_API_KEY="your api key here" ```

Rebuild the project and the service calls should work. 