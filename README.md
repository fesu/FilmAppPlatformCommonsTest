Task Justification

1. Feature Completion 
	a. Single Activity App
	b. Home screen with List of movies from the API
	c. Settings screen with Dark Theme toggle & Language selector [English, French]	
	d. If internet is available than it will show data from API else it will load the cached data from the Room DB.
	e. WorkManager will also sync data to Room DB every 30 minutes.
	f.  When click on any item from the list it will show Movie Details screen.
	g. Library usage 
		- lifecycle-livedata
	- lifecycle-viewmodel
	- navigation-ui
	- glide
	- hilt-android [Dependency Injection]
	- kotlinx-coroutines
	- Room
	- Work Manger [work-runtime-ktx]
	- retrofit
	- gson
	- okhttp
	
2. Quality 
	a. Code Quality 
	i. Zero Lint error 
		- No Errors
	ii. Less Lint warnings 
	iii. Coding style 
	- Followed Google Kotlin style guidelines 
	b. Architecture 
		- MVVM
		- Singleton Design Pattern
		- LiveData
		- View Binding
		- Data Binding
	c. Resource Management (string.xml, dimens.xml usage) 
		- Included required strings & dimensions in respected files
	d. UI Design 
		- Followed material design guideline & components
		- Used theme.xml for Dark Mode support
	e. Documentation (code comments and github usage) 
		- Added KDoc strings above all of the methods & classes wherever required.
		- Proper Git commits feature or bug fix wise.

3. AndroidManifest and runtime permission management
	- This App requires following permissions which are configured in AndroidManifest.xml file
		- <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		- <uses-permission android:name="android.permission.INTERNET" />

