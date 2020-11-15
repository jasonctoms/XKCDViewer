# Simple and quick XKCD Viewer


App is made with Koin, AndroidX (including architecture and navigation components), Coroutines and Kotlin Flow.  It uses Retrofit and Kotlin Serialization for the network layer.

There are two modules: App and Service.  Service contains API, Repository, and Storage, and App contains UI and ViewModels.

### Included features:
* View latest comic with alt-text
* Browse comments incrementally by date
* Search for specific comic by number
* Search for "relevant XKCD" with a search query
* Favorite comics and view your favorites
* Share link to comic page with other apps (or clipboard)

### Not included features:
* Offline favorites
* XKCD Explanations
* Notifications of new comics

### Improvements to be made:
* Testing
* Zoomable images since all comics don't display nicely
* Better UI for favorites (and whole app really)
* Network error handling
