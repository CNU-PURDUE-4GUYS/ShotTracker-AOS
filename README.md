
<div align="center">

[
![Android](https://img.shields.io/badge/android%20studio-2020.3.1%20Patch%203-%233DDC84?logo=android-studio)
]()
[ ![Kotlin](https://img.shields.io/badge/kotlin-1.6.1-%237F52FF?logo=kotlin)
]()
  
[![Pattern](https://img.shields.io/badge/Pattern-MVC-%2328C8AF) ]()
[![Retrofit](https://img.shields.io/badge/Library-Retrofit-%23%234285F4)  ]()
[![Glide](https://img.shields.io/badge/Library-Glide-%23%234285F4) ]()
[![Room](https://img.shields.io/badge/Library-Room-%23%234285F4) ]()
[![Gson](https://img.shields.io/badge/Library-Gson-%23%234285F4)]()

</div>

## 🤼‍♂️ Team Member

<div align="center">
  
|[Dohyeon Ko](https://github.com/k906506)|[Dongheon Lee](https://github.com/Dongheon97)|[Jisoo Choi](https://github.com/jisoochoi7561)|[Sanghyun Park](https://github.com/hy38)|
|:----:|:----:|:----:|:----:|
|Mobile|Web & Server|Detection|Raspberry|
|<img src="https://github.com/k906506.png" width="100">|<img src="https://github.com/Dongheon97.png" width="100">|<img src="https://github.com/jisoochoi7561.png" width=100>|<img src="https://github.com/hy38.png" width="100">|
  
</div>

## 📄 Page

<div align="center">

<img width="900" src="https://user-images.githubusercontent.com/33795856/155391095-21b5d019-2c48-4701-92b8-9bd0e575b5b2.png">
  
</div>

### 📄 Pages

<div align="center">

| Main | Select Target Image | Shooting | History |
| :--------: | :--------: | :--------: |:--------: |
|<img src="https://user-images.githubusercontent.com/33795856/155388932-59f0f4b5-3884-46d1-90b0-d7fd10b64d7a.png" height = "350" width="180">|<img src="https://user-images.githubusercontent.com/33795856/155390701-a8eee2b4-578e-4a8b-983d-c50398df6ef7.gif" height = "350" width="180">|<img src="https://user-images.githubusercontent.com/33795856/155392169-9ae6cd4c-fa61-475d-8676-c532378f71a7.png" height = "360" width="180">|<img src="https://user-images.githubusercontent.com/33795856/155392178-653b2934-61d7-49f2-8f86-4b41d9ca9eef.png" height = "360" width="180">|
  
</div>

## 📱 Features

###  Main

- Each function is accessible with the bottom menu button.

### Select Target Image

- Take a reference image through the camera.
- Get the reference image through the gallery.

### Shooting

- (Shooting in progress) Draw the actual bullet points on the reference image.
- (End of shooting) Analyzes the distance between bullets points and Provides the size of the shot group size.
- (End of shooting) Receive actual target image from Raspberry Pi via Bluetooth.

### History

- Show history of shooting

## 💡 Technology

>  *Out stystem don't use networks like LTE, Wi-Fi, so you can use it anytime.*
>  
- LoRa
- Bluetooth 
- Object Detection & Warping

## 🖇 Topology

<div align="center">

<img width="900" src="https://user-images.githubusercontent.com/33795856/155391286-df2a0cda-328d-46ac-8187-fc776f3abe0e.png">

</div>

## ⚙️ System Architecture (Network Ver.)

<div align="center">

<img width="900" src="https://user-images.githubusercontent.com/33795856/155391323-662472c9-48c0-4b94-bfa2-53e3f5c041ec.png">
  
</div>

## 👊 Development Goal

- Use minimum activity.
- Asynchronous treatment through threads.
- Reuse components.

## 📂 Project Folder
``` 
📂 ShotTraker
  ├─ model	
  │  ├─ bullet
  │  ├─ history
  │  ├─ reference
  ├─ dao
  │  ├─ bulletDao
  │  ├─ historyDao
  │  ├─ referenceDao  
  ├─ dto
  │  ├─ converters
  ├─ database
  │  ├─ bulletDatabase
  │  ├─ historyDatabase     
  │  ├─ referenceDatabase
  ├─ images
  ├─ activity
  │  ├─ connectBluetoothActivity
  │  ├─ showHistoryDetailActivity
  │  ├─ showShotDetailActivity     
  │  ├─ startShotActivity
  ├─ fragment
  │  ├─ historyFragment
  │  ├─ newShotFragment     
  │  ├─ selectReferenceImageFragment  
  │  ├─ setCameraFragment
```

## 🎞 Demo
