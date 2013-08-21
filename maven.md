
# Mavens cheat cheat

## How to get sources and documentation attached to Eclipse for Maven Dependencies

    mvn eclipse:eclipse -DdownloadSources -DdownloadJavadocs

or

    mvn -Declipse.downloadSources=true eclipse:eclipse


## How to debug Maven tests 

    mvn test -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9000 -Xnoagent -Djava.compiler=NONE"

## How to debug Maven plugins

on windows
    set MAVEN_OPTS=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1234

on linux
    export MAVEN_OPTS="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=1234"


## How to retrieve nexus artifact with wget

    http://parsl1113139.fr.net.intra:8000/nexus/service/local/artifact/maven/content?r=public-snapshots&g=com.bnpparibas.risk.re.services&a=calculation-engine-services&v=LATEST&c=war

    wget "http://repository.sonatype.org/service/local/artifact/maven/content?
r=snapshots&g=org.sonatype.nexus&a=nexus-utils&v=LATEST" --content-disposition

Where\:

r = the id of the repository or group to search (Required)
g = the groupId of the file (Required)
a = the artifactId of the file (Required)
v = the version of the file, this may be "LATEST", "RELEASE", a version number like "2.0", or a snapshot version number like "2.0-SNAPSHOT". (Required)
c = the classifier of the file (Optional)
e = the type or extension of the file (Optional)
p = packaging (Nexus will resolve known packaging types to the correct extension). (Optional)
If the version is not a concrete version, then Nexus will look in the maven-metadata.xml in the same way Maven does to resolve the version. The filename will be set in the content-disposition header field, so if you are using wget, be sure to use the --content-disposition flag so the filename is correct.

There is an alternate form of the API that uses redirects to work around the content-disposition setting. This form is shown below\:

wget "http://repository.sonatype.org/service/local/artifact/maven/redirect?
r=snapshots&g=org.sonatype.nexus&a=nexus-utils&v=LATEST"

## Display dependency tree

mvn dependency:tree

## How to display all profiles

   mvn help:all-profiles

## How to display active profiles

   mvn help:active-profiles


## How to use a profile

    mvn -P profile1,profile2

## How to disable a profile

    mvn -P !profile1,!profile2

## How to skip tests

    mvn install -DskipTests

## How to start one test

    mvn clean integration-test -Dit.test=NewTest

## How to pass parameters from teamcity to maven via profile

    mvn clean install -Pdeploy-artifacts -P%conf.DEPLOY_ENV%

or

    mvn clean install -Dmyapp.url=http://host1:4280/

then add something like this to pom:

```xml
    <properties>
        <myapp.url>http://hostdefault:8080/</myapp.url>
        <in-or-out-process.arg>-Dtest.type=out-process</in-or-out-process.arg>
        <exclude.some.tests>**/none*</exclude.some.tests>
        <soaktest.test>false</soaktest.test>
    </properties>

```

## How to deploy files to Nexus

```bash
mvn deploy:deploy-file -DpomFile=fleet-0.9.5.pom -DrepositoryId=myrepo-releases -Durl=http://hostname/nexus/content/repositories/myrepo-releases -Dfile=fleet-0.9.5.jar
```


```bash
mvn deploy:deploy-file -DgroupId=clj-http -DartifactId=clj-http -Dversion=0.4.1 -Dpackaging=jar -DrepositoryId=myrepo-releases -Durl=http://hostname/nexus/content/repositories/myrepo-releases -Dfile=clj-http-0.4.1.jar
```

## How to deploy files to local repo

```bash
mvn install:install-file -DgroupId=clj-http -DartifactId=clj-http -Dversion=0.4.1 -Dpackaging=jar -Dfile=clj-http-0.4.1.jar
```

