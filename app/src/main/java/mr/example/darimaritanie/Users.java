package mr.example.darimaritanie;

public class Users {
    private int id ;
    private String fullname ;
    private String emailAddress ;
    private String phonNumber;
    private String password ;
    private String dob;
    private String bio ;

    public Users(int id, String fullname, String emailAddress, String phonNumber, String password, String dob, String bio) {
        this.id = id;
        this.fullname = fullname;
        this.emailAddress = emailAddress;
        this.phonNumber = phonNumber;
        this.password = password;
        this.dob = dob;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhonNumber() {
        return phonNumber;
    }

    public void setPhonNumber(String phonNumber) {
        this.phonNumber = phonNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
