package mcLauncher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;

public class McLauncherCore {
	//ȫ�ֱ���
	protected static ArrayList<String> ArrayJsonFilePath=new ArrayList<String>();
	protected static String tweakClass=null;
	protected static String assetIndex=null;
	protected static String nativesPath=null; 
	protected static String javawPath=null; 

	
	public static boolean checkLine(String line,String key){//���������Ƿ���ڹؼ��ʣ����ز���ֵ
		if(line.indexOf(key)==-1){
			return false;
		}else{
			return true;
		}
	}
	
	public static ArrayList<String> getFilePathByType(String path,String type){//����Ŀ¼�����������ض������ļ��ľ���·��
		ArrayList<String> ArrayJsonFilePath=new ArrayList<String>();
		try{
			File file=new File(path);
	        if(!file.isDirectory()){
	            System.out.println("���벻���ļ�Ŀ¼������·��");
	            return ArrayJsonFilePath;
	        }
	        else{
	            File[] directory = file.listFiles();
	            for(int i=0;i<directory.length;i++){
	                //�ж��ļ��б��еĶ����Ƿ�Ϊ�ļ��ж����������ִ�еݹ飬ֱ���Ѵ��ļ����������ض������ļ����Ϊֹ
	                if(directory[i].isDirectory()){
	                    //System.out.println(directory[i].getName()+"\tttdir");
	                	getFilePathByType(directory[i].getAbsolutePath(),type);
	                }
	                else{
	                    if(checkLine(directory[i].getName(),"."+type)==true){
	                    	ArrayJsonFilePath.add(directory[i].getAbsolutePath());
	                    }	
	                }
	            }
	            return ArrayJsonFilePath;
	        }
		}catch(Exception e){
			System.out.println("����Ŀ¼ʧ��");
			e.printStackTrace();
			return ArrayJsonFilePath;
		}
    }
	
	public static String getJavawPath(String path){//����Ŀ¼������javaw.exe�ļ��ľ���·��
		File file=new File(path);
        if(!file.isDirectory()){
        	System.out.println("���벻���ļ�Ŀ¼������·��");
	        return javawPath;
	    }else{
	        File[] directory = file.listFiles();
	        for(int i=0;i<directory.length;i++){
	        	if(directory[i].isDirectory()){
	        		getJavawPath(directory[i].getAbsolutePath());
	            }else{
	                if(directory[i].getName().equalsIgnoreCase("javaw.exe")){
	                	javawPath=directory[i].getAbsolutePath();
	                	return javawPath;
	                }	
	            }
	        }
	        return javawPath;
	      }
    }
	
	public static ArrayList<String> getJsonPath(String path){//����Ŀ¼������������Ч.json�ļ��ľ���·����!!!ArrayJsonFilePath��Ϊ�ֲ������޷����������ڵݹ�ʱ�������ɣ�
		try{
			String type=".json";
			File file=new File(path);
	        if(!file.isDirectory()){
	            System.out.println("���벻���ļ�Ŀ¼������·��");
	            return ArrayJsonFilePath;
	        }
	        else{
	            File[] directory = file.listFiles();
	            for(int i=0;i<directory.length;i++){
	                //�ж��ļ��б��еĶ����Ƿ�Ϊ�ļ��ж����������ִ�еݹ飬ֱ���Ѵ��ļ����������ض������ļ����Ϊֹ
	                if(directory[i].isDirectory()){
	                    //System.out.println(directory[i].getName()+"\tttdir");
	                	getJsonPath(directory[i].getAbsolutePath());
	                }
	                else{
	                    if(checkLine(directory[i].getName(),type)==true){//Ԥ������*.json�ļ�,ѡ�����з���Ҫ���
	                    	InputStreamReader read = new InputStreamReader(new FileInputStream(directory[i]),"GBK");//���ǵ������ʽ
	                        BufferedReader bufferedReader = new BufferedReader(read);
	                        String lineText = null;
	                        int j=0;
	                        while((lineText = bufferedReader.readLine()) != null&&j<10){//������10��
	                        	if(checkLine(lineText,"\"id\": ")==true){
		                    		ArrayJsonFilePath.add(directory[i].getAbsolutePath());
		                    		System.out.println("Find "+ArrayJsonFilePath.size()+" .json file");
		                    		read.close();
		                    	}	
	                        	j++;
	                        }
	                        read.close();	
	                    }	
	                }
	            }
	            return ArrayJsonFilePath; 
	        }
		}catch(IOException e){
			System.out.println("����Ŀ¼ʧ��");
			e.printStackTrace();
			return ArrayJsonFilePath;
		}
    }
	
