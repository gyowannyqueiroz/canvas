## CANDITATE: Gyowanny P. Queiroz

# ASCII Canvas Shell
This app has been built using IntelliJ IDE.

### Technologies and Frameworks:

- Java 8
- Spring Shell 2
- Spring Tests
- Gradle

## Building and Running the Shell

Build the Jar first ```gradle bootJar```

Then, run it with ```java -jar build/libs/canvas*.jar``` and you should get an output like below.
Type ```help``` to list the available commands or ```help <command>``` for command details.
Use ```exit``` to quit the shell.

```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.5.RELEASE)

2019-05-17 18:17:57.711  INFO 76921 --- [           main] c.d.canvas.AsciiCanvasApplication        : Starting AsciiCanvasApplication on pc58580al.retail2u.trcg.co.uk with PID 76921 (/Users/lmer578/Downloads/canvas/build/libs/canvas-0.0.1-SNAPSHOT.jar started by lmer578 in /Users/lmer578/Downloads/canvas)
2019-05-17 18:17:57.714  INFO 76921 --- [           main] c.d.canvas.AsciiCanvasApplication        : No active profile set, falling back to default profiles: default
2019-05-17 18:17:58.891  INFO 76921 --- [           main] c.d.canvas.AsciiCanvasApplication        : Started AsciiCanvasApplication in 1.86 seconds (JVM running for 2.354)
shell:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Draw Shell Command
        C: Creates an empty canvas
        L: Draws a line within the canvas
        R: Draws a rectangle within the canvas

```

