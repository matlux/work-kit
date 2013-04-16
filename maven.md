
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