	public static String getNativesPath(String path){//����Ŀ¼������natives�ļ��еľ���·��
		File file=new File(path);
        if(!file.isDirectory()){
        	System.out.println("���벻���ļ�Ŀ¼������·��");
	        return nativesPath;
	    }else{
	        File[] directory = file.listFiles();
	        for(int i=0;i<directory.length;i++){
	        	if(directory[i].isDirectory()){
	        		if(checkLine(directory[i].getName(),"natives")==true){
	        			nativesPath=directory[i].getAbsolutePath();
	        			return nativesPath;
	        		}
	        		getNativesPath(directory[i].getAbsolutePath());
	            }
	        }
	        return nativesPath;
	      }
    }
	
	public static String getPath(){//��ȡ��ǰjar�ļ�����Ŀ¼�ľ���·��
		java.net.URL url =McLauncher.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;  
        try {  
            filePath = URLDecoder.decode(url.getPath(), "utf-8");// ת��Ϊutf-8����  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        if (filePath.endsWith(".jar")) {// ��ִ��jar�����еĽ�������".jar"  
            // ��ȡ·���е�jar����  
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
        }     
        File file = new File(filePath);            
        filePath = file.getAbsolutePath();
        return filePath;
	}
	
	public static int lastButOneIndexOf(String line,char keyChar){//�����м������ҵ��ڶ����ؼ�������������
		int times=0;
		for(int i=line.length()-1;i>=0;i--){
			if(line.charAt(i)==keyChar){
				times++;
				if(times==2){
					return i;
				}
			}
		}
		return -1;
	}
	
	public static String rewriteLine(String line,String gamePath){//��������д��
		char thisChar;
		String lineRewrited=new String();
		String aIncluding="";
		ArrayList<String> includings=new ArrayList<String>();
		int indexOfIncludings=0;
		int lengthOfIncludings=0;
		int indexOfLine=0;
		if((indexOfLine=line.indexOf(": \""))>=0){
			indexOfLine=indexOfLine+3;
		}
		while((thisChar=line.charAt(indexOfLine))!='\"'){
			if( (thisChar!='.'&&thisChar!=':') || (thisChar=='.'&& indexOfLine>lastButOneIndexOf(line,':')) ) {//�ڵ����ڶ���ð��֮��ķֺŲ��Ͽ�
				aIncluding=aIncluding+thisChar;
			}else{
				includings.add(aIncluding);
				aIncluding="";
			}
			indexOfLine++;
		}
		includings.add(aIncluding);
		lineRewrited=gamePath+"\\.minecraft\\libraries";
		while(indexOfIncludings<(lengthOfIncludings=includings.size())){
			lineRewrited=lineRewrited+"\\"+includings.get(indexOfIncludings);	
			indexOfIncludings++;
		}		
		lineRewrited=lineRewrited+"\\"+includings.get(lengthOfIncludings-2)+"-"+includings.get(lengthOfIncludings-1)+".jar";
		return lineRewrited;
	}
	
	public static String getMainClass(ArrayList<String> arrayLine){//��ArrayList�л�ȡmainClass����
		String mainClass=null;
		String key="mainClass";
		int IndexOfArrayLine=0;
		while(IndexOfArrayLine<arrayLine.size()){
			String thisLine=arrayLine.get(IndexOfArrayLine);
			if(checkLine(thisLine,key)==true){
				mainClass=thisLine.substring(thisLine.indexOf(": \"")+3,thisLine.lastIndexOf("\""));
			}
			IndexOfArrayLine++;
		}
		return mainClass;
	}
	
