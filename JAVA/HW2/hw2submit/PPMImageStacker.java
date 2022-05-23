/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author ybtis
 */
public class PPMImageStacker {
    LinkedList <PPMImage> imageList; //lista apo tis eikonew pou periexontai sto dir
    int height,width;           //height kai width eikonwn sthn lista
    RGBImage resultImage; //dhmiourgia mias RGB eikonas me thn xrhsh ths  stack
    
    public PPMImageStacker (java.io.File dir) throws 
            UnsupportedFileFormatException,FileNotFoundException{
        imageList = new LinkedList<>();//
        File files[]; //pinakas me arxeia pou einai ppm images
        
        try{
            if(!dir.exists()){
                System.out.println("[ERROR] Directory "+dir.getName()+
                        " does not exist!");
                throw new FileNotFoundException(); 
            }
            if(!dir.isDirectory()){
                System.out.println("[ERROR] Directory "+dir.getName()+
                        " is not a directory!");
                throw new UnsupportedFileFormatException();
            }
            files=dir.listFiles();
            //ka8e img einai ena arxeio ppm pou periexei mia fwtgrafia
            //me thn xrhsh toy kataskeuasth ths PPMimage dhmiourgoume thn lista
            //twn eikonwn ppm me tis opoies 8a doulecoume sthn stack
            for(File img : files){
                imageList.add(new PPMImage(img));
            }
            height = imageList.getFirst().height;
            width = imageList.getFirst().width;
        }
        finally{
        }
        //oles oi eikonew exoun tis idies diastaseis opote me bash tis
        //diastaseis ths prwths eikonas arxikopoioume thn RGBImage me tis swstes
        //diastaseis
        resultImage = new RGBImage(width,height,RGBImage.MAX_COLORDEPTH);
    }
    
    public void stack(){
        int i,j,red,green,blue,counter;
        ListIterator<PPMImage> it = imageList.listIterator();
        //gia ka8e pixel ths paragwmenhs eikonas diapername ola ta pixel tvn 
        //eikonwn sthn lsita mas kai pairnoyme ton meso or tvn timwn RGB  
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                red =0;
                green=0;
                blue=0;
                counter=0;
                while(it.hasNext()){
                    it.next();
                    red += imageList.get(counter).image[j][i].getRed();
                    green += imageList.get(counter).image[j][i].getGreen();
                    blue += imageList.get(counter).image[j][i].getBlue();
                    counter++;
                }
                red = red/counter;
                green = green/counter;
                blue = blue/counter;
                resultImage.image[j][i] = new RGBPixel((short)red,
                        (short)green,(short)blue);
                while(it.hasPrevious()){
                    it.previous();
                }
            }
        }
    
    }
    
    public PPMImage getStackedImage(){
        return new PPMImage(resultImage);
    }

  
    
}
