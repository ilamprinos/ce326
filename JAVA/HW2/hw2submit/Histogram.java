/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/*
    Klash gia dhmioyrgia kai e3isorrophsh istgrammatos yuv eikonas
    authors:Kotsiaridis Konstantinos 2547,Lamprinos Isidoros
 */
public class Histogram {
    int []histogramArray;       //arxiko istogramma yuv eikonas
    double [] pmf;              //pmf istogrammatos eikonas
    double [] cdf;              //cdf istogrammatos eikonas
    int [] equalizedHistogram;  //e3isorrophmeno istograma
    double totalPixel;          //sunolikos ari8mos pixel sthn eikona
    int height,width;           //height kai width eikonas
    
    public Histogram(YUVImage img){
        int i,j;
        histogramArray = new int[236];
        pmf = new double[236];
        cdf = new double[236];
        equalizedHistogram = new int[236];
        
        height=img.height;
        width=img.width;
        totalPixel = img.height * img.width;
        //gia ka8e pixel ths eikonas yuv painroyme thn timh y ths fwteinothtas
        //kai au3anoyme ton counter sthn sygkekrimenh 8esh toy pinaka histogramArray
        for(i=0;i<img.height;i++){
            for(j=0;j<img.width;j++){
                histogramArray[intValue(img.image[j][i].getY())-1]++;
            }
        }
        
    }
    
    @Override
    public String toString(){
        int i,j,thousands,hundrends,units;
        StringBuilder str = new StringBuilder();
        
        for(i=0;i<histogramArray.length;i++){
            thousands = histogramArray[i]/1000;
            hundrends = (histogramArray[i]-thousands*1000)/100;
            units = histogramArray[i]-thousands*1000-hundrends*100;
             str.append(i);
            for(j=0;j<thousands;j++){  
                str.append("#");
            }
            for(j=0;j<hundrends;j++){
                str.append("$");
            }
            for(j=0;j<units;j++){
                str.append("*");
            }
            str.append("\n");
        }
        return str.toString();
    }
    
    public void toFile(File file){
    
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(toString(),0,toString().length()-1);
        } catch (IOException ex) {
            System.out.println("ce326.hw2.Histogram.toFile()");
        }
    }
    public void equalize(){
        int i,j;
        //pmf[i] = plh8os pixel sthn i-sth 8esh tou pinaka 
        //HistogramArray /sunolikos ari8mos pixel sthn eikona
        for(i=0;i<pmf.length;i++){
            pmf[i]=histogramArray[i]/totalPixel;
        }
        //cdf[i] = timh ths pmf[i] + timh ths pmf[j],gia j<i
        for(i=0;i<cdf.length;i++){
            for(j=0;j<i;j++){
                cdf[i] = cdf[i]+pmf[j];
            }
        }
        //kanoikopoihsh ths cdf gia thn dhmiourgia tou equalizedHistogram
        for(i=0;i<equalizedHistogram.length;i++){
            equalizedHistogram[i] = intValueD((cdf[i]*235));  
        }
    }
    
    public short getEqualizedLuminocity(int luminocity){
        //epistrofh ths timhs tou eualizedHistogram sthn 8esh = luminocity
        return (short)(equalizedHistogram[luminocity]);
    }

     private Integer intValue(short R) {
        int r = (int)(R);
        return r;
    }

    private int intValueD(double d) {
        int D;
        D = (int)d;
        return D;
    }
    
}
