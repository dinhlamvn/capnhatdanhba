./gradlew assembleDebug
adb install -r "C:\AndroidStudioProjects\capnhatdanhba\Capnhatdanhba\app\build\outputs\apk\debug\app-debug.apk"
adb shell am start -a android.intent.action.MAIN android.lcd.vn.capnhatdanhba/android.vn.lcd.ui.main.MainActivity