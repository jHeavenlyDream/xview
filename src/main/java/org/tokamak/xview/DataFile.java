package org.tokamak.xview;

public class DataFile {

    private String sign;
    private int headLength;
    private int dataLength;
    private byte dataForm;
    private byte dataType;
    private int shotNumber;
    private int shotDate;
    private int shotTime;
    private String addon;
    private String comment;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getHeadLength() {
        return headLength;
    }

    public void setHeadLength(int headLength) {
        this.headLength = headLength;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public byte getDataForm() {
        return dataForm;
    }

    public void setDataForm(byte dataForm) {
        this.dataForm = dataForm;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public int getShotNumber() {
        return shotNumber;
    }

    public void setShotNumber(int shotNumber) {
        this.shotNumber = shotNumber;
    }

    public int getShotDate() {
        return shotDate;
    }

    public void setShotDate(int shotDate) {
        this.shotDate = shotDate;
    }

    public int getShotTime() {
        return shotTime;
    }

    public void setShotTime(int shotTime) {
        this.shotTime = shotTime;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
