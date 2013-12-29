window.fbAsyncInit = function() {
	console.log("FB load successful!")
	// init the FB JS SDK
	FB.init({
		appId : '1392196911029810', // App ID from the app dashboard
		status : true, // Check Facebook Login status
		xfbml : true
		// Look for social plugins on the page
	});

	// Additional initialization code such as adding Event Listeners goes here
	console.log("Checking loging status......");
	console.log(FB);
	FB.getLoginStatus(function(response) {
		console.log(response.status)
		if (response.status === 'connected') {
			// the user is logged in and has authenticated your
			// app, and response.authResponse supplies
			// the user's ID, a valid access token, a signed
			// request, and the time the access token 
			// and signed request each expire
			var uid = response.authResponse.userID;
			var accessToken = response.authResponse.accessToken;
			console.log("User ID: "+uid);
			console.log("User Access token: "+accessToken);
			FB.api('/me', function(response) {
				console.log('Good to see you, ' + response.name + '.');
				console.log(response);
			});

		} else if (response.status === 'not_authorized') {
			// the user is logged in to Facebook, 
			// but has not authenticated your app
			FB.login(function(response) {
				if (response.authResponse) {
					console.log('Welcome!  Fetching your information.... ');
					FB.api('/me', function(response) {
						console.log('Good to see you, ' + response.name + '.');
						console.log(response);
					});
				} else {
					console.log('User cancelled login or did not fully authorize.');
				}
			});

		} else {
			// the user isn't logged in to Facebook.
			window.top.location='https://www.facebook.com/index.php';
		}
	});     

};

// Load the SDK asynchronously
(function() {
	console.log("FB API load started...");
	console.log(document.getElementById('facebook-jssdk'));

	// If we've already installed the SDK, we're done
	if (document.getElementById('facebook-jssdk')) {
		console.log("facebook jssdk not already loaded!!");
		return;
	}

	// Get the first script element, which we'll use to find the parent node
	var firstScriptElement = document.getElementsByTagName('script')[0];
	console.log("firstScriptElement: " + firstScriptElement);

	// Create a new script element and set its id
	var facebookJS = document.createElement('script');
	facebookJS.id = 'facebook-jssdk';
	console.log(facebookJS);
	console.log(facebookJS.id);

	// Set the new script's source to the source of the Facebook JS SDK
	facebookJS.src = '//connect.facebook.net/en_US/all.js';
	console.log(facebookJS.src);

	// Insert the Facebook JS SDK into the DOM
	firstScriptElement.parentNode.insertBefore(facebookJS,
			firstScriptElement);

}());