	public static ArrayList<String> checkArray(ArrayList<String> arrayLine){//������ѡȡlibraries
		ArrayList<String> arrayLineChecked = new ArrayList<String>();
		String key0="\"name\"";//�����ؼ���
		String key1="--tweakClass";
		String key2="\"assets\":";
		int IndexOfArrayLine=0;
		while(IndexOfArrayLine<arrayLine.size()){
			String thisLine=arrayLine.get(IndexOfArrayLine);
			if(checkLine(thisLine,key1)==true){
				tweakClass=thisLine.substring(thisLine.indexOf("tweakClass")+11, thisLine.lastIndexOf("\","));
			}
			if(checkLine(thisLine,key2)==true){
				assetIndex=thisLine.substring(lastButOneIndexOf(thisLine,'\"')+1,thisLine.lastIndexOf("\""));
			}
			if(checkLine(thisLine,key0)==true&&thisLine.charAt(thisLine.lastIndexOf(":")+1)!=' '){
				arrayLineChecked.add(thisLine);
				System.out.println(thisLine);
			}
			IndexOfArrayLine++;
		}
		return arrayLineChecked;
	}
	
	public static ArrayList<String> rewriteArray(ArrayList<String> arrayLine,String gamePath){//��������дlibraries
		String thisLine=null;
		ArrayList<String> arrayLineRewrited = new ArrayList<String>();
		int IndexOfArrayLine=0;
		while(IndexOfArrayLine<arrayLine.size()){
			thisLine=arrayLine.get(IndexOfArrayLine);
			arrayLineRewrited.add(thisLine=rewriteLine(thisLine,gamePath));
			System.out.println(thisLine);
			IndexOfArrayLine++;
		}
		return arrayLineRewrited;
	}

