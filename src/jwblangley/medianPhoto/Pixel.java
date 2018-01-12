package jwblangley.medianPhoto;

import java.awt.Color;

public class Pixel implements Comparable {

  private Color c;

  public Pixel(int rgb) {
    c = new Color(rgb);
  }

  public int getIntensity() {
    return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
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
