package com.efida.sports.dmp.service.dubbo.intergration;


import com.efida.file.vo.FileTransmissionVo;

public interface FileUploadFacadeClient {

    String uploadSourceFile(FileTransmissionVo vo);

}
