package tuan5_1.bai2;

public class IpInfo {
    private String thanhPho,QuocGia,ChauLuc;
    public IpInfo(){

    }

    public IpInfo(String thanhPho, String quocGia, String chauLuc) {
        this.thanhPho = thanhPho;
        QuocGia = quocGia;
        ChauLuc = chauLuc;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getQuocGia() {
        return QuocGia;
    }

    public void setQuocGia(String quocGia) {
        QuocGia = quocGia;
    }

    public String getChauLuc() {
        return ChauLuc;
    }

    public void setChauLuc(String chauLuc) {
        ChauLuc = chauLuc;
    }

    @Override
    public String toString() {
        return "IpInfo{" +
                "thanhPho='" + thanhPho + '\'' +
                ", QuocGia='" + QuocGia + '\'' +
                ", ChauLuc='" + ChauLuc + '\'' +
                '}';
    }
}
