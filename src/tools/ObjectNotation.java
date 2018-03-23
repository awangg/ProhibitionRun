package tools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ObjectNotation extends HashMap<String, String> {

    private String[] keySet;
    public ObjectNotation(String file){
        String src = readFile("res/" + file, Charset.defaultCharset());
        buildMap(src);
    }

    static String readFile(String path, Charset encoding)//use Charset.defaultCharset();
    {
        try{
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }
        catch(IOException e){
            System.out.println("File not found");
            e.printStackTrace();
        }

        return null;
    }

    private void buildMap(String src){
        String[] lines = src.split("\n");
        keySet = new String[lines.length];
        int idx = 0;
        for(String line : lines){
            String[] split = line.split(":");
            put(split[0], split[1]);
            keySet[idx] = split[0];
            idx++;
        }
    }

    public String[] keys(){
        return keySet;
    }

}
