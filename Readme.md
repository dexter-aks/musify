#Musify - Artist Details

#API Endpoints
### GET - /musify/music-artist/details/{mbid}
- Get the artist details by mbid
- Sample mbid 
  1. f27ec8db-af05-4f36-916e-3d57f91ecf5e - Michael Jackson
  2. 5b11f4ce-a62d-471e-81fc-a69a8278c7da - Nirvana

### Technical Stack
1. WEB - SpringBoot(Kotlin)
2. Testing - JUnit 5 + Mockito
3. Project Management - Gradle
4. Runtime Env - Java 11

### Build
- ./gradlew clean build

### Run
- ./gradlew bootRun

### Comments
1. More Test cases can be added
2. Image API has redirection, Followed API doc and used /{id}/front api to get image link from redirection
3. API key can be used from configuration files
4. Rest Controller Advice can be added for common error handling for UI and security purpose
5. More Logs can be added