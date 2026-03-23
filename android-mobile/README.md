# Erdo Browser Mobile (Android APK)

Bu klasor Android Studio ile derlenebilir mobil uygulama projesidir.
Masaustu `PyQt5` kodu Android'e dogrudan APK olarak cikmadigi icin,
Android tarafi native `WebView` ile ayrica olusturuldu.

## Gereksinimler

- Android Studio (guncel)
- Android SDK 34
- JDK 17 (Android Studio ile gelir)

## APK nasil alinur

1. Android Studio ac
2. `android-mobile` klasorunu proje olarak ac
3. Gradle sync bitmesini bekle
4. Menu: `Build > Build Bundle(s) / APK(s) > Build APK(s)`
5. Cikan dosya:
   - `app/build/outputs/apk/debug/app-debug.apk`

## Android Studio yoksa (onerilen)

Bulutta APK derleme:

1. Projeyi GitHub'a yukle
2. GitHub'da `Actions` sekmesine gir
3. `Build Android APK` workflow'unu sec
4. `Run workflow` bas
5. Is bitince `Artifacts` altindan `ErdoBrowserMobile-debug-apk` indir
6. Zip icinden `app-debug.apk` dosyasini telefona atip kur

Release icin:

1. `Build > Generate Signed Bundle / APK`
2. `APK` sec
3. Keystore olustur veya mevcut keystore sec
4. `release` build al

## Siteye koyma

- APK dosyasini sunucuna yukle (ornek: `ErdoBrowserMobile-v1.0.apk`)
- Sitedeki indirme butonuna APK linkini ekle
- Ornek:
  - `<a href="ErdoBrowserMobile-v1.0.apk">Android APK indir</a>`
