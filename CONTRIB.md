# Contributing
Just some notes for the developers.

## Environement Setup
To develop the project, setup your development environment as follows:

1. Download and install the latest version of [Android Studio](https://developer.android.com/studio/index.html).
2. Clone the repository, ensuring that you name it `nascent-pipeline`.  The project must be renamed 
    or Android Studio will rename the module within the project, which will break the deployment. 

## Deployment
To deploy a new version of the library:

1. Run all unit tests in the `pipeline-tests/src/test/java` folder.
2. Edit the `local.properties` file on the project root and add the following keys:
```shell
bintray.user=YOUR_USERNAME
bintray.apikey=YOUR_APIKEY
```
3. Execute the following command from the project root folder:

```shell
> ./gradlew install
> ./gradlew bintrayUpload
```