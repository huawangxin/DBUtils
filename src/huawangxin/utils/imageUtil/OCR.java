package huawangxin.utils.imageUtil;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  
import org.jdesktop.swingx.util.OS;

  
public class OCR {  
    private final String LANG_OPTION = "-l";  //英文字母小写l，并非数字1  
    private final String EOL = System.getProperty("line.separator");  
    private String tessPath = "C://Program Files (x86)//Tesseract-OCR";  
    //private String tessPath = new File("tesseract").getAbsolutePath();  
      
    public String recognizeText(File imageFile,String imageFormat)throws Exception{  
        File tempImage = ImageIOHelper.createImage(imageFile,imageFormat);  
        File outputFile = new File(imageFile.getParentFile(),"output");  
        StringBuffer strB = new StringBuffer();  
        StringBuffer strC = new StringBuffer(); 
        List cmd = new ArrayList();  
        if(OS.isWindowsXP()){  
            cmd.add(tessPath+"//tesseract.exe"); 
            strC.append(tessPath+"//tesseract.exe");
        }else if(OS.isLinux()){  
            cmd.add("tesseract"); 
            strC.append("tesseract");
        }else{  
            cmd.add(tessPath+"//tesseract.exe"); 
            strC.append(tessPath+"//tesseract.exe");
        }  
        
        System.out.println(EOL);
        cmd.add("");
        strC.append("1");
        cmd.add(outputFile.getName());  
        strC.append(outputFile.getName());
        cmd.add(LANG_OPTION);  
        strC.append(LANG_OPTION);
        //cmd.add("chi_sim");  
        cmd.add("eng");  
        strC.append("chi_sim"); 
        ProcessBuilder pb = new ProcessBuilder();  
        pb.directory(imageFile.getParentFile());  
          System.out.println(tempImage.getName());
        cmd.set(1, tempImage.getName());  
        pb.command(cmd);  
        pb.redirectErrorStream(true);  
          
        System.out.println(strC.toString());
        
        Process process = pb.start();  
        //tesseract.exe 1.jpg 1 -l chi_sim  
        int w = process.waitFor();  
          
        //删除临时正在工作文件  
        tempImage.delete();  
          
        if(w==0){  
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"),"UTF-8"));  
              
            String str;  
            while((str = in.readLine())!=null){  
                strB.append(str).append(EOL);  
            }  
            in.close();  
        }else{  
            String msg;  
            switch(w){  
                case 1:  
                    msg = "Errors accessing files.There may be spaces in your image's filename.";  
                    break;  
                case 29:  
                    msg = "Cannot recongnize the image or its selected region.";  
                    break;  
                case 31:  
                    msg = "Unsupported image format.";  
                    break;  
                default:  
                    msg = "Errors occurred.";  
            }  
            tempImage.delete();  
            throw new RuntimeException(msg);  
        }  
        new File(outputFile.getAbsolutePath()+".txt").delete();  
        return strB.toString();  
    }  
}  