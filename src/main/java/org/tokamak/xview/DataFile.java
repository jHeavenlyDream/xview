package org.tokamak.xview;

import lombok.Getter;
import lombok.Setter;

public class DataFile {

    @Getter @Setter
    private String sign;
    @Getter @Setter
    private int headLength;
    @Getter @Setter
    private int dataLength;
    @Getter @Setter
    private byte dataForm;
    @Getter @Setter
    private byte dataType;
    @Getter @Setter
    private int shotNumber;
    @Getter @Setter
    private int shotDate;
    @Getter @Setter
    private int shotTime;
    @Getter @Setter
    private String addon;
    @Getter @Setter
    private String comment;
    @Getter @Setter
    private int columnNum;
    @Getter @Setter
    private short timeNum;
    @Getter @Setter
    private long timeBegin;
    @Getter @Setter
    private long timeEnd;
    @Getter @Setter
    private long timeDelta;

    public DataFile() {
    }

    public String[] listChannels(){
        String[] channels = addon.substring(49, addon.length()-11)
                .replace("<name>", "")
                .replace("</name>", "")
                .split("</ch>");
        for (int i = 0; i < channels.length; i++) {
            channels[i] = channels[i].substring(channels[i].indexOf(">")+1);
        }

        return channels;
    }

}