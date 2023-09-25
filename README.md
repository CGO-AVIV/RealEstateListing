# RealEstateListing

This project is a technical test for the AVIV-Group.
The request is to build an application displaying a real estate listing and a detailed version when tapping one of them.
The only requests were:
- Kotlin only
- Api 26 minimum

## Application architecture

I have opted for a multi-module approach. This offers many benefits including build time, team-friendly codebase, dependencies management, layer-based architectures.
The app is composed of:
- A network util module
- Feature modules
- The app itself of course

## Utils modules

### The network module

In this module we can find the building bricks of the network layer needed by the features to get their data.
Only data modules (and the app module to declare the dependencies injections) depend on it, others have no business with it.
Tools wise, it uses Retrofit (and OkHttp by transitivity) to declare the APIs and provide Web servers and Moshi to parse the data retrieved.

## Feature modules

Due to the small scope of the app and the close deadline, there is not a ton of features but good practices should not be taken when a project is too big to be maintained, which is why
I added a feature package containing the features of the app.
A recursive call inside the gradle file allows to include them all without having to declare them one by one.

### Real Estate Listing Data

This module contains everything data related.
From the Retrofit API to the Web server without forgetting the repository. For the sake of this test, the only data source is the web server but the code is 
architectured to permit the addition of other ways of getting the data, be it a database, a file or even an in-memory object acting as a per-session cache.
This module also contains Data models as they are parsed by the Moshi generated adapters.
Tools wise, this module uses the same dependencies as the network bricks with an icing of Flow (it could have been Rx too that I'm more comfortable with but it seems it loses traction to Flow nowadays, 
at least for straight-to-the-point usecases like a network layer).

### Real Estate Listing Domain

This module contains everything domain related.
It requests the data from the data layer and transforms them into to domain objects that views can observe to react to changes via a ViewModel.
To do this, I created usecases to reflect one user action or one feature. In this case, one to get the listing and one to get the details.
The usecases use the repository to get the data.
The tools used here are mainly Android components.

Due to the deadline, I took an impactful decision that I wouldn't advise doing on a larger scale project. I want to talk about it briefly though so it is clear that I didn't take this decision mindlessly.
The decision is to not having a third module with the presentation layer. In this module would have been put everything view related: the composables specific to this feature (views, navigation, etc) 
or the fragments/ adapters for non-compose apps but the reduced scope once again made me abandon this idea and I chose to keep the view inside the app module but don't try this at home.

### App module

Due to the rise of Compose, I have chosen to go with this tech. I'm fairly convinced (as are many other companies) that Compose is the future of Android UI but if you use Fragments at AVIV, 
I'm more than comfortable to work with fragments too as there was no other choices when I started my journey as a developer.
Nothing really fancy to add here except that this module contains the composables of the feature instead of a dedicated presentation module. I used Jetpack Navigation to go from the listing to the details.
I created a few custom composables and implemented a skeleton of Dimens to be able to handle tablets fairly easily.

### Dependency injection

As the scope was reduced, I wanted to try a new DI tool that I hadn't experimented with: Koin. The doc was easy to follow and it hasn't been a burden to implement. I'll keep digging into it to see what are its 
limits but pleasantly surprised so far.
As most DI tools, it works with modules that can include other modules to handle cross dependencies. Modules are declared at the top level, usually the Application.

### Unit Tests

To ensure the validity of my parsing and the states management of my ViewModel, I added a few unit tests.
They are done using Mockito, jUnit and the android and coroutines testing libs.
