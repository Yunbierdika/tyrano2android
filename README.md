# TyranoBuilder Game To Android APK

可用于把使用 TyranoBuilder 制作的游戏（如希尔薇TeachingFeeling）打包成安卓APK的模板项目，参考自 TyranoBuilder 官方打包安卓APK的教程：https://tyranobuilder.com/tutorials/more/exporting-for-android-devices

### 使用方法

1. 将游戏资源（以TeachingFeeling为例，资源文件夹为：“**TeachingFeeling\resources\app**” 目录下的 “**data**”、“**tyrano**” 两个**文件夹**以及 “**index.html**” **文件**）存放到 **_app/src/main/assets/_** 目录下。

2. 打开游戏资源文件的 “**index.html**” 文件，在 “`<head></head>`” 标签内部，或者说是 “`</head>`” 的前一行，**添加一行**：“`<script type="text/javascript" src="tyrano_player.js"></script>`” 然后保存。

3. 将 **_app/src/build.gradle.kts_** 中的 **applicationId = "game.YourGameName"** 改成你自己的游戏名称（英文）比如：**my.game**，用于显示路径包名；将 **_app/src/main/res/values/strings.xml_** 中的 **YourGameName** 改成你自己的游戏名称，用于显示 App 的名称。

4. 使用 Android Studio 打开项目，删除 **_app/src/main/res_** 目录下的 mipmap ，然后右键 **_app/src/main/res_** 点击 **“New -> Image Asset”** 添加你自己的游戏图标，图标的 **“Name”** 需要和 **_app/src/main/AndroidManifest.xml_** 中的 **“android:icon”**、**“android:roundIcon”** 一致。 最后编译即可。

### 特性

- 点击游戏中存档的保存按钮后，存档将直接保存到 **_Android/data/game.YourGameName/files/save/_** 目录下；不需要获取任何权限，非 ROOT 用户可以直接进入目录内对存档进行导入、导出操作；并且存档可以和 PC 版互通。

- 游戏出现错误时会把错误记录到 **_Android/data/game.YourGameName/files/log.txt_** 文件中。

### 致谢
- [TyranoBuilder](https://tyranobuilder.com/)

### 捐赠

如果你希望这个项目，欢迎通过爱发电给我打赏：https://afdian.com/a/yun3812528
