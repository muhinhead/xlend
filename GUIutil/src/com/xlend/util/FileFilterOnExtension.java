package com.xlend.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Nick Mukhin
 */
public class FileFilterOnExtension extends FileFilter {

    String extension;

    public FileFilterOnExtension(String extension) {
        super();
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        boolean ok = f.isDirectory()
                || f.getName().toLowerCase().endsWith(extension);
        return ok;
    }

    @Override
    public String getDescription() {
        return "*." + extension;
    }
}
