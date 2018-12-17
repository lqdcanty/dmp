package cn.evake.file.model;

/**
 *
 */
import java.io.Serializable;

/**
 * 
 * 文件上传返回信息包装类
 * @author Evance
 * @version $Id: FileResWrapper.java, v 0.1 2018年7月22日 下午5:40:33 Evance Exp $
 */
public class FileResWrapper implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private boolean           success;

    /**
     * 错误信息
     */
    private String            errorMsg;

    /**
     * 文件唯一标识
     */
    private String            fileUid;

    /**
     *文件URL
     *
     */
    private String            fileUrl;

    public FileResWrapper() {
        setSuccess(true);
    }

    public FileResWrapper(boolean success, String errorMsg, String fileUid, String fileUrl) {
        super();
        this.success = success;
        this.errorMsg = errorMsg;
        this.fileUid = fileUid;
        this.fileUrl = fileUrl;
    }

    public void setErrorCode(String errorInfo) {
        setSuccess(false);
        setError(errorInfo);
    }

    private void setError(String errorMsg) {
        setSuccess(false);
        this.errorMsg = errorMsg;
    }

    public String getErrorInfo() {

        return this.errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUid() {
        return fileUid;
    }

    public void setFileUid(String fileUid) {
        this.fileUid = fileUid;
    }

}