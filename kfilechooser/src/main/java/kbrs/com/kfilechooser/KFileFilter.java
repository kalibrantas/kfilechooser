package kbrs.com.kfilechooser;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Aan on 5/10/2018.
 */

public class KFileFilter implements FileFilter {

    public static final int MODE_SELECT_FILE = 1;
    public static final int MODE_SELECT_FOLDER = 2;
    public static final int MODE_SELECT_FOLDER_ONLY = 3;

    private String expression;
    private int select;

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean accept(File file) {
        if (select == MODE_SELECT_FOLDER_ONLY && !file.isDirectory()) {
            return false;
        }
        if (file.isDirectory()) {
            return true;
        }
        if (expression != null) {
            return file.getName().matches(expression);
        } else {
            return true;
        }
    }


}
