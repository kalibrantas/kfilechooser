package kbrs.com.kfilechooser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Aan on 5/6/2018.
 */

public class KFileChooser extends DialogFragment implements ListView.OnItemClickListener, DialogInterface.OnClickListener {
    public static final String TAG = "K_FILE_CHOOSER";

    private ListView fileListView;
    private FileAdapter fileAdapter;
    private File rootDirectory;
    private File currentDirectory;
    private OnFileChooserResultListener onFileChooserResultListener;
    private String title = "File Chooser";
    private FileFilter filter;
    private int select;

    //    private
    public static KFileChooser getInstance() {
        return new KFileChooser();
    }

    public KFileChooser() {
        this.setSelect(KFileFilter.MODE_SELECT_FILE);
    }

    public KFileChooser setRootDir(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.currentDirectory = rootDirectory;
        return this;
    }

    public KFileChooser setRootDir(String rootDirectory) {
        return this.setRootDir(new File(rootDirectory));
    }

    public KFileChooser setOnFileChooserResultListener(OnFileChooserResultListener onFileChooserResultListener) {
        this.onFileChooserResultListener = onFileChooserResultListener;
        return this;
    }

    public void show(FragmentManager manager) {
        super.show(manager, TAG);
    }

    public String getTitle() {
        return title;
    }

    public KFileChooser setTitle(String title) {
        this.title = title;
        return this;
    }

    public KFileChooser setFilter(FileFilter filter) {
        this.filter = filter;
        return this;
    }

    public KFileChooser setFilter(String expression) {
        if (this.filter == null) {
            this.filter = new KFileFilter();
        }
        ((KFileFilter) this.filter).setExpression(expression);
        return this;
    }

    public KFileChooser setExtension(String extension) {
        return this.setFilter(".*\\." + extension);
    }

    public KFileChooser setSelect(int select) {
        this.select = select;
        if (this.select == KFileFilter.MODE_SELECT_FOLDER || this.select == KFileFilter.MODE_SELECT_FOLDER_ONLY) {
            if (this.filter == null)
                this.filter = new KFileFilter();
            ((KFileFilter) this.filter).setSelect(select);
        }
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_file_chooser, null);
        TextView title = view.findViewById(R.id.title);
        title.setText(getTitle());
        builder.setView(view);
        builder.setNegativeButton("Cancel", this);
        builder.setPositiveButton("Choose", this);
        // Create the AlertDialog object and return it
        fileListView = view.findViewById(R.id.file_lv);
        fileListView.setOnItemClickListener(this);
        setListFile(rootDirectory, true);
        return builder.create();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        File file = fileAdapter.getItem(position);
        if (file != null) {
            if (!file.isDirectory() && this.select == KFileFilter.MODE_SELECT_FILE) {
                if (this.fileAdapter.getSelectedView() != null) {
                    this.fileAdapter.getSelectedView().setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(Color.parseColor("#E0E0E0"));
                this.fileAdapter.setSelectedPosition(position);
                this.fileAdapter.setSelectedView(view);
            } else if (file.isDirectory()) {
                this.currentDirectory = file;
                setListFile(position != 0 ? file : file.getParentFile(), false);
            }
        }
    }


    public void setListFile(File currentFile, boolean isroot) {
        if (currentFile.isDirectory()) {
            this.fileAdapter = new FileAdapter(this.getActivity());
            fileAdapter.setFiles(getListFile(currentFile), isroot ? null : currentFile);
            fileListView.setAdapter(fileAdapter);
        }
    }

    private File[] getListFile(File file) {
        if (this.filter != null) {
            return file.listFiles(this.filter);
        } else {
            return file.listFiles();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
        if (this.onFileChooserResultListener != null) {
            if (id == DialogInterface.BUTTON_POSITIVE) {
                File resultFile = this.select == KFileFilter.MODE_SELECT_FILE ? this.fileAdapter.getSelectedFile() : this.currentDirectory;
                this.onFileChooserResultListener.onResult(resultFile);
            }
            if (id == DialogInterface.BUTTON_NEGATIVE)
                this.onFileChooserResultListener.onCancel(this.fileAdapter.getSelectedFile());
        }
    }

    public interface OnFileChooserResultListener {
        public void onResult(File fileResult);

        public void onCancel(File lastChoosed);
    }

}
