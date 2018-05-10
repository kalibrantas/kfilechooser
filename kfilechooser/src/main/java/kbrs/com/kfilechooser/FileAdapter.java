package kbrs.com.kfilechooser;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aan on 5/7/2018.
 */

public class FileAdapter extends ArrayAdapter<File> {
    Activity context;
    File[] files;
    File parentFile;
    boolean isSelected=true;
    int selectedPosition=-1;
    public View selectedView;

    public FileAdapter(@NonNull Activity context) {
        super(context, R.layout.item_file);
        this.context = context;
    }

    @Override
    public int getCount() {
        return files.length + 1;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public File getSelectedFile(){
        return this.getSelectedPosition()==-1?null:getItem(this.getSelectedPosition());
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setFiles(File[] files, File parentFile) {
        this.files = files;
        this.parentFile = parentFile;
        notifyDataSetChanged();
    }

    public View getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(View selectedView) {
        this.selectedView = selectedView;
    }

    @Nullable
    @Override
    public File getItem(int position) {
        if (position == 0) {
            return this.parentFile;
        } else {
            return files[position - 1];
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // reuse views
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_file, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fileName = convertView.findViewById(R.id.file_name);
            viewHolder.fileLastModified = convertView.findViewById(R.id.file_last_modifed);
            viewHolder.fileSize = convertView.findViewById(R.id.file_size);
            viewHolder.fileIcon = (ImageView) convertView
                    .findViewById(R.id.file_icon);
            convertView.setTag(viewHolder);
        }
        // fill data
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.fileLastModified.setText("");
        holder.fileSize.setText("");
        File file = getItem(position);
        if (position == 0) {
            holder.fileIcon.setImageBitmap(null);
            if (file != null)
                holder.fileName.setText("../" + file.getName());
            else
                holder.fileName.setText("/");
        } else {
            holder.fileName.setText(file.getName());
            SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            holder.fileLastModified.setText(ft.format(new Date(file.lastModified())));
            if (!file.isDirectory()) {
                holder.fileSize.setText(getFormatedFileSize(file.length()));
                holder.fileIcon.setImageResource(R.mipmap.k_file);
            } else {
                holder.fileSize.setText("");
                holder.fileIcon.setImageResource(R.mipmap.k_folder);
            }
        }
        if(isSelected){
            if(position==getSelectedPosition()){
                convertView.setBackgroundColor(Color.parseColor("#E0E0E0"));
                this.selectedView=convertView;
            }else{
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        return convertView;
    }

    private String getFormatedFileSize(long length) {
        String fileSize = "";
        if (length < 1024) {
            return length + " bytes";
        } else {
            length = length / 1024;
        }
        if (length < 1024) {
            fileSize = length + " Kb";
        } else if (length < 1048576) {
            fileSize = ((double) length / 1024) + " Mb";
        } else if (length < 1073741824) {
            fileSize = ((double) length / 1048576) + " Gb";
        }
        return fileSize;
    }


    static class ViewHolder {
        public TextView fileName;
        public TextView fileLastModified;
        public TextView fileSize;
        public ImageView fileIcon;
    }
}