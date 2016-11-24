package huawangxin.utils.imageUtil;

import java.io.File;
import java.io.IOException;

public class OcrTest {

 public static void main(String[] args) {
        String path = "C:\\jcaptcha (1).jpg";     
        System.out.println("ORC Test Begin......");
        try {     
            String valCode = new OCR().recognizeText(new File(path), "jpg");     
            System.out.println(valCode);     
        } catch (IOException e) {     
            e.printStackTrace();     
        } catch (Exception e) {  
            e.printStackTrace();  
        }       
        System.out.println("ORC Test End......");
        
        
       /*for (int i = 2; i < 83; i++) {
    	   String path2 = "C:\\"+i+".png";
    	   System.out.println("ORC Test Begin......"+i);
    	   try {     
               String valCode = new OCR().recognizeText(new File(path2), "png");     
               System.out.println(valCode);     
           } catch (IOException e) {     
               e.printStackTrace();     
           } catch (Exception e) {  
               e.printStackTrace();  
           }      
    	   System.out.println("ORC Test End......"+i);
    	   
	}*/
        
        
    }  

}



