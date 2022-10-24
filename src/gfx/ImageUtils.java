package gfx;

import core.Size;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static final int ALPHA_OPAQUE = 1;
    public static final int ALPHA_BIT_MASKED = 2;
    public static final int ALPHA_BLEND = 3;

    public static Image loadImage(String filePath) {
        try {
            Image imageFromDisk = ImageIO.read(ImageUtils.class.getResource(filePath));
            BufferedImage compatibleImage = (BufferedImage) createCompatibleImage(new Size(
                            imageFromDisk.getWidth(null)
                            ,imageFromDisk.getHeight(null))
                    ,ALPHA_BIT_MASKED //toate imaginile vor fi bit masked -> pt performanta
            );
            Graphics2D graphics=compatibleImage.createGraphics();
            graphics.drawImage(imageFromDisk,0,0,null);

            graphics.dispose();
            return compatibleImage;

        } catch (IOException e) {
            System.out.println("Nu s-a putut incarca imaginea de pe disk " + filePath);
        }
        return null;
    }

    public static Image createCompatibleImage(Size size, int transparency){
        GraphicsConfiguration graphicsConfiguration =GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        return graphicsConfiguration.createCompatibleImage(size.getWidth(),size.getHeight(), transparency);//transparecny are moduri diferite (opac ,la noi: bitMasked (ieftin de rendat) = complet invizibil/complet vizibil, sau modul Blend (ex fantoma -> scump de rendat (se calculeaza pt orice pixel culoarea de fiecare data)))
    }
}
