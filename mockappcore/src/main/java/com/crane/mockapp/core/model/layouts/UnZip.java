package com.crane.mockapp.core.model.layouts;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by crane2002 on 2/22/2017.
 */
public class UnZip {
    private static final int BUFFER = 2048;

    private ZipFile zipFile;
    private byte data[] = new byte[BUFFER];

    public UnZip(File zipFileName) throws IOException {
        this.zipFile = new ZipFile(zipFileName);
    }

    public void extract(File targetFolder) throws IOException {
        for (Enumeration e = zipFile.entries(); e.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            unzipEntry(zipFile, entry, targetFolder);
        }
    }

    private void unzipEntry(ZipFile zipfile, ZipEntry entry, File targetFolder) throws IOException {

        if (entry.isDirectory()) {
            File internalFolder = new File(targetFolder, entry.getName());
            if (!internalFolder.exists())
                if (!internalFolder.mkdirs())
                    throw new IOException("Error creating folder " + entry.getName());
            return;
        }

        File outputFile = new File(targetFolder, entry.getName());
        if (!outputFile.getParentFile().exists()) {
            if (!outputFile.getParentFile().mkdirs())
                throw new IOException("Error creating folder " + outputFile.getParentFile().getName());
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {

            int count;
            while ((count = inputStream.read(data, 0, BUFFER)) != -1) {
                outputStream.write(data, 0, count);
            }
        }
    }
}
