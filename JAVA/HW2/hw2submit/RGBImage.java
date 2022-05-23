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
public class RGBImage implements Image{
    public static final int MAX_COLORDEPTH = 255;
    int height,width,colordepth;
    RGBPixel [][] image;
    
    public RGBImage(int width, int height, int colordepth){
        this.height = height;
        this.width = width;
        this.colordepth = colordepth;
        image = new RGBPixel[width][height];
    }
    
    public RGBImage(RGBImage copyImg){
    
        height = copyImg.height;
        width = copyImg.width;
        colordepth = copyImg.colordepth;
        image = copyImg.image;
    }

   public RGBImage(){
       height = 0;
       width = 0;
       colordepth = 0;
       image = null;
   }
    
    public RGBImage(YUVImage YUVImg){
        this();
        int i,j;
        short val = 3;
        
        colordepth = MAX_COLORDEPTH;
        height = YUVImg.height;
        width = YUVImg.width;
        image = new RGBPixel[width][height];
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
            image[j][i]= new RGBPixel(YUVImg.image[j][i]);
            }
        }
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public RGBPixel getPixel(int row, int col){
        return image[col][row];
    }
      
    public void setPixel(int row, int col, RGBPixel pixel){
        image[col][row] = pixel;
    }
    
    @Override
    public void grayscale(){
        int i,j;
        double tempR,tempG,tempB;
        int gray, r,g,b;
        RGBPixel tempPixel;
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                tempR = 0.3 * getPixel(i, j).getRed();
                r = intValueD(tempR);
               //r = r << 16;
                tempG = 0.59 * getPixel(i, j).getGreen();
                g = intValueD(tempG);
                //g = g << 8;
                tempB = 0.11 * getPixel(i, j).getBlue();
                b = intValueD(tempB);
                gray = r + g + b;
                
                tempPixel = new RGBPixel((short)gray,(short)gray,(short)gray);
                setPixel(i,j,tempPixel);
                
            }
        }
    }
    
    @Override
    public void doublesize(){
        int newHeight,newWidth,i,j;
        RGBImage newImage;
        
        newHeight = 2*height;
        newWidth = 2*width;
        
        newImage = new RGBImage(newWidth,newHeight,MAX_COLORDEPTH);
        
        for(i=0;i<height;i++){
            for(j=0;j<width;j++){
                newImage.image[2*j][2*i] = new RGBPixel(getPixel(i, j));
                newImage.image[2*j+1][2*i+1] = new RGBPixel(getPixel(i, j));
                newImage.image[2*j][2*i+1] = new RGBPixel(getPixel(i, j));
                newImage.image[2*j+1][2*i] = new RGBPixel(getPixel(i, j));
            }
        }
        image = newImage.image;
        height = newImage.height;
        width = newImage.width;
        colordepth = newImage.colordepth;
    }
    
    @Override
    public void halfsize(){
        int newHeight,newWidth,i,j,avrR,avrG,avrB,temp,value;
        RGBImage newImage;
        int greenMask = 65280, blueMask = 255;
        
        newHeight = height/2;
        newWidth = width/2;
        
        newImage = new RGBImage(newWidth,newHeight,MAX_COLORDEPTH);
        
        for(i=0;i<newHeight;i++){
            for(j=0;j<newWidth;j++){
                
                value = getPixel(2*i, 2*j).getRGB();
                temp = value;
                avrR = (short)(temp >> 16);
                temp = value;
                temp = greenMask & temp;
                avrG = (short)(temp >> 8);
                temp = value;
                temp = blueMask & temp;
                avrB = (short)temp; 
                
                value = getPixel(2*i+1, 2*j+1).getRGB();
                temp = value;
                avrR = avrR +(short)(temp >> 16);
                temp = value;
                temp = greenMask & temp;
                avrG = avrG+(short)(temp >> 8);
                temp = value;
                temp = blueMask & temp;
                avrB = avrB+(short)temp;
                
                value = getPixel(2*i+1, 2*j).getRGB();
                temp = value;
                avrR = avrR +(short)(temp >> 16);
                temp = value;
                temp = greenMask & temp;
                avrG = avrG+(short)(temp >> 8);
                temp = value;
                temp = blueMask & temp;
                avrB = avrB+(short)temp;
                
                value = getPixel(2*i, 2*j+1).getRGB();
                temp = value;
                avrR = avrR +(short)(temp >> 16);
                temp = value;
                temp = greenMask & temp;
                avrG = avrG+(short)(temp >> 8);
                temp = value;
                temp = blueMask & temp;
                avrB = avrB+(short)temp;
               
                avrR = avrR/4;
                avrG = avrG/4;
                avrB = avrB/4;
                
                newImage.image[j][i]= new RGBPixel((short)avrR,(short)avrG,(short)avrB);
                
            }
        }
        image = newImage.image;
        height = newImage.height;
        width = newImage.width;
        colordepth = newImage.colordepth;
        
        
    }
    @Override
    public void rotateClockwise(){
        int i,j,temp ;
        RGBImage newImage;
        
        temp = height;
        height = width;
        width = temp;
        
        newImage = new RGBImage(width,height,MAX_COLORDEPTH);
        
        for(i=0; i<width; i++){
		for(j=0; j<height; j++){
			newImage.image[width-1-i][j]= image[j][i];
		}
	}
        image = newImage.image;
        colordepth = newImage.colordepth;
        
    }
    
   private int intValueD(double d) {
        int D;
        D = (int)d;
        return D;
    }
 
    
}
