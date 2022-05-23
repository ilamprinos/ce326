/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author ybtis
 */
public class YUVImage {
    public static final String YUVMAGIC = ("YUV3");
    public int height ,width;
    public YUVPixel[][]image;
    
    public YUVImage(int width, int height){
        this.width = width;
        this.height = height;
        image = new YUVPixel[width][height];
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                image[j][i] = new YUVPixel((short)16,(short)128,(short)128); 
            }
        } 
    }
    
    public YUVImage(YUVImage copyImg){
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.image = copyImg.image;
    
    } 
    
    public YUVImage(RGBImage RGBImg){
        this.width = RGBImg.getWidth();
        this.height = RGBImg.getHeight();
        image = new YUVPixel[width][height];
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                image[j][i] = new YUVPixel(RGBImg.image[j][i]);
            }
        }
    }
    
    public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException{
        int i, j, check, value, Y, U, V, temp;
        
        try(Scanner myReader = new Scanner(file)) {
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            if(!file.canRead()){
                throw new FileNotFoundException();
            }
           
            if(!myReader.hasNext(YUVMAGIC)){
                throw new UnsupportedFileFormatException();
            }
            myReader.nextLine();
            for(i=0;i<2;i++){
                if(!myReader.hasNextInt()){
                    throw new UnsupportedFileFormatException();
                }
                switch (i) {
                    case 0:
                        width = myReader.nextInt();
                        break;
                    case 1:
                        height = myReader.nextInt();
                        break;
                }
            }
            image = new YUVPixel[width][height];
            
            for(i=0;i<height;i++){
                for(j=0;j<width;j++){
                    if(!myReader.hasNext()){
                       throw new UnsupportedFileFormatException();
                    }
                     else{
                        Y = myReader.nextInt();
                        U = myReader.nextInt();
                        V = myReader.nextInt();
                        
                        image[j][i] = new YUVPixel((short)Y,(short)U,(short)V);
                    }
                }
            }
            if(myReader.hasNext())
                throw new UnsupportedFileFormatException();

        } 
        finally{ 
        }
    
    }
    
    @Override
    public String toString(){
        StringBuilder yuvString = new StringBuilder();
        short Y,U,V;
        Integer y,u,v,i,j;
        
        yuvString.append(YUVMAGIC + "\n").append(width).append(" ").append(height).append("\n");
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                Y = image[j][i].getY();
                U = image[j][i].getU();
                V = image[j][i].getV();
                
                y = intValue(Y);
                u = intValue(U);
                v = intValue(V);
                
                yuvString.append(y.toString()).append(" ").append(u.toString()).append(" ").append(v.toString()).append("\n");
            }
        }
        
        return yuvString.toString();
    
    }
    
    public void toFile(java.io.File file){
        try (FileWriter writer = new FileWriter(file)){
            writer.write(toString(),0,toString().length()-1);
        } catch (IOException ex) {
            System.out.println("ce326.hw2.YUVImage.toFile()");
        }
    }
    
    public void equalize(){
        int i,j;
        Histogram covid_19 = new Histogram(this);
        
        covid_19.equalize();
        for(i=0;i<this.height;i++){
            for(j=0;j<this.width;j++){
                image[j][i].setY(covid_19.getEqualizedLuminocity(image[j][i].getY()));
            }
        }
        
    }
    private Integer intValue(short R) {
        int r = (int)(R);
        return r;
    }
}
