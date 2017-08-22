package main.utils;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Images {

    public static byte[] getByteArrayFromFile(String imageLocation){

        /*ByteArrayOutputStream bos = null;
        try{
            File f = new File(imageLocation);
            FileInputStream fis = new FileInputStream(f);

            byte[] buffer = new byte[1024];

            bos = new ByteArrayOutputStream();

            for(int len; (len = fis.read(buffer)) != -1;){
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return bos != null ? bos.toByteArray() : null;

        */
     byte[]result=null;

         FileInputStream fileInStr=null;
      try{
             File imgFile=new File(imageLocation);
            fileInStr=new FileInputStream(imgFile);
             long imageSize=imgFile.length();

             if(imageSize>Integer.MAX_VALUE){
                return null; //imageistoolarge
                }

           if(imageSize>0){
                result=new byte[(int)imageSize];
               fileInStr.read(result);
                }
            }catch(Exception e){
            e.printStackTrace();
            }finally{
            try{
                fileInStr.close();
               }catch (Exception e){
               }
          }
     return result;
    }

    public static Image getImageFromByte(byte [] imageArray){
        return new Image(new ByteArrayInputStream(imageArray));
    }

    public static byte[] getByteArrayFromImage(Image image) {
        byte[] imageByte = null;

        return imageByte;
    }

    public static byte[] getImageByte(Mat matImage){
        Imgcodecs.imwrite(FileLocations.TEMP_IMAGE_LOCATION, matImage);
        return Images.getByteArrayFromFile(FileLocations.TEMP_IMAGE_LOCATION);
    }
}
