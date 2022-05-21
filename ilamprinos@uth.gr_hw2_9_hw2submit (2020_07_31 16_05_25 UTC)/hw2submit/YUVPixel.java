/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

     

/**
 *
 * @author ybtis
 */
public class YUVPixel {
    private int yuv;
    
    public YUVPixel(short Y, short U, short V){
        int y,u,v;
        
        y = intValue(Y);
        y = y << 16;
        u = intValue(U);
        u = u << 8;
        v = intValue(V);
        yuv = y + u + v;
    }
    public YUVPixel(YUVPixel pixel){
        yuv = pixel.yuv;
        
    }
    public YUVPixel(RGBPixel pixel){
        int R,G,B;
        int y,u,v;
        
        R = pixel.getRed();
        G = pixel.getGreen();
        B = pixel.getBlue();
        
        y = ( (  66 * R + 129 * G +  25 * B + 128) >> 8) +  16;
        u = ( ( -38 * R -  74 * G + 112 * B + 128) >> 8) + 128;
        v = ( ( 112 * R -  94 * G -  18 * B + 128) >> 8) + 128;
        
        y = y << 16;
        u = u << 8;
        yuv = y + u + v;
    }
    
    public short getY(){
        short y;
        int temp = yuv;
        
        temp = temp >> 16;
        y = (short)temp;
        return y;
    }
    
    public short getU(){
        short u;
        int temp = yuv;
        
        temp = temp & 65280;
        temp = temp >> 8;
        u = (short)temp;
        return u;
    }
    
    public short getV(){
        short v;
        int temp = yuv;
        
        temp = temp & 255;
        v = (short)temp;
        return v;
    }    
    
    public void setY(short Y){
        int y;
        
        yuv = yuv & 65535;
        y = Y;
        y = y << 16;
        
        yuv = yuv +y;
    }    
    
    public void setU(short U){
        int u;
        
        yuv = yuv & 16711935;
        u = U;
        u = u << 8;
        
        yuv = yuv +u;
    }      
    
    public void setV(short V){
        int v;
        
        yuv = yuv & 16776960;
        v = V;
        
        yuv = yuv +v; 
    }        
    
    private Integer intValue(short R) {
        int r = (int)(R);
        return r;
    }
}
