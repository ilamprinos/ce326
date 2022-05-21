package ce326.hw2;


import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
     authors:Kotsiaridis Konstantinos 2547,Lamprinos Isidoros
 */
public class PPMImage extends RGBImage{
    public static final String MAGICPPM = ("P3");
   
    
    public PPMImage(java.io.File file) throws UnsupportedFileFormatException,
            FileNotFoundException{
        //dhmioyrgia mias RGBImage me ton default kataskeuasth
        super(new RGBImage());
        
        int i, j, value, R, G, B, temp;
        
        
        try(Scanner myReader = new Scanner(file)) {
            //elegxos gia thn ypar3h kai thn 
            //ikanothta anagnwshs toy do8entos arxeiou
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            if(!file.canRead()){
                throw new FileNotFoundException();
            }
            
            //elegxos thn magic word P3 twn ppm eikonwn
            if(!myReader.hasNext(MAGICPPM)){
                throw new UnsupportedFileFormatException();
            }
            myReader.nextLine();
            //apo8hkeush meta apo elegxo twn basikwn xarakthristikwn thw eikonas
            //width,height,colordepth sta pedia ths klashs
            for(i=0;i<3;i++){
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
                    default:
                        colordepth = myReader.nextInt();
                        break;
                }
            }
            //arxikopoihsh tou pediou thw klashs image me to kaallhlo mege8os
            image = new RGBPixel[width][height];
            //gia ka8e pixel ths eikonas diabazoume 3 int(r,g,b) kai 
            //dhmiourgoume ena RGBPixel 
            //se periptwsh pou uparxoyn ligoteroi h perissoteroi apo 3*width*height
            //int sto arxeio tote den einai ppm arxeio kai dhmiourgeitai exeption
            for(i=0;i<height;i++){
                for(j=0;j<width;j++){
                    if(!myReader.hasNext()){
                      throw new UnsupportedFileFormatException();
                    }
                     else{
                        R = myReader.nextInt();
                        G = myReader.nextInt();
                        B = myReader.nextInt();
                        if((R<0 || R>colordepth)||(G<0 || G>colordepth)||
                                (B<0 || B >colordepth))
                            throw new UnsupportedFileFormatException();
                        image[j][i] = new RGBPixel((short)R,(short)G,(short)B);
                    }
                }
            }
            if(myReader.hasNext())
                throw new UnsupportedFileFormatException();
        } 
        finally{ 
        }
    }
    
    public PPMImage(RGBImage img){
        super(img);
        
        int i,j;
        short R, G, B;
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){         
                R = img.image[j][i].getRed();
                G = img.image[j][i].getGreen();
                B = img.image[j][i].getBlue();
                
                image[j][i] = new RGBPixel(R,G,B);
            }
        }
       
    }
    public PPMImage(YUVImage img){
        super(img);   
    }
    
    
    @Override
    public String toString(){
        Integer i,j,r,g,b;
        short R,G,B;
        StringBuilder ppmString = new StringBuilder();
        
        ppmString.append(MAGICPPM + "\n").append(width).append(" ").
                append(height).append("\n").append(colordepth).append("\n");
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                R = image[j][i].getRed();
                G = image[j][i].getGreen();
                B = image[j][i].getBlue();
                
                r = intValue(R);
                g = intValue(G);
                b = intValue(B);
                
                ppmString.append(r.toString()).append("\n").append(g.toString())
                        .append("\n").append(b.toString()).append("\n");
            }
        }
        
        return ppmString.toString();
        
    }
    public void toFile(java.io.File file){
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(toString(),0,toString().length()-1);
        } catch (IOException ex) {
            System.out.println("ce326.hw2.PPMImage.toFile()");
        }
    }

    private Integer intValue(short R) {
        int r = (int)(R);
        return r;
    }
    
}
