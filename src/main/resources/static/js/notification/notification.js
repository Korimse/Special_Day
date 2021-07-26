const firebaseModule = (function () {
    async function init() {
        // Your web app's Firebase configuration
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', function() {
                navigator.serviceWorker.register('/firebase-messaging-sw.js')
                    .then(registration => {
                        var firebaseConfig = {
                            apiKey: "AIzaSyDb-XVw0Tx9KAFIiFzy4BG1WUkKCBSknog",
                            authDomain: "special-day-7c361.firebaseapp.com",
                            projectId: "special-day-7c361",
                            storageBucket: "special-day-7c361.appspot.com",
                            messagingSenderId: "1081475902847",
                            appId: "1:1081475902847:web:6bbadd48daa3f41e9b7e4a",
                            measurementId: "G-ENBDGVHMTS"
                          };
                        // Initialize Firebase
                        firebase.initializeApp(firebaseConfig);


                        // Show Notificaiton Dialog
                        const messaging = firebase.messaging();
                        messaging.requestPermission()
                        .then(function() {
                            console.log("You have permission");
                            return messaging.getToken();
                        })
                        .then(async function(token) {
                            console.log(token);
                            await fetch('/register', { method: 'post', body: token });

                            messaging.onMessage(payload => {
                                const title = payload.notification.title;
                                const options = {
                                    body : payload.notification.body
                                };
                                navigator.serviceWorker.ready
                                .then(function(serviceWorkerRegistration) {
                                    serviceWorkerRegistration.showNotification(title, options);
                                });
                            });
                        })
                        .catch(function(err) {
                            console.log("Error Occured");
                        })
                    })
            })
        }
    }

    return {
        init: function () {
            init()
        }
    }
})()

firebaseModule.init()