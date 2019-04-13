# AutodeskTestApplication
Implemented techniques and principles for “News app”
1. Applictaion.
Architectural pattern:MVP
Language: Java
Libs:
 - glide library to load image from url.
 - retrofit library to retrieve json from News API
 - rx java libraries to organize asynchronous mechanism
 - espresso libraries to UI tests
 - kotlin library to use Kotlin language
 - android databinding to get an access to xml elements from java file.
Structure:
  Activity (list of articles) -> Fragment (webview with article)

2. Espresso Test
Architectural pattern: robots (Kotlin)
Robot-file is a screen checker, included methods for UI check. Robot doesn’tknow about structure of the screen, how manyfragments on them, is it activity or fragment. New screen - new robot.
3. Unit test
Presenter test.
