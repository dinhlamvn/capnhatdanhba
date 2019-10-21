./gradlew assembleDebug
adb install -r "C:\AndroidStudioProjects\ContactUtilities\ContactUtilities\app\build\outputs\apk\debug\app-debug.apk"
adb shell am start -a android.intent.action.MAIN com.adomino.ddsdb/android.vn.lcd.ui.main.MainActivity