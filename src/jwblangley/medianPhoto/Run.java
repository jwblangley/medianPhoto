package jwblangley.medianPhoto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class Run {

  public static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

  public static void main(String[] args) {

    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    File workingDirectory;

    if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
      //return;
      workingDirectory = new File("testPhotos");
    } else {
      workingDirectory = fc.getSelectedFile();
    }
    ArrayList<BufferedImage> validImages = new ArrayList<BufferedImage>();
    for (File f : workingDirectory.listFiles()) {
      try {
        BufferedImage img = ImageIO.read(f);
        validImages.add(img);
      } catch (IOException e) {
        // Not a valid image
        continue;
      }
    }
    BufferedImage[] images = validImages.toArray(new BufferedImage[validImages.size()]);

    BufferedImage result = medianImage(images);

    saveImage(result);

  }

  private static void saveImage(BufferedImage img) {
    try {
      ImageIO.write(img, "jpg", unusedFile("out", "jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static File unusedFile(String filename, String fileExt) {
    int counter = 1;
    File tempFile;
    do {
      tempFile = new File(filename + counter + "." + fileExt);
      counter++;
    } while (tempFile.exists());
    return tempFile;
  }

  private static boolean allSameDimensions(BufferedImage[] imgs) {
    for (int i = 1; i < imgs.length; i++) {
      BufferedImage tempImg = imgs[i];
      if (tempImg.getHeight() != imgs[0].getHeight() || tempImg.getWidth() != imgs[0].getWidth()) {
        return false;
      }
    }
    return true;
  }

  private static Pixel medianPixel(Pixel[] pxs) {
    Arrays.sort(pxs);
    return pxs[pxs.length / 2];
  }

  private static BufferedImage medianImage(BufferedImage[] imgs) {
    assert allSameDimensions(imgs) : "Images are not all the same dimension";
    BufferedImage newImg = new BufferedImage(imgs[0].getWidth(),
        imgs[0].getHeight(),
        BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < imgs[0].getHeight(); y++) {
      for (int x = 0; x < imgs[0].getWidth(); x++) {
        Pixel[] pxs = new Pixel[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
          pxs[i] = new Pixel(imgs[i].getRGB(x, y));
        }
        Pixel tempPixel = medianPixel(pxs);
        newImg.setRGB(x, y, tempPixel.getColor().getRGB());
      }
    }
    return newImg;
  }


}
