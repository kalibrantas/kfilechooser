# kfilechooser
Lightweight Android File Chooser

## Feature
* File and folder chooser mode
* File and folder name and extension filtering
* Easy to use

## Intall

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

* Dont forget to add uses permission in android manifest to read external storage

```xml
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
```

## Usage

* Minimal usage of this library

```java
KFileChooser.getInstance()
                .setRootDir(Environment.getExternalStorageDirectory())
                .setOnFileChooserResultListener(new KFileChooser.OnFileChooserResultListener() {
                    @Override
                    public void onResult(File fileResult) {
                        if(fileResult!=null){
                            //do your action here    
                        }
                    }

                    @Override
                    public void onCancel(File lastChoosed) {

                    }
                })
                .show(getSupportFragmentManager());
```

* In your onResult method dont forget to check if the result file is null or not. Null File instance return if nothing file is choosed.

* Full usage of this library:
```java
KFileChooser.getInstance()
                .setTitle("My File Chooser")
                .setRootDir(Environment.getExternalStorageDirectory())
                .setSelect(KFileFilter.MODE_SELECT_FILE)  
                /* KFileFilter.MODE_SELECT_FILE || KFileFilter.MODE_SELECT_FOLDER || KFileFilter.MODE_SELECT_FOLDER_ONLY */
                .setExtension("pdf") 
                /* .setExpression( yourRegularExpression ) */
                .setOnFileChooserResultListener(new KFileChooser.OnFileChooserResultListener() {
                    @Override
                    public void onResult(File fileResult) {
                        if(fileResult!=null){
                            //do your action here    
                        }
                    }

                    @Override
                    public void onCancel(File lastChoosed) {

                    }
                })
                .show(getSupportFragmentManager());
```

## Compatibility

This library using DialogFragment and this project minimum target in android 15 or later
