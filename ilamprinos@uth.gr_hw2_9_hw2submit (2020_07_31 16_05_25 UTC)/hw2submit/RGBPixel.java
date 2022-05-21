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
public class RGBPixel {
    private int rgb;
    
    public RGBPixel(short red, short green, short blue){
        int r,g,b;
        
        r = red;
        r = r << 16;
        g = green;
        g = g << 8;
        b = blue;
        rgb = r + g + b;
    }
    
    public RGBPixel(RGBPixel pixel){
        rgb = pixel.rgb;
        
    }
    
    public RGBPixel(YUVPixel pixel){
        int Y,U,V,C,D,E,R,G,B;
        
        Y = intValue(pixel.getY());
        U = intValue(pixel.getU());
        V = intValue(pixel.getV());
                
        C = Y - 16;
        D = U - 128;
        E = V - 128;

        R = clip(( 298 * C           + 409 * E + 128) >> 8);
        G = clip(( 298 * C - 100 * D - 208 * E + 128) >> 8);
        B = clip(( 298 * C + 516 * D           + 128) >> 8);
        
        setRed((short)R);
        setGreen((short)G);
        setBlue((short)B);
    
    }
    
    private int clip(int val){
        if(val<0){
            return 0;
        }
        else if(val > 255){
            return 255;
        }
        return val;
    }
    
    public short getRed(){
        short r;
        int temp = rgb;
        
        temp = temp >>16; 
        r = (short)temp;
        
        return r;
    }
    
    public short getGreen(){ 
        short g;
        int temp = rgb;
        
        temp = temp & 65280;
        temp = temp >> 8;
        g = (short)temp;
        return g;
    }
    
    
    public short getBlue(){
        short b;
        int temp = rgb;
        
        temp = temp & 255;
        b = (short)temp;
        return b;
    }
    public void setRed(short red){
        int r;
        
        rgb = rgb & 65535;
        r = red;
        r = r << 16;
        
        rgb = rgb +r;
    }
    
    public void setGreen(short green){
         int g;
        
        rgb = rgb & 16711935;
        g = green;
        g = g << 8;
        
        rgb = rgb +g;
    }
    
    public void setBlue(short blue){
        int b;
        
        rgb = rgb & 16776960;
        b = blue;
        
        rgb = rgb +b; 
    }
    public int getRGB(){      
        return rgb;
    }
    
    public void setRGB(int value){
        int temp,greenMask,blueMask,r,g,b;
        
        greenMask = 65280;
        blueMask = 255;
        
        temp = value;
        r = (short)(temp >> 16);
        setRed((short)r);
        temp = value;
        temp = greenMask & temp;
        g = (short)(temp >> 8);
        setGreen((short)g);
        temp = value;
        temp = blueMask & temp;
        b = (short)temp;  
        setBlue((short)b);
        
    }
    
    public final void setRGB(short red, short green, short blue){
        setRed((short)red);
        setGreen((short)green);
        setBlue((short)blue);
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        str.append(getRed()).append(" ").append(getGreen()).append(" ").append(getBlue()).append(" ");
        
        return str.toString();
    }
    
    
    private Integer intValue(short R) {
       int r = (int)(R);
       return r;
    }
}
