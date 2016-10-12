package com.study.common.util;

        import org.apache.commons.io.FileUtils;
        import org.apache.commons.io.IOUtils;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.*;
        import java.util.zip.*;


/**
 * @author Nick create at 2011-3-9
 */
public class ZipUtil {

    public static byte[] deflate(byte[] content) {
        byte[] result = null;
        if (content != null) {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            DeflaterOutputStream compresser = new DeflaterOutputStream(temp);
            try {
                compresser.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(compresser);
            }
            result = temp.toByteArray();
        }
        return result;
    }

    public static byte[] inflate(InputStream input) {
        byte[] result = null;
        InflaterInputStream decompresser = null;
        try {
            decompresser = new ZipInputStream(input);
            result = IOUtils.toByteArray(decompresser);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(decompresser);
        }
        return result;
    }

    public static void zip(File src, File zip) throws IOException {
        zip(src, FileUtils.openOutputStream(zip));
    }

    public static void zip(File src, OutputStream out) throws IOException {
        zip(src, new ZipOutputStream(out));
    }

    public static void zip(File src, ZipOutputStream zout) throws IOException {
        try {
            doZip(src, zout, "");
        } finally {
            IOUtils.closeQuietly(zout);
        }
    }

    public static void unzip(File zip, File dest) throws IOException {
        unzip(FileUtils.openInputStream(zip), dest);
    }

    public static void unzip(InputStream in, File dest) throws IOException {
        unzip(new ZipInputStream(in), dest);
    }

    public static void unzip(ZipInputStream zin, File dest) throws IOException {
        try {
            doUnzip(zin, dest);
        } finally {
            IOUtils.closeQuietly(zin);
        }
    }

    private static void doZip(File src, ZipOutputStream zout, String ns)
            throws IOException {
        if (src.isFile()) {
            String entryName = ns + src.getName();
            zout.putNextEntry(new ZipEntry(entryName));
            fillZip(FileUtils.openInputStream(src), zout);
        } else {
            File[] files=src.listFiles();
            for (File file : files) {
                String entryName = ns + file.getName();

                if (file.isDirectory()) {
                    System.out.println ("polWatch zip dir  name:"+file.getPath()+entryName);
                    zout.putNextEntry(new ZipEntry(entryName));
                    doZip(file, zout, entryName);
                } else {
                    System.out.println ("polWatch zip file name:"+file.getPath()+entryName);
                    zout.putNextEntry(new ZipEntry(entryName));
                    fillZip(FileUtils.openInputStream(file), zout);
                }
            }
        }
    }

    private static void doUnzip(ZipInputStream zin, File dest)
            throws IOException {
        for (ZipEntry e; (e = zin.getNextEntry()) != null; zin.closeEntry()) {
            File file = new File(dest, e.getName());

            if (e.isDirectory()) {
                FileUtils.forceMkdir(file);
            } else {
                flushZip(zin, FileUtils.openOutputStream(file));
            }
        }
    }

    private static void fillZip(InputStream in, ZipOutputStream zout)
            throws IOException {
        try {
            IOUtils.copy(in, zout);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    private static void flushZip(ZipInputStream zin, OutputStream out)
            throws IOException {
        try {
            IOUtils.copy(zin, out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static boolean ectractFromZip(File file, String target) {
        boolean success = false;
        try {
            ZipInputStream zipSourceStream = new ZipInputStream(new FileInputStream(file));
            ZipOutputStream zipOutputStream =null;
            ZipEntry ze = null;
            byte[] ch = new byte[256];

            while ((ze = zipSourceStream.getNextEntry()) != null) {
                File targetFile = new File(target);
                File fpath = new File(targetFile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!targetFile.exists())
                        targetFile.mkdirs();
                    zipSourceStream.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    int i;
                    if(zipOutputStream==null){
                         zipOutputStream = new ZipOutputStream(new FileOutputStream(target));
                    }

                    ZipEntry zeOut = new ZipEntry(ze.getName());
                    zipOutputStream.putNextEntry(zeOut);
                    while ((i = zipSourceStream.read(ch)) != -1)
                        zipOutputStream.write(ch, 0, i);


                }
            }
            zipSourceStream.closeEntry();
            zipOutputStream.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();

            return success;
        }
        return success;
    }


    public static void main(String []args){

        try {
            ZipUtil.ectractFromZip(new File("C:\\sd2\\14801007_20150107134437_16.zip"),"C:\\sd2\\148.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
