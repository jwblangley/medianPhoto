package jwblangley.medianPhoto;

import java.awt.Color;

public class Pixel implements Comparable {

  private Color c;

  public Pixel(int rgb) {
    c = new Color(rgb);
  }

  public int getIntensity() {
    int min = Integer.min(c.getRed(), Integer.min(c.getGreen(), c.getBlue()));
    int max = Integer.max(c.getRed(), Integer.max(c.getGreen(), c.getBlue()));
    return (min + max) / 2;
  }

  public Color getColor() {
    return c;
  }


  @Override
  public int compareTo(Object o) throws ClassCastException {
    if (!(o instanceof Pixel)) {
      throw new ClassCastException("Can only compare Pixels to Pixels");
    }
    Pixel p = (Pixel) o;
    return this.getIntensity() - p.getIntensity();
  }
}
