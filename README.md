# urlhda-android
Open-source android application for URL shortening.  
Android application part separated from [urlhda](https://github.com/cryptofuture/urlhda) project.  
I moved it to git submodule, since its easier to manage android application that way.
#####Features:  
* Strong URL - 15 symbols long uid for shorted URLs, 5 symbols by default.
* Fast copy-paste. Copy button in android app.
* Ability to share URLs.

### Install
[<img src="https://f-droid.org/badge/get-it-on.png"
      alt="Get it on F-Droid"
      height="80">](https://f-droid.org/app/me.hda.urlhda)

### Configuring application for use with own [urlhda](https://github.com/cryptofuture/urlhda/) setup
You need just install application and change hostname URL in application Settings (gear icon).

### How it works
 <p align="center">
<img src="https://raw.githubusercontent.com/cryptofuture/urlhda-android/master/gradle/img/Diagram1.png"/>
</p>

You can shorten URLs with a POST-request:
```
  curl -X POST https://website.name/add?url=http://add.me
```

This will give you JSON-response back with the generated 5 character UID:
```
[{"uid":"abcd5"}]
```

With that you can go to for example to *https://website.name/abcd5* and you will be redirected to http://add.me

##Other
Bitcoin donate: **1N5czHaoSLukFSTq2ZJujaWGjkmBxv2dT9**
