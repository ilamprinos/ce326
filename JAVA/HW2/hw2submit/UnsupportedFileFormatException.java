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
public class UnsupportedFileFormatException extends java.lang.Exception{
    
    public UnsupportedFileFormatException(){
        System.out.println("ce326.hw2.UnsupportedFileFormatException.<init>()");
    }
    public UnsupportedFileFormatException(String msg){
        System.out.println("ce326.hw2.UnsupportedFileFormatException.<init>("+msg+")");
    }

}
