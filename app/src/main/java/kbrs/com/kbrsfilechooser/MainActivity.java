package kbrs.com.kbrsfilechooser;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import kbrs.com.kfilechooser.KFileChooser;
import kbrs.com.kfilechooser.KFileFilter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showFileChooser(View view) {
        KFileChooser.getInstance()
                .setTitle("My File Chooser")
                .setRootDir(Environment.getExternalStorageDirectory())
                .setSelect(KFileFilter.MODE_SELECT_FILE)
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
    }
}
