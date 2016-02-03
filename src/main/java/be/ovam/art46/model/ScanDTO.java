package be.ovam.art46.model;

public class ScanDTO {

    private Integer briefId;

    private String dmsFolder;

    private String fileName;

    private String dmsId;

    private String errorMsg;

    private Boolean status;
    private String userId;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getBriefId() {
        return briefId;
    }

    public void setBriefId(Integer briefId) {
        this.briefId = briefId;
    }

    public String getDmsFolder() {
        return dmsFolder;
    }

    public void setDmsFolder(String dmsFolder) {
        this.dmsFolder = dmsFolder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDmsId() {
        return dmsId;
    }

    public void setDmsId(String dmsId) {
        this.dmsId = dmsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ScanDTO [briefId=" + briefId + ", dmsFolder=" + dmsFolder + ", fileName=" + fileName + ", dmsId=" + dmsId + ", errorMsg=" + errorMsg
                + ", status=" + status + ", userId=" + userId + "]";
    }
    
    
    
}
