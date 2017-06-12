# Spothill library

Spothill library is a tool for working with Spothill data based on Beacon
devices + working with user data from Spothill database. Service for Beacon
detecting and Beacon handling is dealt with inside SDK

Basic setup for start using Spothill library:

There is necessity to add this line into build.gradle of project into dependencies of buildscript because of Realm database system:
```
classpath "io.realm:realm-gradle-plugin:3.1.1"
```

In case of troubles with Realm database there is [Getting Started](https://realm.io/docs/java/latest/#getting-started) guide. There is no need for downloading and applying realm plugin but there is still need for adding classpath + Progruad lines in getting stared.

Then add these dependencies to the app level build.gradle in the dependencies section:
```
dependencies {
    ...
    
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.squareup.retrofit2:retrofit:2.2.0'
    compile 'com.squareup.retrofit2:converter-gson:2.2.0'
    compile 'com.squareup.okhttp3:logging-interceptor:3.6.0'
    compile 'org.altbeacon:android-beacon-library:2.9.2'
    
    ...
}
```

Next step is to inicialize Spothill Library in Application file.

```
public class ExampleApplication extends Application {
 public SpothillLibrary spothillLibrary;

 @Override
 public void onCreate() {
  super.onCreate();
  spothillLibrary = SpothillLibrary.getInstanceForApplication(this, "hash");
 }

 public SpothillLibrary getSpothillLibrary() {
  return this.spothillLibrary;
 }
}
```


In Application file 

1. [User actions](#user-actions)
	- [User set up](#user-set-up)
	- [Login](#login)
	- [Logout](#logout)
2. [Lists](#lists)
	- [Providers](#providers)
	- [Campaigns](#campaigns)
	- [Category](#category)
3. [Campaign receiving](#campaign-receiving)
	- [Set up](#set-up)
	- [Nescesary methods](#nescesary-methods)
4. [Notofications set up](#notifications-set-up)

## 1. User Actions

### User set up

There are two ways to set up which can be used with Spothill SDK.

Temporal user:

```
spothillLibrary.temporalLogin(new Callback<User>() {
	@Override
		public void success(User user) {
		Log.i("User:", "Logged in!!")
	}

	@Override
	public void error(Spothill error) {
		Log.i("User:", "Failed to login!!")
	}
});
```

Set up user with its data:

```
spothillLibrary.registration(name,surname,email,sex,password, new Callback<User>() {

	@Override
	public void success(User user) {
		Log.i("User", "Registered!")
	}

	@Override
	public void error(Spothill error) {
		Log.i("User", "Not registered!")
	}
});
```
By default the registration methods requires the activation of the account by link send to registration email address. If you want to skip the activation then use the skipActivation param with "true" value.

```
spothillLibrary.registration(name,surname,email,sex,password, true, new Callback<User>() {

	@Override
	public void success(User user) {
		Log.i("User", "Registered!")
	}

	@Override
	public void error(Spothill error) {
		Log.i("User", "Not registered!")
	}
});
```

### Login

Login user is posible with simple callback method 

```
spothillLibrary.login(email,password, new Callback<User>() {

	@Override
	public void success(User user) {
		Log.i("User", "Logged in!")
	}

	@Override
	public void error(Spothill error) {
		Log.i("User", "Uups something went wrong")
	}
});
```

### Logout

Loging out user from Spothill-library is easy with sipmple method call:

```
spothillLibrary.lougout()
```

## Lists

### Providers:

You can call different actions on campaign providers. Here is a short list of methods.

To use actions you must first call method:

```
pubic ProviderActions providerActions = spothillLibrary.getProviderActions()
``` 

You can use call methods on this object:
``` 
//Ignore provider
//param: int id
providerActions.actionIgnore(id)

//Get list of blocked providers
//param: Callback<ProviderList>
providerActions.blockedProvidersList(callback)

// Parameters for page loading blocked providers
providerActions.blockedProvidersList(page, count, callback)

//Same parameters as two methods above
providerActions.favourtieProvidersList(callback)
providerActions.favourtieProvidersList(page, count, callback)
``` 
### Campaigns:

Here are some actions which you can call outside of receiving real-time campaigns

To use these actions you must first call method:

```
pubic CampaignsActions campaignsActions = spothillLibrary.getCampaignsActions()
``` 

Here is a list of available methods for campaign handling. Remember that method signature is basically similar to ProviderActions

``` 
campaignsActions.actionReminder(id)
campaignsActions.actionFavourite(id)
campaignsActions.actionIgnore(id)

//Callback<CamaignList>
campaignsActions.reminderCampaignsList(callback)
campaignsActions.reminderCampaignsList(page, count, callback)

campaignsActions.favouriteCampaignsList(callback)
campaignsActions.favouriteCampaignsList(page, count, callback)

campaignsActions.ignoredCampaignsList(callback)
campaignsActions.ignoredCampaignsList(page, count, callback)

``` 

### Category:

Here are some actions which you can use with category items

To use these actions you must first call method:

```
pubic CategoryActions categoryActions = spothillLibrary.getCategoryActions()
```

```
categoryActions.getBlockedCategories(callback)
``` 

## Campaign receiving

### Setup: 

Your activity or fragment must implement this interface: CampaignsReceiver

``` 
public class ExampleFragment extends Fragment implements CampaignsReceiver
``` 

This wil invoke overriding method :

```
@Override
public void nearByCampaigns(ArrayList<Campaign> campaigns)
{
	Log.e("Info", "You will get near by campaigns here");
}
```
### Nescesary methods

On start of activity/fragment you must call these two method with help of instance of SpothillLibrary
```
spothillLibrary.bind(applicationContext, this);
spothillLibrary.startSendingCampaigns();
```

if you want to stop receiving campaigns you must call :
```
spothillLibrary.stopSendingCampaigns();
```
## Notofications set up

First you will need to call method setNotificationSettings with activityToOpen parameter which is class of an activity to which user will be redirected after clicking on notification. smallIcon parameter represents image resource id which will displayed as icon in the notification.

```
spothillLibrary.setNotificationSettings(activityToOpen, smallIcon)
```

### SpothillError
Use the error.getCode() to get the code and then you can compare it by defined error coder like "SpothillError.UNKNOWN_ERROR". So you are able to get more info about the error.
