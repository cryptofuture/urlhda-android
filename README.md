# urlhda-android
Secure and open-source android application for URL shortening.  
Android application part separated from [urlhda](https://github.com/cryptofuture/urlhda) project.

##Usage
Change host in [index.html](https://raw.githubusercontent.com/cryptofuture/urlhda-android/master/app/src/main/assets/index.html), import to android studio, sign apk with your personal key and finally build apk.

Live example: [hda.me](https://hda.me)  
You can also install Urlhda application from [F-droid](https://f-droid.org/repository/browse/?fdid=me.hda.urlhda) if you don't need your own URL shortener for android.

## How it works
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
