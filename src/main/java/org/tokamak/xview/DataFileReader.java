package org.tokamak.xview;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataFileReader {

    public DataFileReader() {
    }

    public DataFile load(File datafile){

        DataFile data = new DataFile();
        try {
            LittleEndianDataInputStream in = new LittleEndianDataInputStream(new FileInputStream(datafile));

            //read head
            byte[] sign = new byte[20];
            in.read(sign);
            data.setSign(new String(sign));
            data.setHeadLength(in.readInt());
            data.setDataLength(in.readInt());
            data.setDataForm(in.readByte());
            data.setDataType(in.readByte());
            data.setShotNumber(in.readInt());
            data.setShotDate(in.readInt());
            data.setShotTime(in.readInt());

            byte[] addon = new byte[in.readShort()];
            in.read(addon);
            data.setAddon(new String(addon));
            byte[] comment = new byte[in.readShort()];
            in.read(comment);
            data.setComment(new String(comment));

            switch (data.getDataForm()){
                case 0:{
                    readDataFormZero(in, data);
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

        return data;

    }

    private void readDataFormZero(LittleEndianDataInputStream in, DataFile data){

    }

}
