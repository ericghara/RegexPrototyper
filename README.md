# RegexPrototyper
Across different libraries and languages regular expressions can be implemented in subtly different ways.  For this reason it makes sense to prototype a regular expression using the same implementation that will be used in production.  RegexPrototyper is a CLI utility written in java and uses java.util.regex.  Its intent is to help java developers prototype regular expressions by showing matches to regular expression searches in a given text file.

## Build
```gradle build```

## Run
```java -jar build/libs/RegExPrototyper-0.0.1.jar {text file path}```<br>
<em>Note:</em> requires java >= 17

## Use
Input a regular expression.  Press enter.<br>
Exit with Ctrl+z or Ctrl+d.

 
