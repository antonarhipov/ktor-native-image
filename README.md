## Ktor GraalVM native image example

Run:
```shell
./gradlew clean nativeCompile
```
The native binary is generated to `./build/native/nativeCompile/graalvm-server`

Run:
```shell
./build/native/nativeCompile/graalvm-server
```
```
2024-06-04 17:26:19.378 [main] INFO  ktor.application - Autoreload is disabled because the development mode is off.
2024-06-04 17:26:19.382 [main] INFO  ktor.application - Application started in 0.005 seconds.
2024-06-04 17:26:19.387 [DefaultDispatcher-worker-1] INFO  ktor.application - Responding at http://0.0.0.0:8080
```

Run:
```shell
curl localhost:8080
```
```
Data(id=324, title=Hello, description=This is the response)
```