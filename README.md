# global

A simple app that queries the Github api to retrieve basic data about the `firebase` repositories

Updated to use `StateFlow` and UI Events. 

Project infers separation of concerns i.e domain layer but does not make use of UseCases due to its size and simple nature of the API calls. 



Running the application will require you to either:

- Add your own access token to the gradle properties file or
- Comment out line 27 in remote_source_module.kt (There is a 60 requests per hour rate-limit)