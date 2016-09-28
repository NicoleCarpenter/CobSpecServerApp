[![Build Status](https://travis-ci.org/NicoleCarpenter/CobSpecServerApp.svg?branch=master)](https://travis-ci.org/NicoleCarpenter/CobSpecServerApp)

# Cob Spec Runner

This application is made to run using a [Java Http Server](https://github.com/NicoleCarpenter/JavaHttpServer) as a dependency. The application adheres to FitNess [Cob Spec](https://github.com/8thlight/cob_spec) acceptance criteria.

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

From there, you will need to build the application, which will enable you to use the jar file provided as a dependency and stored in [Clojars](https://clojars.org/org.clojars.ncarpenter/java-http-server)

```
gradlew build
```

## Running the Tests

### Local unit tests

To run the unit tests from the root directory, type

```
./gradlew test
```

### Cob Spec acceptance suite

The Cob Spec tests can be run using the instructions in the [Cob Spec](https://github.com/8thlight/cob_spec) repo. When editing the server information, your path variable* will be 

```
<pathToApplication>/CobSpecServerApp/libs/CobSpecServerApp-0.1.0.jar -p 5000 -d public
```

The __p__ flag indicates that the application will run on port 5000 and the __d__ flag specifies that public files are located in the public directory, so the directory variable* should look as such


```
define PUBLIC_DIR {./public/}
```

_*be sure to remove the dashes in front of the variable declarations_