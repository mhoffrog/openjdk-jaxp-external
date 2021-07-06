# openjdk-jaxp-external
The JAXP package of Open-JDK as external package - supposed for backward compatibility use cases.
The version number will reflect the corresponding original JDK version.


## Purpose
Regarding XML file transformation and output rendering there did have been incompatibilities between JDK versions in the past.<br/>
See: [JDK packaged JAXP transformer indent no longer working in OpenJDK 11](https://mail.openjdk.java.net/pipermail/jdk-dev/2019-May/002818.html).<br/>
Since the JAXP pacakage is bundled as an internal package within the JDKs runtime library, it cannot simply be replaced or overloaded by another version or a former implementation.<br/>
Further sources of information:<br/>
&emsp;[Xerces Update: Move to Xalan based DOM L3 serializer. Deprecate Xerces' native serializer.](https://bugs.openjdk.java.net/browse/JDK-8035467)<br/>
&emsp;[LSSerializer pretty print does not work anymore. regression?](https://bugs.openjdk.java.net/browse/JDK-8087303)<br/>
&emsp;[Regression in XML Transform caused by JDK-8087303 [1]](https://bugs.openjdk.java.net/browse/JDK-8174025)<br/>
&emsp;[Regression in XML Transform caused by JDK-8087303 [2]](https://bugs.openjdk.java.net/browse/JDK-8175629)<br/>
&emsp;[Transform filtered through SAX filter mishandles character entities](https://bugs.openjdk.java.net/browse/JDK-8237456)<br/>


## Solution
This package will provide the original implementation of the JAXP package from a particular JDK version but with replacing the `com.sun.` portion of the package by <jdk-repo-name>.jaxp. - e.g.: `jdk8u.jaxp.`.
So the package can be used and mixed with any JDK version runtime supporting the code version of this external package.


## Following steps are performed by the Maven build of this package
1. Download the original code from [OpenJDK on Github](https://github.com/openjdk)<br>
   e.g.: [Zipped release 8u144 b34](https://github.com/openjdk/jdk8u/archive/refs/tags/jdk8u144-b34.zip) 
2. Copy all folders beyond `[Unpacked JDK source code folder]/jaxp/src/com/sun`  into<br/>
   `[project root]/openjdk8-jaxp-external/src-gen/jaxp/jdk8u/jaxp`<br/>
   Should look like this:<br/>
   ```
   /
   pom.xml
   README.md
   └── openjdk8-jaxp-external
       ├── pom.xml
       └── src-gen
           └── jaxp
               └── jdk8u
                   └── jaxp
                       ├── java_cup
                       ├── org
                       └── xml
   ```
3. Replace all folder names `**/internal/**` -> `**/external/**`
4. Replace the following text in ALL files beyond `[project root]/open-jdk-jaxp-external/src/jaxp/java`:
   1. `com.sun.` -> `jdk8u.jaxp.`
   2. `com/sun/` -> `jdk8u/jaxp/`
   3. `.internal.` -> `.external.`
   4. `/internal/` -> `/external/`
   5. `.internal;` -> `.external;`      


## Maven
For JDK >=7:
```xml
<dependency>
  <groupId>de.mhoffrogge.openjdk</groupId>
  <artifactId>openjdk7-jaxp-external</artifactId>
  <version>7.60.33</version>
</dependency>
```

For JDK >=8:
```xml
<dependency>
  <groupId>de.mhoffrogge.openjdk</groupId>
  <artifactId>openjdk8-jaxp-external</artifactId>
  <version>8.144.34</version>
</dependency>
```

## Example
```java
// TODO
```

## License
This project is licensed under [GNU General Public License, version 3](https://opensource.org/licenses/GPL-3.0).
