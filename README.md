To Use the App Please create an API key on The Movie DB at : https://www.themoviedb.org/

The API key should be used to replace <YOUR_API_KEY> in the build.gradle file of the module.

buildTypes.each {
        it.buildConfigField 'String', 'TMDB_API_KEY', "<YOUR_API_KEY>"
}