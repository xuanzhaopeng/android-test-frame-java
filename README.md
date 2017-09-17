# Android Test Framework Demo in Java
> A simple demo framework for testers to test Android native app in Ruby

### Development
> You need to have basic knowledge of Appium and its configuration

> It only works from Android 5.0, otherwise you need to switch to use appium-selendroid instead of appium-uiautomator2

Basic
```bash
#Homebrew
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
#Java 1.8
brew update
brew cask install java

# Install mvn by anyway you prefer...
....
```

Install Node.js & Ruby
```bash
#Install nvm
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.1/install.sh | bash
#
# Install node
nvm install 7.6.0
nvm install 0.10.33
nvm alias default 7.6.0
node -v
npm -v
```

Install Android SDK
> Easily you could use Android studio install the latest version of Android SDK with Platform/Build tools. 
As the bug of Appium(appium-adb), https://github.com/appium/appium/issues/7961, please change as what I described in the ticket after you install appium.

Install Appium(at least 1.6.4) on Mac OS X
```bash
cd ~
brew install carthage # For iOS test
npm install appium
npm install appium-doctor

#link command, please you use ~/ as nvm root
ln -s ~/node_modules/appium/build/lib/main.js /usr/local/bin/appium
ln -s ~/node_modules/appium-doctor/appium-doctor.js /usr/local/bin/appium-doctor
```

Run Test
> The default calculator app path is /tmp/app-debug.apk

> You DO NOT need to start your local appium server manually!
```bash
cd <project_root>
mvn clean install
cd android
mvn clean install test -DskipTests=false -Ddriver.appPath=<calculator app abusolute path>
```