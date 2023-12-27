package com.develeap.thumbnailer;


import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@CommandLine.Command(name = "thumbnailer", footer = "Copyright(c) 2019",
        description = "Generate thumbnails from image files")
public class Main {
    @CommandLine.Option(names = { "-s", "--size" }, description = "Sets the width/height of biggest dimension in generated thumbnails. Given in pixels. Defaults to 50.")
    private int imageSize = 50;

    @CommandLine.Parameters(arity = "0..*", paramLabel = "FILE", description = "File(s) to process.")
    private File[] inputFiles;

    public static void main(String[] args) {
        try {
            Main app = CommandLine.populateCommand(new Main(), args);
            app.go();
        } catch ( Exception e) {
            System.err.println(e.getMessage());
            CommandLine.usage(new Main(), System.out);
            System.exit(1);
        }
    }

    private void go() {
        if (inputFiles == null) {
            CommandLine.usage(this, System.out);
            System.exit(1);
        }
        for (File f : inputFiles) {
            if (!f.exists()) {
                System.out.println(f.getName() + " does not exist.");
            } else if (!f.isFile()) {
                System.out.println(f.getName() + " is not a file.");
            } else {
                try {
                    BufferedImage image = ImageIO.read(f);
                    if (image == null) {
                        System.out.println(f.getName() + " - not an image.");
                    } else {
                        String fPath = f.getAbsolutePath();
                        String fFullName = f.getName();
                        String fName = fFullName.substring(0,fFullName.lastIndexOf('.'));
                        String tnName = fPath.substring(0,fPath.length()-fFullName.length())+"tn-"+fName+".png";
                        File thumbnail = new File(tnName);
                        createThumbnail(image,thumbnail);
                        System.out.println("Created thumbnail "+tnName);
                    }
                } catch (IOException ex) {
                    System.out.println(f.getName() + " could not be opened.");
                }
            }

        }
    }

    public void createThumbnail(BufferedImage sourceImage,  File thumbnail) {

        try {
            thumbnail.createNewFile();
            float width = sourceImage.getWidth();
            float height = sourceImage.getHeight();
            float scaledWidth=imageSize,scaledHeight=imageSize;
            int offsetx=0,offsety=0;

            if (width > height) {
                scaledWidth = (width / height) * (float) imageSize;
                scaledHeight = imageSize;
                offsetx = (int) ((scaledWidth - scaledHeight) / 2f);
                offsety = 0;
            } else if (width < height) {
                scaledWidth = imageSize;
                scaledHeight = (height / width) * (float) imageSize;
                offsetx = 0;
                offsety = (int) ((scaledHeight - scaledWidth) / 2f);
            }
            BufferedImage img = new BufferedImage((int) scaledWidth, (int) scaledHeight, sourceImage.getType());
            Image scaledImage = sourceImage.getScaledInstance((int) scaledWidth, (int) scaledHeight, Image.SCALE_SMOOTH);
            img.createGraphics().drawImage(scaledImage, 0, 0, null);

            BufferedImage img2;
            if (offsetx>0 || offsety>0) {
                img2 = img.getSubimage(offsetx, offsety, imageSize, imageSize);
            } else {
                img2 = img;
            }

            ImageIO.write(img2, "png", thumbnail);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
