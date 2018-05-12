# kfilechooser
Lightweight Android File Chooser

# Feature
* File and folder chooser mode
* File and folder name and extension filtering
* Easy to use

# Intall

In yout project build.gradle add this maven repository

```
repositories {
    ...
    maven {
        url  "https://dl.bintray.com/kalibrantas/android" 
    }
    ...
}
```

In your application build.gradle dependencies add this line, for grandle <3.0 :

```
compile 'kbrs.com.kfilechooser:kfilechooser:1.0'
```

for grandle >=3.0 :

```
implementation  'kbrs.com.kfilechooser:kfilechooser:1.0'
```

# Usage


```android

KFileChooser.getInstance()
                .setTitle("My File Chooser")
                .setRootDir(Environment.getExternalStorageDirectory())
                .setSelect(KFileFilter.MODE_SELECT_FILE)  /* KFileFilter.MODE_SELECT_FILE || KFileFilter.MODE_SELECT_FOLDER || KFileFilter.MODE_SELECT_FOLDER_ONLY */
                .setExtension("pdf")
                .setOnFileChooserResultListener(new KFileChooser.OnFileChooserResultListener() {
                    @Override
                    public void onResult(File fileResult) {
                        if(fileResult!=null)
                            Toast.makeText(MainActivity.this,fileResult.getAbsolutePath(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(File lastChoosed) {

                    }
                })
                .show(getSupportFragmentManager());

```
* In your onResult method dont forget to check if the result file is null or not. Null File instance return if nothing file is choosed.

# Compatibility

This library using DialogFragment and this project minimum target in android 15 or later
