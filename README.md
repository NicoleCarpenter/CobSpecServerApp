* [![Build Status](https://travis-ci.org/NicoleCarpenter/CobSpecServerApp.svg?branch=master)](https://travis-ci.org/NicoleCarpenter/CobSpecServerApp)

# Java Http Server

This application adheres to FitNess [Cob Spec](https://github.com/8thlight/cob_spec) acceptance criteria and is run using the provided jar file from the server. 

This project encorporates Gradle as a build tool. Gradle provides a wrapper feature which allows you to run Gradle commands without having Gradle installed by using the `gradlew` command when a wrapper is present.

## Dependencies

* [Java](https://java.com/en/download/)

## Running the Server

From your desired file location, clone the repo

```
git clone git@github.com:NicoleCarpenter/CobSpecServerApp.git

```

Then `cd` into the application's root directory

```
cd CobSpecServerApp.git
```

From there, you will need to build the application, which will enable you to use the jar file provided in `libs`

```
gradlew build
```

## Running the Tests

To run the unit tests from the root directory, type

```
./gradlew test
```

The Cob Spec tests can be run using the instructions in the [Cob Spec](https://github.com/8thlight/cob_spec) repo. When editing the server information, your path variable will be 

```
pathToServer/CobSpecServerApp/lib/JavaHttpServer.jar -p 5000 -d public
```

The __p__ flag indicates that the application will run on port 5000 and the __d__ flag specifies that public files are located in the public directory, so the directory variable should look as such

```
define PUBLIC_DIR {./public/}
```

_be sure to remove the dashes in front of the variable declarations_