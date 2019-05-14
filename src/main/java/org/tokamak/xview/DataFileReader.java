package org.tokamak.xview;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class DataFileReader {

    public DataFileReader() {
    }

    public void load(File datafile){
        try {
            LittleEndianDataInputStream in = new LittleEndianDataInputStream(new FileInputStream(datafile));

            //read head
            byte[] sign = new byte[20];
            in.read(sign);
            String version = new String(sign).substring(10, 16);
            int headLen = in.readInt();
            int dataLen = in.readInt();
            byte dataForm = in.readByte();
            byte dataType = in.readByte();
            int shotNumber = in.readInt();
            int shotDate = in.readInt();
            int shotTime = in.readInt();
            short addonLen = in.readShort();
            byte[] addon = new byte[addonLen];
            in.read(addon);
            short commentLen = in.readShort();
            byte[] comment = new byte[commentLen];
            in.read(comment);

            //read channels name
            String taddon = new String(addon);
            String[] channels = taddon.substring(49, taddon.length() - 11)
                    .replace("<name>", "")
                    .replace("</name>", "")
                    .split("</ch>");
            for (int i = 0; i < channels.length; i++)
                channels[i] = channels[i].substring(channels[i].indexOf(">") + 1);

            switch (dataForm){
                case 0:{
                    readDataFormZero(in);
                   break;
                }
                default:{
                    //TODO: заглушка для других пока не используемых форматов данных
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readDataFormZero(LittleEndianDataInputStream in){

    }

}
