package com.sportspartner.util;

import org.imgscalr.Scalr;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {

    private static String USERICONPATH = "./res/usericon/";
    private static String SPORTICONPATH = "./res/sporticon/";

    public String getImagePath(String spId, String object, String type){
        String motherPath = "";
        if(object.equals("USER"))
            motherPath = USERICONPATH;
        else if(object.equals("SPORT"))
            motherPath = SPORTICONPATH;
        return motherPath + spId + "_" + type + ".png";
    }

    /**
     * Read image from server.
     * @param imagePath The path on the server where the image is saved.
     * @return image
     */
    public BufferedImage getImage(String imagePath){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Read user icon from server.
     * @param userId The email of user
     * @param type 'origin' for original size, 'small' for reduced size
     * @return BufferedImage
     */
    public BufferedImage getUserImage(String userId, String type){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(USERICONPATH + userId + "_" + type + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    /**
     * Read sport icon from server.
     * @param sportId The UUID of sport
     * @param type 'origin' for original size, 'small' for reduced size
     * @return BufferedImage
     */
    public BufferedImage getSportImage(String sportId, String type){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(SPORTICONPATH + sportId + "_" + type + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    /**
     * Image padding and resizing.
     * @param img the original icon image that is needed to be reduced in size
     */
    public BufferedImage resizeImage(BufferedImage img){
        // image padding : add white space to turn the rectangular image to a square image
        int w = img.getWidth();
        int h = img.getHeight();
        int l = w<h?h:w;
        BufferedImage newImg = new BufferedImage(l,l,img.getType());
        Graphics g = newImg.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,l,l);
        if(w<h)
            g.drawImage(img, (h-w)/2, 0, null);
        else
            g.drawImage(img, 0, (w-h)/2, null);
        g.dispose();

        // resize the image to 100*100px
        BufferedImage scaledImage = Scalr.resize(img, 100);

        return scaledImage;

    }

    /**
     * Save an image to server.
     * @param image The image waiting to be saved.
     * @param imagePath The path where the image will be saved.
     * @return true or false for whether the image is saved successfully
     */
    public boolean saveImage(BufferedImage image, String imagePath){
        try {
            if (ImageIO.write(image, "png", new File(imagePath)))
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save user image in server.
     * @param img The image waiting to be saved.
     * @param userId The email of the user.
     * @param type 'origin' for original size, 'small' for reduced size
     * @return boolean for whether the image is saved successfully
     */
    public boolean saveUserImage(BufferedImage img, String userId, String type){
        try {
            if (ImageIO.write(img, "png", new File(USERICONPATH + userId + "_" + type + ".png")))
            {
                System.out.println("fdfdsaf");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save sport image in server.
     * @param img The image waiting to be saved.
     * @param sportId The UUID of the sport.
     * @param type 'origin' for original size, 'small' for reduced size
     * @return boolean for whether the image is saved successfully
     */
    public boolean saveSportImage(BufferedImage img, String sportId, String type){
        try {
            if(ImageIO.write(img, "png", new File(SPORTICONPATH + sportId + "_" + type + ".png"))){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Decode Base64 string to image.
     * @param base64String decoded base64 string
     * @return decoded image
     * @throws Exception Base64Decoder Exception
     */
    public BufferedImage Base64ToImage(String base64String) throws Exception{
        byte[] decodedBytes = new BASE64Decoder().decodeBuffer(base64String);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
        return image;
    }

    /**
     * Encode image to Base64 string.
     * @param image image waiting to be encoded
     * @return base64 string
     * @throws Exception Base64 Exception
     */
    public String ImageToBase64(BufferedImage image) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
        return base64String;
    }

}