	public static ArrayList<String> readFile(String filePath){//����Ϊ��λ��ȡ.json�ļ�,����ArrayList��
		ArrayList<String> arrayLine = new ArrayList<String> ();//����.json��libraries
		try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ 
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//���ǵ������ʽ
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                while((lineText = bufferedReader.readLine()) != null){
                    System.out.println(lineText);
                    arrayLine.add(lineText);
                }
                read.close();
                return arrayLine;
            }else{
            	System.out.println("�Ҳ���ָ����json�ļ�");
            	return arrayLine;
            }
		} catch (Exception e) {
			System.out.println("��ȡjson�ļ����ݳ���");
			e.printStackTrace();
			return arrayLine;
		}	
	}
	
	public static void writeFile(String jsonPath,ArrayList<String> arrayLine,String mainClass){//����������� ������д��.bat�ļ�
		String gamePath=jsonPath.substring(0, jsonPath.indexOf("\\.minecraft\\version"));
		String batchPath=gamePath+"\\start.bat";
		int indexOfArrayLine=0;
		try{
			File file=new File(batchPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("@echo off");
			bufferedWriter.newLine();
			bufferedWriter.write("if \"%1\" == \"h\" goto begin");
			bufferedWriter.newLine();
			bufferedWriter.write("mshta vbscript:createobject(\"wscript.shell\").run(\"%~nx0 h\",0)(window.close)&&exit");
			bufferedWriter.newLine();
			bufferedWriter.write(":begin");
			bufferedWriter.newLine();
			bufferedWriter.write("cd "+gamePath);
			bufferedWriter.newLine();
			bufferedWriter.write("\""+McLauncher.arguments[0]+"\""+" ");
			bufferedWriter.write("-Xmx"+McLauncher.arguments[1]+"m"+" ");
			if(McLauncher.arguments[4].length()>0){
				bufferedWriter.write(McLauncher.arguments[4]+" ");
			}
			bufferedWriter.write("-Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true"+" ");
			bufferedWriter.write("-Djava.library.path="+nativesPath+" ");
			bufferedWriter.write("-cp \"");
			while(indexOfArrayLine<arrayLine.size()){
				bufferedWriter.write(arrayLine.get(indexOfArrayLine)+";");
				indexOfArrayLine++;
			}
			String jarPath=jsonPath.substring(0, jsonPath.length()-5)+".jar";
			bufferedWriter.write(jarPath);
			bufferedWriter.write("\""+" ");
			bufferedWriter.write(mainClass+" ");
			bufferedWriter.write("--username "+McLauncher.arguments[2]+" ");
			bufferedWriter.write("--version "+McLauncher.arguments[3]+" ");//version
			bufferedWriter.write("--gameDir "+gamePath+"\\.minecraft"+" ");
			bufferedWriter.write("--assetsDir "+gamePath+"\\.minecraft\\assets"+" ");
			bufferedWriter.write("--assetIndex "+assetIndex+" ");//���ʰ汾
			bufferedWriter.write("--uuid ${auth_uuid} --accessToken ${auth_access_token} --userProperties {} --userType legacy"+" ");
			if(tweakClass!=null){
				bufferedWriter.write("--tweakClass "+tweakClass);
			}
			//bufferedWriter.newLine();
			//bufferedWriter.write("pause");
			//bufferedWriter.newLine();
			//bufferedWriter.write("del %0");
			bufferedWriter.newLine();
			bufferedWriter.write("exit");
			bufferedWriter.close();
			System.out.println("�����������ļ����"); 
		}catch(IOException e){
			System.out.println("�����������ļ�ʧ��");
		}	
	}

	public static void batchCreate(String jsonPath){//�������ȡѡ����json�ļ������˴�������д.bat�ļ�
		String gamePath=jsonPath.substring(0, jsonPath.indexOf("\\.minecraft\\version"));
		ArrayList<String> arrayLine = new ArrayList<String> ();//����.json���������ݣ�ÿ��Ϊһ��String����
		ArrayList<String> arrayLineChecked = new ArrayList<String> ();
		ArrayList<String> arrayLineRewrited = new ArrayList<String> ();//����ת�����library·��		
		arrayLine=readFile(jsonPath);
		arrayLineChecked=checkArray(arrayLine);//�ҳ��ؼ���
		arrayLineRewrited=rewriteArray(arrayLineChecked,gamePath);//��д
		writeFile(jsonPath,arrayLineRewrited,getMainClass(arrayLine));
	}
	
	public static void configCreate(String gamePath){//��ָ��Ŀ¼�´���.cfg�����ļ�
		try{
			File configFile=new File(gamePath+"\\config.cfg");
			if (!configFile.exists()) {
				configFile.createNewFile();
			}
			FileWriter writer = new FileWriter(configFile.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("java path:");
			bufferedWriter.newLine();
			bufferedWriter.write(McLauncher.arguments[0]);
			bufferedWriter.newLine();
			bufferedWriter.write("maximum memory:");
			bufferedWriter.newLine();
			bufferedWriter.write(McLauncher.arguments[1]);
			bufferedWriter.newLine();
			bufferedWriter.write("username:");
			bufferedWriter.newLine();
			bufferedWriter.write(McLauncher.arguments[2]);
			bufferedWriter.newLine();
			bufferedWriter.write("version:");
			bufferedWriter.newLine();
			bufferedWriter.write(McLauncher.arguments[3]);
			bufferedWriter.newLine();
			bufferedWriter.write("extra instructions:");
			bufferedWriter.newLine();
			bufferedWriter.write(McLauncher.arguments[4]);
			bufferedWriter.close();
			System.out.println("�������óɹ�");
		}catch(IOException e){
			System.out.println("����config�ļ�����");
			e.printStackTrace();
		}
	}
	
	public static String[] configRead(String gamePath){//�������ȡָ��·���µ�.cfg�����ļ�����
		String[] argumentsSaved=new String[5];
		try{
			File configFile=new File(gamePath+"\\config.cfg");
			if (!configFile.exists()) {
				System.out.println("δ�ҵ�����");
				return argumentsSaved=McLauncher.arguments;
			}else{
				InputStreamReader read = new InputStreamReader(new FileInputStream(configFile));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                int indexOfLine =1;
                while((lineText = bufferedReader.readLine()) != null){
                	switch(indexOfLine){
                	case 1:;break;
                	case 2: argumentsSaved[0]=lineText;break;	
                	case 3:;break;
                	case 4:	argumentsSaved[1]=lineText;break;
                	case 5:;break;
                	case 6: argumentsSaved[2]=lineText;break;	
                	case 7:;break;
                	case 8: argumentsSaved[3]=lineText;break;	
                	case 9:;break;
                	case 10: argumentsSaved[4]=lineText;break;	
                	default:;break;
                	}
                	indexOfLine++;
                }
                read.close();
                System.out.println("��ȡ���óɹ�");
			}
			return argumentsSaved;	
		}catch(IOException e){
			System.out.println("��ȡconfig�ļ�����");
			e.printStackTrace();
			return argumentsSaved=McLauncher.arguments;
		}		
	}
	
}		