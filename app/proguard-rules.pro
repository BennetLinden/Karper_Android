# Keeping Parse. For now, because parse will stop
-dontwarn com.parse.**

# Keep EventBus functioning: https://github.com/greenrobot/EventBus/issues/6
-keepclassmembers class ** {
    public void onEvent(**);
}
-keepclassmembers class ** {
    public void onEventMainThread(**);
}

