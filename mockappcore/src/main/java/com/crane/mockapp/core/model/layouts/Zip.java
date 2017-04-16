package com.crane.mockapp.core.model.layouts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by crane2002 on 2/22/2017.
 */
public class Zip implements Closeable {
    private static final int BUFFER = 2048;

    private ZipOutputStream out;
    private byte data[] = new byte[BUFFER];

    public Zip(File zipFile) throws IOException {
        out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
    }

    private void appendFile(File file, String prefix) throws IOException {
        try (BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file), BUFFER)) {
            ZipEntry entry = new ZipEntry(prefix + file.getName());
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        }
    }

    public void appendFolder(File folder) throws IOException {
        appendFolder(folder, null);
    }

    private void appendFolder(File folder, String prefix) throws IOException {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                appendFolder(file, prefix == null ? "" : prefix + folder.getName() + "/");
            else
                appendFile(file, prefix == null ? "" : prefix + folder.getName() + "/");
        }
    }

    @Override
    public void close() throws IOException {
        if (out != null)
            out.close();
    }
}
