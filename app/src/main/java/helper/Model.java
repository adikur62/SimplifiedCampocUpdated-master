package helper;

public class Model {

    private String Deskripsi, Foto, Lokasi, Detail;

    public Model(){}

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi (String Deskripsi) {
        this.Deskripsi = Deskripsi;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String Foto) {
        this.Foto = Foto;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String Lokasi) {
        this.Lokasi = Lokasi;
    }

}
