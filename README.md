<img src="https://github.com/rmscoal/ABA/blob/md/readme_assets/home.jpeg" width="100" height="100" align="right" />


# Jagawana-AndroidApp
Capstone project Bangkit 2021

[![Mockup](https://img.shields.io/badge/mockup-PSDMockups-informational.svg)](https://www.psdmockups.com/mobile-android-samsung-smartphone-psd-mockup/)
[![Ringtone](https://img.shields.io/badge/ringtone-Zedge-informational.svg)](https://www.zedge.net/ringtone/03b5aaa6-a9fc-3a09-9543-122667669467)
![Build](https://img.shields.io/badge/build-passing-success.svg)
![Server](https://img.shields.io/badge/server_status-Active-critical.svg)
![Version](https://img.shields.io/badge/kotlin-1.5.0-informational.svg)


![Jagawana](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/AppScreenshot.jpg)

## About Our App

This is a client application of Jagawana, an illegal logging prevention app. This app displays where the IoT device is located and displays all the events captured by the connected IoT device. This application also in real time notifies the user if an emergency event occurs and shows the location of the event.

### Team Members

* Bobby Anggunawan (A1721801) - Mobile Programming (Android) - STMIK Mikroskil
* Marsya Yeece Jenniffer (A3092763) - Mobile Programming (Android) - Universitas Sam Ratulangi
* Jeffry Haryanto Gunawan (C2442279) - Cloud Computing - Universitas Kristen Petra
* Rini Aprilianty Riadi (C2142092) - Cloud Computing - Universitas Islam Indonesia
* Harisno (M0080883) - Machine Learning - Universitas Gadjah Mada
* Nico Renaldo (M0080886) - Machine Learning - Universitas Gadjah Mada

### Another Jagawana Github Path

* [Cloud](https://github.com/jeffrywu28/jagawana-cloud)
* [Internet of Things](https://github.com/nicorenaldo/jagawana-iot)
* [Machine Learning](https://github.com/nicorenaldo/jagawana-ml)

## How To Make Thing Like We Made

### How To Build This Project

If you build this application an error will occur. That's because this application requires an API Key to display the Google Map. Follow this tutorial to generate Google map api key
>[Set up in Cloud Console](https://developers.google.com/maps/documentation/android-sdk/start#set_up_in_cloud_console)(Note: ignore the other steps from this link)
Once you have the api key, follow these steps:
* In the main menu, select File > New > Android Resource File
* Name it google_maps_api and in the source set field select debug

![Add Api Key](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/add_api_key.JPG)

* Fill the file with the code below and replace YourApiKeyHere with the api key you created in the previous step 
```xml
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YourApiKeyHere</string>
</resources>
```
* Repeat the above steps from step 1. But this time in the source set field select release.
* Now you can build this project as usual with android studio 

### Tutorial List

* [How to make a RecyclerView](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/wiki/How-to-make-a-RecyclerView)

### Libraries We Use

| Library name                                                          | Usages                                            | Dependency                                                          |
| -------------                                                         | -------------                                     | -------------                                                       |
| [OkHttp](https://square.github.io/okhttp/)                            | Make a data request to the server                 | implementation "com.squareup.okhttp3:okhttp:4.9.1"                  |
| [Gson](https://github.com/google/gson)                                | Convert json obtained from okhttp into an object  | implementation 'com.google.code.gson:gson:2.8.6'                    |
| [Navigation component](https://developer.android.com/guide/navigation)| navigation between pages                          | implementation("androidx.navigation:navigation-fragment-ktx:2.3.5") |
|                                                                       |                                                   | implementation("androidx.navigation:navigation-ui-ktx:2.3.5")       |

### UI Libraries We Use

| Library name                                                      | Dependency                                                    | Demo                                                                                                                                |
| -------------                                                     | -------------                                                 | -------------                                                                                                                       |
| [Material Design](https://material.io/)                           | ***Auto added by android studio***                            | ![Jagawana Appbar](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/MaterialDesign.PNG)            |
| [SmoothBottomBar](https://github.com/ibrahimsn98/SmoothBottomBar) | implementation 'com.github.ibrahimsn98:SmoothBottomBar:1.7.6' | ![Jagawana SmoothBottomBar](https://github.com/Bobby-Anggunawan/Jagawana-AndroidApp/blob/master/readme_assets/BottomNavigation.PNG) |
